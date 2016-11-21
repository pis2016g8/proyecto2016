package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataAccessLayer.Manejador.ClientHandler;
import calc4fun.cliente.Utilities.MusicService;

/**
 * Activity que muestra el menu principal del juego, luego de logearse con facebook
 * y registrarse satisfactoriamente
 */
public class MainGo4Calcs extends AppCompatActivity implements View.OnClickListener,  NavigationView.OnNavigationItemSelectedListener{
    ImageButton play;
    ImageView title;
    boolean clicking = false;
    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_go4_calcs);
        progressBar = (FrameLayout) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Principal - Go4Calcs");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        title = (ImageView) findViewById(R.id.imageView2);
        play = (ImageButton) findViewById(R.id.PlayButton);
        play.setOnClickListener(this);
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);
        doBindService();
        runAnimations();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mensajes) {
            Intent actividadMessage = new Intent(this,MessageActivity.class);
            startActivity(actividadMessage);
        } else if (id == R.id.nav_perfil) {
            //new pedirPerfil(this).execute();
            Intent actividadProfile = new Intent(this,PerfilConEfectoActivity.class);
            startActivity(actividadProfile);
        } else if (id == R.id.nav_ranking) {
            Intent actividadRanking = new Intent(this,RankingActivity.class);
            startActivity(actividadRanking);

        } else if (id == R.id.nav_tutorial){
            //ClientController.getInstance().setTutorialActivo(false);
            Intent tutorial = new Intent(this, TutorialActivity.class);
            startActivity(tutorial);
        }
        else if (id == R.id.nav_logout) {
            ClientHandler manejador = new ClientHandler(MainGo4Calcs.this);
            SQLiteDatabase db = manejador.getWritableDatabase();
            db.delete(manejador.getTableName(), null, null);
            db.close();
            LoginManager.getInstance().logOut();
            Intent login = new Intent(this,LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(login);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Se implementa para evitar que al apretar la flecha de atras
     * del celular se finalize la activity
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }


    /**
     * Este método se implementa para controlar el apretado
     * del boton jugar y realizar las acciones correspondientes
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PlayButton:

                /*if (ClientController.getInstance().getTutorialActivo())
                {
                    Intent actividadTutorial = new Intent(this, TutorialActivity.class);
                    startActivity(actividadTutorial);
                    ClientController.getInstance().setTutorialActivo(false);
                    return;
                }*/
                if (!clicking) {
                    clicking = true;
                    progressBar.setVisibility(View.VISIBLE);
                    new PedirMundos(this).execute();
                }

                break;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        TextView texto_nick = (TextView)findViewById(R.id.texto_nick_usuario);
        texto_nick.setText(EstadoJugador.getInstance().getNick_jugador());
        return true;
    }


    /**
     * Task que se encarga de ejecutar en segundo plano los llamados
     * al ClientController para obtener los datos de mundos. Luego
     * resume (onPostExecute) lanzando una nueva activity para mostrar
     * los mundos, o indicando que hubo error en el pedid al Servidor
     */
    public class PedirMundos extends AsyncTask<Void, Void, Boolean>{

        AppCompatActivity activity;
        boolean finishActivity = true;

        public PedirMundos(AppCompatActivity activity)
        {
            this(activity, false);
        }

        public PedirMundos(AppCompatActivity activity, boolean finishActivity) {
            this.activity = activity;
            this.finishActivity = finishActivity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return ClientController.getInstance().obtenerMundos();
        }


        @Override
        protected void onPostExecute(Boolean ok) {
            if (ok){
                Intent actividadWorld = new Intent(activity, WorldsScreenSlide.class);
                startActivity(actividadWorld);
                if (finishActivity){
                    this.activity.finish();
                }
            }
            else
            {
                AlertDialog.Builder builder = ClientController.getInstance().armarMensaje(activity, "El servidor esta teniendo problemas","Disculpe");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            progressBar.setVisibility(View.GONE);
            clicking = false;
        }

    }

    /**
     * Se vuelve a reproducir la música
     */
    @Override
    protected void onRestart(){
        if (mServ != null) mServ.restartMusic();
        super.onRestart();
    }

    /**
     * Se desconecta del servicio, parando la música
     */
    @Override
    protected void onDestroy(){
        doUnbindService();
        super.onDestroy();
    }


    private LocalFadeInAnimationListener myFadeInAnimationListener = new LocalFadeInAnimationListener();
    private LocalFadeOutAnimationListener myFadeOutAnimationListener = new LocalFadeOutAnimationListener();


    /**
     * Crea las animaciones y las empieza por primera vez
     */
    private void runAnimations() {
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeIn.setAnimationListener( myFadeInAnimationListener );
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        fadeOut.setAnimationListener( myFadeOutAnimationListener );

        Animation translacion= AnimationUtils.loadAnimation(this, R.anim.star_text);

        title.startAnimation(translacion);
        launchInAnimation();
    }



    private Animation fadeIn = null;
    private Animation fadeOut = null;

    private void launchOutAnimation() {
        play.startAnimation(fadeOut);
    }

    /**
     * El método se encarga de empezar la animación del boton jugar de fading
     */
    private void launchInAnimation() {
        play.startAnimation(fadeIn);
    }


    private Runnable mLaunchFadeOutAnimation = new Runnable() {
        public void run() {
            launchOutAnimation();
        }
    };

    private Runnable mLaunchFadeInAnimation = new Runnable() {
        public void run() {
            launchInAnimation();
        }
    };

    /**
     *  Listener de fade in
     */
    private class LocalFadeInAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            play.post(mLaunchFadeOutAnimation);
        }
        public void onAnimationRepeat(Animation animation){
        }
        public void onAnimationStart(Animation animation) {
        }
    };

    /**
     * Listener de fade out
     */
    private class LocalFadeOutAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            play.post(mLaunchFadeInAnimation);
        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    };


    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        /**
         * Al conectarse le servicio lo guardo como referencia para luego detener
         * la música
         */
        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ.stopMusic();
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

}

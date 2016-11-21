package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataProblema;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    Button responder;
    String laRespuesta;
    EditText respuesta;
    ImageView correcto_incorrecto;
    ImageView mensaje_respuesta;
    Drawable cruz;
    Drawable tick;
    Drawable darth_vader;
    Drawable c3po;
    private FrameLayout progressBar;
    boolean explicacion = false;
    private static final int MENU_TUTORIAL = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        progressBar = (FrameLayout) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        mensaje_respuesta = (ImageView) findViewById(R.id.message_image);
        mensaje_respuesta.setVisibility(View.GONE);
        correcto_incorrecto = (ImageView) findViewById(R.id.tick_image);
        correcto_incorrecto.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pregunta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView contenido = (TextView) findViewById(R.id.ChallengeText);
        String contStr = "No hay contenido";
        int id_problema = EstadoJugador.getInstance().getId_problema_actual();
        DataProblema problema = EstadoJugador.getInstance().getProblema(id_problema);
        //TODO habilitar o deshabilitar boton explicacion segun dataproblema
        if (problema.isTut_activo()){
            explicacion = true;
        } else {
            explicacion = false;
        }
        contStr = problema.getDescripcion();
        contenido.setText(contStr);
        ImageView imagenProblema = (ImageView) findViewById(R.id.imagenProblema);
        Bitmap imagen = EstadoJugador.getInstance().getBitmapProblema(EstadoJugador.getInstance().getId_problema_actual());
        imagenProblema.setImageBitmap(imagen);

        responder = (Button) findViewById(R.id.AnswerButton);
        responder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.AnswerButton:
                progressBar.setVisibility(View.VISIBLE);
                respuesta = (EditText) findViewById(R.id.AnswerText);
                laRespuesta = respuesta.getText().toString();
                v.setEnabled(false);
                new responderPregunta(this).execute(new String[]{
                        laRespuesta
                });
            default:
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.tutorialmenu:
                if (explicacion){
                    Intent actividadTutorial = new Intent(QuestionActivity.this, ExplicacionActivity.class);
                    actividadTutorial.putExtra("HelpText",  EstadoJugador.getInstance().getProblema( EstadoJugador.getInstance().getId_problema_actual()).getAyuda());
                    startActivity(actividadTutorial);
                }
                return true;
            case R.id.helpmenu:
                Intent actividadAyuda = new Intent(this,SendMessageActivity.class);
                startActivity(actividadAyuda);
                return true;
            case R.id.reportmenu:
                Intent actividadReporte = new Intent(this,ReportActivity.class);
                startActivity(actividadReporte);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.question_menu, menu);
        if (!explicacion){
            MenuItem item = menu.findItem(R.id.tutorialmenu);
            item.setVisible(false);
        }
        return true;
    }




    public class responderPregunta extends AsyncTask<String,Void, Integer>{

        QuestionActivity activity;

        responderPregunta(QuestionActivity activity){ this.activity = activity; }

        @Override
        protected Integer doInBackground(String... params) {
            EstadoJugador estado = EstadoJugador.getInstance();
            return ClientController.getInstance().ResponderProblema(params[0],String.valueOf(estado.getId_problema_actual())).getExperiencia();
        }

        @Override
        protected void onPostExecute(Integer experiencia){
            progressBar.setVisibility(View.GONE);
            if (experiencia > 0){
                if(android.os.Build.VERSION.SDK_INT >= 21){
                    tick = getResources().getDrawable(R.drawable.tick, getTheme());
                    c3po = getResources().getDrawable(R.drawable.c3po, getTheme());
                } else {
                    tick = getResources().getDrawable(R.drawable.tick);
                    c3po = getResources().getDrawable(R.drawable.c3po);
                }
                correcto_incorrecto.setImageDrawable(tick);
                mensaje_respuesta.setImageDrawable(c3po);
                mensaje_respuesta.setVisibility(View.VISIBLE);
                correcto_incorrecto.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        mensaje_respuesta.setVisibility(View.GONE);
                        correcto_incorrecto.setVisibility(View.GONE);
                        new PedirPreguntaEnQuestion(activity).execute();
                    }
                }, 2000);
            }
            else{
                if(android.os.Build.VERSION.SDK_INT >= 21){
                    cruz = getResources().getDrawable(R.drawable.cross, getTheme());
                    darth_vader = getResources().getDrawable(R.drawable.darthvader, getTheme());
                } else {
                    cruz = getResources().getDrawable(R.drawable.cross);
                    darth_vader = getResources().getDrawable(R.drawable.darthvader);
                }
                correcto_incorrecto.setImageDrawable(cruz);
                mensaje_respuesta.setImageDrawable(darth_vader);
                correcto_incorrecto.setVisibility(View.VISIBLE);
                mensaje_respuesta.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        correcto_incorrecto.setVisibility(View.GONE);
                        mensaje_respuesta.setVisibility(View.GONE);
                    }
                }, 2000);
                if (!explicacion){
                    explicacion = true;
                    EstadoJugador.getInstance().habilitarTutorial();
                    Intent actividadTutorial = new Intent(QuestionActivity.this, ExplicacionActivity.class);
                    actividadTutorial.putExtra("HelpText",  EstadoJugador.getInstance().getProblema( EstadoJugador.getInstance().getId_problema_actual()).getAyuda());
                    startActivity(actividadTutorial);
                    invalidateOptionsMenu();
                }
            }
            activity.findViewById(R.id.AnswerButton).setEnabled(true);
            ((EditText) activity.findViewById(R.id.AnswerText)).setText("");
        }

    }




    public class PedirPreguntaEnQuestion extends AsyncTask<Void, Void, DataProblema>{

        QuestionActivity activity;

        public PedirPreguntaEnQuestion(QuestionActivity activity)
        {
            this.activity = activity;
        }

        @Override
        protected DataProblema doInBackground(Void... params) {
            DataProblema p = ClientController.getInstance().getSiguienteProblema();
            if (p!=null){
                ClientController.getInstance().getImagenProblema(p.getContenido(), p.getId_problema());
            }
            return p;
        }


        @Override
        protected void onPostExecute(DataProblema resultado) {
            correcto_incorrecto.setVisibility(View.GONE);
            if (resultado!=null){
                TextView contenido = (TextView) activity.findViewById(R.id.ChallengeText);
                contenido.setText(resultado.getDescripcion());
                ImageView imagenProblema = (ImageView) findViewById(R.id.imagenProblema);
                Bitmap imagen = EstadoJugador.getInstance().getBitmapProblema(EstadoJugador.getInstance().getId_problema_actual());
                imagenProblema.setImageBitmap(imagen);
                if (resultado.isTut_activo()){
                    explicacion = true;
                } else {
                    explicacion = false;
                }
                invalidateOptionsMenu();
            } else {
                AlertDialog.Builder builder = ClientController.getInstance().armarMensaje(QuestionActivity.this,"Has terminado el nivel!", "ENHORABUENA");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent actividad_niveles=null;
                        if (EstadoJugador.getInstance().todosNivelesCompletosMundoActual()){
                            EstadoJugador.getInstance().setMundoCompletado();
                            actividad_niveles = new Intent(QuestionActivity.this, WorldsScreenSlide.class);
                        }else{
                             actividad_niveles = new Intent(QuestionActivity.this, WorldLevelsActivity.class);
                        }

                        actividad_niveles.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(actividad_niveles);
                        QuestionActivity.this.finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
    }
}


package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataCardList;
import calc4fun.cliente.DataTypes.DataJugador;
import calc4fun.cliente.DataTypes.DataLogro;
import calc4fun.cliente.DataTypes.DataMundoNivel;
import calc4fun.cliente.DataTypes.DataStringWrapper;

/**
 * Activity que muestra el perfil del usuario usando el efecto al estilo
 * whatsup, en el cual al scrollear hacia abajo se agranda la toolbar
 * mostrando la foto del usuario y al scrollear hacia arriba se comprime
 */
public class PerfilConEfectoActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;

    CardAdapter adapter;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_con_efecto);
        imagen = (ImageView)findViewById(R.id.ImageProfileView);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        imagen = (ImageView) findViewById(R.id.ImageProfileView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        new ProfileUpdater(this).execute();
    }

    /**
     * Llamado al finalizar el task asíncrono que obtiene los datos
     * del jugador, se encarga de actualizar las vistas con los datos
     * correspondientes
     * @param jugador dataJugador recabado del servidor
     */
    public void updateProfile(DataJugador jugador) {
        collapsingToolbarLayout.setTitle(jugador.getNick());
        Bitmap foto = EstadoJugador.getInstance().getProfile_picture();
        if (foto != null) {
            imagen.setImageBitmap(foto);
        }

        Palette incopalette=Palette.from(foto).maximumColorCount(16).generate();
        int primaryColorInt=incopalette.getVibrantColor(0x000000);
        collapsingToolbarLayout.setContentScrimColor(primaryColorInt);


        ((TextView) findViewById(R.id.exp)).setText("Experiencia: " + String.valueOf(jugador.getExperiencia()));
        List<DataMundoNivel> lista_mundo_nivel = jugador.getMundosNiveles();
        List<DataLogro> lista_logros = jugador.getLogros();
        List<DataCardList> lista = new ArrayList<DataCardList>();
        for (DataMundoNivel i:lista_mundo_nivel){
            if(i.isMundo_completo()){
                lista.add(new DataStringWrapper("Mundo: "+ i.getMundo()+ "(Completado)"));
            }else {
                if(i.getNivel()>0){
                    lista.add(new DataStringWrapper("Mundo: " + i.getMundo()));
                    for(int j=0; j<i.getNivel(); j++){
                        lista.add(new DataStringWrapper("Nivel: " + j));
                    }
                }
            }
        }
        for(DataLogro i:lista_logros){
            lista.add(new DataStringWrapper("Logro: " + i.getDesc()));
        }
        adapter = new CardAdapter(PerfilConEfectoActivity.this.getApplicationContext(), lista);
        recyclerView.setAdapter(adapter);

    }


    /**
     *  Clase que dispara una task asíncrono pidiendo al controlador los datos de perfil
     *  del jugador
     */
    public class ProfileUpdater extends AsyncTask<Void, Void, DataJugador> {

        PerfilConEfectoActivity activity;

        public ProfileUpdater(PerfilConEfectoActivity activity)
        {
            this.activity = activity;
        }

        @Override
        protected DataJugador doInBackground(Void... params) {
            ClientController.getInstance().getFacebookProfilePicture();
            return ClientController.getInstance().getPerfil();
        }


        @Override
        protected void onPostExecute(DataJugador resultado) {
            if (activity!=null){
                if (resultado != null){
                    activity.updateProfile(resultado);
                } else {
                    AlertDialog.Builder builder =ClientController.getInstance().armarMensaje(PerfilConEfectoActivity.this, "El servidor esta teniendo problemas","Disculpe");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NavUtils.navigateUpFromSameTask(PerfilConEfectoActivity.this);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        }
    }

}

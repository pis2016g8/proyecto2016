package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataNivel;

/**
 * Actividad encargada de mostrar los niveles
 */
public class WorldLevelsActivity extends AppCompatActivity implements View.OnClickListener {

    private FlowLayout levelBlockContainer = null;
    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_levels);
        progressBar = (FrameLayout) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        levelBlockContainer = (FlowLayout) findViewById(R.id.level_block_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Niveles "+EstadoJugador.getInstance().getMundoActual().getNombre());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dataFromMenu = getIntent().getExtras();
        LoadLevels();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadLevels() {
        Button view;
        int nivel = 0;
        List<DataNivel> lista_niveles=EstadoJugador.getInstance().getNivelesMundoActual();
        for (DataNivel dt:lista_niveles) {
            view = (Button)((LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.level_block,levelBlockContainer, false );
            view.setText("" + dt.getNum_nivel());
            view.setTag(dt.getNum_nivel());
            view.setId(dt.getId_nivel());
            if (dt.isNivel_completo()){
                view.setEnabled(true);
                view.setBackground(getResources().getDrawable(R.drawable.complete_level_button_selector));
            }
            if (dt.isNivel_disponible()){
                view.setEnabled(true);
            }else {
                view.setEnabled(false);
            }

            levelBlockContainer.addView(view);
            view.setOnClickListener(this);
       }
    }


    @Override
    public void onClick(View v) {
        if (v instanceof Button){
            Button view = (Button) v;
            progressBar.setVisibility(View.VISIBLE);
            EstadoJugador.getInstance().setId_nivel_actual(v.getId());
            new pedirProblemas(WorldLevelsActivity.this).execute();
        }
    }

    public class pedirProblemas extends AsyncTask<Void, Void, Boolean> {

        AppCompatActivity activity;

        public pedirProblemas(AppCompatActivity activity) {this.activity = activity;}


        @Override
        protected Boolean doInBackground(Void... params) {
            return ClientController.getInstance().pedirProblemas();
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            if (resultado){
                Intent actividad_preguntas = new Intent(activity,QuestionSelectionActivity.class);
                startActivity(actividad_preguntas);
            } else {
                AlertDialog.Builder builder =ClientController.getInstance().armarMensaje(WorldLevelsActivity.this, "El servidor esta teniendo problemas","Disculpe");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(WorldLevelsActivity.this);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionMenu(Menu menu){
        return true;
    }
}

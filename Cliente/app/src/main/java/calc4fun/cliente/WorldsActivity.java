package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataProblema;

public class WorldsActivity extends AppCompatActivity implements View.OnClickListener {

    int id_mundo_clickeado;
    Button world;
    boolean mundo_clickeado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worlds);
        TextView nombre_mundo = (TextView) findViewById(R.id.world_text);
        if (EstadoJugador.getInstance().getMundoActual()!=null){
            nombre_mundo.setText(EstadoJugador.getInstance().getMundoActual().getNombre());
        } else {
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mundos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        world = (Button) findViewById(R.id.world);
        world.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.world:
                if (!mundo_clickeado){
                    mundo_clickeado = true;
                    id_mundo_clickeado=1; // TODO: esto es hardcoded para esta iteracion
                    EstadoJugador.getInstance().setId_mundo_actual(id_mundo_clickeado);
                    new WorldsActivity.PedirNiveles(this).execute();
                }
                break;
        }
    }

    public boolean onCreateOptionMenu(Menu menu){
        return true;
    }

    public class PedirNiveles extends AsyncTask<Void, Void, Boolean> {

        AppCompatActivity activity;

        public PedirNiveles(AppCompatActivity activity)
        {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return ClientController.getInstance().obtenerNiveles();
        }


        @Override
        protected void onPostExecute(Boolean ok) {
            if (ok){
                Intent actividad_niveles = new Intent(activity, WorldLevelsActivity.class);
                startActivity(actividad_niveles);
            } else {
                AlertDialog.Builder builder =ClientController.getInstance().armarMensaje(WorldsActivity.this, "El servidor esta teniendo problemas","Disculpe");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(WorldsActivity.this);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            mundo_clickeado = false;
        }

    }

}

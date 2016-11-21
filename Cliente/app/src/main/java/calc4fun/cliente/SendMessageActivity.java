package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataEstadoMensaje;

public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener {

    Button enviar,volverMain;
    int problem_id = -1;
    EditText mensaje;
    boolean respondo=false;
    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle dataFromParent =  getIntent().getExtras();
        if (dataFromParent != null){
            respondo=true;
            problem_id =dataFromParent.getInt("id_problema", -1);
        }else{
            problem_id= EstadoJugador.getInstance().getId_problema_actual();
        }
        setContentView(R.layout.activity_send_message);
        progressBar = (FrameLayout) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ayuda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enviar = (Button) findViewById(R.id.SendButton);
        volverMain = (Button) findViewById(R.id.CancelButton);
        mensaje = (EditText) findViewById(R.id.editMessageText);
        enviar.setOnClickListener(this);
        volverMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CancelButton:
                finish();
                break;
            case R.id.SendButton:
                if (!mensaje.getText().toString().isEmpty()){
                        progressBar.setVisibility(View.VISIBLE);
                        new EnviarMensaje(this).execute(mensaje.getText().toString());
                }
                break;
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
        }
        return super.onOptionsItemSelected(item);
    }


    public class EnviarMensaje extends AsyncTask<String,Void, DataEstadoMensaje> {

        SendMessageActivity activity;

        public EnviarMensaje(SendMessageActivity activity){ this.activity = activity; }

        @Override
        protected DataEstadoMensaje doInBackground(String... params) {
            if (!respondo){
                return ClientController.getInstance().enviarMensaje(params[0], String.valueOf(problem_id));
            }
            else{
                return ClientController.getInstance().responderMensaje(params[0]);
            }

        }

        @Override
        protected void onPostExecute(DataEstadoMensaje mens){
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);


            if (mens != null){
                builder.setTitle("Exito");
                builder.setMessage("El mensaje ha sido enviado");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
                builder.create();
                builder.show();
            }
            else {
                builder.setTitle("Error");
                builder.setMessage("Error de comunicación con el servidor, intente mas tarde");
                builder.setPositiveButton("OK", null);
                builder.create();
                builder.show();
            }


        }
    }





}

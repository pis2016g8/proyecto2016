package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataEstadoMensaje;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{


    EditText reporte;
    int problem_id = -1;
    Button enviarReporte,volverQuestion;
    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        progressBar = (FrameLayout) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        problem_id= EstadoJugador.getInstance().getId_problema_actual();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reportar problema");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enviarReporte = (Button) findViewById(R.id.SendReportButton);
        volverQuestion = (Button) findViewById(R.id.CancelReportButton);
        reporte = (EditText) findViewById(R.id.editReportText);
        enviarReporte.setOnClickListener(this);
        volverQuestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CancelReportButton:
                finish();
                break;
            case R.id.SendReportButton:
                if (!reporte.getText().toString().isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    new EnviarReporte(this).execute(reporte.getText().toString());
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

    public class EnviarReporte extends AsyncTask<String,Void, DataEstadoMensaje> {

        ReportActivity activity;

        public EnviarReporte(ReportActivity activity){ this.activity = activity; }

        @Override
        protected DataEstadoMensaje doInBackground(String... params) {
            return ClientController.getInstance().enviarReporte(params[0], String.valueOf(problem_id));

        }

        @Override
        protected void onPostExecute(DataEstadoMensaje mens){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            progressBar.setVisibility(View.GONE);

            if (mens != null){
                builder.setTitle("Exito");
                builder.setMessage("El reporte ha sido enviado");
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

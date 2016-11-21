package calc4fun.cliente;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.BussinesLayer.Controladores.ProblemChangedListener;
import calc4fun.cliente.DataTypes.DataProblema;

public class QuestionSelectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, ProblemChangedListener {

    ListView listaPreguntas;
    private List<DataProblema> problemas = null;
    boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lista Preguntas Nivel " + String.valueOf(EstadoJugador.getInstance().getNumeroNivelActual()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaPreguntas = (ListView) findViewById(R.id.questionListView);
        listaPreguntas.setOnItemClickListener(this);
        EstadoJugador.getInstance().RegisterProblemListener(this);
        (new QuestionUpdater(this)).execute();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //prevent posted actions
        finished = true;
        EstadoJugador.getInstance().DeRegisterProblemListener(this);
    }

    @Override
    public void onItemClick(AdapterView adapter, View v, int position, long id) {
        DataProblema problema = (DataProblema) adapter.getItemAtPosition(position);
        EstadoJugador.getInstance().setId_problema_actual(problema.getId_problema());
        new setearPregunta(QuestionSelectionActivity.this).execute();
    }

    protected void updateQuestions(List<DataProblema> problemas) {
        this.problemas = problemas;
        listaPreguntas.setAdapter(new QuestionListAdapter(this, problemas));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_item:
                DataProblema problema = (DataProblema) v.getTag();
                EstadoJugador.getInstance().setId_problema_actual(problema.getId_problema());
                new setearPregunta(QuestionSelectionActivity.this).execute();
        }
    }

    @Override
    public void OnProblemPassed(int problemId) {
        // como comparte dataproblema es solo resetear el adapter
        if (problemas != null)
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!QuestionSelectionActivity.this.finished)
                        QuestionSelectionActivity.this.listaPreguntas.setAdapter(new QuestionListAdapter(QuestionSelectionActivity.this, problemas));
                }
            });

    }

    public class QuestionListAdapter extends BaseAdapter {
        List<DataProblema> lista;
        Context context;

        QuestionListAdapter(Context context, List<DataProblema> lista) {
            this.lista = lista;
            this.context = context;
        }

        @Override
        public int getCount() {
            return lista.size();
        }

        @Override
        public Object getItem(int position) {
            return lista.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            // importante, ListView internamente recicla las listas que
            // se salen de la pantalla y las reutiliza para aquellas que entran a la misma
            // esto aumenta la velocidad en pos de no usar layoutInflater
            if (convertView == null) {
                // Create a new view into the list.
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.question_layout_item, parent, false);
            }
            DataProblema problema = lista.get(position);
            // Set data into the view.
            ImageView problemImage = (ImageView) rowView.findViewById(R.id.question_thumbnail);
            ImageView ticked = (ImageView)rowView.findViewById(R.id.tick_question);
            if (problema.getResuleto()){
                ticked.setVisibility(View.VISIBLE);
            }
            else
            {
                ticked.setVisibility(View.GONE);
            }
            TextView problemText = (TextView) rowView.findViewById(R.id.question_preview_text);
            problemText.setText(problema.getDescripcion());
            new ImageUpdater(problemImage).execute(problema.getContenido(), String.valueOf(problema.getId_problema())/*Le paso la url de dropbox o donde sea*/);


            return rowView;
        }
    }

    public class ImageUpdater extends AsyncTask<String,Void, Bitmap>{
        private final ImageView view;

        ImageUpdater(ImageView view){
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            int id_problema = Integer.valueOf(params[1]);
            return ClientController.getInstance().getImagenProblema(params[0], id_problema);
        }

        @Override
        protected void onPostExecute(Bitmap imageToUpdate){
            if(view != null && imageToUpdate != null)
                view.setImageBitmap(imageToUpdate);
        }
    }

    public class QuestionUpdater extends AsyncTask<Void, Void, List<DataProblema>> {

        QuestionSelectionActivity activity;

        public QuestionUpdater(QuestionSelectionActivity activity) {
            this.activity = activity;
        }

        @Override
        protected List<DataProblema> doInBackground(Void... params) {
            return EstadoJugador.getInstance().getProblemasNivelActual();

        }

        @Override
        protected void onPostExecute(List<DataProblema> resultado) {
            if (activity != null) {
                activity.updateQuestions(resultado);
            }
        }
    }

    public class setearPregunta extends AsyncTask<Void, Void, Boolean> {

        QuestionSelectionActivity activity;

        public setearPregunta(QuestionSelectionActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            DataProblema p = EstadoJugador.getInstance().getProblema(EstadoJugador.getInstance().getId_problema_actual());
            Bitmap bitmap = ClientController.getInstance().getImagenProblema(p.getContenido(), p.getId_problema());
            if (bitmap!=null){
                return true;
            } else {
                return false;
            }
        }


        @Override
        protected void onPostExecute(Boolean resultado) {
            if (resultado){
                Intent intent = new Intent(QuestionSelectionActivity.this, QuestionActivity.class);
                startActivity(intent);
            } else {
                //TODO mensaje de error
            }
        }
    }
}

package calc4fun.cliente;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataListaRanking;
import calc4fun.cliente.DataTypes.DataPuntosJugador;

public class RankingActivity extends AppCompatActivity {

    static HashMap<Integer,Bitmap> resizedImages= new HashMap<>();

    public Bitmap getResizedImage(int resourceId){
        if (resizedImages.containsKey(resourceId)){
            return resizedImages.get(resourceId);
        }
        Bitmap bm = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), resourceId), 100, 100, true);
        resizedImages.put(resourceId, bm);
        return bm;
    }

    ListView listaRanking;
    ImageView recuadro;
    EstadoJugador estado;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ranking - Go4Calcs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        estado = EstadoJugador.getInstance();

        listaRanking = (ListView) findViewById(R.id.listView);
        listaRanking.setAdapter(new RankingListAdapter(this, new DataListaRanking()));
        new RankingUpdater(this).execute();
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

    public void updateRanking(DataListaRanking lista){
        listaRanking.setAdapter( new RankingListAdapter(this, lista));
    }

    public class RankingListAdapter extends BaseAdapter {
        DataListaRanking lista;
        Context context;



        RankingListAdapter(Context context , DataListaRanking lista){
            this.lista = lista;
            this.context=context;
        }

        @Override
        public int getCount(){
            return lista.getListaPuntos().size();
        }

        @Override
        public Object getItem(int position){
            return lista.getListaPuntos().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (convertView == null) {
                // Create a new view into the list.
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.ranking_layout_item, parent, false);
            }


            // Set data into the view.
            TextView nickTextView = (TextView) rowView.findViewById(R.id.nick);
            TextView puntosTextView = (TextView) rowView.findViewById(R.id.puntos);

            recuadro = (ImageView) rowView.findViewById(R.id.imageRank);
            if (position == 0) {
                recuadro.setImageBitmap(getResizedImage(R.drawable.casco1));
                DataPuntosJugador item = this.lista.getListaPuntos().get(position);
                if (item.getNick().equals(EstadoJugador.getInstance().getNick_jugador())){
                    rowView.setBackgroundResource(R.drawable.ranking_accent);
                }
                else rowView.setBackground(null);
                nickTextView.setText(item.getNick());
                nickTextView.setTextColor(getResources().getColor(R.color.Oro));
                puntosTextView.setText(String.valueOf(item.getPuntos()));
                puntosTextView.setTextColor(getResources().getColor(R.color.Oro));
            }else if (position == 1) {
                recuadro.setImageBitmap(getResizedImage(R.drawable.casco2));
                DataPuntosJugador item = this.lista.getListaPuntos().get(position);
                if (item.getNick().equals(EstadoJugador.getInstance().getNick_jugador())){
                    rowView.setBackgroundResource(R.drawable.ranking_accent);
                }
                else rowView.setBackground(null);
                nickTextView.setText(item.getNick());
                nickTextView.setTextColor(getResources().getColor(R.color.Plata));
                puntosTextView.setText(String.valueOf(item.getPuntos()));
                puntosTextView.setTextColor(getResources().getColor(R.color.Plata));
            }else if(position == 2){
                recuadro.setImageBitmap(getResizedImage(R.drawable.casco6));
                DataPuntosJugador item = this.lista.getListaPuntos().get(position);
                if (item.getNick().equals(EstadoJugador.getInstance().getNick_jugador())){
                    rowView.setBackgroundResource(R.drawable.ranking_accent);
                }
                else rowView.setBackground(null);

                nickTextView.setText(item.getNick());
                nickTextView.setTextColor(getResources().getColor(R.color.Bronce));
                puntosTextView.setText(String.valueOf(item.getPuntos()));
                puntosTextView.setTextColor(getResources().getColor(R.color.Bronce));
            }else {
                recuadro.setImageBitmap(getResizedImage(R.drawable.casco4));
                DataPuntosJugador item = this.lista.getListaPuntos().get(position);
                if (item.getNick().equals(EstadoJugador.getInstance().getNick_jugador())){
                    rowView.setBackgroundResource(R.drawable.ranking_accent);
                }
                else rowView.setBackground(null);

                nickTextView.setText(item.getNick());
                puntosTextView.setText(String.valueOf(item.getPuntos()));
            }
            return rowView;
        }


    }

    public class RankingUpdater extends AsyncTask<Void, Void, DataListaRanking> {

        RankingActivity activity;

        public RankingUpdater(RankingActivity activity)
        {
            this.activity = activity;
        }

        @Override
        protected DataListaRanking doInBackground(Void... params) {
            return ClientController.getInstance().VerRanking();
        }


        @Override
        protected void onPostExecute(DataListaRanking resultado) {
            if (activity!=null){
                if (resultado!=null) {
                    activity.updateRanking(resultado);
                } else {
                    AlertDialog.Builder builder =ClientController.getInstance().armarMensaje(RankingActivity.this, "El servidor esta teniendo problemas","Disculpe");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NavUtils.navigateUpFromSameTask(RankingActivity.this);
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

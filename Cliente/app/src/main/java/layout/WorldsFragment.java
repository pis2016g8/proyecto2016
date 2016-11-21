package layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.BussinesLayer.Controladores.WorldChangedListener;
import calc4fun.cliente.R;
import calc4fun.cliente.WorldLevelsActivity;
import calc4fun.cliente.WorldsScreenSlide;

public class WorldsFragment extends Fragment implements WorldChangedListener{
    Bitmap imagen_mundo;
    String nombre_mundo;
    ImageButton left;
    ImageButton right;
    int id_mundo_clickeado;
    int position = -1;
    boolean isLastPosition;
    private boolean toDestroy = false;
    private FrameLayout progressBar;


    /**
     * Guarda las imagenes redimensionadas
     */
    static HashMap<Integer,Bitmap> resizedImages= new HashMap<>();

    public Bitmap getResizedImage(int resourceId){
        if (resizedImages.containsKey(resourceId)){
            return resizedImages.get(resourceId);
        }
        Bitmap bm = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), resourceId), 100, 100, true);
        resizedImages.put(resourceId, bm);
        return bm;
    }

    public static WorldsFragment create(Bitmap img, String nombre_mundo , int id_mundo, boolean passed, int position, boolean isLastPosition) {
        WorldsFragment fragment = new WorldsFragment();
        Bundle args = new Bundle();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (img!=null){
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            args.putByteArray("imagen_mundo", byteArray);
        }
        args.putString("nombre_mundo", nombre_mundo);
        args.putInt("id_mundo_clickeado",id_mundo);
        args.putInt("slider_position",position);
        args.putBoolean("last_position", isLastPosition);
        args.putBoolean("passed", passed);
        fragment.setArguments(args);
        return fragment;
    }

    public WorldsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getByteArray("imagen_mundo")!=null){
            imagen_mundo = BitmapFactory.decodeByteArray(getArguments().getByteArray("imagen_mundo"),0, getArguments().getByteArray("imagen_mundo").length);
        }
        nombre_mundo = getArguments().getString("nombre_mundo");
        id_mundo_clickeado = getArguments().getInt("id_mundo_clickeado");
        isLastPosition = getArguments().getBoolean("last_position");
        position = getArguments().getInt("slider_position");

    }

    ImageView checkMark = null;
    boolean clicked = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_worlds, container, false);
        left = (ImageButton)rootView.findViewById(R.id.flecha_izquierda);
        right = (ImageButton)rootView.findViewById(R.id.flecha_derecha);
        progressBar = (FrameLayout) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        left.setTag(position - 1);
        right.setTag(position + 1);
        //segun la posición del fragment y la cantidad de mundos
        //se ponen o no  visibles las flechas que indican si hay
        //mundo siguiente o anterior
        if (position == 0){
            left.setVisibility(View.GONE);
        }
        else {
            left.setVisibility(View.VISIBLE);
            left.setOnClickListener((WorldsScreenSlide)getActivity());
        }
        if (isLastPosition){
            right.setVisibility(View.GONE);
        }
        else{
            right.setVisibility(View.VISIBLE);
            right.setOnClickListener((WorldsScreenSlide)getActivity());
        }
        Button mundo = (Button) rootView.findViewById(R.id.world);
        checkMark = (ImageView) rootView.findViewById(R.id.check_mark);
        if (getArguments().getBoolean("passed")){
            checkMark.setVisibility(View.VISIBLE);

        }
        else{
            checkMark.setVisibility(View.GONE);

        }
        checkMark.bringToFront();
        if (imagen_mundo!=null){
            BitmapDrawable mundo_drawble = new BitmapDrawable(getContext().getResources(), imagen_mundo);
            mundo.setBackground(mundo_drawble);
        } else {
            mundo.setBackgroundResource(R.drawable.death_star);
        }
        TextView text_mundo = (TextView) rootView.findViewById(R.id.world_text);
        if (nombre_mundo==null){
            nombre_mundo = "";
        }
        text_mundo.setText(nombre_mundo);
        mundo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!clicked){
                    clicked = true;
                    progressBar.setVisibility(View.VISIBLE);
                    new PedirNiveles(WorldsFragment.this).execute();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy(){
        toDestroy = true;
        super.onDestroy();

    }

    @Override
    public void worldPassed() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!toDestroy)
                    checkMark.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getWorldId() {
        return id_mundo_clickeado;
    }


    /**
     * Clase encargada de mandar pedir los niveles para del mundo seleccionado
     * a la instancia singleton {@link ClientController} y lanzar una nueva
     * activity mostrando los niveles {@link WorldLevelsActivity}
     */
    public class PedirNiveles extends AsyncTask<Void, Void, Boolean> {

        WorldsFragment activity;

        public PedirNiveles(WorldsFragment activity)
        {
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            EstadoJugador.getInstance().setId_mundo_actual(id_mundo_clickeado);
            return ClientController.getInstance().obtenerNiveles();
        }


        @Override
        protected void onPostExecute(Boolean ok) {
            if (ok){
                Intent actividad_niveles = new Intent(activity.getActivity(), WorldLevelsActivity.class);
                activity.getActivity().startActivity(actividad_niveles);
                clicked = false;
            }
            else
            {
                AlertDialog.Builder builder = ClientController.getInstance().armarMensaje(activity.getActivity(), "El servidor esta teniendo problemas","Disculpe");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(activity.getActivity());
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}
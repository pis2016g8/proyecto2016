package calc4fun.cliente;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 *
 */
public class ScreenSlidePageFragment extends Fragment implements View.OnClickListener {

	private static final String IMAGEN="imagen";
	private static final String BOTON="boton";

	private Bitmap imagen;
    Button botonend = null;
	private boolean boton;

	/**
	 * Instances a new fragment with a background color and an index page.
	 * @return a new page
	 */
	public static ScreenSlidePageFragment newInstance(Drawable imagen, boolean boton) {

		// Instantiate a new fragment
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

		// Save the parameters
		Bundle bundle = new Bundle();
        Bitmap bitmap = ((BitmapDrawable)imagen).getBitmap();
		bundle.putParcelable(IMAGEN, bitmap);
		bundle.putBoolean(BOTON, boton);
		fragment.setArguments(bundle);
		fragment.setRetainInstance(true);

		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.boton = this.getArguments().getBoolean(BOTON);
		this.imagen = getArguments().getParcelable(IMAGEN);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);
		if (boton){
            botonend = (Button)rootView.findViewById(R.id.boton);
            botonend.setVisibility(View.VISIBLE);
            botonend.setOnClickListener(this);
		}
		else{
			rootView.findViewById(R.id.boton).setVisibility(View.GONE);
		}

		ImageView imagen_view = (ImageView) rootView.findViewById(R.id.imageView);
		imagen_view.setImageBitmap(this.imagen);

		// Change the background color
		rootView.setBackgroundColor(Color.parseColor("#000000"));


		return rootView;

	}

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.boton){
            botonend.setEnabled(false);
            //new PedirMundos(getActivity(), true).execute();
            Intent actividadMain = new Intent(this.getContext(), MainGo4Calcs.class);
            actividadMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(actividadMain);
        }
    }

    /*public class PedirMundos extends AsyncTask<Void, Void, Boolean>{

        Activity activity;
        boolean finishActivity = true;

        public PedirMundos(Activity activity)
        {
            this(activity, false);
        }

        public PedirMundos(Activity activity, boolean finishActivity) {
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
            } else {
                botonend.setEnabled(true);
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
        }

    }*/
}

package calc4fun.cliente;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.DataTypes.DataListaMensajes;
import calc4fun.cliente.DataTypes.DataMensaje;

public class MessageActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bandeja de Mensajes");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ListView.OnItemClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static int count = 0;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static List<DataMensaje> leidos = new ArrayList<DataMensaje>();
        private static List<DataMensaje> nuevos = new ArrayList<DataMensaje>();
        private static Fragment parteLeidos = null;
        private static Fragment parteNuevos = null;
        private static ListView listaMensajesLeidos = null;
        private static ListView listaMensajesNuevos = null;
        private int myId;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_message, container, false);
            rootView.setBackgroundResource(R.drawable.fondopantalla);
            myId = getArguments().getInt(ARG_SECTION_NUMBER);

           if (myId  == 0)
           {
               parteNuevos = this;
               listaMensajesNuevos = (ListView)rootView.findViewById(R.id.message_list);

               listaMensajesNuevos.setOnItemClickListener(this);
               //listaMensajesNuevos.setAdapter(new MessageListAdapter(this.getContext(), nuevos));
           }
           else
           {
               parteLeidos = this;
               listaMensajesLeidos = (ListView)rootView.findViewById(R.id.message_list);
               listaMensajesLeidos.setOnItemClickListener(this);
               //listaMensajesLeidos.setAdapter(new MessageListAdapter(this.getContext(), leidos));
           }
            new GetMessagesTask().execute();
            return rootView;
        }

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //1 - open message
            DataMensaje mensaje = (DataMensaje)parent.getItemAtPosition(position);
            //ejecuto nueva activity pare leer el mensaje
            Intent showMessageIntent = new Intent(getActivity(), MessageViewActivity.class );
            new MarkAsReadTask().execute(new DataMensaje[]{mensaje});
            showMessageIntent.putExtra("mensaje", mensaje);// se le pasa el id del mensaje o la clase directamente puesta como parseable
            startActivity(showMessageIntent);
            if( myId == 0){

                nuevos.remove(position);
                leidos.add(mensaje);
                listaMensajesNuevos.setAdapter(new MessageListAdapter(parteNuevos.getContext(), nuevos));
                listaMensajesLeidos.setAdapter(new MessageListAdapter(parteLeidos.getContext(), leidos));
            }

            //2 - change message from list if myId = 0
        }

        public class MessageListAdapter extends BaseAdapter{

            List<DataMensaje> list;
            Context context;

            public MessageListAdapter(Context context, List<DataMensaje> list){
                this.context = context;
                this.list = list;
            }

            @Override
            public int getCount() {
                return this.list.size();
            }

            @Override
            public Object getItem(int position) {
                return this.list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.message_list_item, parent, false);
                }
                ((TextView) convertView.findViewById(R.id.message_item_subject)).setText(list.get(position).getAsunto());
                ((TextView) convertView.findViewById(R.id.message_item_preview)).setText(list.get(position).getContenido());
                return convertView;
            }
        }

        private class GetMessagesTask extends AsyncTask<Void,Void,DataListaMensajes>{
            public GetMessagesTask(){}

            @Override
            protected DataListaMensajes doInBackground(Void... params) {
                try {
                    if (myId == 0) {
                        return ClientController.getInstance().getMensajesNuevos();
                    } else {
                        return ClientController.getInstance().getMensajesViejos();
                    }
                } catch (Exception e) {
                    return null;
                }
            }


            @Override
            protected void onPostExecute(DataListaMensajes resultado){
                if (resultado != null){
                    if (myId == 0){
                        nuevos = resultado.getMensajes();//lo cambio y luego reseteo el adapter para provocar refresh
                        listaMensajesNuevos.setAdapter(new MessageListAdapter(parteNuevos.getContext(), nuevos));
                    }
                    else {
                        leidos = resultado.getMensajes();
                        listaMensajesLeidos.setAdapter(new MessageListAdapter(parteLeidos.getContext(), leidos));
                    }
                }
            }
        }

        private class MarkAsReadTask extends AsyncTask<DataMensaje,Void,Boolean> {
            public MarkAsReadTask() {
            }

            @Override
            protected Boolean doInBackground(DataMensaje... params) {
                try {
                    if (myId == 0) {
                        return ClientController.getInstance().marcarMensajeLeido(params[0].getId());

                    } else {
                        throw new Exception("Error se trato de marcar como leido un mensaje ya leido segun la interfaz");
                    }

                } catch (Exception e) {
                    Log.e("MSG_NO_MARCADO", e.getMessage());
                    return false;
                }
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position );
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "NO LEIDOS";
                case 1:
                    return "LEIDOS";
            }
            return null;
        }
    }
}

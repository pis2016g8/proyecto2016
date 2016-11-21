package calc4fun.cliente;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataTypes.DataMundo;
import layout.WorldsFragment;

public class WorldsScreenSlide extends FragmentActivity implements View.OnClickListener{
    /**
     * The number of pages (wizard steps) to show in this demo.
     */

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
   Drawable tick;

    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    //TODO falta conectar la logica cuando apreta el boton y el action bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worlds_screen_slide);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }
        });

    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpFromSameTask(this);
                return true;

           }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag()instanceof Integer){

                mPager.setCurrentItem((int)v.getTag(), true);
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        List<DataMundo> mundos;
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            mundos = new ArrayList<>();
            for (DataMundo mundo: EstadoJugador.getInstance().getMundos()) {
                if (mundo.isMundo_disponible()){
                    mundos.add(mundo);
                }
            }
        }

        @Override
        public Fragment getItem(int position) {
            EstadoJugador estadoJugador = EstadoJugador.getInstance();
            estadoJugador.setId_mundo_actual(mundos.get(position).getId_mundo());
            Bitmap imagen_mundo = estadoJugador.getBitmapMundo(mundos.get(position).getId_mundo());
            String nombre_mundo = mundos.get(position).getNombre();
            return WorldsFragment.create(imagen_mundo, nombre_mundo,mundos.get(position).getId_mundo(), estadoJugador.todosNivelesCompletosMundoActual(), position, position == (getCount() - 1));

        }

        @Override
        public int getCount() {

           return mundos.size();

        }
    }

}

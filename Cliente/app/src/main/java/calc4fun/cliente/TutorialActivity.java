package calc4fun.cliente;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.viewpagerindicator.TitlePageIndicator;

import transformers.ZoomOutPageTransformer;
public class TutorialActivity extends FragmentActivity {

    static SparseArray<Bitmap> resizedImages= new SparseArray<>();

    ViewPager pager = null;

    public Bitmap getResizedImage(int resourceId){
        if (resizedImages.get(resourceId) != null){
            return resizedImages.get(resourceId);
        }
        Bitmap bm = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), resourceId), 300, 500, true);
        resizedImages.put(resourceId, bm);
        return bm;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.activity_tutorial);
        this.pager = (ViewPager) this.findViewById(R.id.pager);
        this.pager.setPageTransformer(true, new ZoomOutPageTransformer());

        // Create an adapter with the fragments we show on the ViewPager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager());
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.jugar)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.barra1)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.barra2)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.barra3)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.barra4)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.barra5)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.mundo1)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.nivel1)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.nivel2)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.nivel3)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.listapregunta)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.pregunta1)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.pregunta2)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.pregunta3)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.pregunta4)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.pregunta5)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.pregunta6)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.mundocompletado1)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.mundocompletado2)),false));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(new BitmapDrawable(getResources(), getResizedImage(R.drawable.fin)),true));
        this.pager.setAdapter(adapter);
        // Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);
    }


    /**
     Si se presiona para volver atras, solo se vuelve a la activity anterior
     si la página del viewPager es la página 0, sino se va hacia la página
     anterior dentro del mismo
     */
    @Override
    public void onBackPressed() {
        if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);

    }
}
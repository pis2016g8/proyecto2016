package calc4fun.cliente;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class ExplicacionActivity extends AppCompatActivity{

    TextView tutorial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Explicación");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tutorial = (TextView) findViewById(R.id.LearnText);
        Bundle dataFromMenu = getIntent().getExtras();
        if(dataFromMenu != null){
            tutorial.setText(dataFromMenu.getString("HelpText"));
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

}

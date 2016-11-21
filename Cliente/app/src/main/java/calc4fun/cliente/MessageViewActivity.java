package calc4fun.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.DataTypes.DataMensaje;

public class MessageViewActivity extends AppCompatActivity implements View.OnClickListener {

    private DataMensaje mensajeMostrado;
    Button botonResponder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mensajeMostrado = getIntent().getExtras().getParcelable("mensaje");
        setContentView(R.layout.activity_message_view);
        botonResponder = (Button)findViewById(R.id.response_button);
        ((TextView)findViewById(R.id.message_sender_value)).setText(mensajeMostrado.getRemitente());
        ((TextView)findViewById(R.id.message_subject_value)).setText(mensajeMostrado.getAsunto());
        ((TextView)findViewById(R.id.message_date_value)).setText(ClientController.getDateFormat().format(mensajeMostrado.getFecha()));
        ((TextView)findViewById(R.id.message_content)).setText(mensajeMostrado.getContenido());
        botonResponder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.response_button){
            Intent escribirMensaje = new Intent(this, SendMessageActivity.class);
            escribirMensaje.putExtra("id_problema", mensajeMostrado.getId());
            startActivity(escribirMensaje);
        }
    }
}

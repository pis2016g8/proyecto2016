package calc4fun.cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import calc4fun.cliente.BussinesLayer.Controladores.ClientController;
import calc4fun.cliente.BussinesLayer.Controladores.EstadoJugador;
import calc4fun.cliente.DataAccessLayer.Manejador.ClientHandler;

/**
 * Activity  que se muestra al comienzo de la aplicación. La misma se encarga de
 * el login de ususario. Si el ususario esta logueado se pasa directo a {@link MainGo4Calcs}
 * Si no esta logueado,
 */
public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private String nick;
    private AlertDialog dialog;
    private EditText nickname;
    private ProgressBar progressBar;
    private boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* ----------------------------------------------------------------------------------------------------------------------------------
                                                USAR SOLO CUANDO SE HAYA BORRADO MI USUARIO EN EL SERVIDOR
        ClientHandler manejador = new ClientHandler(LoginActivity.this);
        SQLiteDatabase db = manejador.getWritableDatabase();
        db.delete(manejador.getTableName(), null, null);
        db.close();
       /*-------------------------------------------------------------------------------------------------------------------------------------*/
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        /* ----------------------------------------------------------------------------------------------------------------------------------
                                              USAR SOLO UNA VEZ PARA OBTENER LA KEY HASH PARA AGREGAR A FACEBOOK
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "calc4fun.cliente",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        /*-------------------------------------------------------------------------------------------------------------------------------------*/

        //se fija si esta logueado
        if (ClientController.getInstance().estaLogueado(LoginActivity.this)){
            //me logueo por facebook
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, null);

            //redirijo a main
            Intent actividad_main = new Intent(LoginActivity.this, MainGo4Calcs.class);
            actividad_main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(actividad_main);
            LoginActivity.this.finish();
        } else {
            setContentView(R.layout.activity_login);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(final LoginResult loginResult) {
                            LoginActivity.this.findViewById(R.id.login_button).setVisibility(View.GONE);
                            new Login(LoginActivity.this, loginResult).execute();
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancel() {
                            AlertDialog.Builder builder =ClientController.getInstance().armarMensaje(LoginActivity.this, "Se ha cancelado la conexion con Facebook","Disculpe");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            AlertDialog.Builder builder =ClientController.getInstance().armarMensaje(LoginActivity.this, exception.getMessage(),"Disculpe");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void crearDialogoNickname(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Nickname");
        nickname = new EditText(LoginActivity.this);
        nickname.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(nickname);
        builder.setMessage("Elija un nickname unico");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                LoginActivity.this.findViewById(R.id.login_button).setVisibility(View.VISIBLE);
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
    }

    /* -----------------------------------------HILOS DE EJECUCIÓN---------------------------------------------------------------------------*/

     /**
      * Clase encargada de comunicarse con el controlador para que lleve
      * a cabo el registro de usuario, en caso de este no estar registrado
      * anteriormente. Ejecuta de manera asíncrona.
      */
    public class Registro extends AsyncTask<Void, Void, Boolean> {

        AppCompatActivity activity;
        LoginResult loginResult;

        public Registro(AppCompatActivity activity, LoginResult loginResult) {
            this.activity = activity;
            this.loginResult = loginResult;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            boolean respuesta = false;
            try {
                respuesta = ClientController.getInstance().Registro(LoginActivity.this, loginResult.getAccessToken(), nick);
                error = false;
            } catch (Exception e) {
                dialog.setMessage(e.getMessage());
                error = true;
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(Boolean registro) {
            if (registro) {
                progressBar.setVisibility(View.GONE);
                dialog.dismiss();
                Intent actividad_tutorial = new Intent(activity, TutorialActivity.class);
                actividad_tutorial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(actividad_tutorial);
                activity.finish();
            } else {
                progressBar.setVisibility(View.GONE);
                if (!error) {
                    dialog.setMessage("Ese nickname no esta disponible");
                }
            }
        }
    }

    /**
     * Clase encargada de hacer el login del usuario en el celular una
     * vez este registrado. Ejecuta de manera asíncrona.
     */
    public class Login extends AsyncTask<Void, Void, Boolean> {

        AppCompatActivity activity;
        LoginResult loginResult;

        public Login(AppCompatActivity activity, LoginResult loginResult) {
            this.activity = activity;
            this.loginResult = loginResult;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            boolean respuesta = false;
            try {
                respuesta = ClientController.getInstance().Login(activity, loginResult.getAccessToken().getUserId());
                error = false;
            } catch (Exception e) {
                error = true;
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(Boolean login) {
            if (login) {
                Intent actividad_main = new Intent(activity, MainGo4Calcs.class);
                actividad_main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(actividad_main);
                activity.finish();
            } else {
                if (error) {
                    AlertDialog.Builder builder = ClientController.getInstance().armarMensaje(LoginActivity.this, "El servidor esta teniendo problemas", "Disculpe");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressBar.setVisibility(View.GONE);
                            LoginManager.getInstance().logOut();
                            LoginActivity.this.findViewById(R.id.login_button).setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                } else {
                    crearDialogoNickname();
                    //llamado para obtener nombre
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                EstadoJugador estado = EstadoJugador.getInstance();
                                estado.setNombre_jugador(object.getString("name"));
                                progressBar.setVisibility(View.GONE);
                                dialog.show();
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //sobreescribo la funcion del ok del dialogo para corroborar que sea unico y que no este vacio el nick
                                        nick = nickname.getText().toString();
                                        if ((nick.trim().length() > 0)) {
                                            new Registro(activity, loginResult).execute();
                                            progressBar.setVisibility(View.VISIBLE);
                                        } else {
                                            dialog.setMessage("El nickname no puede ser vacio");
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "name");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }
        }
    }
}
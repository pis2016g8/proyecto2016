package calc4fun.cliente.BussinesLayer.Controladores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.facebook.AccessToken;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import calc4fun.cliente.DataAccessLayer.Manejador.ClientHandler;
import calc4fun.cliente.DataTypes.DataEstadoMensaje;
import calc4fun.cliente.DataTypes.DataExperiencia;
import calc4fun.cliente.DataTypes.DataJugador;
import calc4fun.cliente.DataTypes.DataListaMensajes;
import calc4fun.cliente.DataTypes.DataListaMundos;
import calc4fun.cliente.DataTypes.DataListaRanking;
import calc4fun.cliente.DataTypes.DataMundo;
import calc4fun.cliente.DataTypes.DataNivel;
import calc4fun.cliente.DataTypes.DataProblema;

/**
 * Clase singleton encargada de recabar la información del servidor y de la base, necesaria para
 * el correcto funcionamiento del cliente, y actualizar el estado del jugador
 * si corresponde (almacenado en la clase {@link EstadoJugador})
 *
 * La gran mayoría de los métodos son autocontenidos. Para aquellos que
 * hacen pedidos al servidor, deben ejecutarse en background porque pueden
 * demorar. Se optaron por dos estrategias para las solicitudes http:
 * Utilizar {@link RestTemplate} (camino rápido, transforma el json automaticamente),
 * o utilizar {@link OkHttpClient} para tratar con el pedido a más bajo nivel y
 * luego mapear el json a clases usando {@Link JSONObject} y {@link JSONArray}
 * para operar sobre el y {@link ObjectMapper} para mapear objetos a clases
 */
public class ClientController {
    /**
     * Formato de la fecha acordado en común con el servidor para transformar la misma a string
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //private static String baseUrl = "servidorpracticotsi2.azurewebsites.net";
    //private static String baseUrl = "servidorgrupo8.azurewebsites.net";
    private static String baseUrl = "10.0.2.2:8080";
    //private static String baseUrl = "192.168.1.44:8080";
    private EstadoJugador estado_jugador = EstadoJugador.getInstance();
    private static ClientController ourInstance = new ClientController();

    /**
     * Clase usada para los llamados http para asi consumir los servicios
     * usados. Mapea automáticamente los jSon de los llamados a clases
     */
    private RestTemplate restTemplate;

    //private static boolean tutorialActivo = true;

    public static ClientController getInstance() {
        return ourInstance;
    }

    private ClientController() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    //public boolean getTutorialActivo() {return tutorialActivo;}


    //public void setTutorialActivo(boolean activo) {this.tutorialActivo = activo;}


    public boolean Registro(Context context, AccessToken token, final String nick) throws Exception {
        boolean ok = false;
        //averiguo si el nick es unico en el juego llamada al servidor
        OkHttpClient client = new OkHttpClient();
        Request request_servidor = new Request.Builder()
                .url("http://" + baseUrl + "/Servidor/registrarjugador?nick=" + nick + "&fb_token=" + token.getUserId() + "&nombre=" + estado_jugador.getNombre_jugador())
                .get()
                .build();
        Response response = null;
        response = client.newCall(request_servidor).execute();
        if (response.isSuccessful()) {
            String respuesta = response.body().string();
            boolean es_unico = Boolean.parseBoolean(respuesta);
            if (es_unico) {

                //obtengo el fb_token del usuario
                String fb_token = token.getUserId();

                //guardo datos en el controlador
                estado_jugador.setNick_jugador(nick);
                estado_jugador.setFb_token_jugador(fb_token);

                //guardo en la base de datos
                guardarDatosJugador(context);
                ok = true;
            }
        } else {
            throw new Exception("El servidor esta teniendo problemas, vuelva a intentarlo");
        }
        return ok;
    }

    public boolean Login(Context context, String fb_token) throws Exception {
        boolean ok = false;
        //lamada rest a Login
        OkHttpClient client = new OkHttpClient();
        Request request_servidor = new Request.Builder()
                .url("http://" + baseUrl + "/Servidor/loginjugador?fb_token=" + fb_token)
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request_servidor).execute();
            if (response.isSuccessful()) {
                String respuesta = response.body().string();
                JSONObject mainObject = new JSONObject(respuesta);
                boolean existe_usuario = mainObject.getBoolean("existe_token");
                if (existe_usuario) {
                    //guardo datos en el controlador
                    estado_jugador.setNick_jugador(mainObject.getString("nick"));
                    estado_jugador.setFb_token_jugador(fb_token);
                    estado_jugador.setNombre_jugador(mainObject.getString("nombre"));

                    //guardo datos en la base
                    guardarDatosJugador(context);
                    ok = true;
                }
            } else {
                throw new Exception("el servidor esta teniendo problemas");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public boolean estaLogueado(Context context){
        boolean ok = false;
        ClientHandler manejador = new ClientHandler(context);
        SQLiteDatabase db = manejador.getReadableDatabase();
        Cursor cursor = null;
        String Query = "SELECT * FROM " + manejador.getTableName();
        cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.moveToFirst()) {
            estado_jugador.setFb_token_jugador(cursor.getString(cursor.getColumnIndex("fb_token")));
            estado_jugador.setNombre_jugador(cursor.getString(cursor.getColumnIndex("NOMBRE")));
            estado_jugador.setNick_jugador(cursor.getString(cursor.getColumnIndex("NICKNAME")));
            cursor.close();
            ok = true;
        }
        return ok;
    }

    public DataListaRanking VerRanking() {
        try {
            return restTemplate.getForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path("/Servidor/verranking")
                            .build().toUriString()
                    , DataListaRanking.class);
        } catch (Exception e) {
            Log.e("ResponderProblema mal", e.getMessage(), e);
            return null;
        }
    }


    public DataExperiencia ResponderProblema(String respuesta, String id_problema) {

        try {
            DataExperiencia de = restTemplate.getForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path("/Servidor/responderProblema")
                            .queryParam("id_problema", id_problema)
                            .queryParam("respuesta", respuesta)
                            .queryParam("id_jugador", estado_jugador.getNick_jugador())
                            .build().toUriString()
                    , DataExperiencia.class);
            if (de.getExperiencia() > 0) {
                estado_jugador.agregarProblemaRespondido(Integer.valueOf(id_problema));
            }
            return de;
        } catch (Exception e) {
            Log.e("ResponderProblema mal", e.getMessage(), e);
            return null;
        }


    }

    public DataJugador getPerfil() {

        try {
            return restTemplate.getForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path("/Servidor/verperfil")
                            .queryParam("nick", estado_jugador.getNick_jugador())
                            .build().toUriString(), DataJugador.class);
        } catch (Exception e) {
            Log.e("Error:Profile Not Found", e.getMessage(), e);
            return null;
        }
    }

    //en caso de expandirse la ayuda como una clase, este método se encargaría de recabar el
    //dataTypy con el contenido
//    public DataAyuda getAyuda(String id_problem){
//        try {
//            return restTemplate.getForObject(
//                    UriComponentsBuilder.fromUriString("http://" +baseUrl)
//                            .path("/Servidor/getayuda")
//                            .queryParam("id_problema", id_problem )
//                            .build().toUriString(), DataAyuda.class);
//        } catch(Exception e){
//            Log.e("Error:Help Not Found", e.getMessage(), e);
//            return null;
//        }
//    }


    public DataEstadoMensaje enviarMensaje(String texto, String id_problema) {
        try {
            return restTemplate.getForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path("/Servidor/enviarmensaje")
                            .queryParam("id_problema", id_problema)
                            .queryParam("nick", estado_jugador.getNick_jugador())
                            .queryParam("fecha", getDateFormat().format(new Date()))
                            .queryParam("mensaje", URLEncoder.encode(texto, "UTF-8"))
                            .queryParam("asunto", "Mensaje de Ayuda")
                            .build().toUriString(), DataEstadoMensaje.class);


        } catch (Exception e) {
            Log.e("Error:Message not sent", e.getMessage(), e);
            return null;
        }
    }

    public DataEstadoMensaje responderMensaje(String texto) {
        try {
            restTemplate.postForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path("/Servidor/respondermensaje")
                            .queryParam("destinatario", "marce_fing")
                            .queryParam("asunto", "Respuesta de " + estado_jugador.getNick_jugador())
                            .queryParam("contenido", URLEncoder.encode(texto, "UTF-8"))
                            .queryParam("remitente", estado_jugador.getNick_jugador())
                            .build().toUriString(), null/*emptyBody*/, String.class

            );
            return new DataEstadoMensaje(true);

        } catch (Exception e) {

            return null;
        }
    }


    public DataEstadoMensaje enviarReporte(String texto, String id_problema) {
        try {
            restTemplate.postForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path("/Servidor/reportarproblema")
                            .queryParam("id_problema", id_problema)
                            .queryParam("nick", estado_jugador.getNick_jugador())
                            .queryParam("mensaje", URLEncoder.encode(texto, "UTF-8"))
                            .build().toUriString(), null/*emptyBody*/, String.class

            );
            return new DataEstadoMensaje(true);

        } catch (Exception e) {

            return null;
        }
    }


    private final String MARCAR_LEIDO = "/Servidor/mensajeleido";

    public boolean marcarMensajeLeido(int id_mensaje) {
        try {
            restTemplate.postForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path(MARCAR_LEIDO)
                            .queryParam("nick", estado_jugador.getNick_jugador())
                            .queryParam("id_mensaje", id_mensaje)
                            .build().toUriString(), null/*emptyBody*/, String.class

            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private final String GET_MENSAJES_NUEVOS = "/Servidor/vermensajesnuevos";

    public DataListaMensajes getMensajesNuevos() {
        try {
            return restTemplate.getForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path(GET_MENSAJES_NUEVOS)
                            .queryParam("nick", estado_jugador.getNick_jugador())
                            .build().toUriString(), DataListaMensajes.class
            );
        } catch (Exception e) {
            Log.e("Error Lista", e.getMessage(), e);
            return null;
        }
    }

    private final String GET_MENSAJES_VIEJOS = "/Servidor/vermensajesviejos";

    public DataListaMensajes getMensajesViejos() {
        try {
            return restTemplate.getForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path(GET_MENSAJES_VIEJOS)
                            .queryParam("nick", estado_jugador.getNick_jugador())
                            .build().toUriString(), DataListaMensajes.class
            );
        } catch (Exception e) {
            Log.e("Error Lista", e.getMessage(), e);
            return null;
        }
    }

    public DataListaMundos pedirMundos() {
        try {
            return restTemplate.getForObject(
                    UriComponentsBuilder.fromUriString("http://" + baseUrl)
                            .path("/Servidor/listarmundosjugador")
                            .queryParam("nick", estado_jugador.getNick_jugador())
                            .build().toUriString(), DataListaMundos.class);
        } catch (Exception e) {
            Log.e("Error:Mundos not Found", e.getMessage(), e);
            return null;
        }
    }

    public boolean pedirProblemas() {
        boolean ok = false;
        // if (estado_jugador.getProblemasNivelActual().isEmpty()){
        OkHttpClient client = new OkHttpClient();
        Request request_servidor = new Request.Builder()
                .url("http://" + baseUrl + "/Servidor/listarproblemasnivel?" +
                        "nick=" + estado_jugador.getNick_jugador() +
                        "&id_mundo=" + estado_jugador.getId_mundo_actual() +
                        "&id_nivel=" + estado_jugador.getId_nivel_actual())
                .get()
                .build();
        try {
            Response response = client.newCall(request_servidor).execute();
            if (response.isSuccessful()) {
                String respuesta = response.body().string();
                JSONObject mainObject = new JSONObject(respuesta);
                JSONArray lista_problemas = mainObject.getJSONArray("problemas_nivel");
                List<DataProblema> problemas = new ArrayList<DataProblema>();
                for (int i = 0; i < lista_problemas.length(); i++) {
                    JSONObject jproblema = lista_problemas.getJSONObject(i);
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonInString = jproblema.toString();
                    DataProblema problema = mapper.readValue(jsonInString, DataProblema.class);
                    problemas.add(problema);
                }
                estado_jugador.setProblemasNivelActual(problemas);
                ok = true;
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*} else {
            return true;
        }*/
        return ok;
    }

    public DataProblema getSiguienteProblema() {
        List<DataProblema> problemas = estado_jugador.getProblemasNivelActual();
        DataProblema problema_actual = null;
        for (DataProblema p : problemas) {
            if (!p.getResuleto()) {
                problema_actual = p;
                estado_jugador.setId_problema_actual(problema_actual.getId_problema());
                return problema_actual;
            }
        }
        //aca sabemos que estan todos los problemas completados
        return problema_actual;
    }

    //retorno la imagen y la guardo en el cache estado jugador
    public Bitmap getImagenProblema(String urlString, int id_problema) {
        Bitmap bmp = estado_jugador.getBitmapProblema(id_problema);
        if (bmp == null) {
            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(new Request.Builder().url(urlString).get().build()).execute();
                if (response.isSuccessful()) {
                    bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null) {
                        estado_jugador.addCachedBitmaps(urlString, bmp);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bmp;
    }

    public void getImagenMundo(String urlString, int id_mundo) {
        Bitmap bmp = estado_jugador.getBitmapMundo(id_mundo);
        if (bmp == null) {
            OkHttpClient client = new OkHttpClient();
            try {
                Response response = client.newCall(new Request.Builder().url(urlString).get().build()).execute();
                if (response.isSuccessful()) {
                    bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null) {
                        estado_jugador.addCachedBitmapWorld(id_mundo, bmp);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean obtenerMundos() {
        //me fijo si no estan en memoria los pido
        boolean ok = false;
        //   if (estado_jugador.getMundos().isEmpty()) {
        OkHttpClient client = new OkHttpClient();
        Request request_servidor = new Request.Builder()
                .url("http://" + baseUrl + "/Servidor/listarmundosjugador?nick=" + estado_jugador.getNick_jugador())
                .get()
                .build();
        try {
            Response response = client.newCall(request_servidor).execute();
            if (response.isSuccessful()) {
                String respuesta = response.body().string();
                JSONObject mainObject = new JSONObject(respuesta);
                JSONArray lista_mundos = mainObject.getJSONArray("lista_mundos");
                List<DataMundo> mundos = new ArrayList<DataMundo>();
                for (int i = 0; i < lista_mundos.length(); i++) {
                    JSONObject jmundo = lista_mundos.getJSONObject(i);
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonInString = jmundo.toString();
                    DataMundo mundo = mapper.readValue(jsonInString, DataMundo.class);
                    getImagenMundo(mundo.getImagen(), mundo.getId_mundo());
                    mundos.add(mundo);
                }
                estado_jugador.setMundos(mundos);
                ok = true;
            }
        } catch (IOException e) {
            Log.e("IO Exception", e.getMessage());
        } catch (JSONException e) {
            Log.e("JSonParser Exception", e.getMessage());
        }
      /*  } else {
            ok = true;
        }*/
        return ok;
    }

    public boolean obtenerNiveles() {
        int id_mundo = estado_jugador.getId_mundo_actual();
       /* if (estado_jugador.getNivelesMundoActual() != null && !estado_jugador.getNivelesMundoActual().isEmpty()){
            return true;
        }*/

        boolean ok = false;
        //me fijo si no estan en memoria los pido
        //if (estado_jugador.hayNivelesMundoActual()){
        OkHttpClient client = new OkHttpClient();
        Request request_servidor = new Request.Builder()
                .url("http://" + baseUrl + "/Servidor/listarnivelesmundojugador?nick=" + estado_jugador.getNick_jugador() + "&id_mundo=" + id_mundo)
                .get()
                .build();
        try {
            Response response = client.newCall(request_servidor).execute();
            if (response.isSuccessful()) {
                String respuesta = response.body().string();
                JSONObject mainObject = new JSONObject(respuesta);
                JSONArray lista_niveles = mainObject.getJSONArray("lista");
                List<DataNivel> niveles = new ArrayList<DataNivel>();
                for (int i = 0; i < lista_niveles.length(); i++) {
                    JSONObject jnivel = lista_niveles.getJSONObject(i);
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonInString = jnivel.toString();
                    DataNivel nivel = mapper.readValue(jsonInString, DataNivel.class);
                    niveles.add(nivel);
                }
                estado_jugador.setNivelesMundoActual(niveles);
                ok = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*} else {
            ok = true;
        }*/
        return ok;
    }


    private void guardarDatosJugador(Context context) {
        ClientHandler manejador = new ClientHandler(context);
        SQLiteDatabase db = manejador.getWritableDatabase();
        ContentValues values = new ContentValues(3);
        values.put("fb_token", estado_jugador.getFb_token_jugador());
        values.put("NOMBRE", estado_jugador.getNombre_jugador());
        values.put("NICKNAME", estado_jugador.getNick_jugador());
        db.insert(manejador.getTableName(), null, values);
        db.close();
    }

    public AlertDialog.Builder armarMensaje(final Activity pantalla, String mensaje, String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(pantalla);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        return builder;
    }

    public boolean getFacebookProfilePicture() {
        boolean ok = false;
        String fb_token = estado_jugador.getFb_token_jugador();
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + fb_token + "/picture?type=large");
            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            if (bitmap != null) {
                estado_jugador.setProfile_picture(bitmap);
                ok = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public static DateFormat getDateFormat() {
        return dateFormat;
    }
}

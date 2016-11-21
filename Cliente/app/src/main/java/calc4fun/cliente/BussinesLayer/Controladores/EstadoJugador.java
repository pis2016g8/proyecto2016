package calc4fun.cliente.BussinesLayer.Controladores;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import calc4fun.cliente.DataTypes.DataMundo;
import calc4fun.cliente.DataTypes.DataNivel;
import calc4fun.cliente.DataTypes.DataProblema;

/**
 * Clase singleton que se encarga de guardar datos de estado del juego,
 * tanto del jugador como del juego en si. Aqui se guarda la
 * información relevante de los pedidos rest al servidor, y los
 * datos de logueo de facebook.
 */

public class EstadoJugador {
    private static EstadoJugador instancia = new EstadoJugador();
    public static EstadoJugador getInstance() {
        return instancia;
    }


    private EstadoJugador() {
    }


    private String nombre_jugador;
    private String nick_jugador;
    private String fb_token_jugador;
    private Bitmap profile_picture;
    private int id_mundo_actual = 1;
    private int id_nivel_actual;
    private int id_problema_actual;

    private Hashtable<Integer, Bitmap> cachedBitmaps = new Hashtable<>();
    private Hashtable<Integer, Bitmap> cachedBitmapWorlds = new Hashtable<Integer, Bitmap>();
    private List<DataMundo> mundos = new ArrayList<DataMundo>();
    private Map<Integer, List<DataNivel>> mundo_niveles = new HashMap<Integer, List<DataNivel>>();
    private Map<Integer, List<DataProblema>> nivel_problemas = new HashMap<Integer, List<DataProblema>>();
    private Map<Integer, Integer> veces_errada_preguntas = new HashMap<Integer, Integer>();


    public String getNombre_jugador() {
        return nombre_jugador;
    }

    public void setNombre_jugador(String nombre_jugador) {
        this.nombre_jugador = nombre_jugador;
    }

    public String getNick_jugador() {
        return nick_jugador;
    }

    public void setNick_jugador(String nick_jugador) {
        this.nick_jugador = nick_jugador;
    }

    public String getFb_token_jugador() {
        return fb_token_jugador;
    }

    public void setFb_token_jugador(String fb_token_jugador) {
        this.fb_token_jugador = fb_token_jugador;
    }

    public int getId_mundo_actual() {
        return id_mundo_actual;
    }

    public void setId_mundo_actual(int id_mundo) {
        id_mundo_actual = id_mundo;
    }

    public List<DataMundo> getMundos() {
        return mundos;
    }

    public void setMundos(List<DataMundo> mundos) {
        this.mundos = mundos;
    }

    public DataMundo getMundoActual() {
        DataMundo mundo = null;
        for (DataMundo dm : mundos) {
            if (dm.getId_mundo() == id_mundo_actual) {
                mundo = dm;
            }
        }
        return mundo;
    }

    public int getId_nivel_actual() {
        return id_nivel_actual;
    }

    public void setId_nivel_actual(int id_nivel_actual) {
        this.id_nivel_actual = id_nivel_actual;
    }

    public List<DataProblema> getProblemasNivelActual() {
        return nivel_problemas.get(id_nivel_actual);
    }

    public void setProblemasNivelActual(List<DataProblema> nivel_problemas) {
        this.nivel_problemas.put(id_nivel_actual, nivel_problemas);
    }

    public void habilitarTutorial() {
        List<DataProblema> problemas = nivel_problemas.get(id_nivel_actual);
        for (DataProblema dp : problemas) {
            if (dp.getId_problema() == id_problema_actual) {
                dp.setTut_activo(true);
            }
        }
    }

    public List<DataNivel> getNivelesMundoActual() {
        return mundo_niveles.get(id_mundo_actual);
    }


    public boolean todosNivelesCompletosMundoActual() {
        return this.getMundoActual().isMundo_completado();

    }


    public void setNivelesMundoActual(List<DataNivel> mundo_niveles) {
        this.mundo_niveles.put(id_mundo_actual, mundo_niveles);
    }

    public boolean hayNivelesMundoActual() {
        return mundo_niveles.isEmpty();
    }

    public boolean hayProblemasNivelActual() {
        return nivel_problemas.isEmpty();
    }

    public void agregarProblemaRespondido(int id_problema) {
        List<DataProblema> problemas = this.getProblemasNivelActual();
        for (DataProblema p : problemas) {
            if (p.getId_problema() == id_problema) {
                p.setResuelto(true);
                this.OnProblemChanged(id_problema);
                break;
            }
        }
        //me fijo que todos los problemas esten aprobados
        //forma ineficiente pero util
        boolean faltaResolver = false;
        for (DataProblema p : problemas) {
            if (!p.getResuleto()) {
                faltaResolver = true;
                break;
            }
        }
        //ahora veo que este sea el ultimo nivel
        if (!faltaResolver) {
            boolean nivelesCompletados = true;
            for (DataNivel nivel : this.getNivelesMundoActual()) {
                if (!nivel.isNivel_completo() && nivel.getId_nivel() == id_nivel_actual) {
                    nivel.setNivel_completo(true);
                    setNivelHabilitado(nivel.getNum_nivel()+1);
                }
                if (!nivel.isNivel_completo()) {
                    nivelesCompletados = false;
                }
            }
            if (nivelesCompletados) {
                this.getMundoActual().setMundo_completado(true);
                this.OnWorldChanged(id_mundo_actual);
            }
        }
    }


    public int getId_problema_actual() {
        return id_problema_actual;
    }

    public void setId_problema_actual(int id_problema_actual) {
        this.id_problema_actual = id_problema_actual;
    }

    public DataProblema getProblema(int id_problema) {
        List<DataProblema> problemas = getProblemasNivelActual();
        for (DataProblema p : problemas) {
            if (p.getId_problema() == id_problema) {
                return p;
            }
        }
        return null;
    }


    public void setNivelHabilitado(int numero_nivel){
        List<DataNivel> niveles = mundo_niveles.get(id_mundo_actual);
        for (DataNivel dn : niveles) {
            if (dn.getNum_nivel() == numero_nivel) {
                dn.setNivel_disponible(true);
                break;
            }
        }
    }

    public int getNumeroNivelActual() {
        int numero_nivel = 0;
        List<DataNivel> niveles = mundo_niveles.get(id_mundo_actual);
        for (DataNivel dn : niveles) {
            if (dn.getId_nivel() == id_nivel_actual) {
                numero_nivel = dn.getNum_nivel();
            }
        }
        return numero_nivel;
    }

    public Bitmap getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(Bitmap profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void addCachedBitmaps(String url, Bitmap imagen) {
        List<DataProblema> problemas = getProblemasNivelActual();
        for (DataProblema dp : problemas) {
            if (dp.getContenido() == url) {
                cachedBitmaps.put(dp.getId_problema(), imagen);
            }
        }
    }

    public Bitmap getBitmapProblema(int id_problema) {
        return cachedBitmaps.get(id_problema);
    }

    /**
     * Agrega una version redimensionada de la imagen de un mundo
     * y la guarda en un diccionario con clave id_mundo,
     * sobreescribe la imagen si esta ya se encuantra almacenada.
     * @param id_mundo id del mundo al cual la imagen pertenece
     * @param imagen bitmap con la imagen pura sin redimensionar
     */
    public void addCachedBitmapWorld(int id_mundo, Bitmap imagen) {
        addCachedBitmapWorld(id_mundo, imagen, true);
    }
    /**
     * Agrega una version redimensionada de la imagen de un mundo
     * y la guarda en un diccionario con clave id_mundo.
     * @param id_mundo id del mundo al cual la imagen pertenece
     * @param imagen bitmap con la imagen pura sin redimensionar
     * @param newLink indica si la imagen debe ser sustituida
     *                en caso de estar guardada porque el de donde
     *                se trajo la misma es nuevo
     */
    public void addCachedBitmapWorld(int id_mundo, Bitmap imagen, boolean newLink) {
        if (!cachedBitmaps.containsKey(id_mundo) || newLink) {
            Bitmap.createScaledBitmap(imagen, (int)(100/(float)imagen.getHeight() * imagen.getWidth()), 100, true);
            cachedBitmapWorlds.put(id_mundo, imagen);
        }
    }

    public Bitmap getBitmapMundo(int id_mundo) {
        return cachedBitmapWorlds.get(id_mundo);
    }


    private List<ProblemChangedListener> problemListeners = new ArrayList<>();
    private List<WorldChangedListener> worldListeners = new ArrayList<>();

    public synchronized void RegisterProblemListener(ProblemChangedListener listener) {
        if (problemListeners.indexOf(listener) == -1) {
            problemListeners.add(listener);
        }
    }

    public synchronized void RegisterWorldListener(WorldChangedListener listener) {
        if (worldListeners.indexOf(listener) == -1) {
            worldListeners.add(listener);
        }
    }

    protected void OnProblemChanged(int idProblema) {
        for (ProblemChangedListener listener : problemListeners) {
            listener.OnProblemPassed(idProblema);
        }
    }

    protected void OnWorldChanged(int worldId) {
        for (WorldChangedListener listener : worldListeners) {
            if (listener.getWorldId() == worldId) {
                listener.worldPassed();
            }
        }
    }

    public synchronized void DeRegisterWorldListener(WorldChangedListener listener) {
        if (worldListeners.indexOf(listener) > -1) {
            worldListeners.remove(listener);
        }
    }

    public synchronized void DeRegisterProblemListener(ProblemChangedListener listener) {
        if (problemListeners.indexOf(listener) > -1) {
            problemListeners.remove(listener);
        }
    }


    public void setMundoCompletado() {
        this.mundos.get(this.id_mundo_actual - 1).setMundo_completado(true);

        if (mundos.size() > this.id_mundo_actual) {

            this.mundos.get(this.id_mundo_actual).setMundo_disponible(true);
            this.id_mundo_actual++;
        }
    }

    public void setNivelCompletado(){

    }

}

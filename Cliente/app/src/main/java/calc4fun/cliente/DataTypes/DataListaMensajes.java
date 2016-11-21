package calc4fun.cliente.DataTypes;

import java.util.ArrayList;
/**
 * Created by tperaza on 30/9/2016.
 */
public class DataListaMensajes {
    private ArrayList<DataMensaje> mensajes;

    public DataListaMensajes(){

    }

    public DataListaMensajes(ArrayList<DataMensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public ArrayList<DataMensaje> getMensajes() {
        return mensajes;
    }
}

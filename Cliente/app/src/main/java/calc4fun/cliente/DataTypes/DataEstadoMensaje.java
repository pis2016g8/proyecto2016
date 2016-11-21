package calc4fun.cliente.DataTypes;

/**
 * Created by Miguel on 14/09/2016.
 */
public class DataEstadoMensaje {
    private boolean ok;

    public DataEstadoMensaje() {

    }


    public DataEstadoMensaje(boolean ok) {
        this.ok = ok;

    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}

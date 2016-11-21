package Datatypes;


public class DataEstadoMensaje {
	
    private boolean ok;

    //----CONSTRUCTORES----//
    public DataEstadoMensaje() {

    }

    public DataEstadoMensaje(boolean ok) {
        this.ok = ok;
    }

    //----GETTERS----//
    public boolean isOk() {
        return ok;
    }

    //----SETTERS----//
    public void setOk(boolean ok) {
        this.ok = ok;
    }
    
}

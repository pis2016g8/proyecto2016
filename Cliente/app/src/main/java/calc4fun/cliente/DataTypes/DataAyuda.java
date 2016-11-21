package calc4fun.cliente.DataTypes;

/**
 * Created by tperaza on 12/9/2016.
 */
public class DataAyuda {
    private String ayuda;

    public DataAyuda(){

    }

    public DataAyuda(String ayuda) {
        this.setAyuda(ayuda);
    }


    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }
}

package calc4fun.cliente.DataTypes;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by tperaza on 30/9/2016.
 */
public class DataListaMundos {
    private List<DataMundo> lista_mundos;

    public DataListaMundos(){

    }

    public DataListaMundos(List<DataMundo> lista_mundos) {
        this.lista_mundos = lista_mundos;
    }

    public List<DataMundo> getLista_mundos() {
        return lista_mundos;
    }
}

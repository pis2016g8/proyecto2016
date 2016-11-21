package calc4fun.cliente.DataTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tperaza on 30/9/2016.
 */
public class DataListaNiveles {
    private List<DataNivel> lista;

    public DataListaNiveles(){

    }

    public DataListaNiveles(List<DataNivel> lista) {
        this.lista = lista;
    }

    public List<DataNivel> getLista() {
        return lista;
    }
}

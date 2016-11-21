package calc4fun.cliente.DataTypes;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by tperaza on 12/9/2016.
 */
public class DataListaRanking {
    private List<DataPuntosJugador> listaPuntos=new ArrayList<>();

    public DataListaRanking(){

    }



    public List<DataPuntosJugador> getListaPuntos() {
        return listaPuntos;
    }

    public void setListaPuntos(List<DataPuntosJugador> listaPuntos) {
        this.listaPuntos = listaPuntos;
    }
}

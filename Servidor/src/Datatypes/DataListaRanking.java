package Datatypes;

import java.util.ArrayList;
import java.util.List;

public class DataListaRanking {
	
    private List<DataPuntosJugador> listaPuntos=new ArrayList<>();

    public DataListaRanking(List<DataPuntosJugador> listaPuntos) {
        this.listaPuntos=listaPuntos;
    }
    
    public List<DataPuntosJugador> getListaPuntos() {
        return listaPuntos;
    }

}

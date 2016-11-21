package Datatypes;

import java.util.ArrayList;
import java.util.List;

public class DataListaNiveles {
	
	private List<DataNivel> lista = new ArrayList<DataNivel>();

	public DataListaNiveles(List<DataNivel> lista) {
		this.lista = lista;
	}

	public List<DataNivel> getLista() {
		return lista;
	}

}

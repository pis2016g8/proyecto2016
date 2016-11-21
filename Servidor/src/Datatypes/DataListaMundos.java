package Datatypes;

import java.util.ArrayList;
import java.util.List;

public class DataListaMundos {
	
	private List<DataMundo> lista_mundos = new ArrayList<DataMundo>();

	public DataListaMundos(List<DataMundo> lista_mundos) {
		this.lista_mundos = lista_mundos;
	}

	public List<DataMundo> getLista_mundos() {
		return lista_mundos;
	}

}

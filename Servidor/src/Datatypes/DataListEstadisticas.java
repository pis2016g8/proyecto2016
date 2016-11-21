package Datatypes;

import java.util.ArrayList;
import java.util.List;

public class DataListEstadisticas {
	
	private List<DataEstadistica> lista = new ArrayList<DataEstadistica>();

	public DataListEstadisticas(List<DataEstadistica> lista) {
		this.lista = lista;
	}

	public List<DataEstadistica> getLista() {
		return lista;
	}

	public void setLista(List<DataEstadistica> lista) {
		this.lista = lista;
	}

}

package Datatypes;

import java.util.List;

public class DataListaDataProblema {
	
	private List<DataProblema> problemas_nivel; 

	public DataListaDataProblema(List<DataProblema> problemas_nivel_act) {
		this.problemas_nivel = problemas_nivel_act;
	}

	public List<DataProblema> getProblemas_nivel() {
		return problemas_nivel;
	}

}

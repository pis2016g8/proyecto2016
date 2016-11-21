package Datatypes;

public class DataEstadistica {
	
	private String nombre_mundo;
	private int nro_nivel;
	private int id_problema;
	private int cant_intentos;
	private int cant_aciertos;
	private String url_problema;

	//----CONSTRUCTORES----//
	public DataEstadistica(String nombre_mundo, int nro_nivel, int id_problema, int cant_intentos, int cant_aciertos,
			String url_problema) {
		this.nombre_mundo = nombre_mundo;
		this.nro_nivel = nro_nivel;
		this.id_problema = id_problema;
		this.cant_intentos = cant_intentos;
		this.cant_aciertos = cant_aciertos;
		this.url_problema = url_problema;
	}
	
	public DataEstadistica() {}
	
	//----GETTERS----//
	public String getNombre_mundo() {
		return nombre_mundo;
	}
	
	public int getNro_nivel() {
		return nro_nivel;
	}
	
	public int getId_problema() {
		return id_problema;
	}
	
	public int getCant_intentos() {
		return cant_intentos;
	}
	
	public int getCant_aciertos() {
		return cant_aciertos;
	}
	
	public String getUrl_problema() {
		return url_problema;
	}
	
	//----SETTERS----//
	public void setNombre_mundo(String nombre_mundo) {
		this.nombre_mundo = nombre_mundo;
	}
		
	public void setNro_nivel(int nro_nivel) {
		this.nro_nivel = nro_nivel;
	}
		
	public void setId_problema(int id_problema) {
		this.id_problema = id_problema;
	}
		
	public void setCant_intentos(int cant_intentos) {
		this.cant_intentos = cant_intentos;
	}
		
	public void setCant_aciertos(int cant_aciertos) {
		this.cant_aciertos = cant_aciertos;
	}
		
	public void setUrl_problema(String url_problema) {
		this.url_problema = url_problema;
	}

}

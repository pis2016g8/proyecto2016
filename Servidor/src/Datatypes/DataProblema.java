package Datatypes;


public class DataProblema {
	
	private Integer id_problema;
	private String descripcion;
	private String respuesta;
	private int puntos_exp;
	private String ayuda;
	private String contenido;
	private String id_autor;
	private boolean resuelto;
	private boolean tut_activo;
	
	//----CONSTRUCTOR----//
	public DataProblema(Integer id_problema, String descripcion, String respuesta, int puntos_exp, String ayuda,
			String contenido, String id_autor, boolean resuelto, boolean tut_activo) {
		this.id_problema = id_problema;
		this.descripcion = descripcion;
		this.respuesta = respuesta;
		this.puntos_exp = puntos_exp;
		this.ayuda = ayuda;
		this.contenido = contenido;
		this.id_autor = id_autor;
		this.resuelto = resuelto;
		this.tut_activo = tut_activo;
	}
	
	//----GETTERS----//
	public Integer getId_problema() {
		return id_problema;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public int getPuntos_exp() {
		return puntos_exp;
	}

	public String getAyuda() {
		return ayuda;
	}

	public String getContenido() {
		return contenido;
	}

	public String getId_autor() {
		return id_autor;
	}

	public boolean isResuelto() {
		return resuelto;
	}

	public boolean isTut_activo() {
		return tut_activo;
	} 	

}

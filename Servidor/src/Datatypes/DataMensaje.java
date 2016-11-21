package Datatypes;

import java.util.Date;

public class DataMensaje {
	
	private int id;
	private String asunto;
	private String contenido;
	private Date fecha;
	private String remitente;
	
	//----CONSTRUCTOR----//
	public DataMensaje(int id, String asunto, String contenido, Date fecha, String remitente) {
		super();
		this.id = id;
		this.asunto = asunto;
		this.contenido = contenido;
		this.fecha = fecha;
		this.remitente = remitente;
	}
	
	//----GETTERS----//
	public int getId() {
		return id;
	}
	
	public String getAsunto() {
		return asunto;
	}
	
	public String getContenido() {
		return contenido;
	}
	
	public String getRemitente(){
		return remitente;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	//----SETTERS----//
	public void setId(int id) {
		this.id = id;
	}
		
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
		
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
		
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}

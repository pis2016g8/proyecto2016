package Modelo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "MENSAJE")
public class Mensaje {

	@Id  @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_mensaje;
	private String contenido;
	private String asunto;
	private Date fecha;
	private String id_remitente;
	
	//----CONSTRUCTORES----//
	public Mensaje(){}
	
	public Mensaje(String contenido, String asunto, Date fecha, String id_remitente) {
		this.contenido = contenido;
		this.asunto = asunto;
		this.fecha = fecha;
		this.id_remitente = id_remitente;
	}
	
	//----GETTERS----//
	public int getId() {
		return id_mensaje;
	}
	
	public String getContenido() {
		return contenido;
	}
	
	public String getAsunto() {
		return asunto;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public String getRemitente(){
		return id_remitente;
	}
	
	//----SETTERS----//
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asunto == null) ? 0 : asunto.hashCode());
		result = prime * result + ((contenido == null) ? 0 : contenido.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + id_mensaje;
		result = prime * result + ((id_remitente == null) ? 0 : id_remitente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensaje other = (Mensaje) obj;
		if (asunto == null) {
			if (other.asunto != null)
				return false;
		} else if (!asunto.equals(other.asunto))
			return false;
		if (contenido == null) {
			if (other.contenido != null)
				return false;
		} else if (!contenido.equals(other.contenido))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id_mensaje != other.id_mensaje)
			return false;
		if (id_remitente == null) {
			if (other.id_remitente != null)
				return false;
		} else if (!id_remitente.equals(other.id_remitente))
			return false;
		return true;
	}
	
}

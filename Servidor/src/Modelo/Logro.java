package Modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import Datatypes.DataLogro;
@Entity
@Table(name = "LOGRO")
public class Logro {
	
	 @Id  @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_logro;
	private String descripcion;
	private int id; //habr�a q borrar este
	
	//----CONSTRUCTORES----//
	public Logro(String descripcion){
		this.descripcion=descripcion;
	}
	
	public Logro (){
		
	}
	
	//----GETTERS----//
	public int getId() {
		return id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	//----SETTERS----//
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	//----OPERACIONES----//
	//Retorna el DataLogro correspondiente al Logro
	public DataLogro obtenerDataLogro(){
		DataLogro dl = new DataLogro(descripcion, id);
		return dl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id;
		result = prime * result + id_logro;
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
		Logro other = (Logro) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		if (id_logro != other.id_logro)
			return false;
		return true;
	}
}

package Modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "CONTENIDO")
public class Contenido {
	
	@Id  @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_contenido;
	private String url;

	//----CONSTRUCTORES----//
	public Contenido(){}
	
	public Contenido(String url){
		this.url = url;
	}
	
	//----GETTERS----//
	public String getURL() {
		return url;
	}

	//----SETTERS----//
	public void setURL(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_contenido;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Contenido other = (Contenido) obj;
		if (id_contenido != other.id_contenido)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}

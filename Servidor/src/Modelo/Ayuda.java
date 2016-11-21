package Modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "AYUDA")
public class Ayuda {
	
	@Id  @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_ayuda;
	private String info;
	
	//----CONSTRUCTORES----//
	public Ayuda(){}
	
	public Ayuda(String info){
		this.info = info;
	}
	
	//----GETTERS----//
	public String getInfo() {
		return info;
	}

	//----SETTERS----//
	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_ayuda;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
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
		Ayuda other = (Ayuda) obj;
		if (id_ayuda != other.id_ayuda)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}

}

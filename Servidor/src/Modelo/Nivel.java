package Modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
@Table(name = "NIVEL")
public class Nivel {
	
	@Id  @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_nivel;
	private int nro_nivel;//seria el num de nivel dentro de un mundo
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "nivel") @LazyCollection(LazyCollectionOption.FALSE)
	private List<Problema> problemas = new ArrayList<Problema>();
	@ManyToOne @LazyCollection(LazyCollectionOption.FALSE)
	private Mundo mundo;
	private int nro_problema;
	
	//----CONSTRUCTORES----//
	public Nivel(List<Problema> problemas, Mundo mundo) {
		this.problemas = problemas;
		this.mundo = mundo;
		this.nro_problema=0;
	}
	
	public Nivel(){}
	
	//----GETTERS----//
	public int getNro_nivel(){
		return nro_nivel;
	}
	
	public int getId_nivel(){
		return id_nivel;
	}
	
	public List<Problema> getProblemas() {
		return problemas;
	}
	
	public Mundo getMundo() {
		return mundo;
	}
	
	//----SETTERS----//
	public void setNivel(int n){
		nro_nivel=n;
	}
	
	public void setId(int n){
		id_nivel=n;
	}
	
	public void setProblemas(List<Problema> problemas) {
		this.problemas = problemas;
	}

	public void setMundo(Mundo mundo) {
		this.mundo = mundo;
	}
	
	
	public int asignarNumeroProblema(){
		int var=this.nro_problema;
		this.nro_problema++;
		return var;
	}
	
	//----OPERACIONES----//
	//Retorna TRUE si el Problema con id = id_problema es el ultimo problema de su Nivel
	//En caso contrario retorna FALSE
	public boolean esUltima(int id_problema){
		int it = 0;
		boolean encontre = false;
		int tamano = problemas.size();
		
		while (it < tamano &&  !encontre ){
			encontre = (problemas.get(it).getId() == id_problema);
			it++;
		}
		
		return (encontre && (it <= tamano));//Si it es menor que el tama�o es que queda algun problema m�s
		//HAY QUE VER QUE PASA SI NO ENCUENTRA EL id_problema
	}
	
	//Agrega el Problema p a la lista de problemas del Nivel
	public void agregarProblema(Problema p){
		p.setNro_problema(nro_problema);
		nro_problema++;
		problemas.add(p);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_nivel;
		result = prime * result + ((mundo == null) ? 0 : mundo.hashCode());
		result = prime * result + nro_nivel;
		result = prime * result + ((problemas == null) ? 0 : problemas.hashCode());
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
		Nivel other = (Nivel) obj;
		if (id_nivel != other.id_nivel)
			return false;
		if (mundo == null) {
			if (other.mundo != null)
				return false;
		} else if (!(mundo.getId()==(other.mundo.getId())))
			return false;
		if (nro_nivel != other.nro_nivel)
			return false;
		if (problemas == null) {
			if (other.problemas != null)
				return false;
		} else if (!problemas.equals(other.problemas))
			return false;
		return true;
	}

}

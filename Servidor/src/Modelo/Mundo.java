package Modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
@Entity
@Table(name = "MUNDO")
public class Mundo {
	
	@Id 
	private int id_mundo;
	private int nro_nivel;
	private String nombre;
	private String imagen;
	private String descripcion;
	private int puntos_exp;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="mundo_siguiente", joinColumns={@JoinColumn (name="id_mundo_anterior", referencedColumnName= "id_mundo" )},inverseJoinColumns={@JoinColumn(name="id_mundo_sig",referencedColumnName="id_mundo")})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Mundo> mundos_siguientes = new ArrayList<Mundo>();
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "mundo") @LazyCollection(LazyCollectionOption.FALSE)
	private List<Nivel> niveles = new ArrayList<Nivel>(); 
	
	//----CONSTRUCTORES----//
	public Mundo(int id_mundo,String nombre, String imagen, String descripcion, int puntos_exp,
			List<Mundo> mundos_siguientes, List<Nivel> niveles) {
		this.id_mundo=id_mundo;
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.puntos_exp = puntos_exp;
		this.mundos_siguientes = mundos_siguientes;
		this.niveles = niveles;
		this.nro_nivel = 0;
	}
	
	public Mundo(){}
	
	//----GETTERS----//
	public int getId() {
		return id_mundo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getImagen() {
		return imagen;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public int getPuntos_exp() {
		return puntos_exp;
	}
	
	public List<Mundo> getMundos_siguientes() {
		return mundos_siguientes;
	}

	public List<Nivel> getNiveles() {
		return niveles;
	}
	
	public int getNro_nivel() {
		return nro_nivel;
	}

	//----SETTERS----//
	public void setId(int id) {
		this.id_mundo = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setPuntos_exp(int puntos_exp) {
		this.puntos_exp = puntos_exp;
	}

	public void setMundos_siguientes(ArrayList<Mundo> mundos_siguientes) {
		this.mundos_siguientes = mundos_siguientes;
	}

	public void setNiveles(List<Nivel> niveles) {
		this.niveles = niveles;
	}

	public void setNro_nivel(int nro_nivel) {
		this.nro_nivel = nro_nivel;
	}

	//----OPERACIONES----//
	//Retorna TRUE en caso que el Nivel n sea el ultimo nivel de su Mundo. En caso contrario retorna FALSE.
	public boolean ultimoNivelMundo(Nivel n){

		if(n.getMundo().getId() == this.id_mundo ){
			int largoMundo = niveles.size();
			Nivel ultimo_nivel = niveles.get(largoMundo - 1);
			return (ultimo_nivel.getId_nivel() == n.getId_nivel());
		}else{
			return false;
		}
	}
	
	//Retorna el Nivel siguiente al Nivel = nivel.
	public Nivel siguienteNivel(Nivel nivel){//PRECONDICION nivel es un Nivel del MUNDO
		return niveles.get(nivel.getNro_nivel() + 1);
	}
	
	//Agrega el Nivel n a la lista de Niveles
	public void agregarNivel(Nivel n){
		n.setNivel(nro_nivel);
		nro_nivel++;
		niveles.add(n);
	}
	
	//Retorna el Nivel con id = id_nivel. 
	//En caso que no exista el nivel con dicho id retorna NULL.
	public Nivel buscarNivel(int id_nivel){
		for(Nivel n: niveles){
			if(n.getId_nivel() == id_nivel){
				return n;
			}
		}
		return null;
	}
	
	//Retorna el Nivel con numero = nro_nivel
	//En caso que no exista el nivel con dicho numero retorna NULL.
	public Nivel buscarNivelPorNro(int nro_nivel){
		for(Nivel n: niveles){
			if(n.getNro_nivel() == nro_nivel){
				return n;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id_mundo;
		result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
		result = prime * result + ((mundos_siguientes == null) ? 0 : mundos_siguientes.hashCode());
		result = prime * result + ((niveles == null) ? 0 : niveles.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + nro_nivel;
		result = prime * result + puntos_exp;
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
		Mundo other = (Mundo) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id_mundo != other.id_mundo)
			return false;
		if (imagen == null) {
			if (other.imagen != null)
				return false;
		} else if (!imagen.equals(other.imagen))
			return false;
		if (mundos_siguientes == null) {
			if (other.mundos_siguientes != null)
				return false;
		} else if (!(mundos_siguientes.size()== other.mundos_siguientes.size()))
			return false;
		if (niveles == null) {
			if (other.niveles != null)
				return false;
		} else if (!niveles.equals(other.niveles))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nro_nivel != other.nro_nivel)
			return false;
		if (puntos_exp != other.puntos_exp)
			return false;
		return true;
	}
	
}

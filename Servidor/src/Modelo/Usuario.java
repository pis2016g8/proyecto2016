package Modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
@Entity
@Table(name = "USUARIO")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Usuario {  
	
	@Id
	@Column(name="nick")
	private String nick;
	private String nombre;
	@OneToMany(cascade=CascadeType.ALL)  
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="usuario_mensajes_viejos", joinColumns={@JoinColumn (name="id_usuario", referencedColumnName= "nick" )},inverseJoinColumns={@JoinColumn(name="id_mensaje",referencedColumnName="id_mensaje")})
	private List<Mensaje> mensajes_viejos = new ArrayList<Mensaje>();
	@OneToMany(cascade=CascadeType.ALL) 
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="usuario_mensajes_nuevos", joinColumns={@JoinColumn (name="id_usuario", referencedColumnName= "nick" )},inverseJoinColumns={@JoinColumn(name="id_mensaje",referencedColumnName="id_mensaje")})
	private List<Mensaje> mensajes_nuevos = new ArrayList<Mensaje>();
	
	//----CONSTRUCTORES----//
	public Usuario(String nombre, String nick) {
		
		this.nombre = nombre;
		this.nick = nick;
	}
	public Usuario(){};
	
	//----GETTERS----//
	public String getNombre() {
		return nombre;
	}
	public String getNick() {
		return nick;
	}
	public List<Mensaje> getMensajes_viejos() {
		return mensajes_viejos;
	}
	public List<Mensaje> getMensajes_nuevos() {
		return mensajes_nuevos;
	}
	
	//----SETTERS----//
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setMensajes_viejos(List<Mensaje> mensajes_viejos) {
		this.mensajes_viejos = mensajes_viejos;
	}

	public void setMensajes_nuevos(List<Mensaje> mensajes_nuevos) {
		this.mensajes_nuevos = mensajes_nuevos;
	}
	
	//----OPERACIONES----//
	//Agrega el Mensaje mensaje a la lista de mensajes nuevos
	public void agregar_mensaje_nuevo(Mensaje mensaje) {
		this.mensajes_nuevos.add(mensaje);
	}
	
	//Agrega el Mensaje mensaje a la lista de mensajes viejos
	public void agregar_mensaje_viejo(Mensaje mensaje) {
		this.mensajes_viejos.add(mensaje);
	}
	
	//Retorna TRUE si el Mensaje con id = id_mensaje es un mensaje nuevo
	public boolean esMensajeNuevo(int id_mensaje){
		for(Mensaje m: mensajes_nuevos){
			if(m.getId() == id_mensaje){
				return true;
			}
		}
		return false;
	}
	
	//Cambia el Mensaje con id = id_mensaje de mensajes_nuevos a mensajes_viejos
	//PRECONDICION: se ejecuta previamente esMensajeNuevo y en caso que de TRUE se ejecuta esta operacion.
	public void mensajeLeido(int id_mensaje){
		for(Mensaje m: mensajes_nuevos){
			if(m.getId() == id_mensaje){
				mensajes_viejos.add(m);
				mensajes_nuevos.remove(m);
				break;
			}
		}
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mensajes_nuevos == null) ? 0 : mensajes_nuevos.hashCode());
		result = prime * result + ((mensajes_viejos == null) ? 0 : mensajes_viejos.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Usuario other = (Usuario) obj;
		if (mensajes_nuevos == null) {
			if (other.mensajes_nuevos != null)
				return false;
		} else if (!mensajes_nuevos.equals(other.mensajes_nuevos))
			return false;
		if (mensajes_viejos == null) {
			if (other.mensajes_viejos != null)
				return false;
		} else if (!mensajes_viejos.equals(other.mensajes_viejos))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}

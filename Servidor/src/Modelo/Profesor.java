package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
@Entity
@Table(name = "PROFESOR")
@PrimaryKeyJoinColumn(name="nick")
public class Profesor extends Usuario {

	private String password;
	

	@OneToMany(cascade=CascadeType.ALL) 
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="profesor_reportes_nuevos", joinColumns={@JoinColumn (name="id_usuario", referencedColumnName= "nick" )},inverseJoinColumns={@JoinColumn(name="id_mensaje",referencedColumnName="id_mensaje")})
	private List<Mensaje> reportes_nuevos = new ArrayList<Mensaje>();
	
	@OneToMany(cascade=CascadeType.ALL) 
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="profesor_reportes_viejos", joinColumns={@JoinColumn (name="id_usuario", referencedColumnName= "nick" )},inverseJoinColumns={@JoinColumn(name="id_mensaje",referencedColumnName="id_mensaje")})
	private List<Mensaje> reportes_viejos = new ArrayList<Mensaje>();

	//----CONSTRUCTORES----//
	public Profesor(){
		super();
	};

	public Profesor(String nombre, String nick, String password, List<Mensaje> reportes_nuevos,
			List<Mensaje> reportes_viejos) {
		super(nombre, nick);
		this.password = password;
		this.reportes_nuevos = reportes_nuevos;
		this.reportes_viejos = reportes_viejos;
	}



	//----GETTERS----//
	public String getPassword() {
		return password;
	}
	
	public List<Mensaje> getReportes_nuevos() {
		return reportes_nuevos;
	}

	public List<Mensaje> getReportes_viejos() {
		return reportes_viejos;
	}
	
	//----SETTERS----//
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setReportes_nuevos(List<Mensaje> reportes_nuevos) {
		this.reportes_nuevos = reportes_nuevos;
	}
	
	public void setReportes_viejos(List<Mensaje> reportes_viejos) {
		this.reportes_viejos = reportes_viejos;
	}
	
	//METODOS A IMPLEMENTAR

	//----OPERACIONES----//
	// Envia el mensaje con contenido = mensaje, fecha = fecha, asunto = asunto, remitente = id_jugador a 
	//la lista de mensajes nuevos
	public void enviarMensaje(String mensaje,Date fecha, String asunto,String id_jugador){
		Mensaje m=new Mensaje(mensaje,asunto, fecha,id_jugador);
		this.getMensajes_nuevos().add(m);
	}
	
	//Agrega el Mensaje reporte a la lista de Mensajes reportes_nuevos
	public void agregarReporte(Mensaje reporte){
		this.reportes_nuevos.add(reporte);
	}
	
	//Retorna TRUE si el Mensaje con id = id_mensaje es
	//un mensaje de reporte_nuevo
	public boolean esReporteNuevo(int id_mensaje){
		for(Mensaje m: reportes_nuevos){
			if(m.getId() == id_mensaje){
				return true;
			}
		}
		return false;
	}
	
	//Cambia el Mensaje con id = id_mensaje de reportes_nuevos a reportes_viejos
	//PRECONDICION: se ejecuta previamente esReporteNuevo y en caso que de TRUE se ejecuta esta operacion.
	public void reporteLeido(int id_mensaje){
		for(Mensaje m: reportes_nuevos){
			if(m.getId() == id_mensaje){
				reportes_viejos.add(m);
				reportes_nuevos.remove(m);
				break;
			}
		}
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Profesor other = (Profesor) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}

package Modelo;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import Persistencia.HibernateUtility;
@Entity
@Table(name = "PROBLEMA")
public class Problema {
	
	@Id  @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id_problema;
	private Integer nro_problema;
	private String descripcion;
	private String respuesta;
	private int puntos_exp;
	@OneToOne(cascade = CascadeType.ALL) @LazyCollection(LazyCollectionOption.FALSE)
	private Estadistica estadisticas;
	@OneToOne(cascade = CascadeType.ALL) @LazyCollection(LazyCollectionOption.FALSE)
	private Ayuda ayuda;
	@OneToOne(cascade = CascadeType.ALL) @LazyCollection(LazyCollectionOption.FALSE)
	private Contenido contenido;
	@ManyToOne @LazyCollection(LazyCollectionOption.FALSE)
	private Nivel nivel;
	@ManyToOne @LazyCollection(LazyCollectionOption.FALSE)
	private Profesor autor; 
	
	//----CONSTRUCTORES----//
	public Problema(String descripcion, String respuesta, int puntos_exp, Ayuda ayuda, Contenido contenido,
			Nivel nivel, Profesor autor, Estadistica estadisticas) {
		this.descripcion = descripcion;
		this.respuesta = respuesta;
		this.puntos_exp = puntos_exp;
		this.ayuda = ayuda;
		this.contenido = contenido;
		this.nivel = nivel;
		this.autor = autor;
		this.estadisticas = estadisticas;
	}
	
	public Problema(){};

	//----GETTERS----//
	public int getId() {
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
	
	public Ayuda getAyuda() {
		return ayuda;
	}
	
	public Contenido getContenido() {
		return contenido;
	}
	
	public Nivel getNivel() {
		return nivel;
	}
	
	public Profesor getAutor() {
		return autor;
	}
	
	public Estadistica getEstadisticas() {
		return estadisticas;
	}
	
	public Integer getNro_problema() {
		return nro_problema;
	}
	
	//----SETTERS----//
	public void setId(int id) {
		this.id_problema = id;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public void setPuntos_exp(int puntos_exp) {
		this.puntos_exp = puntos_exp;
	}

	public void setAyuda(Ayuda ayuda) {
		this.ayuda = ayuda;
	}

	public void setContenido(Contenido contenido) {
		this.contenido = contenido;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public void setAutor(Profesor autor) {
		this.autor = autor;
	}
	
	public void setEstadisticas(Estadistica estadisticas) {
		this.estadisticas = estadisticas;
	}


	public void setNro_problema(Integer nro_problema) {
		this.nro_problema = nro_problema;
	}

	//----OPERACIONES----//
	//Retorna TRUE si la respuesta es la respuesta correcta del problema
	//En caso contrario retorna FALSE
	public boolean verificarRespuesta(String respuesta){
		String sin_espacios = respuesta.replaceAll("\\s","");
		return sin_espacios.equalsIgnoreCase(this.respuesta.replaceAll("\\s",""));
	}
	
	//Envia el mensaje con contenido = mensaje, fecha = fecha, asunto = asunto, remitente = id_jugador al Profesor autor
	//de dicho problema.
	public void enviarMensaje(String mensaje,Date fecha, String asunto,String id_jugador){
		autor.enviarMensaje(mensaje, fecha, asunto,id_jugador);
		
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		session.saveOrUpdate(autor);
		t.commit();
		session.close();
		System.out.println("successfully saved mensaje");
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((ayuda == null) ? 0 : ayuda.hashCode());
		result = prime * result + ((contenido == null) ? 0 : contenido.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id_problema == null) ? 0 : id_problema.hashCode());
		result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
		result = prime * result + puntos_exp;
		result = prime * result + ((respuesta == null) ? 0 : respuesta.hashCode());
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
		Problema other = (Problema) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (ayuda == null) {
			if (other.ayuda != null)
				return false;
		} else if (!ayuda.equals(other.ayuda))
			return false;
		if (contenido == null) {
			if (other.contenido != null)
				return false;
		} else if (!contenido.equals(other.contenido))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id_problema == null) {
			if (other.id_problema != null)
				return false;
		} else if (!id_problema.equals(other.id_problema))
			return false;
		if (nivel == null) {
			if (other.nivel != null)
				return false;
		} else if (!(nivel.getNro_nivel() == (other.nivel.getNro_nivel())))
			return false;
		if (puntos_exp != other.puntos_exp)
			return false;
		if (respuesta == null) {
			if (other.respuesta != null)
				return false;
		} else if (!respuesta.equals(other.respuesta))
			return false;
		return true;
	}

}

package Datatypes;

import java.util.List;

public class DataMundo {
	
	private int id_mundo;
	private String nombre;
	private String imagen;
	private String descripcion;
	private boolean mundo_completado;
	private boolean mundo_disponible;
	private List<Integer> mundos_siguientes;
	
	//----CONSTRUCTOR----//
	public DataMundo(int id_mundo, String nombre, String imagen, String descripcion, boolean mundo_completado,
			boolean mundo_disponible, List<Integer> mundos_siguientes) {
		this.id_mundo = id_mundo;
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.mundo_completado = mundo_completado;
		this.mundo_disponible = mundo_disponible;
		this.mundos_siguientes = mundos_siguientes;
	}
	
	//----GETTERS----//
	public int getId_mundo() {
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
	
	public boolean isMundo_completado() {
		return mundo_completado;
	}
	
	public boolean isMundo_disponible() {
		return mundo_disponible;
	}

	public List<Integer> getMundos_siguientes() {
		return mundos_siguientes;
	}

}

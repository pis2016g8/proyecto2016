package Datatypes;

import java.util.List;

public class DataJugador {
	
	private String nick;
	private String imagen;
	private List<DataMundoNivel> mundos_niveles; 
	private int experiencia;
	private List<DataLogro> logros;
	
	//----CONSTRUCTOR----//
	public DataJugador(String nick, String imagen, List<DataMundoNivel> mundos_niveles, int experiencia, List<DataLogro> logros) {
		this.nick = nick;
		this.imagen = imagen;
		this.mundos_niveles = mundos_niveles;
		this.experiencia = experiencia;
		this.logros = logros;
	}
	
	//----GETTERS----//
	public String getNick() {
		return nick;
	}
	
	public String getimagen() {
		return imagen;
	}
	
	public List<DataMundoNivel> getMundosNiveles() {
		return mundos_niveles;
	}
	
	public int getExperiencia() {
		return experiencia;
	}
	
	public List<DataLogro> getLogros() {
		return logros;
	}
	
	//----SETTERS----//
	public void setNick(String nick) {
		this.nick = nick;
	}
		
	public void setimagen(String imagen) {
		this.imagen = imagen;
	}
		
	public void setMundoNivel(List<DataMundoNivel> mundos_niveles) {
		this.mundos_niveles = mundos_niveles;
	}
	
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}
		
	public void setLogros(List<DataLogro> logros) {
		this.logros = logros;
	}
	
}

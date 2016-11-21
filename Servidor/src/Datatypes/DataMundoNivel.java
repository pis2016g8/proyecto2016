package Datatypes;

public class DataMundoNivel {
	
	private String mundo;
	private int nivel;
	private boolean mundo_completo;
	
	//----CONSTRUCTOR----//
	public DataMundoNivel(String mundo, int nivel, boolean mundo_completo) {
		this.mundo = mundo;
		this.nivel = nivel;
		this.mundo_completo = mundo_completo;
	}
	
	//----GETTERS----//
	public String getMundo() {
		return mundo;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public boolean isMundo_completo() {
		return mundo_completo;
	}
	
	//----SETTERS----//
	public void setMundo(String mundo) {
		this.mundo = mundo;
	}
		
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
		
	public void setMundo_completo(boolean mundo_completo) {
		this.mundo_completo = mundo_completo;
	}

}

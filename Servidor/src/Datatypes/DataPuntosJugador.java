package Datatypes;

public class DataPuntosJugador implements Comparable<DataPuntosJugador>{
	
	private String nick;
	private Integer puntos;

	//----CONSTRUCTORES----//
	public DataPuntosJugador(){
		nick = null;
		puntos = 0;
	}
	
	public DataPuntosJugador(String nick, Integer puntos){
		this.nick = nick;
		this.puntos = puntos;
	}	
	
	//----GETTERS----//
	public String getNick(){
		return nick;
	}
	
	public Integer getPuntos(){
		return puntos;
	}

	//----SETTERS----//
	public void setNick(String nick){
		this.nick = nick;
	}
	
	public void setPuntos(Integer puntos){
		this.puntos = puntos;
	}
	
    @Override
    public int compareTo(DataPuntosJugador dpj) {
    	return dpj.puntos - this.puntos;
    }
	
}

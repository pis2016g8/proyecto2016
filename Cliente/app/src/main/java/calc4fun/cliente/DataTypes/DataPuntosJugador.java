package calc4fun.cliente.DataTypes;

public class DataPuntosJugador implements Comparable<DataPuntosJugador>{

	private String nick;
	private Integer puntos;

	public DataPuntosJugador(){
		nick = null;
		puntos = 0;
	}

	public DataPuntosJugador(String nick, Integer puntos){
		this.nick = nick;
		this.puntos = puntos;
	}

	public String getNick(){
		return nick;
	}

	public Integer getPuntos(){
		return puntos;
	}

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

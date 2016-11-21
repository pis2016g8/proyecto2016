package Datatypes;

public class DataLogin {

	private String nick;
	private String nombre;
	private boolean existe_token;
	
	public DataLogin(String nombre, String nick, boolean existe_token) {
		this.nombre = nombre;
		this.nick = nick;
		this.existe_token = existe_token;
	}
	
	//----GETTERS----//
	public String getNombre() {
		return nombre;
	}

	public String getNick() {
		return nick;
	}

	public boolean isExiste_token() {
		return existe_token;
	}

}

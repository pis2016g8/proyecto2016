package Controladores;


import Datatypes.DataAyuda;
import Datatypes.DataEstadoMensaje;
import Datatypes.DataExperiencia;

public interface IControladorProblema {
	
	public DataExperiencia responderProblema(int id_problema, String respuesta, String id_jugador);
	public DataEstadoMensaje enviarMensaje(int id_problema, String nick,String mensaje, String fecha, String asunto);
	public void reportarProblema(int id_problema, String nick, String mensaje);
	public DataAyuda getAyuda(int id_problema);
	public void agregarProblema(String descripcion,String respuesta,int puntos_exp,String cont_ayuda,
			String cont,int id_mundo,int num_nivel,String nick_prof);
	public void modificarProblema(int id_problema, String descripcion,String respuesta,int puntos_exp,String cont_ayuda,
			String cont);
}

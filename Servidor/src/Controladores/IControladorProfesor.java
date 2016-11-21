package Controladores;



import org.springframework.web.bind.annotation.RequestParam;

import Datatypes.DataListEstadisticas;
import Datatypes.DataListaDataProblema;
import Datatypes.DataListaMensajes;
import Datatypes.DataListaMundos;
import Datatypes.DataListaNiveles;
import Datatypes.DataMensaje;

public interface IControladorProfesor {
	
	public DataMensaje verMensaje(int id_mensaje);//Funciona para mensajes de todo tipo, sea mensaje comun o reporte
	public DataListaMensajes verMensajesNuevos(String nick);
	public DataListaMensajes verMensajesViejos(String nick);
	public void responderMensaje(String nick_jugador,String asunto,String contenido,String id_profesor);
	public void mensajeleido(String nick_prof,int id_mensaje);
	public DataListaMundos listarMundosProfesor();
	public DataListaNiveles listarNivelesMundoProfesor(int id_mundo);
	public DataListEstadisticas verEstadisticas();
	public void agregarNivel(int id_mundo);
	public void agregarMundo(String nombre,String imagen, int exp, String desc);
	public DataListaMensajes verReportesNuevos(String nick);
	public DataListaMensajes verReportesViejos(String nick);
	public void reporteleido(String nick_prof,int id_mensaje);
	public DataListaDataProblema listarProblemasNivelProfesor(int id_mundo,int id_nivel);
		
}
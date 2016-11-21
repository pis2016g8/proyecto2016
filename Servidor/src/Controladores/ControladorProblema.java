package Controladores;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Datatypes.DataAyuda;
import Datatypes.DataEstadoMensaje;
import Datatypes.DataExperiencia;
import Datatypes.DataTypeConstants;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.Ayuda;
import Modelo.Contenido;
import Modelo.Estadistica;
import Modelo.Jugador;
import Modelo.Mensaje;
import Modelo.Nivel;
import Modelo.Problema;
import Modelo.Profesor;

@CrossOrigin
@RestController
public class ControladorProblema implements IControladorProblema{
	
	//METODOS A IMPLEMENTAR
	@RequestMapping(value="/responderProblema", method=RequestMethod.GET)
	public DataExperiencia responderProblema(@RequestParam(value="id_problema") int id_problema,@RequestParam(value="respuesta") String respuesta,@RequestParam(value="id_jugador") String id_jugador){//JUAN
		//Operacion que dado una respuesta para el problema, chequea si la respuesta es correcta, otorga experiencia, logros y desbloquea niveles y mundos
		ManejadorProblema mp = ManejadorProblema.getInstancia();
		IControladorJugador cu = new ControladorJugador();
		IControladorSistemaJuego csj = new ControladorSistemaJuego();
		//Se verifica la respuesta
		int exp_ganada = mp.verificarRespuesta(id_problema, respuesta);
		//Se obtiene el problema
		Problema p = mp.buscarProblema(id_problema);
		Estadistica estadisticas = p.getEstadisticas();
		if(exp_ganada == 0){
			//Si no se respondio correctamente
			ManejadorUsuario mu = ManejadorUsuario.getInstancia();
			//Se obtiene el jugador
			Jugador jugador = mu.buscarJugador(id_jugador);
			List<Integer> lista = jugador.getEstado().getProblemas_tutorial_activo();
			//Si el problema no tiene el tutorial activo, se agrega el problema a la lista de problemas con tutorial activo
			if(!lista.contains(id_problema)){
				lista.add(id_problema);
			}
			//Se guardan los datos del jugador
			mu.agregarJugador(jugador);
		}
		if(!cu.yaRespondida(id_jugador, id_problema)){
			//Si el jugador no respondio el problema antes, se aumenta en uno los intentos
			estadisticas.aumentarIntentos();
			if(exp_ganada > 0){
				//Si se respondio bien, se aumentan los aciertos
				estadisticas.aumentarAciertos();
				//Se suman los puntos al jugador y se otorgan logros
				cu.sumarPuntos(exp_ganada, id_jugador, id_problema);
				int id_mundo = p.getNivel().getMundo().getId();
				//Se avanza el juego, desbloqueando niveles y/o logros
				csj.avanzarJuego(id_jugador, id_problema, id_mundo);
			}
			//Se persiste el problema
			mp.agregarProblema(p);
		}
		
		return new DataExperiencia(exp_ganada);
	}
	
	@RequestMapping(value="/enviarmensaje", method=RequestMethod.GET)
	public DataEstadoMensaje enviarMensaje(@RequestParam(value="id_problema") int id_problema,@RequestParam(value="nick") String nick,@RequestParam(value="mensaje") String mensaje,@RequestParam(value="fecha") String fechaStr,@RequestParam(value="asunto") String asunto){ 
		//Operacion para enviar un mensaje
		Date fecha;
		try {
			
			//Se parsea la fecha a un tipo Date
			fecha = DataTypeConstants.getDateFormat().parse(fechaStr);
			ManejadorProblema mp=ManejadorProblema.getInstancia();
			//Se busca el problema desde el cual se envia el mensaje
			Problema p=mp.buscarProblema(id_problema);
			//Al asunto del mensaje se le agrega de que mundo, nivel y problema es.
			String apendice= " - "+p.getNivel().getMundo().getNombre() + " Nivel: " + p.getNivel().getNro_nivel() + " Problema: "+ p.getNro_problema();
			//Se envia el mensaje
			p.enviarMensaje(URLDecoder.decode(mensaje, "UTF-8") , fecha, asunto+apendice,nick);
			//Se retorna true, indicando que se envio el mensaje corractamente
			return new DataEstadoMensaje(true);
		
		} catch (ParseException e) {			
			e.printStackTrace();
			return new DataEstadoMensaje(false);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new DataEstadoMensaje(false);
		}
	}
	
	@RequestMapping(value="/getayuda", method=RequestMethod.GET)
	public DataAyuda getAyuda(@RequestParam(value="id_problema") int id_problema){
		//Operacion para obtener la ayuda de un problema
		ManejadorProblema manejador = ManejadorProblema.getInstancia();
		//Se retorna la ayuda del problema
		return new DataAyuda(manejador.getAyuda(id_problema));
	}
	
	@RequestMapping(value="/agregarproblema", method=RequestMethod.POST)
	public void agregarProblema(@RequestParam(value="desc")String descripcion, @RequestParam(value="resp")String respuesta,
			@RequestParam(value="exp")int puntos_exp, @RequestParam(value="ayuda")String cont_ayuda, @RequestParam(value="cont")String cont,
			@RequestParam(value="id_mundo") int id_mundo,@RequestParam(value="num_nivl")int num_nivel, @RequestParam(value="nick_prof")String nick_prof){
	//Operacion para agregar un problema a un mundo y nivel indicado
		//Se crea la ayuda y el contenido del problema
		Ayuda ayuda = new Ayuda(cont_ayuda);
		Contenido contenido = new Contenido(cont);
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		
		
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se obtiene el profesor que agrega el problema
		Profesor profe = mu.buscarProfesor(nick_prof);
		//Se obtiene el nivel en el cual se va a agregar el nivel
		Nivel nivel = mm.obtenerMundo(id_mundo).buscarNivelPorNro(num_nivel);
		//Se crea el problema con sus datos
		Problema problema = new Problema( descripcion, respuesta, puntos_exp, ayuda, contenido, nivel, profe, new Estadistica(0,0));
		//Se le asigna el numero al problema
		problema.setNro_problema(nivel.asignarNumeroProblema());
		ManejadorProblema mp = ManejadorProblema.getInstancia();
		//Se guarda el problema en la base de datos
		mp.agregarProblema(problema);
	}
	@RequestMapping(value="/reportarproblema", method=RequestMethod.POST)
	public void reportarProblema(@RequestParam(value="id_problema") int id_problema,@RequestParam(value="nick") String nick,@RequestParam(value="mensaje") String mensaje){
		//Operacion para reportar un problema
	try {
		ManejadorProblema mp = ManejadorProblema.getInstancia();
		//Se busca el problema
		Problema p = mp.buscarProblema(id_problema);
		//Se obtiene el profesor autor del problema
		Profesor profe = p.getAutor();
		//Se crea el mensaje indicado que el problema fue reportado
		String apendice = p.getNro_problema() + " - Nivel: " + p.getNivel().getNro_nivel() + " - Mundo: "+p.getNivel().getMundo().getNombre();
		Mensaje reporte = new Mensaje( URLDecoder.decode(mensaje, "UTF-8"), "Reporte Problema: "+ apendice, new Date(), nick);
		//Se agrega el reporte a los reportes del profesor
		profe.agregarReporte(reporte);
		//Se guarda el profesor
		ManejadorUsuario.getInstancia().agregarProfesor(profe);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	}
	
	@RequestMapping(value="/modificarproblema", method=RequestMethod.POST)
	public void modificarProblema(@RequestParam(value="id_problema")int id_problema, @RequestParam(value="desc")String descripcion, @RequestParam(value="resp")String respuesta,
			@RequestParam(value="exp")int puntos_exp, @RequestParam(value="ayuda")String cont_ayuda, @RequestParam(value="cont")String cont){
		//Operacion que permite modificar un problema existente
		ManejadorProblema mp = ManejadorProblema.getInstancia();
		//Se obtiene el problema
		Problema problema = mp.buscarProblema(id_problema);
		//Se le asignan los nuevos valores
		problema.setDescripcion(descripcion);
		problema.setRespuesta(respuesta);
		problema.setPuntos_exp(puntos_exp);
		problema.getAyuda().setInfo(cont_ayuda);
		problema.getContenido().setURL(cont);
		//Se guarda el problemas
		mp.agregarProblema(problema);
	}

} 

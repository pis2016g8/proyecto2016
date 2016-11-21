package Controladores;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Datatypes.DataEstadistica;
import Datatypes.DataListEstadisticas;
import Datatypes.DataListaDataProblema;
import Datatypes.DataListaMensajes;
import Datatypes.DataListaMundos;
import Datatypes.DataListaNiveles;
import Datatypes.DataMensaje;
import Datatypes.DataMundo;
import Datatypes.DataNivel;
import Datatypes.DataProblema;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.Jugador;
import Modelo.Mensaje;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;
import Modelo.Profesor;
import Modelo.Usuario;

@CrossOrigin
@RestController
public class ControladorProfesor implements IControladorProfesor{

	@RequestMapping(value="/vermensajesnuevos", method=RequestMethod.GET)
	public DataListaMensajes verMensajesNuevos(@RequestParam(value="nick")String nick){
		//Operacion para ver los mensajes nuevos de un usuario
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se obtiene el usuario
		Usuario usuario = mu.buscarUsuario(nick);
		ArrayList<DataMensaje> lista_nuevos = new ArrayList<DataMensaje>();
		//Para cada mensaje nuevo del usuario, se obtiene sus datos y se los agrega la lista de mensajes nuevos
		for (Mensaje m: usuario.getMensajes_nuevos()){
			lista_nuevos.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(),m.getRemitente()));
		}
		//Se retorna la lista
		return new DataListaMensajes(lista_nuevos);
	}
	
	@RequestMapping(value="/vermensajesviejos", method=RequestMethod.GET)
	public DataListaMensajes verMensajesViejos(@RequestParam(value="nick")String nick){
		//Operacion para ver los mensajes leidos de un usuario
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se busca el usuario
		Usuario usuario = mu.buscarUsuario(nick);
		ArrayList<DataMensaje> lista = new ArrayList<DataMensaje>();
		//Para cada mensaje leido del usuario, se obtiene sus datos y se los agrega la lista de mensajes leidos
		for (Mensaje m: usuario.getMensajes_viejos()){
			lista.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(),m.getRemitente()));
		}
		//Se retorna la lista
		return new DataListaMensajes(lista);
	}
	
	//SI NO ENCUENTRA EL MENSAJE CON DICHO ID, RETORNA NULL.
	@RequestMapping(value="/vermensaje", method=RequestMethod.GET)
	public DataMensaje verMensaje(@RequestParam(value="id_mensaje")int id_mensaje){
		//Operacion para ver un mensaje
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se obtiene el mensajes
		Mensaje m = mu.buscarMensaje(id_mensaje);
		//Se retornan los datos del mensaje
		return new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(), m.getRemitente());
	}

	
	 @RequestMapping(value="/respondermensaje", method=RequestMethod.POST) //Responde un mensaje
	public void responderMensaje(@RequestParam(value="destinatario")String destinatario,@RequestParam(value="asunto")String asunto,@RequestParam(value="contenido")String contenido,@RequestParam(value="remitente")String remitente){
		//Operacion para responder un mensaje
		 try{
			 //Se obtiene la fecha del sistema
			Date fecha = new Date();
			//Se crea el mensaje
			Mensaje m = new Mensaje(URLDecoder.decode(contenido, "UTF-8"), asunto, fecha, remitente);
			
			ManejadorUsuario mu = ManejadorUsuario.getInstancia();
			//Se busca el destinatario y se le agrega el mensaje a los mensajes nuevos
			Usuario u =mu.buscarUsuario(destinatario);
			u.agregar_mensaje_nuevo(m);
			//Se guarda el mensaje y el destinatario
			mu.guardarMensaje(m);
			mu.guardarUsuario(u);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/mensajeleido", method=RequestMethod.POST) //Cambia un mensaje de nuevo a viejo.
	public void mensajeleido(@RequestParam(value="nick")String nick,@RequestParam(value="id_mensaje")int id_mensaje){
		//Operacion para marcar un mensaje como leido
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se busca el usario
		Usuario usuario = mu.buscarUsuario(nick);
		//Si el mensaje es un mensaje nuevo
		if(usuario.esMensajeNuevo(id_mensaje)){
			//Se marca el mensaje como leido y se guarda el usuario
			usuario.mensajeLeido(id_mensaje);
			mu.guardarUsuario(usuario);
		}
	}
	
	@RequestMapping(value="/listarmundosprofesor", method=RequestMethod.GET)
	public DataListaMundos listarMundosProfesor(){
		//Operacion para listar los mundos existentes en el sistem
		List<DataMundo> lista = new ArrayList<DataMundo>();
		
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		//Se obtienen los mundos
		List<Mundo> mundos = mm.obtenerMundos();
		//Para cada mundo se obtienen sus datos y se los agrega a la lista de mundos
		for(Mundo m: mundos){
			lista.add(new DataMundo(m.getId(), m.getNombre(), m.getImagen(), m.getDescripcion(),true, true, null));
		}																//Los dos true son de mundo completado y mundo disponible, para el profesor no tienen sentido pero si para el jugador
		//Se retorna la lista											// El null es la lista de mundos siguientes que creo que no es necesario en la web
		return new DataListaMundos(lista);								
	}
	
	@RequestMapping(value="/listarnivelesmundoprofesor", method=RequestMethod.GET)
	public DataListaNiveles listarNivelesMundoProfesor(@RequestParam(value="id_mundo")int id_mundo){
		//Operacion para listar los niveles de un mundo en el sistema
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		//Se obtiene el mundo
		Mundo mundo = mm.obtenerMundo(id_mundo);
		
		List<DataNivel> lista_niveles = new ArrayList<DataNivel>();
		//Para cada nivel del mundos, se obtienen sus datos y se los agrega a la lista de niveles
		for(Nivel n: mundo.getNiveles()){
			lista_niveles.add(new DataNivel(n.getId_nivel(), n.getNro_nivel(), true, true));
		}
		//Se retorna la lista de niveles
		return new DataListaNiveles(lista_niveles);
	}
	
	@RequestMapping(value="/verestadisticas", method=RequestMethod.GET)
	public DataListEstadisticas verEstadisticas(){
		//Operacion para ver las estadisticas de los problemas
		List<DataEstadistica> lista = new ArrayList<DataEstadistica>();
		ManejadorProblema mp =ManejadorProblema.getInstancia();
		//Para cada problema del sistema, se obtienen los datos del problema y se los agrega a la lista de problemas
		for(Problema p : mp.getProblemas().values()){
			DataEstadistica dt = new DataEstadistica(p.getNivel().getMundo().getNombre(), p.getNivel().getNro_nivel(), p.getId(), p.getEstadisticas().getCant_intentos(), p.getEstadisticas().getCant_aciertos(),p.getContenido().getURL());
			lista.add(dt);
		}
		//Se retorna la lista
		return new DataListEstadisticas(lista);
	}
	@RequestMapping(value="/agregarnivel", method=RequestMethod.POST)
	public void agregarNivel(@RequestParam(value="id_mundo")int id_mundo){
		//Operacion para agregar un nivel a un mundo
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();

		//Se obtiene el mundo
		Mundo mundo_nivel =  mm.obtenerMundo(id_mundo);
		//Se obtiene la cantidad de niveles en el mundo
		int ultimo_nivel = mundo_nivel.getNro_nivel();
		//Se crea el nuevo nivel y se lo asigna al mundo
		Nivel nuevo_nivel = new Nivel(new ArrayList<Problema>(), mundo_nivel);
		//Se agrega el nuevo nivel al mundo
		mundo_nivel.agregarNivel(nuevo_nivel);
		//Se guarda el mundo en el sistema
		mm.agregarMundo(mundo_nivel);
		
		if(nuevo_nivel.getNro_nivel() == 0 && id_mundo > 0){
			//Si el nivel es el primer nivel del mundo, desbloqueo el mundo a los jugadores que completaron el mundo anterior
			int id_m_anterior =id_mundo - 1;
			
			for(Jugador j : mu.obtenerJugadores()){
				boolean encontre = false;
				for(Mundo m_comp : j.getEstado().getMundos_completos()){
					if(m_comp.getId() == id_m_anterior){
						encontre = true;
						break;
					}
				}
				if(encontre){
					//Se desbloquea el mundo y se guarda el jugador
					j.getEstado().agregarMundoActivo(mundo_nivel);
					mu.agregarJugador(j);
					break;
				}
			}
		}
		//Para cada jugador, si el jugador esta en el ultimo nivel de ese mundo, y ese nivel esta completo, se le desloquea el nuevo nivel
		for (Jugador j : mu.obtenerJugadores()){
			Map<Integer,Nivel> mapa = j.getEstado().getNiveles_actuales();
			if (mapa.containsKey(id_mundo) && (mapa.get(id_mundo).getNro_nivel() == ultimo_nivel-1)){
				if(j.getEstado().nivelCompleto(mapa.get(id_mundo))){
					j.getEstado().getNiveles_actuales().put(id_mundo,nuevo_nivel);
					mu.agregarJugador(j);
				}
			}
		}

		
	}
	
	@RequestMapping(value="/agregarmundo", method=RequestMethod.POST)
	//Operacion para agregar un nuevo mundo al sistema
	public void agregarMundo(@RequestParam(value="nombre")String nombre,@RequestParam(value="imagen")String imagen,@RequestParam(value="exp")int exp,@RequestParam(value="desc")String desc){
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		int max_id = -1;
		//Se busca el ultimo mundo
		for(Mundo m: mm.obtenerMundos()){
			if(m.getId() > max_id){
				max_id = m.getId();
			}
		}
		//Se crea el nuevo mundo con los datos obtenidos y se lo agrega al sistema
		Mundo nuevo_mundo = new Mundo(max_id+1, nombre, imagen, desc, exp, new ArrayList<Mundo>(), new ArrayList<Nivel>());
		mm.agregarMundo(nuevo_mundo);
		
		if(max_id >= 0){
			//Si no es el primer mundo del sistama, se busca el mundo anterior y a dicho mundo se le agrega el nuevo mundo como mundo siguiente, y luego se guarda el mundo
			Mundo m_anterior = mm.obtenerMundo(max_id);
			m_anterior.getMundos_siguientes().add(nuevo_mundo);	
			mm.agregarMundo(m_anterior);
		}
	}
	@RequestMapping(value="/verreportesnuevos", method=RequestMethod.GET)
	public DataListaMensajes verReportesNuevos(@RequestParam(value="nick")String nick){
		//Operacion para ver los reportes de problemas no leidos de un profesor
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se busca el profesor
		Profesor profe = mu.buscarProfesor(nick);
		ArrayList<DataMensaje> lista = new ArrayList<DataMensaje>();
		//Para cada reporte no leido, se obtienen los datos y se agregan a la lista de reportes no leidos
		for (Mensaje m: profe.getReportes_nuevos()){
			lista.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(), m.getRemitente()));
		}
		//Se retorna la lista
		return new DataListaMensajes(lista);
		
	}
	@RequestMapping(value="/verreportesviejos", method=RequestMethod.GET)
	public DataListaMensajes verReportesViejos(@RequestParam(value="nick")String nick){
		//Operacion para ver los reportes de problemas leidos de un profesor
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se busca el profesor
		Profesor profe = mu.buscarProfesor(nick);
		ArrayList<DataMensaje> lista = new ArrayList<DataMensaje>();
		//Para cada reporte leido, se obtienen los datos y se agregan a la lista de reportes leidos
		for (Mensaje m: profe.getReportes_viejos()){
			lista.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(), m.getRemitente()));
		}
		//Se retorna la lista
		return new DataListaMensajes(lista);
	}
	

	@RequestMapping(value="/reporteleido", method=RequestMethod.POST) //Cambia un mensaje de nuevo a viejo.
	//Operacion para marcar un reporte como leido
	public void reporteleido(@RequestParam(value="nick")String nick,@RequestParam(value="id_mensaje")int id_mensaje){
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se busca el profesor
		Profesor profe = mu.buscarProfesor(nick);	
		//Si el reporte es nuevo, se lo marca como leido y luego se guarda el profesor
		if(profe.esReporteNuevo(id_mensaje)){
			profe.reporteLeido(id_mensaje);
			mu.guardarUsuario(profe);
		}
	}
	
	@RequestMapping(value="/listarproblemasnivelprofesor", method=RequestMethod.GET)
	public DataListaDataProblema listarProblemasNivelProfesor(@RequestParam(value="id_mundo")int id_mundo,@RequestParam(value="id_nivel")int id_nivel){
		List<DataProblema> lista_problemas = new ArrayList<DataProblema>();
		//Operacion para listar los problemas de un nivel para el profesor
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		//Se busca el nivel
		Nivel nivel = mm.obtenerMundo(id_mundo).buscarNivel(id_nivel);
		
		//Para cada problema del nivel
		for(Problema p: nivel.getProblemas()){
			//Se agregan los datos del problema a la lista
			lista_problemas.add(new DataProblema(p.getId(), p.getDescripcion(), p.getRespuesta(), p.getPuntos_exp(), p.getAyuda().getInfo(), p.getContenido().getURL(), p.getAutor().getNick(), false,false));
		}
		//Se retorna la lista
		return new DataListaDataProblema(lista_problemas);
	}
	
	
}

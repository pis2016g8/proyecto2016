package Controladores;


import Datatypes.DataPuntosJugador;
import Manejadores.ManejadorUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Datatypes.DataJugador;
import Datatypes.DataListaDataProblema;
import Datatypes.DataListaMundos;
import Datatypes.DataListaNiveles;
import Datatypes.DataListaRanking;
import Datatypes.DataLogin;
import Datatypes.DataMundo;
import Datatypes.DataNivel;
import Datatypes.DataProblema;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Logro;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;

@CrossOrigin
@RestController
public class ControladorJugador implements IControladorJugador{
	
	@RequestMapping(value="/verperfil", method=RequestMethod.GET)
	public DataJugador verPerfil(@RequestParam(value="nick")String id_jugador)
	{
		//Retorna un dataJugador con los datos del perfil del jugador con nick = id_jugador
		ManejadorUsuario mu=ManejadorUsuario.getInstancia();
		DataJugador dj=mu.obtenerDatosJugador(id_jugador);
		return dj;
	}
	//METODOS A IMPLEMENTAR
	
	public void sumarPuntos( int exp, String id_jugador,int id_problema){
		//Suma los puntos de un problema a un jugador 
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		ManejadorProblema mp = ManejadorProblema.getInstancia();
		//Se obtiene el jugador
		Jugador j = mu.buscarJugador(id_jugador);
		EstadoJugador estado = j.getEstado();
		//Se obtiene el problema
		Problema pro = mp.buscarProblema(id_problema);
		//Se agrega la experiencia
		estado.ganarExperiencia(exp);
		//Se agrega el problema al estado de jugador, como resuelto
		estado.agregarProblema(pro);
		//Si se generan nuevos logros, los asigna al jugador
		ArrayList<Logro> nuevos_logros = estado.nuevosLogros();
		for(Logro l:nuevos_logros){
			estado.ganarLogro(l);
		}
		//Se persiste el usuario
		mu.guardarUsuario(j);
		
		
	}
	
	
	public boolean yaRespondida(String id_jugador,int id_problema){
		//Operacion para saber si un problema fue respondido por un jugador
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se obtiene el estado del jugador
		EstadoJugador estado = mu.buscarJugador(id_jugador).getEstado();
		//Si en el estado del jugador se encuentra el problema como resuelto retorna true, sino false
		for(Problema pro: estado.getProblemas_resueltos()){
			if (pro.getId() == id_problema){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value="/verranking", method=RequestMethod.GET)
	public DataListaRanking obtenerRanking(){
		//Operacion que retorna el ranking del juego
		ManejadorUsuario manUs = ManejadorUsuario.getInstancia();
		//Se obtiene el ranking
		List<DataPuntosJugador> list_dpj = manUs.obtenerRanking();
		return new DataListaRanking(list_dpj);
	}
	
	@RequestMapping(value="/listarmundosjugador", method=RequestMethod.GET)
	public DataListaMundos listarMundosJugador(@RequestParam(value="nick")String nick){
		//Operacion que lista los mundos para un jugador
		List<DataMundo> lista = new ArrayList<DataMundo>();
		//Se obtienen los mundos
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		List<Mundo> mundos = mm.obtenerMundos();
		//Se obtiene el jugador
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		EstadoJugador estado = mu.buscarJugador(nick).getEstado();
		
		List<Mundo> mundos_completos = estado.getMundos_completos();
		List<Integer> mundos_disponibles = new ArrayList<Integer>();
		//Para cada mundo que el jugador desbloquio, se lo agrega a la lista de mundos_disponibles
		for(Integer id_mundo: estado.getNiveles_actuales().keySet()){
			mundos_disponibles.add(id_mundo);
		}
		
		for(Mundo m: mundos){
			
			boolean completo = false;
			//Un mundo esta completo por un jugador, si este se encuentra en el estado jugador como completo
			for (Mundo m_completo:mundos_completos){
				 if (m_completo.getId()==m.getId()){
					 completo=true;
				 }
			}
			//Un mundo esta disponible para un jugador si se encutra en la lista de disponibles
			boolean disponible = mundos_disponibles.contains(m.getId());
			List<Integer> mundos_siguientes = new ArrayList<Integer>();
			
			for(Mundo sig : m.getMundos_siguientes()){
				mundos_siguientes.add(sig.getId());
			}
					
			lista.add(new DataMundo(m.getId(), m.getNombre(), m.getImagen(), m.getDescripcion(),completo, disponible,mundos_siguientes));
		}										
		return new DataListaMundos(lista);
	}
	
	@RequestMapping(value="/registrarjugador", method=RequestMethod.GET)
	public boolean registrarJugador(@RequestParam(value="nick")String nick,@RequestParam(value="fb_token")String fb_token,@RequestParam(value="nombre")String nombre){
		//Operacion para registrar un nuevo jugador en el juego
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Si existe un usuario con ese nic, se retorna false
		if(mu.existeJugador(nick)){
			return false;
		}else{
			//Se crea un nuevo estado jugador
			EstadoJugador estado = new EstadoJugador(0, new ArrayList<Mundo>(), new ArrayList<Logro>(), new HashMap<Integer,Nivel>(), new ArrayList<Problema>(), new ArrayList<Integer>());

			ManejadorMundo mm = ManejadorMundo.getInstancia();
			//Se activa el primer mundo como activo
			estado.agregarMundoActivo(mm.obtenerMundo(1));
			//Se crea el jugador
			Jugador jugador = new Jugador(nombre, nick, fb_token, null, estado);
			//Se persiste el nuevo jugador
			mu.agregarJugador(jugador);
			//Retora true, registro exitoso
			return true;
		}	
	}
	
	@RequestMapping(value="/listarnivelesmundojugador", method=RequestMethod.GET)
	public DataListaNiveles listarNivelesMundoJugador(@RequestParam(value="nick")String nick,@RequestParam(value="id_mundo") int id_mundo){
		//Operacion para listar los niveles de un mundo para un jugador
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		//Se obtiene el mundo
		Mundo mundo = mm.obtenerMundo(id_mundo);
		//Se obtiene el jugador
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		EstadoJugador estado = mu.buscarJugador(nick).getEstado();
		
		List<DataNivel> lista_niveles = new ArrayList<DataNivel>();
		//Para cada nivel del mundo
		for(Nivel n: mundo.getNiveles()){
			boolean completo;
			boolean disponible;
			//Si el jugador tiene el mundo como disponible
			if(estado.getNiveles_actuales().containsKey(id_mundo)){
				
				if (!mundo.ultimoNivelMundo(n)){
					//Si no es el ultimo nivel del mundo, un nivel esta completo si en los niveles actuales de ese mundo se encuentra en un nivel mayor
					completo = estado.getNiveles_actuales().get(id_mundo).getNro_nivel() > n.getNro_nivel();
				}else{
					//Si es el ultimo nivel del mundo, se chequea que se respondieron todos los problemas de ese nivel
					completo = estado.nivelCompleto(n);
				}
				//Un nivel esta disponible si en los niveles actuales de ese mundo se encuentra un numero mayor o igual al nro de nivel
				disponible = estado.getNiveles_actuales().get(id_mundo).getNro_nivel() >= n.getNro_nivel();
				
			}else{
				//Si el jugador no tiene disponible el mundo, entonces no tiene disponible ni completo ningun nivel
				completo = false;
				disponible = false;
			}
			//Se agrega el data nivel a la lista
			lista_niveles.add(new DataNivel(n.getId_nivel(), n.getNro_nivel(), completo, disponible));
		}
		//Se retorna la lista
		return new DataListaNiveles(lista_niveles);
	}
	
	@RequestMapping(value="/listarproblemasnivel", method=RequestMethod.GET)
	public DataListaDataProblema listarProblemasNivel(@RequestParam(value="nick")String nick,@RequestParam(value="id_mundo")int id_mundo,@RequestParam(value="id_nivel")int id_nivel){
		List<DataProblema> lista_problemas = new ArrayList<DataProblema>();
		//Operacion para listar los problemas de un nivel para un jugador
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		//Se busca el nivel
		Nivel nivel = mm.obtenerMundo(id_mundo).buscarNivel(id_nivel);
		//Se busca el jugador
		EstadoJugador estado = mu.buscarJugador(nick).getEstado();
		//Se buscan los problemas resueltos del jugador
		List<Problema> problemas_resueltos = estado.getProblemas_resueltos();
		//Se buscan los problemas con tutorial activo del jugador
		List<Integer> problemas_tutorial = estado.getProblemas_tutorial_activo();
		
		//Para cada problema del nivel
		for(Problema p: nivel.getProblemas()){
			
			boolean resuelto =false; 
			boolean tut_activo = false;
			
			//Un problema esta resuelto si dicho problema se encuentra la lista problemas resueltos del estado jugador
			for (Problema p_resuelto:problemas_resueltos){
					if (p.getId()==p_resuelto.getId()){
						resuelto=true;
					}
			}

			//Un problema tiene el tutorial activo si dicho problema se encuentra la lista problemas con tutorial activo del estado jugador
			for (int id_p_tut:problemas_tutorial){
				if (p.getId()==id_p_tut){
					tut_activo=true;
				}
		}
			//Se agregan los datos del problema a la lista
			lista_problemas.add(new DataProblema(p.getId(), p.getDescripcion(), p.getRespuesta(), p.getPuntos_exp(), p.getAyuda().getInfo(), p.getContenido().getURL(), p.getAutor().getNick(), resuelto,tut_activo));
		}
		//Se retorna la lista
		return new DataListaDataProblema(lista_problemas);
	}
	
	
	@RequestMapping(value="/loginjugador", method=RequestMethod.GET)
	public DataLogin loginJugador(@RequestParam(value="fb_token")String fb_token){
		//Operacion para realizar el login de un jugador
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		//Se obtienen la lista de todos los jugadores
		List<Jugador> jugadores = mu.obtenerJugadores();
		//Para cada jugador en la lista
		for(Jugador j: jugadores){
			//Si algun jugador tiene el mismo fb_token se retornan los datos de ese jugador
			if(j.getFBToken().equalsIgnoreCase(fb_token)){
				
				return new DataLogin(j.getNombre(),j.getNick(),true);
			}
		}
		//Si ningun jugador tiene ese fb_token, se retorna un DataLogin con un false que indica que no hay ningun jugador con dicho fb_token
		return new DataLogin(null,null, false);
	}
	
	
	
}
package Controladores;


import java.util.List;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Logro;
import Modelo.Mundo;
import Modelo.Nivel;
import Persistencia.CargarDatosBD;


@CrossOrigin
@RestController
public class ControladorSistemaJuego implements IControladorSistemaJuego {
	
	@RequestMapping(value="/cargardatos", method=RequestMethod.POST)
	//Operacion para inicialzar el sistema con los datos iniciales
	public void CargarDatos() throws SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException{
			CargarDatosBD.Cargar();
	}
	
	public void avanzarJuego(String id_jugador, int id_problema, int id_mundo){
		//Operacion para avanzar el juego para un jugador luego de responder un problema de un mundo
		ManejadorProblema mp = ManejadorProblema.getInstancia();
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		//Se busca el jugador
		Jugador j=mu.buscarJugador(id_jugador);
		EstadoJugador estado = j.getEstado();
		//Se busca el nivel del problema
		Nivel nivel = mp.buscarProblema(id_problema).getNivel();
		if(estado.nivelCompleto(nivel)){
			//Si el nivel no esta completo
			//Se obtiene el mundo
			Mundo mundo = mm.obtenerMundo(id_mundo);
			//Se marca el nivel como completo y se desbloquea un nuevo nivel para el jugador
			estado.agregarNivelActivo(mundo);
			if(mundo.ultimoNivelMundo(nivel)){
				//Si se completo el mundo
				List<Mundo> mundos_siguientes = mundo.getMundos_siguientes();
				boolean encontre = false;
				//Se busca si el mundo no fue completado antes
				for(Mundo m: estado.getMundos_completos()){
					if (m.getId() == mundo.getId()) {
						encontre = true;
						break;
					}
				}
				if(!encontre){
					//Si el mundo no fue completado antes se agrega la experiencia del mundo, se marca el mundo como completo, se otorga logro por mundo completo
					estado.agregarMundoCompleto(mundo);
					estado.ganarExperiencia(mundo.getPuntos_exp());
					Logro mundo_terminado = new Logro("Mundo "+mundo.getNombre()+" completado");
					estado.ganarLogro(mundo_terminado);
				}
				for(Mundo m: mundos_siguientes){//Desbloqueo todos los mundos siguientes
					if(!estado.getNiveles_actuales().containsKey(m.getId())){//Solo se desbloque si no esta desbloqueado de antes
						estado.agregarMundoActivo(m);
					}
				}
			}
			//Se guarda el jugador
			mu.guardarUsuario(j);
		}
	}

}

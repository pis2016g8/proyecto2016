package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorUsuario;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Mensaje;
import Modelo.Profesor;

public class TestResponderMensaje {
	
	Profesor profe;
	Jugador j;
	EstadoJugador estado;
	
	
	@Before
	public void setUp() throws Exception {
		
		ManejadorUsuario mu=ManejadorUsuario.getInstancia();
		mu.borrar();
		ManejadorMundo mm=ManejadorMundo.getInstancia();
		mm.borrar();
		mu.borrarProfesores();
		profe = new Profesor("nombreProfe", "nickProfe", "passwordProfe",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		estado = new EstadoJugador(0);
		
		j = new Jugador("nombre", "nick", "FBToken", "imagen", estado);
		mu.agregarProfesor(profe);
		
		mu.guardarEstado(estado);
		mu.agregarJugador(j);
	}

	
	@Test
	public void test() {
		
		Date fecha = new Date();
		Mensaje m = new Mensaje("contenido", "asunto", fecha, profe.getNick());
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		Jugador jugador = mu.buscarJugador(j.getNick());
		jugador.agregar_mensaje_nuevo(m);
		mu.guardarMensaje(m);
		mu.guardarUsuario(jugador);
		
		j=mu.buscarJugador(j.getNick());
		assertEquals(1,j.getMensajes_nuevos().size());
		assertEquals("asunto",j.getMensajes_nuevos().get(0).getAsunto());
		assertEquals("contenido",j.getMensajes_nuevos().get(0).getContenido());
		assertEquals(profe.getNick(),j.getMensajes_nuevos().get(0).getRemitente());
		
		mu.borrar();
		ManejadorMundo mm=ManejadorMundo.getInstancia();
		mm.borrar();
		mu.borrarProfesores();
	}

}

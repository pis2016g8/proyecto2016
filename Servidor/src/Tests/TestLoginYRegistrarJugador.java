package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import Controladores.ControladorJugador;
import Controladores.IControladorJugador;
import Datatypes.DataLogin;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorUsuario;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Logro;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;

public class TestLoginYRegistrarJugador {

	ManejadorUsuario mu = ManejadorUsuario.getInstancia();
	ManejadorMundo mm = ManejadorMundo.getInstancia();
	Mundo mundo;
	List<Nivel> niveles;
	Nivel nivel;
	EstadoJugador estado;
	ArrayList<Mundo> mundos_completos = new ArrayList<Mundo>();
	ArrayList<Logro> logros = new ArrayList<Logro>();
	Map<Integer, Nivel> mundo_nivel = new HashMap<Integer, Nivel>();
	List<Problema> nivel_problema = new ArrayList<Problema>();
	Jugador jugador;
	
	
	@Before
	public void setUp() throws Exception {
		
		mu.borrar();
		mm.borrar();	
		estado = new EstadoJugador(0, mundos_completos, logros, mundo_nivel, nivel_problema,new ArrayList<Integer>());
		
		jugador = new Jugador("ni", "nick", "fBToken", "imagen", estado);
		niveles = new ArrayList<Nivel>();
		mundo = new Mundo(1,"nombreMund", "imagenMundo", "descripcionMundo", 0, null, niveles);
		
		nivel = new Nivel(new ArrayList<Problema>(), mundo);
		mundo.agregarNivel(nivel);
		
		mm.agregarMundo(mundo);
		
		mu.agregarJugador(jugador);
	}

	@Test
	public void test() {
		
		IControladorJugador cj = new ControladorJugador();
		
		DataLogin loginJ1 = cj.loginJugador(jugador.getFBToken());
		
		assertEquals(jugador.getNick(),loginJ1.getNick());
		assertTrue(loginJ1.isExiste_token());
		
		DataLogin loginJ1_2 = cj.loginJugador("fb_tokenJ2");
		assertNull(loginJ1_2.getNick());
		assertFalse(loginJ1_2.isExiste_token());
		
		assertFalse(cj.registrarJugador("nick", "fb_token222", "nombre2"));
		
		assertTrue(cj.registrarJugador("nickJ2", "fb_tokenJ2", "nombreJ2"));
		
		Jugador j2 = mu.buscarJugador("nickJ2");
		assertEquals("nickJ2",j2.getNick());
		assertEquals("fb_tokenJ2",j2.getFBToken());
		assertEquals("nombreJ2",j2.getNombre());
		
		DataLogin loginJ2 = cj.loginJugador("fb_tokenJ2");
		assertEquals(j2.getNick(),loginJ2.getNick());
		assertTrue(loginJ2.isExiste_token());

		mu.borrar();
		mm.borrar();
	}

}

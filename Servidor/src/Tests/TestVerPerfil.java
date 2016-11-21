package Tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import Datatypes.DataJugador;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorUsuario;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Logro;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;
import static org.junit.Assert.*;

public class TestVerPerfil {
	
	@Test
	public void testVerPerfil(){
		
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		
		mu.borrar();
		mm.borrar();
		
		//CREO LOS LOGROS PARA EL JUGADOR
		Logro l = new Logro();
		l.setId(1);
		l.setDescripcion("genio");

		Logro l2 = new Logro();
		l2.setId(3);
		l2.setDescripcion("capo");
		
		List<Logro> logros = new ArrayList<Logro> ();
		logros.add(l);
		logros.add(l2);
		
		//CREO LOS MUNDOS Y NIVELES
		Mundo m = new Mundo(1, "Integrales", "ruta de imagen", "mundo 1 de prueba", 8, new ArrayList<Mundo>(), new ArrayList<Nivel>());
		Nivel n = new Nivel();
		n.setMundo(m);
		m.agregarNivel(n);
		
		Mundo m2 = new Mundo(2, "Integrales", "ruta de imagen", "mundo 2 de prueba", 8, new ArrayList<Mundo>(), new ArrayList<Nivel>());
		Nivel n2 = new Nivel();
		n2.setMundo(m2);
		m2.agregarNivel(n2);

		mm.agregarMundo(m);
		mm.agregarMundo(m2);
		
		Map<Integer,Nivel> m_n = new HashMap<Integer,Nivel>();
		m_n.put(m.getId(), n);
		m_n.put(m2.getId(), n2);
		
		//CREO EL ESTADO JUGADOR		
		EstadoJugador ej = new EstadoJugador(100, new ArrayList<Mundo>(), logros,m_n, new ArrayList<Problema>(),new ArrayList<Integer>());
		mu.guardarEstado(ej);

		//CREO EL JUGADOR
		Jugador j = new Jugador("Juan","juancito", "fb", "imagen", ej);
		mu.agregarJugador(j);
		
		DataJugador dj = mu.obtenerDatosJugador(j.getNick());
		assertEquals(100,dj.getExperiencia());
		assertEquals("imagen",dj.getimagen());
		assertEquals("juancito",dj.getNick());
		assertEquals(ej.getLogros().get(0).getDescripcion(),dj.getLogros().get(0).getDesc());
		assertEquals(ej.getLogros().get(1).getId(),dj.getLogros().get(1).getCant());
		
		mu.borrar();
		mm.borrar();
	}

}

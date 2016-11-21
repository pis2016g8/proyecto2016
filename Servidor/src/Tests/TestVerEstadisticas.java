package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import Controladores.ControladorProblema;
import Controladores.ControladorProfesor;
import Controladores.IControladorProblema;
import Controladores.IControladorProfesor;
import Datatypes.DataEstadistica;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.Ayuda;
import Modelo.Contenido;
import Modelo.Estadistica;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Logro;
import Modelo.Mensaje;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;
import Modelo.Profesor;

public class TestVerEstadisticas {
	
	Ayuda ayuda;
	Contenido contenido;
	Mundo mundo;
	Nivel nivel;
	Problema problema;
	Profesor profe;
	List<Mundo> mundos_siguientes = new ArrayList<Mundo>();
	List<Nivel> niveles = new ArrayList<Nivel>();
	List<Problema> problemas = new ArrayList<Problema>();
	
	Jugador j1;
	Jugador j2;
	Jugador j3;
	
	EstadoJugador es1;
	EstadoJugador es2;
	EstadoJugador es3;
	
	ManejadorMundo mm = ManejadorMundo.getInstancia();
	ManejadorUsuario mu = ManejadorUsuario.getInstancia();
	ManejadorProblema mp = ManejadorProblema.getInstancia();
	
	
	@Before
	public void setUp() throws Exception {
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		
		mundo = new Mundo(1,"nombreMundo", "imagen", "descripcion", 1,mundos_siguientes,niveles);
		nivel = new Nivel(problemas, mundo);
		mundo.agregarNivel(nivel);
		mm.agregarMundo(mundo);
		
		profe = new Profesor("nick", "nombre", "password",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		mu.agregarProfesor(profe);
		
		ayuda = new Ayuda("ayuda al problema");
		contenido = new Contenido("Contenio del problema");
		problema = new Problema("Descripcion del problema", "resp", 50 ,ayuda, contenido,	nivel, profe, new Estadistica(0,0));
		mp.agregarProblema(problema);
		
		es1 = new EstadoJugador(0, new ArrayList<Mundo>(),new ArrayList<Logro>(), new HashMap<Integer,Nivel>(), new ArrayList<Problema>(),new ArrayList<Integer>());
		es2 = new EstadoJugador(0, new ArrayList<Mundo>(),new ArrayList<Logro>(), new HashMap<Integer,Nivel>(), new ArrayList<Problema>(),new ArrayList<Integer>());
		es3 = new EstadoJugador(0, new ArrayList<Mundo>(),new ArrayList<Logro>(), new HashMap<Integer,Nivel>(), new ArrayList<Problema>(),new ArrayList<Integer>());
		
		es1.agregarMundoActivo(mundo);
		es2.agregarMundoActivo(mundo);
		es3.agregarMundoActivo(mundo);
		
		j1 = new Jugador("nombreJ1", "nickJ1", "FBTokenJ1", "imagenJ1", es1);
		j2 = new Jugador("nombreJ2", "nickJ2", "FBTokenJ2", "imagenJ2", es2);
		j3 = new Jugador("nombreJ3", "nickJ3", "FBTokenJ3", "imagenJ3", es3);
		
		mu.agregarJugador(j1);
		mu.agregarJugador(j2);
		mu.agregarJugador(j3);
	}

	
	@Test
	public void test() {
		
		IControladorProfesor cp = new ControladorProfesor();
		IControladorProblema cpro = new ControladorProblema();
		List<DataEstadistica> lista = cp.verEstadisticas().getLista();
		assertEquals(1,lista.size());
		assertEquals(0,lista.get(0).getCant_aciertos());
		assertEquals(0,lista.get(0).getCant_intentos());
		
		int id_p = mm.obtenerMundo(1).getNiveles().get(0).getProblemas().get(0).getId();
		cpro.responderProblema(id_p, "mal", "nickJ1");
		cpro.responderProblema(id_p, "mal", "nickJ1");
		cpro.responderProblema(id_p, "mal", "nickJ2");
		lista = cp.verEstadisticas().getLista();
		assertEquals(1,lista.size());
		assertEquals(0,lista.get(0).getCant_aciertos());
		assertEquals(3,lista.get(0).getCant_intentos());
		assertEquals(problema.getId(),lista.get(0).getId_problema());
		assertEquals(problema.getNivel().getNro_nivel(),lista.get(0).getNro_nivel());
		assertEquals(mundo.getNombre(),lista.get(0).getNombre_mundo());
		assertEquals(problema.getContenido().getURL(),lista.get(0).getUrl_problema());
		
		cpro.responderProblema(id_p, "resp", "nickJ1");
		cpro.responderProblema(id_p, "mal", "nickJ2");
		cpro.responderProblema(id_p, "mal", "nickJ2");
		lista = cp.verEstadisticas().getLista();
		assertEquals(1,lista.size());
		assertEquals(1,lista.get(0).getCant_aciertos());
		assertEquals(6,lista.get(0).getCant_intentos());
		assertEquals(problema.getId(),lista.get(0).getId_problema());
		assertEquals(problema.getNivel().getNro_nivel(),lista.get(0).getNro_nivel());
		assertEquals(mundo.getNombre(),lista.get(0).getNombre_mundo());
		assertEquals(problema.getContenido().getURL(),lista.get(0).getUrl_problema());
		
		cpro.responderProblema(id_p, "resp", "nickJ2");
		cpro.responderProblema(id_p, "mal", "nickJ1");
		cpro.responderProblema(id_p, "resp", "nickJ1");
		lista = cp.verEstadisticas().getLista();
		assertEquals(1,lista.size());
		assertEquals(2,lista.get(0).getCant_aciertos());
		assertEquals(7,lista.get(0).getCant_intentos());
		assertEquals(problema.getId(),lista.get(0).getId_problema());
		assertEquals(problema.getNivel().getNro_nivel(),lista.get(0).getNro_nivel());
		assertEquals(mundo.getNombre(),lista.get(0).getNombre_mundo());
		assertEquals(problema.getContenido().getURL(),lista.get(0).getUrl_problema());
		
		mu.borrar();		
		mm.borrar();
		mu.borrarProfesores();
	}

}

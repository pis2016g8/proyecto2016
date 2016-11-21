package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import Controladores.ControladorProblema;
import Controladores.IControladorProblema;
import Datatypes.DataJugador;
import Datatypes.DataMundoNivel;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.*;

public class TestResponder {
	
	ArrayList<Mundo> mundos_siguientes = new ArrayList<Mundo>();

	ArrayList<Mundo> mundos_siguientes2 = new ArrayList<Mundo>();
	
	ArrayList<Nivel> niveles = new ArrayList<Nivel>();
	ArrayList<Nivel> nivelesM2 = new ArrayList<Nivel>();

	ArrayList<Nivel> nivelesM3 = new ArrayList<Nivel>();
	
	ArrayList<Problema> listaP= new ArrayList<Problema>();
	ArrayList<Problema> listaP2= new ArrayList<Problema>();
	ArrayList<Problema> listaPM2= new ArrayList<Problema>();

	ArrayList<Problema> listaPM3= new ArrayList<Problema>();
	
	ArrayList<Mundo> mundos_completos = new ArrayList<Mundo>();
	ArrayList<Logro> logros = new ArrayList<Logro>();
	Map<Integer, Nivel> mundo_nivel = new HashMap<Integer, Nivel>();
	List<Problema> nivel_problema = new ArrayList<Problema>();
	
	IControladorProblema cp = new ControladorProblema();
	ManejadorProblema mp = ManejadorProblema.getInstancia();
	ManejadorUsuario mu = ManejadorUsuario.getInstancia();
	ManejadorMundo mm = ManejadorMundo.getInstancia();
	
	Mundo mundo;
	Mundo mundo2;
	
	Mundo mundo3;
	Nivel nivel1M3;
	Problema problema1M3;
	
	Nivel nivel1M2;
	Nivel nivel;
	Nivel nivel2;
	Problema problema;
	Problema problema2;
	Problema problema3;
	Problema problema1M2;
	Jugador jugador;
	EstadoJugador estado;
	

	@Before
	public void setUp() throws Exception {
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		mundo3 = new Mundo(3,"Jupiter3", "imagen3", "descripcion3",50,new ArrayList<Mundo>(),nivelesM3);
		nivel1M3 = new Nivel(listaPM3,mundo3);
		
		
		mundos_siguientes2.add(mundo3);
		mundo2 = new Mundo(2,"Jupiter2", "imagen2", "descripcion2",50,mundos_siguientes2, nivelesM2);
		nivel1M2 = new Nivel(listaPM2,mundo2);

		
		mundos_siguientes.add(mundo2);
		mundo = new Mundo(1,"Jupiter", "imagen", "descripcion",50,mundos_siguientes, niveles);
		nivel = new Nivel(listaP,mundo);

		
		nivel2 = new Nivel(listaP2,mundo);
		
		mundo.agregarNivel(nivel);
		mundo.agregarNivel(nivel2);
		mundo2.agregarNivel(nivel1M2);
		mundo3.agregarNivel(nivel1M3);
		

		problema1M3 = new Problema("Problema xx","respuesta",10,null,null,nivel1M3,null, new Estadistica(0,0));
		nivel1M3.agregarProblema(problema1M3);
		
		problema = new Problema("Problema 1","respu esta",10,null,null,nivel,null, new Estadistica(0,0));
		nivel.agregarProblema(problema);
		
		problema2 = new Problema("Problema 2","r espuesta",10,null,null,nivel,null, new Estadistica(0,0));
		nivel.agregarProblema(problema2);
		
		
		problema3 = new Problema("Problema 3","respues ta",10,null,null,nivel2,null, new Estadistica(0,0));
		nivel2.agregarProblema(problema3);
		
		problema1M2 = new Problema("Problema 4","res puesta",10,null,null,nivel1M2,null, new Estadistica(0,0));
		nivel1M2.agregarProblema(problema1M2);
		
		estado = new EstadoJugador(0, mundos_completos, logros, mundo_nivel, nivel_problema,new ArrayList<Integer>());
		estado.agregarMundoActivo(mundo);
		
		jugador = new Jugador("ni", "nick", "fBToken", "imagen", estado);
		
		mm.agregarMundo(mundo);
		mm.agregarMundo(mundo2);
		
		mu.agregarJugador(jugador);
		mp.agregarProblema(problema);
		mp.agregarProblema(problema2);
		mp.agregarProblema(problema3);
		mp.agregarProblema(problema1M2);
	}
	


	@Test
	public void testEstado(){
		estado.ganarExperiencia(10);
		assertEquals(10, estado.getPuntos_exp());
		
		assertEquals(0,estado.cantCorrectas());
		
		estado.agregarProblema(problema);
		assertEquals(problema,estado.getProblemas_resueltos().get(0));
		
		estado.agregarMundoActivo(mundo);
		assertTrue(estado.getNiveles_actuales().containsKey(mundo.getId()));
		
		estado.agregarNivelActivo(mundo);
		assertEquals(nivel2,estado.getNiveles_actuales().get(mundo.getId()));//Si respondi el primer nivel bien, mi niviel actual es el 2do
		
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
	}
	
	
	@Test
	public void testGeneral() {
		
		int id_p1 = mm.obtenerMundo(1).getNiveles().get(0).getProblemas().get(0).getId();
		cp.responderProblema(id_p1, "respue22sta", "nick");
		EstadoJugador nuevo_estado = mu.buscarJugador("nick").getEstado();
		assertEquals(0,nuevo_estado.getPuntos_exp());
		assertEquals(0,nuevo_estado.cantCorrectas());
		assertEquals(0,nuevo_estado.getLogros().size());
		Estadistica est = mp.buscarProblema(id_p1).getEstadisticas();
		assertEquals(0,est.getCant_aciertos());
		assertEquals(1,est.getCant_intentos());

		
		cp.responderProblema(id_p1, "respu esta", "nick");
		nuevo_estado = mu.buscarJugador("nick").getEstado();
		assertEquals(10,nuevo_estado.getPuntos_exp());
		assertEquals(1,nuevo_estado.cantCorrectas());
		assertEquals(1,nuevo_estado.getLogros().size());//PRIMERA RESPUESTA
		assertEquals(nuevo_estado.getNiveles_actuales().get(mundo.getId()).getId_nivel(),nivel.getId_nivel());
		assertTrue(nuevo_estado.getMundos_completos().isEmpty());
		est = mp.buscarProblema(id_p1).getEstadisticas();
		assertEquals(1,est.getCant_aciertos());
		assertEquals(2,est.getCant_intentos());
		
		
		cp.responderProblema(id_p1, "respues ta", "nick");
		nuevo_estado = mu.buscarJugador("nick").getEstado();
		assertEquals(10,nuevo_estado.getPuntos_exp());
		assertEquals(1,nuevo_estado.cantCorrectas());
		assertEquals(1,nuevo_estado.getLogros().size());//PRIMERA RESPUESTA
		assertEquals(nuevo_estado.getNiveles_actuales().get(mundo.getId()).getId_nivel(),nivel.getId_nivel());
		assertTrue(nuevo_estado.getMundos_completos().isEmpty());
		est = mp.buscarProblema(id_p1).getEstadisticas();
		assertEquals(1,est.getCant_aciertos());
		assertEquals(2,est.getCant_intentos());
		
		
		int id_p2 = mm.obtenerMundo(1).getNiveles().get(0).getProblemas().get(1).getId();
		cp.responderProblema(id_p2, "re spuesta", "nick");
		nuevo_estado = mu.buscarJugador("nick").getEstado();
		assertEquals(20,nuevo_estado.getPuntos_exp());
		assertEquals(2,nuevo_estado.cantCorrectas());
		assertEquals(1,nuevo_estado.getLogros().size());//PRIMERA RESPUESTA
		assertEquals(nuevo_estado.getNiveles_actuales().get(mundo.getId()).getId_nivel(),nivel2.getId_nivel());
		assertTrue(nuevo_estado.getMundos_completos().isEmpty());
		est = mp.buscarProblema(id_p2).getEstadisticas();
		assertEquals(1,est.getCant_aciertos());
		assertEquals(1,est.getCant_intentos());
		
		int id_p3 = mm.obtenerMundo(1).getNiveles().get(1).getProblemas().get(0).getId();
		cp.responderProblema(id_p3, "respuest a", "nick");
		nuevo_estado = mu.buscarJugador("nick").getEstado();
		assertEquals(80,nuevo_estado.getPuntos_exp());
		assertEquals(3,nuevo_estado.cantCorrectas());
		assertEquals(2,nuevo_estado.getLogros().size());//PRIMERA RESPUESTA
		assertEquals(nuevo_estado.getNiveles_actuales().get(mundo.getId()).getId_nivel(),nivel2.getId_nivel());
		assertEquals(mundo.getId(),nuevo_estado.getMundos_completos().get(0).getId());
		est = mp.buscarProblema(id_p3).getEstadisticas();
		assertEquals(1,est.getCant_aciertos());
		assertEquals(1,est.getCant_intentos());

		int id_p4 = mm.obtenerMundo(2).getNiveles().get(0).getProblemas().get(0).getId();
		cp.responderProblema(id_p4, "re spuesta", "nick");
		nuevo_estado = mu.buscarJugador("nick").getEstado();
		assertEquals(140,nuevo_estado.getPuntos_exp());
		assertEquals(4,nuevo_estado.cantCorrectas());
		assertEquals(3,nuevo_estado.getLogros().size());//PRIMERA RESPUESTA
		assertEquals(nuevo_estado.getNiveles_actuales().get(mundo2.getId()).getId_nivel(),nivel1M2.getId_nivel());
		assertEquals(2,nuevo_estado.getMundos_completos().size());
		est = mp.buscarProblema(id_p4).getEstadisticas();
		assertEquals(1,est.getCant_aciertos());
		assertEquals(1,est.getCant_intentos());
		
		DataJugador dj = mu.obtenerDatosJugador(jugador.getNick());
		List<DataMundoNivel> dmn = dj.getMundosNiveles();
		
		assertEquals("Jupiter",dmn.get(0).getMundo());
		assertEquals(1,dmn.get(0).getNivel());
		assertTrue(dmn.get(0).isMundo_completo());
		
		assertEquals("Jupiter2",dmn.get(1).getMundo());
		assertEquals(0,dmn.get(1).getNivel());
		assertTrue(dmn.get(1).isMundo_completo());
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
	}

}
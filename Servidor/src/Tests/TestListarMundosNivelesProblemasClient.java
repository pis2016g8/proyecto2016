package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import Controladores.ControladorJugador;
import Controladores.ControladorProblema;
import Controladores.IControladorJugador;
import Controladores.IControladorProblema;
import Datatypes.DataMundo;
import Datatypes.DataNivel;
import Datatypes.DataProblema;
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

public class TestListarMundosNivelesProblemasClient {
	
	ManejadorMundo mm = ManejadorMundo.getInstancia();
	ManejadorProblema mp = ManejadorProblema.getInstancia();
	ManejadorUsuario mu = ManejadorUsuario.getInstancia();
	
	EstadoJugador estado = new EstadoJugador(0, new ArrayList<Mundo>(), new ArrayList<Logro>(), new HashMap<Integer,Nivel>(), new ArrayList<Problema>(),new ArrayList<Integer>());
	Jugador jugador = new Jugador("nombre", "nick", "FBToken", "imagen", estado);
	
	Profesor profesor = new Profesor("nickProfe", "nombreProfe", "passwordProfe",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
	
	Mundo m1;
	Mundo m2;
	Mundo m3;
	
	Nivel n1m1;
	Nivel n2m1;
	
	Nivel n1m2;
	Nivel n2m2;
	
	Nivel n1m3;
	Nivel n2m3;
	
	ArrayList<Mundo> mundos_siguientesM1 = new ArrayList<Mundo>();
	ArrayList<Mundo> mundos_siguientesM2 = new ArrayList<Mundo>();
	ArrayList<Mundo> mundos_siguientesM3 = new ArrayList<Mundo>();
	
	ArrayList<Nivel> nivelesM1 = new ArrayList<Nivel>();
	ArrayList<Nivel> nivelesM2 = new ArrayList<Nivel>();
	ArrayList<Nivel> nivelesM3 = new ArrayList<Nivel>();
	
	ArrayList<Problema> listaP_M1= new ArrayList<Problema>();
	ArrayList<Problema> listaP_2M1= new ArrayList<Problema>();
	ArrayList<Problema> listaP_M2= new ArrayList<Problema>();
	ArrayList<Problema> listaP_2M2= new ArrayList<Problema>();
	ArrayList<Problema> listaP_M3= new ArrayList<Problema>();
	ArrayList<Problema> listaP_2M3= new ArrayList<Problema>();
	
	Problema p1;
	Problema p2;
	
	Ayuda ayuda1;
	Ayuda ayuda2;
	
	Contenido cont1;
	Contenido cont2;
	
	
	@Before
	public void setUp() throws Exception {
		
		mu.borrar();
		mm.borrar();
		m1 = new Mundo(1,"Jupiter", "imagen", "descripcion",0,mundos_siguientesM1, nivelesM1);
		n1m1 = new Nivel(listaP_M1,m1);
		n1m1.setNivel(0);
		n2m1 = new Nivel(listaP_2M1,m1);
		n2m1.setNivel(1);
		//m1.setId(1);
		
		ayuda1 = new Ayuda("Ayuda 1");
		ayuda2 = new Ayuda("Ayuda 2");
		
		cont1 = new Contenido("Cont1");
		cont2 = new Contenido("Cont2");
		
		
		
		p1 = new Problema("Problema 1","respuesta",10,ayuda1,cont1,n1m1,profesor, new Estadistica(0,0));
		p2 = new Problema("Problema 2","respuesta2",10,ayuda2,cont2,n1m1,profesor, new Estadistica(0,0));
		
		n1m1.agregarProblema(p1);
		n1m1.agregarProblema(p2);
		
		m2 = new Mundo(2,"Jupiter2", "imagen2", "descripcion2",0,mundos_siguientesM2, nivelesM2);
		n1m2 = new Nivel(listaP_M2,m2);
		n2m2 = new Nivel(listaP_2M2,m2);
		//m2.setId(2);
		
		m3 = new Mundo(3,"Jupiter3", "imagen3", "descripcion3",0,mundos_siguientesM3, nivelesM3);
		n1m3 = new Nivel(listaP_M3,m3);
		n2m3 = new Nivel(listaP_2M3,m3);
		//m3.setId(3);
		
		m1.agregarNivel(n1m1);
		m1.agregarNivel(n2m1);
		m2.agregarNivel(n1m2);
		m2.agregarNivel(n2m2);
		m3.agregarNivel(n1m3);
		m3.agregarNivel(n2m3);
		
		mu.agregarProfesor(profesor);
		
		mm.agregarMundo(m1);
		mm.agregarMundo(m2);
		mm.agregarMundo(m3);
		
		estado.agregarMundoActivo(mm.obtenerMundos().get(0));
		
		mu.agregarJugador(jugador);
		
		mp.agregarProblema(p1);
		mp.agregarProblema(p2);
	}

	@Test
	public void test() {
		IControladorJugador cj = new ControladorJugador();
		IControladorProblema cp = new ControladorProblema();
		List<DataMundo> lista_mundos = cj.listarMundosJugador(jugador.getNick()).getLista_mundos();
		
		assertEquals(3,lista_mundos.size());
		assertEquals(m1.getId(),lista_mundos.get(0).getId_mundo());
		assertEquals(m2.getId(),lista_mundos.get(1).getId_mundo());
		assertEquals(m3.getId(),lista_mundos.get(2).getId_mundo());
		
		assertFalse(lista_mundos.get(0).isMundo_completado());
		assertTrue(lista_mundos.get(0).isMundo_disponible());
		
		assertFalse(lista_mundos.get(1).isMundo_completado());
		assertFalse(lista_mundos.get(1).isMundo_disponible());
		
		assertFalse(lista_mundos.get(2).isMundo_completado());
		assertFalse(lista_mundos.get(2).isMundo_disponible());
		
		List<DataNivel> lista_nivelM1 = cj.listarNivelesMundoJugador(jugador.getNick(),m1.getId()).getLista();
		
		assertEquals(n1m1.getId_nivel(),lista_nivelM1.get(0).getId_nivel());
		assertEquals(n2m1.getId_nivel(),lista_nivelM1.get(1).getId_nivel());
		
		assertTrue(lista_nivelM1.get(0).isNivel_disponible());	
		assertFalse(lista_nivelM1.get(0).isNivel_completo());
		
		assertFalse(lista_nivelM1.get(1).isNivel_disponible());	
		assertFalse(lista_nivelM1.get(1).isNivel_completo());
		
		
		List<DataProblema> lista_problemasN1M1 = cj.listarProblemasNivel(jugador.getNick(), m1.getId(),n1m1.getId_nivel()).getProblemas_nivel();
		
		assertEquals(p1.getDescripcion(),lista_problemasN1M1.get(0).getDescripcion());
		assertFalse(lista_problemasN1M1.get(0).isResuelto());
		assertFalse(lista_problemasN1M1.get(0).isTut_activo());
		
		assertEquals(p2.getDescripcion(),lista_problemasN1M1.get(1).getDescripcion());
		assertFalse(lista_problemasN1M1.get(1).isResuelto());
		assertFalse(lista_problemasN1M1.get(1).isTut_activo());
		
		int id_p1 = mm.obtenerMundo(1).getNiveles().get(0).getProblemas().get(0).getId();
		cp.responderProblema(id_p1, "respuestaMAL", "nick");
		lista_problemasN1M1 = cj.listarProblemasNivel(jugador.getNick(), m1.getId(),n1m1.getId_nivel()).getProblemas_nivel();
		
		assertEquals(p1.getDescripcion(),lista_problemasN1M1.get(0).getDescripcion());
		assertFalse(lista_problemasN1M1.get(0).isResuelto());
		assertTrue(lista_problemasN1M1.get(0).isTut_activo());
		
		assertEquals(p2.getDescripcion(),lista_problemasN1M1.get(1).getDescripcion());
		assertFalse(lista_problemasN1M1.get(1).isResuelto());
		assertFalse(lista_problemasN1M1.get(1).isTut_activo());
		
		int id_p2 = mm.obtenerMundo(1).getNiveles().get(0).getProblemas().get(1).getId();
		cp.responderProblema(id_p2, "respuestaMAL", "nick");
		lista_problemasN1M1 = cj.listarProblemasNivel(jugador.getNick(), m1.getId(),n1m1.getId_nivel()).getProblemas_nivel();
		
		assertEquals(p1.getDescripcion(),lista_problemasN1M1.get(0).getDescripcion());
		assertFalse(lista_problemasN1M1.get(0).isResuelto());
		assertTrue(lista_problemasN1M1.get(0).isTut_activo());
		
		assertEquals(p2.getDescripcion(),lista_problemasN1M1.get(1).getDescripcion());
		assertFalse(lista_problemasN1M1.get(1).isResuelto());
		assertTrue(lista_problemasN1M1.get(1).isTut_activo());
		
		mu.borrar();		
		mm.borrar();
		mu.borrarProfesores();
	}

}

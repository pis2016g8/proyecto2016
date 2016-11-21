package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Controladores.ControladorProblema;
import Controladores.ControladorProfesor;
import Controladores.IControladorProblema;
import Controladores.IControladorProfesor;
import Datatypes.DataMensaje;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorUsuario;
import Modelo.Ayuda;
import Modelo.Contenido;
import Modelo.Estadistica;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Mensaje;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;
import Modelo.Profesor;

public class TestModificarYReportarProblema {
	Profesor profe;
	Jugador j;
	EstadoJugador estado;
	ManejadorMundo mm=ManejadorMundo.getInstancia();
	ManejadorUsuario mu=ManejadorUsuario.getInstancia();
	
	@Before
	public void setUp() throws Exception {
		
		mu.borrar();
		
		mm.borrar();
		mu.borrarProfesores();
		profe = new Profesor("nombreProfe", "nickProfe", "passwordProfe",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		estado = new EstadoJugador(0);
		
		j = new Jugador("nombre", "nick", "FBToken", "imagen", estado);
		
		Mundo mundo = new Mundo(0, "nombre", "imagen", "descripcion", 10, new ArrayList<Mundo>(), new ArrayList<Nivel>());
		
		Nivel nivel = new Nivel(new ArrayList<Problema>(), mundo);
		mundo.agregarNivel(nivel);
		
		Problema problema = new Problema("descripcion", "respuesta", 10,new Ayuda("Ayuda"), new Contenido("Cont"), nivel, profe, new Estadistica(0, 0));
		nivel.agregarProblema(problema);
		
		mu.agregarProfesor(profe);
		
		mu.guardarEstado(estado);
		mu.agregarJugador(j);
		mm.agregarMundo(mundo);
	}

	@Test
	public void test() {
		IControladorProblema cp = new ControladorProblema();
		IControladorProfesor cprofe = new ControladorProfesor();
		Problema p =  mm.obtenerMundo(0).getNiveles().get(0).getProblemas().get(0);
		
		assertTrue(p.verificarRespuesta("respuesta"));
		
		int id_problema = p.getId();
		cp.reportarProblema(id_problema, j.getNick(), "Reporte Problema");
		
		ArrayList<DataMensaje> nuevos = cprofe.verReportesNuevos(profe.getNick()).getMensajes();
		ArrayList<DataMensaje> viejos = cprofe.verReportesViejos(profe.getNick()).getMensajes();
		
		assertEquals(1, nuevos.size());
		assertEquals("Reporte Problema",nuevos.get(0).getContenido());
		assertEquals(j.getNick(),nuevos.get(0).getRemitente());
		assertEquals(0,viejos.size());
		
		int id_mensaje = nuevos.get(0).getId();
		
		cprofe.reporteleido(profe.getNick(), id_mensaje);
		
		nuevos = cprofe.verReportesNuevos(profe.getNick()).getMensajes();
		viejos = cprofe.verReportesViejos(profe.getNick()).getMensajes();
		
		assertEquals(0, nuevos.size());
		assertEquals(1,viejos.size());
		assertEquals("Reporte Problema",viejos.get(0).getContenido());
		assertEquals(j.getNick(),viejos.get(0).getRemitente());
		
		cp.modificarProblema(id_problema, "descripcion", "nueva", 20, "AYUDAAA", "CONTENIDO");
		
		p =  mm.obtenerMundo(0).getNiveles().get(0).getProblemas().get(0);
		assertTrue(p.verificarRespuesta("nueva"));
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		
		
		
		
	}

}

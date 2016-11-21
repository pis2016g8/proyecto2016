package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import Controladores.ControladorProblema;
import Controladores.IControladorProblema;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.Ayuda;
import Modelo.Contenido;
import Modelo.Estadistica;
import Modelo.Mensaje;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;
import Modelo.Profesor;   


public class TestVerAyuda {

	@Test
	public void testGetAyuda() {
		
		ManejadorProblema manejador = ManejadorProblema.getInstancia();
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();

		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		
		Ayuda ayuda = new Ayuda("La derivada es cuanto varia la funcion, cuando varia x");
		Contenido contenido = new Contenido("d(8x)/dx");
		
		List<Nivel> niveles = new ArrayList<Nivel>();
		Mundo mundo = new Mundo(1, "nombre", "imagen", "descripcion", 0, new ArrayList<Mundo>(), niveles);
		Nivel nivel = new Nivel(new ArrayList<Problema>(), mundo);
		mundo.agregarNivel(nivel);
		mm.agregarMundo(mundo);
		
		Profesor profesor = new Profesor("Pedro", "pedro04", "1234",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		mu.agregarProfesor(profesor);
		
		Problema problema = new Problema( "Resolver la siguiente derivada", "8", 1, ayuda, contenido, nivel, profesor, new Estadistica(0,0));
		manejador.agregarProblema(problema);
		
		int id_problema = mm.obtenerMundo(1).getNiveles().get(0).getProblemas().get(0).getId();
		IControladorProblema _cproblema = new ControladorProblema();
		assertEquals ("La derivada es cuanto varia la funcion, cuando varia x", _cproblema.getAyuda(id_problema).getAyuda());
		
		assertEquals ("second atempt", "La derivada es cuanto varia la funcion, cuando varia x", _cproblema.getAyuda(id_problema).getAyuda());
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
	}

}



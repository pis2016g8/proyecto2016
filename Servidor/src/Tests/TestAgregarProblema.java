package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.*;

public class TestAgregarProblema {
	
	Ayuda ayuda;
	Contenido contenido;
	Mundo mundo;
	Nivel nivel;
	Problema problema;
	Profesor profe;
	List<Mundo> mundos_siguientes = new ArrayList<Mundo>();
	List<Nivel> niveles = new ArrayList<Nivel>();
	List<Problema> problemas = new ArrayList<Problema>();
	
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
		
		problema = new Problema("Descripcion del problema", "Respuesta del problema", 50 ,ayuda, contenido,	nivel, profe, new Estadistica(0,0));
		mp.agregarProblema(problema);
	}

	@Test
	public void test() {
		
		assertEquals(problema, mp.buscarProblema(problema.getId()));
		
		mu.borrar();		
		mm.borrar();
		mu.borrarProfesores();
		
	}

}

package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.Estadistica;
import Modelo.Mensaje;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;
import Modelo.Profesor;

public class TestSolicitarAyuda {
	
	Profesor profesor;
	Problema problema;

	@Before
	public void setUp() throws Exception {
		
		ManejadorUsuario mu=ManejadorUsuario.getInstancia();
		mu.borrar();
		ManejadorMundo mm=ManejadorMundo.getInstancia();
		mm.borrar();
		mu.borrarProfesores();
		profesor = new Profesor("Juan","nickJuan","123",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		mu.agregarProfesor(profesor);
		
		ArrayList<Nivel> niveles = new ArrayList<Nivel>();
		ArrayList<Problema> listaP = new ArrayList<Problema>();
		
		Mundo mundo = new Mundo(1,"Jupiter", "imagen", "descripcion",50, new ArrayList<Mundo>(), niveles);
		Nivel nivel = new Nivel(listaP,mundo);
		
		mundo.agregarNivel(nivel);
		
		problema= new Problema("problema1","resp",12,null,null,nivel,profesor, new Estadistica(0,0));
		listaP.add(problema);
		
		ManejadorProblema mp=ManejadorProblema.getInstancia();
		mm.agregarMundo(mundo);
		mp.agregarProblema(problema);
		System.out.println("successfully saved datos Test Solicitar Ayuda");
	}

	@Test
	public void test() {
		
		@SuppressWarnings("deprecation")
		Date date =new Date(1,1,1);
		ManejadorProblema mp=ManejadorProblema.getInstancia();
		Problema p=mp.buscarProblema(problema.getId());
		p.enviarMensaje("contenido",date,"asunto","nickJuan");
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		profesor = mu.buscarProfesor("nickJuan");
		Mensaje m =profesor.getMensajes_nuevos().get(0);
		assertEquals(m.getAsunto(),"asunto");
		assertEquals(m.getContenido(),"contenido");
		Date dateBD = new Date(m.getFecha().getTime());
		assertEquals(dateBD,date);
		
		mu.borrar();
		ManejadorMundo mm=ManejadorMundo.getInstancia();
		mm.borrar();
		mu.borrarProfesores();
	}

}

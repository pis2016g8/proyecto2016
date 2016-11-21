package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import Controladores.ControladorProblema;
import Controladores.ControladorProfesor;
import Controladores.IControladorProblema;
import Controladores.IControladorProfesor;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorProblema;
import Manejadores.ManejadorUsuario;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Logro;
import Modelo.Mensaje;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;
import Modelo.Profesor;

public class TestAgregarMundoYNivel {
	
	Profesor profe;
	ManejadorMundo mm = ManejadorMundo.getInstancia();
	ManejadorUsuario mu = ManejadorUsuario.getInstancia();
	ManejadorProblema mp = ManejadorProblema.getInstancia();
	Jugador j;
	EstadoJugador est;
	@Before
	public void setUp() throws Exception {
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		
		//profe = new Profesor("nombre", "nick", "password",new ArrayList<DataMensaje>(),new ArrayList<DataMensaje>());
		profe = new Profesor("nombre", "nick", "password", new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		mu.agregarProfesor(profe);
	}

	@Test
	public void test() {
		IControladorProfesor cp = new ControladorProfesor();
		IControladorProblema cproblema = new ControladorProblema();
		
		cp.agregarMundo("nombreM", "imagenM", 0, "desc");
		cp.agregarMundo("nombreM2", "imagenM2", 0, "desc2");
		assertEquals(2,mm.obtenerMundos().size());
		
		cp.agregarNivel(0);
		cproblema.agregarProblema("descripcion", "resp",10 , "cont_ayuda", "cont", 0, 0, "nick");
		
		est = new EstadoJugador(0, new ArrayList<Mundo>(),new ArrayList<Logro>(), new HashMap<Integer,Nivel>(), new ArrayList<Problema>(),new ArrayList<Integer>());
		est.agregarMundoActivo(mm.obtenerMundo(0));
		j = new Jugador("nombreJ1", "nickJ1", "FBTokenJ1", "imagenJ1", est);
		mu.agregarJugador(j);
		
		Mundo m = mm.obtenerMundo(0);
		Nivel n = m.getNiveles().get(0);
		int id_p = n.getProblemas().get(0).getId();
		cproblema.responderProblema(id_p, "resp", "nickJ1");
		
		EstadoJugador estado = mu.buscarJugador("nickJ1").getEstado();
		
		assertEquals(1,estado.getNiveles_actuales().size());
		assertEquals(0,estado.getNiveles_actuales().get(0).getNro_nivel());
		assertEquals(1,estado.getMundos_completos().size());
		
		cp.agregarNivel(1);
		estado = mu.buscarJugador("nickJ1").getEstado();
		assertEquals(2,estado.getNiveles_actuales().size());
		assertEquals(0,estado.getNiveles_actuales().get(1).getNro_nivel());
		assertEquals(1,estado.getMundos_completos().size());
		
		cp.agregarNivel(1);
		
		assertEquals(1,mm.obtenerMundo(0).getNiveles().size());
		assertEquals(2,mm.obtenerMundo(1).getNiveles().size());
		estado = mu.buscarJugador("nickJ1").getEstado();
		assertEquals(2,estado.getNiveles_actuales().size());
		assertEquals(0,estado.getNiveles_actuales().get(1).getNro_nivel());
		assertEquals(1,estado.getMundos_completos().size());

	}

}

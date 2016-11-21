package Tests;

import Modelo.EstadoJugador;
import Modelo.Profesor;
import org.junit.Test;
import Modelo.Jugador;
import Modelo.Mensaje;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorUsuario;
import Datatypes.DataPuntosJugador;
import org.junit.Before;

public class TestVerRanking {
	
	@Before
	public void setUp() throws Exception {
		
		ManejadorUsuario manUs = ManejadorUsuario.getInstancia();
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		
		manUs.borrar();
		mm.borrar();
		manUs.borrarProfesores();
		
		Profesor Juliana = new Profesor("Juli", "Juliana", "123456",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		
		manUs.agregarProfesor(Juliana);
	  
		EstadoJugador estadoFufi = new EstadoJugador(12);
        Jugador j = new Jugador("Estefania", "fuffi", "estefaniaD", "imagenA", estadoFufi);
        
        manUs.guardarEstado(estadoFufi);
    	manUs.agregarJugador(j);
        
        EstadoJugador estadoCaro = new EstadoJugador(33);
        j = new Jugador("Carolina", "Caro", "carolinaC", "imagenB", estadoCaro);
		
        manUs.guardarEstado(estadoCaro);
        manUs.agregarJugador(j);
        
        EstadoJugador estadoPau = new EstadoJugador(2);
        j = new Jugador("Paula", "Pau", "paulaM", "imagenC", estadoPau);

        manUs.guardarEstado(estadoPau);
        manUs.agregarJugador(j);
        
        EstadoJugador estadoRodri = new EstadoJugador(2);
        j = new Jugador("Rodrigo", "Rodri", "rodrigoH", "imagenD", estadoRodri);

        manUs.guardarEstado(estadoRodri);
        manUs.agregarJugador(j);
        
        EstadoJugador estadoRau = new EstadoJugador(28);
        j = new Jugador("Raul", "Rau", "raulF", "imagenE", estadoRau);

        manUs.guardarEstado(estadoRau);
        manUs.agregarJugador(j);
	}
	
	
	@Test	
	public void test() {
		
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		ManejadorUsuario manUs = ManejadorUsuario.getInstancia();
		List<DataPuntosJugador> resultado = manUs.obtenerRanking();
		
		assertEquals(resultado.get(0).getNick(),"Caro");
		assertTrue(resultado.get(0).getPuntos() == 33);
		
		assertEquals(resultado.get(1).getNick(),"Rau");
		assertTrue(resultado.get(1).getPuntos() == 28);
		
		assertEquals(resultado.get(2).getNick(),"fuffi");
		assertTrue(resultado.get(2).getPuntos() == 12);
		
		assertEquals(resultado.get(3).getNick(),"Pau");
		assertTrue(resultado.get(3).getPuntos() == 2);
		
		assertEquals(resultado.get(4).getNick(),"Rodri");
		assertTrue(resultado.get(4).getPuntos() == 2);
		
		manUs.borrar();
		mm.borrar();
		manUs.borrarProfesores();
	}
	
}
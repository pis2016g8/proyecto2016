package Tests;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import Controladores.ControladorJugador;
import Controladores.ControladorProfesor;
import Controladores.IControladorJugador;
import Controladores.IControladorProfesor;
import Datatypes.DataMensaje;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorUsuario;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Mensaje;
import Modelo.Profesor;

public class TestVerMensajes {
	
	Profesor profe;
	Jugador jugador;
	Mensaje m1;
	Mensaje m2;
	Mensaje m3;
	Mensaje m1j;
	Mensaje m2j;
	ManejadorUsuario mu;
	ManejadorMundo mm;
	
	@Before
	public void setUp() throws Exception {
		
		mu = ManejadorUsuario.getInstancia();
		mm = ManejadorMundo.getInstancia();
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		
		profe = new Profesor("nombre", "nick", "password",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		m1 = new Mensaje("contenido1", "asunto1", new Date(), "id_remitente1");
		m2 = new Mensaje("contenido2", "asunto2", new Date(), "id_remitente2");
		m3 = new Mensaje("contenido3", "asunto3", new Date(), "id_remitente3");
		
		jugador = new Jugador("nombreJ", "nickJ", "FBTokenJugador", "imagen", new EstadoJugador());
		m1j = new Mensaje("contenido1j", "asunto1j", new Date(), "id_remitente1j");
		m2j = new Mensaje("contenido2j", "asunto2j", new Date(), "id_remitente2j");
		
		jugador.agregar_mensaje_nuevo(m1j);
		jugador.agregar_mensaje_nuevo(m2j);
		
		profe.agregar_mensaje_nuevo(m1);
		profe.agregar_mensaje_nuevo(m2);
		profe.agregar_mensaje_nuevo(m3);
		mu.agregarProfesor(profe);
		mu.agregarJugador(jugador);
	}

	
	@Test
	public void test() {
		
		mu = ManejadorUsuario.getInstancia();
		Profesor p = mu.buscarProfesor("nick");
		Jugador j = mu.buscarJugador("nickJ");
		IControladorProfesor cprofe = new ControladorProfesor();
		IControladorJugador cjug = new ControladorJugador();
		
		List<DataMensaje> dlm_nuevos = cprofe.verMensajesNuevos(p.getNick()).getMensajes();

		List<DataMensaje> dlm_viejos = cprofe.verMensajesViejos(p.getNick()).getMensajes();
		
		List<DataMensaje> dlm_nuevosJ = new ArrayList<DataMensaje>();
		for (Mensaje m: j.getMensajes_nuevos()){
			dlm_nuevosJ.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(),m.getRemitente()));
		}
		
		List<DataMensaje> dlm_viejosJ = new ArrayList<DataMensaje>();
		
		for (Mensaje m: j.getMensajes_viejos()){
			dlm_viejosJ.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(),m.getRemitente()));
		}
		
		assertEquals(3,dlm_nuevos.size());
		assertEquals(0,dlm_viejos.size());
		
		assertEquals(2,dlm_nuevosJ.size());
		assertEquals(0,dlm_viejosJ.size());
		
		assertEquals(m1.getContenido(),dlm_nuevos.get(0).getContenido());
		assertEquals(m1.getAsunto(),dlm_nuevos.get(0).getAsunto());
		//SE COMPARA DIA MES ANIO Y HORAS Y MINUTOS. SI JUSTO SE DA QUE HAY CAMBIO DE MINUTO EN LA BASE
		//DA UN MINUTO MAS QUE EN EL DATO ORIGINAL POR LO QUE DA DIFERENCIA EN EL MINUTO.
		//SI SE CORRE OTRA VEZ FUNCIONA.
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m1.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm_nuevos.get(0).getFecha()));
		assertEquals(m1.getRemitente(),dlm_nuevos.get(0).getRemitente());
		
		assertEquals(m2.getContenido(),dlm_nuevos.get(1).getContenido());
		assertEquals(m2.getAsunto(),dlm_nuevos.get(1).getAsunto());
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m2.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm_nuevos.get(1).getFecha()));
		assertEquals(m2.getRemitente(),dlm_nuevos.get(1).getRemitente());
		
		assertEquals(m3.getContenido(),dlm_nuevos.get(2).getContenido());
		assertEquals(m3.getAsunto(),dlm_nuevos.get(2).getAsunto());
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m3.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm_nuevos.get(2).getFecha()));
		assertEquals(m3.getRemitente(),dlm_nuevos.get(2).getRemitente());
		
		assertEquals(m1j.getContenido(),dlm_nuevosJ.get(0).getContenido());
		assertEquals(m1j.getAsunto(),dlm_nuevosJ.get(0).getAsunto());
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m1j.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm_nuevosJ.get(0).getFecha()));
		assertEquals(m1j.getRemitente(),dlm_nuevosJ.get(0).getRemitente());
		
		assertEquals(m2j.getContenido(),dlm_nuevosJ.get(1).getContenido());
		assertEquals(m2j.getAsunto(),dlm_nuevosJ.get(1).getAsunto());
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m2j.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm_nuevosJ.get(1).getFecha()));
		assertEquals(m2j.getRemitente(),dlm_nuevosJ.get(1).getRemitente());
		
		p.mensajeLeido(m1.getId());
		p.mensajeLeido(m2.getId());
		p.mensajeLeido(m3.getId());
		mu.guardarUsuario(p);
		
		List<DataMensaje> dlm2_nuevos = new ArrayList<DataMensaje>();
		
		for (Mensaje m: p.getMensajes_nuevos()){
			dlm2_nuevos.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(),m.getRemitente()));
		}
		
		List<DataMensaje> dlm2_viejos = new ArrayList<DataMensaje>();
		
		for (Mensaje m: p.getMensajes_viejos()){
			dlm2_viejos.add(new DataMensaje(m.getId(), m.getAsunto(), m.getContenido(), m.getFecha(),m.getRemitente()));
		}
		
		assertEquals(0,dlm2_nuevos.size());
		assertEquals(3,dlm2_viejos.size());
		
		assertEquals(m1.getContenido(),dlm2_viejos.get(0).getContenido());
		assertEquals(m1.getAsunto(),dlm2_viejos.get(0).getAsunto());
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m1.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm2_viejos.get(0).getFecha()));
		assertEquals(m1.getRemitente(),dlm2_viejos.get(0).getRemitente());
		
		assertEquals(m2.getContenido(),dlm2_viejos.get(1).getContenido());
		assertEquals(m2.getAsunto(),dlm2_viejos.get(1).getAsunto());
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m2.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm2_viejos.get(1).getFecha()));
		assertEquals(m2.getRemitente(),dlm2_viejos.get(1).getRemitente());
		
		assertEquals(m3.getContenido(),dlm2_viejos.get(2).getContenido());
		assertEquals(m3.getAsunto(),dlm2_viejos.get(2).getAsunto());
		//CREO UN FORMATO UNICO PARA COMPARAR LAS FECHAS. SE OBTIENEN EN FORMATOS DISTINTOS SINO.
		assertEquals(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(m3.getFecha()),new SimpleDateFormat("MM-dd-yyyy HH:mm").format(dlm2_viejos.get(2).getFecha()));
		assertEquals(m3.getRemitente(),dlm2_viejos.get(2).getRemitente());
		
		mm = ManejadorMundo.getInstancia();
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
	}

}

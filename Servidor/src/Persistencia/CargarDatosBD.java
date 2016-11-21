package Persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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


public class CargarDatosBD {	
	
	public static Mensaje PersistirMensaje(String contenido, Date fecha,String asunto,Profesor profesor){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		Mensaje mensaje=new Mensaje(contenido,asunto,fecha,profesor.getNick());
		session.persist(mensaje);
		session.saveOrUpdate(profesor);
		t.commit();//transaction is commited 
		session.close();
		System.out.println("successfully saved mensaje");
		return mensaje;
	}
	
	public static void CargarTestSolicitarAyuda() throws SecurityException, HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException {
		SessionFactory factory= HibernateUtility.getSessionFactory();
		//creating session object
		Session session=factory.openSession();
		//creating transaction object
		org.hibernate.Transaction t= session.beginTransaction();
		
		Profesor profe = new Profesor("Juan","pepe","123",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		Problema problema= new Problema("problema1","resp",12,null,null,null,profe,new Estadistica(0,0));
		
		session.persist(profe);
		session.persist(problema);
		t.commit();//transaction is commited 
		session.close();
		System.out.println("successfully saved datos Test Solicitar Ayuda");
	}
	
	public static void Cargar(){
		ManejadorProblema mp = ManejadorProblema.getInstancia();
		ManejadorUsuario mu = ManejadorUsuario.getInstancia();
		ManejadorMundo mm = ManejadorMundo.getInstancia();
		
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		
		List<Mundo> mundos_siguientes = new ArrayList<Mundo>();
		List<Mundo> mundos_siguientes2 = new ArrayList<Mundo>();
		
		List<Nivel> niveles = new ArrayList<Nivel>();
		List<Nivel> niveles2 = new ArrayList<Nivel>();
		
		List<Problema> listaPN1= new ArrayList<Problema>();
		List<Problema> listaPN2= new ArrayList<Problema>();
		List<Problema> listaPN3= new ArrayList<Problema>();
		List<Problema> listaPN4= new ArrayList<Problema>();
		List<Problema> listaPN5= new ArrayList<Problema>();
		List<Mundo> mundos_completosJ1 = new ArrayList<Mundo>();
		List<Mundo> mundos_completosJ2 = new ArrayList<Mundo>();
		List<Mundo> mundos_completosJ3 = new ArrayList<Mundo>();
		List<Logro> logrosJ1 = new ArrayList<Logro>();
		List<Logro> logrosJ2 = new ArrayList<Logro>();
		List<Logro> logrosJ3 = new ArrayList<Logro>();
		Map<Integer, Nivel> niveles_actualesJ1 = new HashMap<Integer, Nivel>();
		Map<Integer, Nivel> niveles_actualesJ2 = new HashMap<Integer, Nivel>();
		Map<Integer, Nivel> niveles_actualesJ3 = new HashMap<Integer, Nivel>();
		List<Problema> problemas_resueltosJ1 = new ArrayList<Problema>();
		List<Problema> problemas_resueltosJ2 = new ArrayList<Problema>();
		List<Problema> problemas_resueltosJ3 = new ArrayList<Problema>();
		
		Mundo mundo,mundo2;
		Nivel nivel1,nivel2,nivel3,nivel4,nivel5;
		Problema problema1,problema21,problema22,problema31,problema32,problema4,problema5;
		Jugador jugador1,jugador2,jugador3;
		EstadoJugador estado1,estado2,estado3;
		Ayuda ayudaP1,ayudaP21,ayudaP22,ayudaP31,ayudaP32,ayudaP4,ayudaP5;
		Contenido contenidoP1, contenidoP21, contenidoP22, contenidoP31, contenidoP32, contenidoP4, contenidoP5;
		Logro logJ1,logJ21,logJ22,logJ23;
		
		Profesor profesor = new Profesor("Marcelo", "marce_fing", "1234",new ArrayList<Mensaje>(), new ArrayList<Mensaje>());
		mu.agregarProfesor(profesor);

		
		//mundo2= new Mundo(2,"Derivada","https:////c4.staticflickr.com/6/5322/30710213451_e1f72242bc.jpg", "Mundo de Derivada 1", 20,mundos_siguientes2,niveles2);
		
		mundo2= new Mundo(2,"Derivada","http://real28.ru/vozrozhdeniye/images/planet-saturn.png", "Mundo de Derivada 1", 20,mundos_siguientes2,niveles2);

		mundos_siguientes.add(mundo2);
		//mundo = new Mundo(1,"Calculo", "https://c2.staticflickr.com/6/5573/30710215201_f6d4689dd0.jpg", "Mundo de Calculo 1",20,mundos_siguientes, niveles);
		
		mundo = new Mundo(1,"Calculo", "http://vignette3.wikia.nocookie.net/angrybirds/images/c/cc/Death_star_cartoonish.png", "Mundo de Calculo 1",20,mundos_siguientes, niveles);

		
		
		
		nivel1 = new Nivel(listaPN1,mundo);
		nivel2 = new Nivel(listaPN2,mundo);
		nivel3 = new Nivel(listaPN3,mundo2);
		/*
		nivel4 = new Nivel(listaPN4,mundo);
		nivel5 = new Nivel(listaPN5,mundo);*/
		
		ayudaP1 = new Ayuda("La derivada es cuanto varia la funcion, cuando varia x");
		contenidoP1 = new Contenido("https://c5.staticflickr.com/6/5596/30761793476_59e5b95623_m.jpg");
		problema1 = new Problema("Resolver la siguiente derivada","8",10,ayudaP1,contenidoP1,nivel1,profesor, new Estadistica(0,0));
		
		ayudaP21 = new Ayuda("La derivada es cuanto varia la funcion, cuando varia x");
		contenidoP21 = new Contenido("https://c1.staticflickr.com/6/5458/30166690904_bdd8b75dd5.jpg");
		problema21 = new Problema("Resolver la siguiente derivada","x",10,ayudaP21,contenidoP21,nivel2,profesor, new Estadistica(0,0));
		ayudaP22 = new Ayuda("La derivada es cuanto varia la funcion, cuando varia x");
		contenidoP22= new Contenido("https://c2.staticflickr.com/6/5471/30798468345_4c8a01b673_m.jpg");
		problema22 = new Problema("Resolver la siguiente derivada","e^x",10,ayudaP22,contenidoP22,nivel2,profesor, new Estadistica(0,0));
		
		ayudaP31 = new Ayuda("La integral es el area bajo la curva de una funcion");
		contenidoP31 = new Contenido("https://c8.staticflickr.com/6/5520/30709763871_e1a306f4dd.jpg");
		problema31 = new Problema("Resolver la siguiente integral","4",10,ayudaP31,contenidoP31,nivel1,profesor, new Estadistica(0,0));
		ayudaP32 = new Ayuda("La integral es el area bajo la curva de una funcion");
		contenidoP32 = new Contenido("https://c5.staticflickr.com/6/5649/30166691004_743043f0d0.jpg");
		problema32 = new Problema("Resolver la siguiente integral","2",10,ayudaP32,contenidoP32,nivel3,profesor, new Estadistica(0,0));
		
		/*ayudaP4 = new Ayuda("La integral es el area bajo la curva de una funci�n");
		contenidoP4 = new Contenido("Preguntas/integralde2xde2a8.png");
		problema4 = new Problema("Resolver la siguiente integral","60",10,ayudaP4,contenidoP4,nivel4,profesor, new Estadistica(0,0));
		
		ayudaP5 = new Ayuda("La integral es el area bajo la curva de una funci�n");
		contenidoP5 = new Contenido("Preguntas/integraldetangente.png");
		problema5 = new Problema("Resolver la siguiente integral","-log(cos(x))",10,ayudaP5,contenidoP5,nivel5,profesor, new Estadistica(0,0));*/
		
		nivel1.agregarProblema(problema1);
		nivel1.agregarProblema(problema31);
		nivel2.agregarProblema(problema21);
		nivel2.agregarProblema(problema22);
		
		
		nivel3.agregarProblema(problema32);
		/*nivel3.agregarProblema(problema32);
		nivel4.agregarProblema(problema4);
		nivel5.agregarProblema(problema5);*/
		
		mundo.agregarNivel(nivel1);
		mundo.agregarNivel(nivel2);
		
		mundo2.agregarNivel(nivel3);
		
		/*
		mundo.agregarNivel(nivel4);
		mundo.agregarNivel(nivel5);*/
		
		mm.agregarMundo(mundo2);
		mm.agregarMundo(mundo);
		
		
		/*
		logJ1 = new Logro("Primera respuesta correcta");
		logrosJ1.add(logJ1);
		niveles_actualesJ1.put(mundo.getId(), nivel3);
		problemas_resueltosJ1.add(problema1);
		problemas_resueltosJ1.add(problema21);
		problemas_resueltosJ1.add(problema22);
		problemas_resueltosJ1.add(problema32);
		int exp = 0;
		for (Problema p : problemas_resueltosJ1) {
			exp += p.getPuntos_exp();
		}
		estado1 = new EstadoJugador(exp, mundos_completosJ1, logrosJ1, niveles_actualesJ1, problemas_resueltosJ1,new ArrayList<Integer>());
		jugador1 = new Jugador("Nicolas", "nico_fing", "fBTokenJ1", "imagenJ1", estado1);
		
		mu.agregarJugador(jugador1);
		
		logJ21 = new Logro("Primera respuesta correcta");
		logJ23 = new Logro("Has logrado 5 problemas correctos");
		logrosJ2.add(logJ21);	
		logrosJ2.add(logJ23);
		niveles_actualesJ2.put(mundo.getId(), nivel5);
		problemas_resueltosJ2.add(problema1);
		problemas_resueltosJ2.add(problema21);
		problemas_resueltosJ2.add(problema22);
		problemas_resueltosJ2.add(problema31);
		problemas_resueltosJ2.add(problema32);
		problemas_resueltosJ2.add(problema4);
		exp = 0;
		for (Problema p : problemas_resueltosJ2) {
			exp += p.getPuntos_exp();
		}
		estado2 = new EstadoJugador(exp, mundos_completosJ2, logrosJ2, niveles_actualesJ2, problemas_resueltosJ2,new ArrayList<Integer>());
		jugador2 = new Jugador("Maria", "mari_fing", "fBTokenJ2", "imagenJ2", estado2);
		
		mu.agregarJugador(jugador2);
		*/
		
		
		niveles_actualesJ3.put(mundo.getId(), nivel1);
		int exp = 0;
		for (Problema p : problemas_resueltosJ3) {
			exp += p.getPuntos_exp();
		}
		estado3 = new EstadoJugador(exp, mundos_completosJ3, logrosJ3, niveles_actualesJ3, problemas_resueltosJ3,new ArrayList<Integer>());
		jugador3 = new Jugador("Nicolas", "nico_fing", "fBTokenJ2", "imagenJ2", estado3);
		
		mu.agregarJugador(jugador3);
		
		
		Date fecha = new Date();
		for (int k=0;k<10;k++){
			Mensaje m = new Mensaje("contenido "+k, "asunto numero "+k, fecha, "marce_fing");
			Jugador jugador = mu.buscarJugador("nico_fing");
			jugador.agregar_mensaje_nuevo(m);		
			mu.guardarMensaje(m);
			mu.guardarUsuario(jugador);
		}
		
		for (int k=10;k<20;k++){
			Mensaje m = new Mensaje("contenido "+k, "asunto numero "+k, fecha, "marce_fing");
			Jugador jugador = mu.buscarJugador("nico_fing");
			jugador.agregar_mensaje_viejo(m);		
			mu.guardarMensaje(m);
			mu.guardarUsuario(jugador);
		}
	}
	
}

package Manejadores;

import java.util.ArrayList;
import java.util.Collections;
import Datatypes.DataPuntosJugador;
import java.util.List;
import Modelo.EstadoJugador;
import Modelo.Jugador;
import Modelo.Mensaje;
import Modelo.Profesor;
import Modelo.Usuario;
import Persistencia.HibernateUtility;
import java.util.Iterator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import Datatypes.DataJugador;

public class ManejadorUsuario {
	
	private static ManejadorUsuario instancia = new ManejadorUsuario();

	//----CONSTRUCTOR----//
	private ManejadorUsuario(){};
	
	//----GETTERS----//
	public static ManejadorUsuario getInstancia(){
		return instancia;
	}

	//----OPERACIONES----//
	//Retorna el DataJugador correspondiente al Jugador con nick = id_jugador
	public DataJugador obtenerDatosJugador(String id_jugador)
	{
		Session session = null;
		Jugador j = null;
		try{
			session = HibernateUtility.getSessionFactory().openSession();
			j =(Jugador)session.get(Jugador.class,id_jugador);
		} catch (Exception e){
			System.out.println("error:" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		return j.obtenerDataJugador();
	}
	
	//Agrega el Jugador jugador a la BD
	public void agregarJugador(Jugador jugador){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		session.saveOrUpdate(jugador);
		t.commit();
		session.close();
		System.out.println("successfully saved jugador");
	}
	
	//Retorna TRUE si existe un Jugador con nick = nick en el sistema. En caso contrario retorna FALSE.
	public boolean existeJugador(String nick){
		return (buscarJugador(nick)!=null);
	}
	
	//Retorna el Jugador con nick = nick de la BD.
	public Jugador buscarJugador(String nick){
		Session session = null;
		Jugador j = null;
		try{
			session = HibernateUtility.getSessionFactory().openSession();
			j =(Jugador)session.get(Jugador.class,nick);
		} catch (Exception e){
			System.out.println("error:" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		return j;
	}
	
	//Retorna el mensaje con id = id_mensaje de la BD
	public Mensaje buscarMensaje(int id_mensaje){
		Session session = null;
		Mensaje m = null;
		try{
			session = HibernateUtility.getSessionFactory().openSession();
			m =(Mensaje)session.get(Mensaje.class,id_mensaje);
		} catch (Exception e){
			System.out.println("error:" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		return m;
	}
	
	//Para cada Jugador del sistema crea un DataPuntosJugador con su nick y sus puntos adquiridos, agregandolo a una lista la cual
	//es retornada.
	public List<DataPuntosJugador> obtenerRanking(){
		
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		List<DataPuntosJugador> list_dpj = new ArrayList<>();

		List<Jugador> lista = session.createCriteria(Jugador.class).list();	
		for (Jugador j:lista){
			String nickJ = j.getNick();
			DataPuntosJugador dpj = j.obtenerDataPuntosJugador(nickJ);
			list_dpj.add(dpj);
		}
			
		session.close();
		
		Collections.sort(list_dpj);
		return list_dpj;
	}
	
	//Retorna el Profesor con nick = nick de la BD.
	public Profesor buscarProfesor(String nick){
		Session session = null;
		Profesor p = null;
		try{
			session = HibernateUtility.getSessionFactory().openSession();
			p =(Profesor)session.get(Profesor.class,nick);
		} catch (Exception e){
			System.out.println("error:" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		return p;
	}
	
	//Agrega el Profesor p a la BD.
	public void agregarProfesor(Profesor p){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		session.saveOrUpdate(p);
		t.commit();
		session.close();
		System.out.println("successfully saved profesor");
	}

	//Se borra todos los Jugadores de la BD
	public void borrar(){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		
		List<Jugador> lista_jugadores = session.createCriteria(Jugador.class).list();
		
		for (Iterator<Jugador> iterator = lista_jugadores.iterator(); iterator.hasNext();) {
			Jugador j = (Jugador) iterator.next();
			session.delete(j);
			System.out.println("borrar los jugadores");
		}
		
		t.commit();
	
		session.close();
		System.out.println("successfully borrado jugadores");
	}
	
	//Se borran todos los Profesores de la BD.
	public void borrarProfesores(){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		
		List<Profesor> lista_profe = session.createCriteria(Profesor.class).list();
		
		for (Iterator<Profesor> iterator = lista_profe.iterator(); iterator.hasNext();) {
			Profesor j = (Profesor) iterator.next();
			session.delete(j);
			System.out.println("borrar los jugadores");
		}
		
		t.commit();
	
		session.close();
		System.out.println("successfully borrado jugadores");
	}
	
	//Guarda el EstadoJugador estado correspondiente a una determinado Jugador en la BD.
	public void guardarEstado(EstadoJugador estado){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		session.saveOrUpdate(estado);
		t.commit();
		session.close();
		System.out.println("successfully saved estado");
	}
	
	//Guarda el mensaje m en la BD.
	public void guardarMensaje(Mensaje m){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		session.saveOrUpdate(m);
		t.commit();
		session.close();
		System.out.println("successfully saved mensaje");
	}
	
	//Guarda el Usuario p en la BD.
	public void guardarUsuario(Usuario p){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		session.saveOrUpdate(p);
		t.commit();
		session.close();
		System.out.println("successfully saved profe");
	}
	
	//Retorna una lista con todos los Jugadores de la BD.
	public List<Jugador> obtenerJugadores(){                              
		
		Session session = null;
		List<Jugador> lista_jugadores = new ArrayList<Jugador>();
		try
		{
			session = HibernateUtility.getSessionFactory().openSession();
			lista_jugadores = (List<Jugador>)session.createCriteria(Jugador.class).list();
		}
		catch (Exception e)
		{
			System.out.println("error:" + e.getMessage());
		}
		finally
		{
			if (session != null && session.isOpen())
			{
				session.close();
			}
		}
		return lista_jugadores;				
	}
	
	//Retorna el Usuario con nick = nick de la BD.
	public Usuario buscarUsuario(String nick){
		Session session = null;
		Usuario user = null;
		try{
			session = HibernateUtility.getSessionFactory().openSession();
			user =session.get(Usuario.class,nick);	
		} catch (Exception e){
			System.out.println("error:" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		return user;
	}
	
}
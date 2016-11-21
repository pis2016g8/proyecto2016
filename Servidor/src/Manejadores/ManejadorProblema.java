package Manejadores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import Modelo.Problema;
import Persistencia.HibernateUtility;

public class ManejadorProblema {

	private static ManejadorProblema instancia = new ManejadorProblema();
	
	//----CONSTRUCTOR----//
	private ManejadorProblema(){};
	
	//----GETTERS----//
	public static ManejadorProblema getInstancia(){
		return instancia;
	}
	
	//----OPERACIONES----//
	//Se agrega el problema p a la BD
	public void agregarProblema(Problema p){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		session.saveOrUpdate(p);
		t.commit();
		session.close();
		System.out.println("successfully saved problema");
	}
	
	//Retorna un Mapa<Integer,Problema> con todos los Problemas del sistema, donde la clave del mapa es el
	//id del problema correspondiente.
	public Map<Integer, Problema> getProblemas() {    ///////// Esto no sirve para nada
		List<Problema> problemas = null;
		Map<Integer, Problema> mapProblemas = null;
		Session session = null;
		try{
	       session = HibernateUtility.getSessionFactory().openSession();
	       org.hibernate.Transaction tx = session.beginTransaction();
	       problemas = session.createCriteria(Problema.class).list();
		} catch (Exception e){
			System.out.println("error:" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		if (problemas != null){
			mapProblemas = new HashMap<Integer,Problema>();
			for (Problema p : problemas){
				mapProblemas.put(p.getId(), p);
			}
			problemas = null;
		}
		return mapProblemas;
	}
	
	//Retorna TRUE si el problema con id = id_problema es el ultimo de su respectivo nivel,
	//en caso contrario retorna FALSE.
	public boolean ultimaNivel(int id_problema){
		Problema pro = buscarProblema(id_problema);
		return pro.getNivel().esUltima(id_problema);
	}
	
	//Retorna los puntos de experiencia a ganar en caso de que la respuesta sea la correcta del problema
	//con id = id_problema. En caso contrario retorna 0.
	public int verificarRespuesta(int id_problema, String respuesta){
		Problema problema = buscarProblema(id_problema);
		if (problema.verificarRespuesta(respuesta)){
			return problema.getPuntos_exp();
		}else{
			return 0;
		}
	}
	
	//Retorna el problema con id = id_problema de la BD
	public Problema buscarProblema(int id_problema){
		Session session = null;
		Problema p = null;
		try{
			session = HibernateUtility.getSessionFactory().openSession();
			p =(Problema)session.get(Problema.class,id_problema);
			
		} catch (Exception e){
			System.out.println("error:" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()){
				session.close();
			}
		}
		return p;
	}
	
	//Retorna la ayuda del problema con id = id_problema
	public String getAyuda(int id_problema){		
		return buscarProblema(id_problema).getAyuda().getInfo();
	}
		
	//Borra todos los problemas de la BD.
	public void borrar(){
		SessionFactory factory= HibernateUtility.getSessionFactory();
		Session session=factory.openSession();
		org.hibernate.Transaction t= session.beginTransaction();
		List<Problema> lista_problemas = session.createCriteria(Problema.class).list();
		for (Iterator<Problema> iterator = lista_problemas.iterator(); iterator.hasNext();) {
			Problema p = (Problema) iterator.next();
			session.delete(p);
		}
		t.commit();
		session.close();
		System.out.println("successfully borrado problemas");
	}
	
}

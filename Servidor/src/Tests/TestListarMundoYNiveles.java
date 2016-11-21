package Tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import Controladores.ControladorProfesor;
import Controladores.IControladorProfesor;
import Datatypes.DataMundo;
import Datatypes.DataNivel;
import Manejadores.ManejadorMundo;
import Manejadores.ManejadorUsuario;
import Modelo.Mundo;
import Modelo.Nivel;
import Modelo.Problema;

public class TestListarMundoYNiveles {
	
	IControladorProfesor cp = new ControladorProfesor();
	ManejadorMundo mm = ManejadorMundo.getInstancia();
	ManejadorUsuario mu=ManejadorUsuario.getInstancia();
	Mundo m1;
	Mundo m2;
	Mundo m3;
	
	Nivel n1m1;
	Nivel n2m1;
	
	Nivel n1m2;
	Nivel n2m2;
	
	Nivel n1m3;
	Nivel n2m3;
	
	ArrayList<Mundo> mundos_siguientesM1 = new ArrayList<Mundo>();
	ArrayList<Mundo> mundos_siguientesM2 = new ArrayList<Mundo>();
	ArrayList<Mundo> mundos_siguientesM3 = new ArrayList<Mundo>();
	
	ArrayList<Nivel> nivelesM1 = new ArrayList<Nivel>();
	ArrayList<Nivel> nivelesM2 = new ArrayList<Nivel>();
	ArrayList<Nivel> nivelesM3 = new ArrayList<Nivel>();
	
	ArrayList<Problema> listaP_M1= new ArrayList<Problema>();
	ArrayList<Problema> listaP_2M1= new ArrayList<Problema>();
	ArrayList<Problema> listaP_M2= new ArrayList<Problema>();
	ArrayList<Problema> listaP_2M2= new ArrayList<Problema>();
	ArrayList<Problema> listaP_M3= new ArrayList<Problema>();
	ArrayList<Problema> listaP_2M3= new ArrayList<Problema>();
	
	
	@Before
	public void setUp() throws Exception {
		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
		
		m1 = new Mundo(1,"Jupiter", "imagen", "descripcion",0,mundos_siguientesM1, nivelesM1);
		n1m1 = new Nivel(listaP_M1,m1);
		n2m1 = new Nivel(listaP_2M1,m1);
		
		m2 = new Mundo(2,"Jupiter2", "imagen2", "descripcion2",0,mundos_siguientesM2, nivelesM2);
		n1m2 = new Nivel(listaP_M2,m2);
		n2m2 = new Nivel(listaP_2M2,m2);
		
		m3 = new Mundo(3,"Jupiter3", "imagen3", "descripcion3",0,mundos_siguientesM3, nivelesM3);
		n1m3 = new Nivel(listaP_M3,m3);
		n2m3 = new Nivel(listaP_2M3,m3);
		
		m1.agregarNivel(n1m1);
		m1.agregarNivel(n2m1);
		m2.agregarNivel(n1m2);
		m2.agregarNivel(n2m2);
		m3.agregarNivel(n1m3);
		m3.agregarNivel(n2m3);
		
		mm.agregarMundo(m1);
		mm.agregarMundo(m2);
		mm.agregarMundo(m3);
	}

	
	@Test
	public void test() {
		List<DataMundo> mundos = cp.listarMundosProfesor().getLista_mundos();
		
		assertEquals(3,mundos.size());
		assertEquals(m1.getId(),mundos.get(0).getId_mundo());
		assertEquals(m1.getImagen(),mundos.get(0).getImagen());
		assertEquals(m1.getDescripcion(),mundos.get(0).getDescripcion());
		
		assertEquals(m2.getId(),mundos.get(1).getId_mundo());
		assertEquals(m2.getImagen(),mundos.get(1).getImagen());
		assertEquals(m2.getDescripcion(),mundos.get(1).getDescripcion());
		
		assertEquals(m3.getId(),mundos.get(2).getId_mundo());
		assertEquals(m3.getImagen(),mundos.get(2).getImagen());
		assertEquals(m3.getDescripcion(),mundos.get(2).getDescripcion());
		
		List<DataNivel> nivelesM1 = cp.listarNivelesMundoProfesor(m1.getId()).getLista();
		assertEquals(2,nivelesM1.size());
		assertEquals(n1m1.getId_nivel(),nivelesM1.get(0).getId_nivel());
		assertEquals(n1m1.getNro_nivel(),nivelesM1.get(0).getNum_nivel());

		assertEquals(n2m1.getId_nivel(),nivelesM1.get(1).getId_nivel());
		assertEquals(n2m1.getNro_nivel(),nivelesM1.get(1).getNum_nivel());
		
		List<DataNivel> nivelesM2 = cp.listarNivelesMundoProfesor(m2.getId()).getLista();
		assertEquals(2,nivelesM2.size());
		assertEquals(n1m2.getId_nivel(),nivelesM2.get(0).getId_nivel());
		assertEquals(n1m2.getNro_nivel(),nivelesM2.get(0).getNum_nivel());

		assertEquals(n2m2.getId_nivel(),nivelesM2.get(1).getId_nivel());
		assertEquals(n2m2.getNro_nivel(),nivelesM2.get(1).getNum_nivel());
		
		List<DataNivel> nivelesM3 = cp.listarNivelesMundoProfesor(m3.getId()).getLista();
		assertEquals(2,nivelesM3.size());
		assertEquals(n1m3.getId_nivel(),nivelesM3.get(0).getId_nivel());
		assertEquals(n1m3.getNro_nivel(),nivelesM3.get(0).getNum_nivel());
		
		assertEquals(n2m3.getId_nivel(),nivelesM3.get(1).getId_nivel());
		assertEquals(n2m3.getNro_nivel(),nivelesM3.get(1).getNum_nivel());

		mu.borrar();
		mm.borrar();
		mu.borrarProfesores();
	}

}

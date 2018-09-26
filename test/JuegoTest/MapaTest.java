package JuegoTest;

import static org.junit.Assert.*;
import org.junit.Test;

import dp.GameOfThrones.Juego.Llave;
import dp.GameOfThrones.Juego.Mapa;
import dp.GameOfThrones.Juego.Personaje;
import dp.GameOfThrones.Juego.Sala;
import dp.GameOfThrones.Juego.Stark;

public class MapaTest {
	
	Mapa m= Mapa.getInstancia(29, 5, 6, 3);
	
	@Test
	public void testGetInstancia() {
		Mapa m1= Mapa.getInstancia();
		
		assertEquals(m1.getDimX(), 5);
		assertEquals(m1.getDimY(), 6);
		assertEquals(m1.getSalaPuerta(), 29);
		
		m1= Mapa.getInstancia(29, 5, 6, 3);
		
		assertEquals(m1.getDimX(), 5);
		assertEquals(m1.getDimY(), 6);
		assertEquals(m1.getSalaPuerta(), 29);
	}

	@Test
	public void testGetInstanciaIntegerIntegerIntegerInteger() {
		Mapa m1= Mapa.getInstancia();
		
		assertEquals(m1.getDimX(), 5);
		assertEquals(m1.getDimY(), 6);
		assertEquals(m1.getSalaPuerta(), 29);
		
		m1= Mapa.getInstancia(29, 5, 6, 3);
		
		assertEquals(m1.getDimX(), 5);
		assertEquals(m1.getDimY(), 6);
		assertEquals(m1.getSalaPuerta(), 29);
	}

	@Test
	public void testGenerarParedes() {
		//PRUEBAS HECHAS SOBRE 'REGISTRO.LOG' SUMINISTRADOS POR LOS PROFESORES
	}

	@Test
	public void testFormarAtajos() {
		//PRUEBAS HECHAS SOBRE 'REGISTRO.LOG' SUMINISTRADOS POR LOS PROFESORES
	}

	@Test
	public void testObtenerFila() {
		assertEquals(m.obtenerFila(0), 0);
		assertEquals(m.obtenerFila(20), 4);
		assertEquals(m.obtenerFila(66), 13);
		assertEquals(m.obtenerFila(43), 8);
	}

	@Test
	public void testObtenerColumna() {
		assertEquals(m.obtenerColumna(0), 0);
		assertEquals(m.obtenerColumna(20), 0);
		assertEquals(m.obtenerColumna(66), 1);
		assertEquals(m.obtenerColumna(43), 3);
	}

	@Test
	public void testGetSala() {
		Sala s1= new Sala(20);
		Sala s2= new Sala(0);
		Sala s3= new Sala(14);
		Sala s4= new Sala(29);
		
		assertEquals(m.getSala(4, 0), s1);
		assertEquals(m.getSala(0, 0), s2);
		assertEquals(m.getSala(2, 4), s3);
		assertEquals(m.getSala(5, 4), s4);
	}
	
	@Test
	public void testInsertarPersonaje() {
		Personaje p1= new Stark();
		Personaje p2= new Stark();
		Personaje p3= new Stark();
		Personaje p4= new Stark();
		Sala s; 
		for(int i=0; i<m.getDimY(); i++){			//vaciamos el mapa de Pj's posibles de otras pruebas
			for(int j=0; j<m.getDimX(); j++){
				s=m.getSala(i, j);
				while(s.tienePersonajes()){
					s.eliminarPersonaje();
				}
			}
		}
		m.insertarPersonaje(p1, 0);
		assertEquals(m.getSala(0, 0).cuantosPersonajes(), new Integer(1));
		
		m.insertarPersonaje(p2, 0);
		assertEquals(m.getSala(0, 0).cuantosPersonajes(), new Integer(2));
		
		m.insertarPersonaje(p3, 6);
		assertEquals(m.getSala(1, 1).cuantosPersonajes(), new Integer(1));
		
		m.insertarPersonaje(p4, 15);
		assertEquals(m.getSala(3, 0).cuantosPersonajes(), new Integer(1));
		assertEquals(m.getSala(3, 1).cuantosPersonajes(), new Integer(0));
		
		m.insertarPersonaje(p1, 29);
		assertEquals(m.getSala(5, 4).cuantosPersonajes(), new Integer(1));
		
		m.insertarPersonaje(p2, 1);
		assertEquals(m.getSala(0, 1).cuantosPersonajes(), new Integer(1));
	}

	@Test
	public void testInsertarConquistador() {
		Sala s= m.getSalaConquistadores();
		
		assertEquals(s.cuantosPersonajes(), new Integer(0));
		m.insertarConquistador(new Stark());
		assertEquals(s.cuantosPersonajes(), new Integer(1));
		m.insertarConquistador(new Stark());
		m.insertarConquistador(new Stark());
		m.insertarConquistador(new Stark());
		assertEquals(s.cuantosPersonajes(), new Integer(4));
	}

	@Test
	public void testInsertarLlave() {
		
		m.insertarLlave(new Llave(1), 0);
		assertTrue(m.getSala(0, 0).tieneLlave());
		
		m.insertarLlave(new Llave(1), 0);
		assertTrue(m.getSala(0, 0).tieneLlave());
		
		m.insertarLlave(new Llave(1), 6);
		assertTrue(m.getSala(1, 1).tieneLlave());
		
		m.insertarLlave(new Llave(1), 15);
		assertTrue(m.getSala(3, 0).tieneLlave());
		assertFalse(m.getSala(3, 1).tieneLlave());
		
		m.insertarLlave(new Llave(1), 29);
		assertTrue(m.getSala(5, 4).tieneLlave());
		
		m.insertarLlave(new Llave(1), 1);
		assertTrue(m.getSala(0, 1).tieneLlave());
	}

	@Test
	public void testCreacionLlaves() {
		Llave[] combinacion=
			{new Llave(1), new Llave(3), new Llave(5), new Llave(7), new Llave(9)};
		
		Llave[] resultados;
		
		Llave[] experado=
			{new Llave(0), new Llave(1), new Llave(1), new Llave(2), new Llave(3),new Llave(3), 
			new Llave(4), new Llave(5),new Llave(5),new Llave(6), new Llave(7), new Llave(7), 
			new Llave(8),new Llave(9), new Llave(9)};
		
		resultados=m.creacionLlaves(combinacion);
		
		assertArrayEquals(resultados, experado);
	}

	@Test
	public void testRepartoLlaves() {
		//PRUEBAS HECHAS SOBRE 'REGISTRO.LOG' SUMINISTRADOS POR LOS PROFESORES
	}

}

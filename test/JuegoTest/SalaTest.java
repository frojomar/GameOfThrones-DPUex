package JuegoTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dp.GameOfThrones.Juego.Llave;
import dp.GameOfThrones.Juego.Personaje;
import dp.GameOfThrones.Juego.Sala;
import dp.GameOfThrones.Juego.Stark;


public class SalaTest {

	Sala s1;
	Sala s2;
	
	@Before
	public void starUP(){
		s1= new Sala();
		
		s2= new Sala(2);
	}
	
	@Test
	public void testSalaInt() {
		assertNotEquals(s1.getNumero(), s2.getNumero());
		s1.setNumero(2);
		assertEquals(s1.getNumero(), s2.getNumero());
		assertEquals(s2.getNumero(), 2);
		assertEquals(s2.getMarca(), 2);
	}

	@Test
	public void testTieneLlave() {
		assertFalse(s1.tieneLlave());
		assertFalse(s2.tieneLlave());
		
		s2.insertarLlave(new Llave(5));
		
		assertTrue(s2.tieneLlave());
		
		s2.eliminarLlave();
		
		assertFalse(s2.tieneLlave());
	}

	@Test
	public void testObtenerLlave() {
		Llave l;
		
		assertNull(s2.obtenerLlave());
		assertNull(s1.obtenerLlave());
		
		l=new Llave(5);
		s2.insertarLlave(l);
		
		assertEquals(s2.obtenerLlave(), l);
		
		l=new Llave(20);
		s2.insertarLlave(l);
		
		assertEquals(s2.obtenerLlave(), new Llave(5));
		s2.insertarLlave(new Llave(1));
		assertEquals(s2.obtenerLlave(), new Llave(1));
		s2.eliminarLlave();
		assertEquals(s2.obtenerLlave(), new Llave(5));
		
	}

	@Test
	public void testEliminarLlave() {
		s1.eliminarLlave(); //para comprobar que no da error si eliminamos sobre una lista de llaves vacía.
		s1.insertarLlave(new Llave(1));
		s1.insertarLlave(new Llave(2));
		s1.insertarLlave(new Llave(3));
		s1.insertarLlave(new Llave(4));
		s1.eliminarLlave();
		assertNotEquals(s1.obtenerLlave(), new Llave(1));
		assertEquals(s1.obtenerLlave(), new Llave(2));
	}

	@Test
	public void testInsertarLlave() {
		s1.insertarLlave(new Llave(5));
		assertEquals(s1.obtenerLlave(), new Llave(5));
		s1.insertarLlave(new Llave(2));
		assertEquals(s1.obtenerLlave(), new Llave(2));
		s1.insertarLlave(new Llave(4));
		assertEquals(s1.obtenerLlave(), new Llave(2));
		s1.insertarLlave(new Llave(-1));
		assertEquals(s1.obtenerLlave(), new Llave(-1));
		s1.insertarLlave(new Llave(1));
		assertEquals(s1.obtenerLlave(), new Llave(-1));
		s1.insertarLlave(new Llave(4));
		assertEquals(s1.obtenerLlave(), new Llave(-1));
		s1.insertarLlave(new Llave(-2));
		assertEquals(s1.obtenerLlave(), new Llave(-2));
		s1.insertarLlave(new Llave(0));
		assertEquals(s1.obtenerLlave(), new Llave(-2));
	}

	@Test
	public void testTienePersonajes() {
		assertFalse(s1.tienePersonajes());
		assertFalse(s2.tienePersonajes());

		s1.insertarPersonaje(new Stark());
		assertTrue(s1.tienePersonajes());
		s1.eliminarPersonaje();
		assertFalse(s1.tienePersonajes());
		
		s2.insertarPersonaje(new Stark());
		s2.insertarPersonaje(new Stark());
		s2.insertarPersonaje(new Stark());
		s2.insertarPersonaje(new Stark());
		assertTrue(s2.tienePersonajes());
		s2.eliminarPersonaje();
		assertTrue(s2.tienePersonajes());
	}

	@Test
	public void testCuantosPersonajes() {
		assertEquals(s1.cuantosPersonajes(), new Integer(0));
		assertEquals(s2.cuantosPersonajes(), new Integer(0));

		s1.insertarPersonaje(new Stark());
		assertEquals(s1.cuantosPersonajes(), new Integer(1));
		s1.eliminarPersonaje();
		assertEquals(s1.cuantosPersonajes(), new Integer(0));
		
		s2.insertarPersonaje(new Stark());
		s2.insertarPersonaje(new Stark());
		s2.insertarPersonaje(new Stark());
		s2.insertarPersonaje(new Stark());
		assertEquals(s2.cuantosPersonajes(), new Integer(4));
		s2.eliminarPersonaje();
		assertEquals(s2.cuantosPersonajes(), new Integer(3));
	}

	@Test
	public void testObtenerPersonaje() {
		Personaje p1= new Stark();
		Personaje p2= new Stark();
		Personaje p3= new Stark();
		Personaje p4= new Stark();

		assertEquals(s1.obtenerPersonaje(), null);
		assertEquals(s2.obtenerPersonaje(), null);

		s1.insertarPersonaje(p1);
		assertEquals(s1.obtenerPersonaje(), p1);
		assertEquals(s1.obtenerPersonaje(), p1);
		
		s2.insertarPersonaje(p2);
		s2.insertarPersonaje(p3);
		s2.insertarPersonaje(p4);
		assertEquals(s2.obtenerPersonaje(), p1);
		s2.eliminarPersonaje();
		assertEquals(s2.obtenerPersonaje(), p2);
		
	}

	@Test
	public void testEliminarPersonaje() {
		Personaje p1= new Stark();
		Personaje p2= new Stark();
		Personaje p3= new Stark();
		Personaje p4= new Stark();

		assertEquals(s1.obtenerPersonaje(), null);
		assertEquals(s2.obtenerPersonaje(), null);
		s1.eliminarPersonaje();
		s2.eliminarPersonaje();
		assertEquals(s1.obtenerPersonaje(), null);
		assertEquals(s2.obtenerPersonaje(), null);

		s1.insertarPersonaje(p1);
		assertEquals(s1.cuantosPersonajes(), new Integer(1));
		s1.eliminarPersonaje();
		assertEquals(s1.cuantosPersonajes(), new Integer(0));
		
		s2.insertarPersonaje(p2);
		s2.insertarPersonaje(p3);
		s2.insertarPersonaje(p4);
		assertEquals(s2.cuantosPersonajes(), new Integer(3));
		s2.eliminarPersonaje();
		assertEquals(s2.obtenerPersonaje(), p3);
	}

	@Test
	public void testInsertarPersonaje() {
		Personaje p1= new Stark();
		Personaje p2= new Stark();
		
		assertEquals(s1.cuantosPersonajes(), new Integer(0));
		assertEquals(s2.cuantosPersonajes(), new Integer(0));
		s1.insertarPersonaje(p1);
		s1.insertarPersonaje(p2);
		assertEquals(s1.cuantosPersonajes(), new Integer(2));
		assertEquals(s1.obtenerPersonaje(), p1);
	}

	@Test
	public void testTratarPersonajes() {
		//PRUEBAS REALIZADAS CON FICHEROS DE 'REGISTRO.LOG' APORTADOS POR LOS PROFESORES
	}

	@Test
	public void testEqualsObject() {
		Sala s3= new Sala();
		
		assertFalse(s1.equals(s2));
		assertTrue(s1.equals(s3));
		assertTrue(s2.equals(s2));
		assertTrue(s2.equals(new Sala(2)));
		s3.setMarca(2);
		assertFalse(s2.equals(s3));
		s3.setNumero(2);
		assertTrue(s2.equals(s3));
	}

	@Test
	public void testCompareTo() {
		Sala s3= new Sala();
		
		assertEquals(s1.compareTo(s2), -1);
		assertEquals(s1.compareTo(s3), 0);
		assertEquals(s2.compareTo(s2), 0);
		assertEquals(s2.compareTo(new Sala(2)), 0);
		s3.setMarca(2);
		assertEquals(s2.compareTo(s3), 1);
		s3.setNumero(2);
		assertEquals(s2.compareTo(s3), 0);	
	}

}

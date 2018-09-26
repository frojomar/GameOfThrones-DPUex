package JuegoTest;

import static org.junit.Assert.*;

import org.junit.Test;

import dp.GameOfThrones.Juego.Lannister;
import dp.GameOfThrones.Juego.Mapa;
import dp.GameOfThrones.Juego.Personaje;
import dp.GameOfThrones.Juego.Puerta;
import dp.GameOfThrones.Juego.Sala;
import dp.GameOfThrones.Juego.Stark;

public class LannisterTest {

	@Test
	public void testCargarDirecciones() {
		//PROBADO CON LOS REGISTROS ENTREGADOS POR LOS PROFESORES
	}

	@Test
	public void testInteractuarSala() {
		Personaje p= new Lannister();
		
		Sala s1= new Sala(0);
		Sala s2= new Sala(1);
		Sala s3= new Sala(2);
		
		assertTrue(s1.tieneLlave()== false);
		p.interactuarSala(s1);
		assertTrue(s1.tieneLlave()== true);
		p.interactuarSala(s1);
		assertTrue(s1.tieneLlave()== true);	
		
		assertTrue(s2.tieneLlave()== false);
		p.interactuarSala(s2);
		assertTrue(s2.tieneLlave()== false);
		
		assertTrue(s3.tieneLlave()== false);
		p.interactuarSala(s3);
		assertTrue(s3.tieneLlave()==true);
		p.interactuarSala(s3);
		assertTrue(s3.tieneLlave()== true);
		s3.eliminarLlave();
		assertTrue(s3.tieneLlave()== true);
		s3.eliminarLlave();
		assertTrue(s3.tieneLlave()== false);

	}

	@Test
	public void testInteractuarPuerta() {
		Personaje p1= new Stark("p1", '1', 1);
		Personaje p2= new Stark("p2", '2', 1);
		Mapa m= Mapa.getInstancia(29, 5, 6, 3);
		m.insertarPersonaje(p1, 0);
		m.insertarPersonaje(p2, 29);
		
		assertTrue(p1.interactuarPuerta(m.getSala(0, 0), 29, new Puerta(100), new Sala(1111)));
		assertTrue(p2.interactuarPuerta(m.getSala(4, 5), 29, new Puerta(100), new Sala(1111)));	
	
	}



	@Test
	public void testEqualsObject() {
		Personaje p1= new Lannister();
		Personaje p2= new Lannister();
		Personaje p3= new Lannister("Jaime",'J',3);
		Personaje p4= new Lannister("Joffrey", 'J',3);
		Personaje p5= new Lannister("Cersei", 'C', 3);
		Personaje p6= new Lannister("Cersei", 'C', 4);
		Personaje p7= new Lannister("Tyrion", 'T', 5);
		Personaje p8= new Lannister("Tyrion", 'L', 5);
		
		
		assertTrue(p1.equals(p1)); //comprobacion con mismo puntero
		assertTrue(p1.equals(p2)); //comprobacion con dos por defecto
		assertTrue(p3.equals(p4)); //comprobacion con dos con misma marca, distinto nombre
		assertTrue(p5.equals(p6)); //comprobacion con dos con misma marca y nombre, distinto turno
		assertFalse(p6.equals(p7)); //comprobacion con distintas marcas, nombres y turnos
		assertFalse(p7.equals(p8)); //comprobacion con distintas marcas, mismo nombre, mismo turno
	}

	@Test
	public void testCompareTo() {
		//Misma Prueba para todas las subclases
		Personaje p1= new Lannister();
		Personaje p2= new Lannister();
		Personaje p3= new Lannister("Jaime",'J',3);
		Personaje p4= new Lannister("Joffrey", 'J',3);
		Personaje p5= new Lannister("Cersei", 'C', 3);
		Personaje p6= new Lannister("Cersei", 'C', 4);
		Personaje p7= new Lannister("Tyrion", 'T', 5);
		Personaje p8= new Lannister("Tyrion", 'L', 5);
		
		assertTrue(p1.compareTo(p2)== 0);
		assertTrue(p3.compareTo(p3)== 0);
		assertTrue(p5.compareTo(p7)< 0);
		assertTrue(p7.compareTo(p5)> 0);
		assertTrue(p7.compareTo(p8)> 0);
		assertTrue(p3.compareTo(p4)== 0);
		assertTrue(p6.compareTo(p8)< 0);
	}

}

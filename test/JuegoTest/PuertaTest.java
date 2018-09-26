package JuegoTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import dp.GameOfThrones.Juego.Llave;
import dp.GameOfThrones.Juego.Puerta;

public class PuertaTest {

	Puerta p1;
	Puerta p2;
	
	@Before
	public void starUP(){
		p1= new Puerta(5);
		
		Llave comb[]= new Llave[5];
		int k=1;
		for(int i=0; i<5; i++){
			comb[i]=new Llave(k);
			k=k+2;
		}
		p2= new Puerta(comb,2);
	}
	
	@Test
	public void testPuertaInt() {
		assertEquals(p1.getCondAlt(), 5);
		assertNotEquals(p1.getCondAlt(), 4);
		assertNotEquals(new Puerta(4), 5);
		
	}

	@Test
	public void testConfigurar() {
		assertFalse(p2.estaAbierta());
		assertTrue(p1.estaAbierta());

		Llave comb[]= new Llave[5];
		for(int i=0; i<5; i++){
			comb[i]=new Llave(i+2);
		}
		p1.configurar(comb);
		
		assertFalse(p1.estaAbierta());
	}

	@Test
	public void testReiniciarPuerta() {
		assertTrue(p1.estaAbierta());
		//p1.reiniciarPuerta();
		assertTrue(p1.estaAbierta());
		
		assertFalse(p2.estaAbierta());
		p2.abrir(new Llave(1));
		p2.abrir(new Llave(3));
		p2.abrir(new Llave(5));
		p2.abrir(new Llave(7));
		p2.abrir(new Llave(9));
		assertTrue(p2.estaAbierta());
		p2.reiniciarPuerta();
		assertFalse(p2.estaAbierta());

	}

	@Test
	public void testEstaAbierta() {
		Puerta p3= new Puerta(4);
		assertTrue(p3.estaAbierta());
		p3.cerrar();
		assertFalse(p3.estaAbierta());
		
		assertFalse(p2.estaAbierta());
	}

	@Test
	public void testCerrar() {
		Puerta p3= new Puerta(4);
		assertTrue(p3.estaAbierta());
		p3.cerrar();
		assertFalse(p3.estaAbierta());
	}

	@Test
	public void testAbrir() {
		p1.abrir(new Llave(5));
		
		assertFalse(p2.estaAbierta());
		p2.abrir(new Llave(1));
		p2.abrir(new Llave(3));
		assertFalse(p2.estaAbierta());
		p2.abrir(new Llave(5));
		p2.abrir(new Llave(7));
		p2.abrir(new Llave(9));
		assertTrue(p2.estaAbierta());
	}

	/**
	 * MÁS PRUEBAS EN EL MAIN DE LA CLASE dp.GameOfThrones.Juego.Puerta
	 */
}

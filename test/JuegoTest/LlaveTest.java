package JuegoTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import dp.GameOfThrones.Juego.Llave;


public class LlaveTest {

	Llave l1;
	Llave l2;
	
	@Before
	public void starUP(){
		l1= new Llave(50);
		l2= new Llave(-1);
	}
	
	@Test
	public void testLlaveInteger() {
		assertEquals(new Llave(50).getValor(), new Integer(50));
		assertNotEquals(l1.getValor(), new Integer(20));
	}

	@Test
	public void testEqualsObject() {
		assertFalse(l1.equals(l2));
		assertFalse(l2.equals(l1));
		assertTrue(l1.equals(l1));
		assertTrue(l1.equals(new Llave(50)));
		assertFalse(l1.equals(new Llave(30)));
	}

	@Test
	public void testCompareTo() {
		assertEquals(l1.compareTo(l2), 1);
		assertEquals(l2.compareTo(l1), -1);
		assertEquals(l1.compareTo(l1), 0);
		
	}

}

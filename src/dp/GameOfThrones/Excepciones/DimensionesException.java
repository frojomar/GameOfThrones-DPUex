package dp.GameOfThrones.Excepciones;

/**
 * Declaracion de la Exception tipo: DimensionesException
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
@SuppressWarnings("serial")
public class DimensionesException extends Exception{ 

	public String mensaje(){
		return "Dimensiones del mapa no validas (dimX<3 o/y dimY<3). Cambielas en 'inicio.txt'";
	}
}

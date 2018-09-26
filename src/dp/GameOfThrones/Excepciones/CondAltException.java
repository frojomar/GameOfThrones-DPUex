package dp.GameOfThrones.Excepciones;

/**
 * Declaracion de la Exception tipo: CondAltException
 *
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
@SuppressWarnings("serial")
public class CondAltException extends Exception{
	public String mensaje(){
		return "Condicion de altura de apertura no valida (debe ser >=1). Cambiela en 'inicio.txt'";
	}
}

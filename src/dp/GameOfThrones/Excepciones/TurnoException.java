package dp.GameOfThrones.Excepciones;


/**
 * Declaracion de la Exception tipo: TurnoException
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
@SuppressWarnings("serial")
public class TurnoException extends Exception{
	public String mensaje(){
		return "Turno de comienzo del perosnaje negativo o 0 (debe ser >=1). Cambielo en 'inicio.txt'";
	}
}

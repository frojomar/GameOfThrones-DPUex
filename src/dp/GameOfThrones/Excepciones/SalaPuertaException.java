package dp.GameOfThrones.Excepciones;

/**
 * Declaracion de la Exception tipo: SalaPuertaException
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
@SuppressWarnings("serial")
public class SalaPuertaException extends Exception {
	public String mensaje(){
		return "La localizacion de la sala de la Puerta esta fuera de los limites del mapa(debe ser entre 0 y (dimX*dimY-1))";
	}
}

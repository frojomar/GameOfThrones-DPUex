package dp.GameOfThrones.Juego;

/**
 * EC3 - proyecto16_17
 * Implementacion de la clase Defensor (subclase de Personaje).
 *
 * @version 1.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public abstract class Defensor extends Personaje{
	
	/**Constructor por defecto de la clase Defensor
	 * 
	 */
	public Defensor(){
	}
	
	/**Constructor parametrizado de la clase Defensor
	 * 
	 * @param nombre String que contiene el nombre que tomará el personaje
	 * @param identificador char que contiene el caracter por el que se identificará al personaje
	 * @param turno int que contiene el turno en el que comenzará la simulación el personaje
	 */
	public Defensor(String nombre, char identificador, int turno) {
		super(nombre, identificador, turno);
	}

	/**Metodo abstracto que se implementará en las subclases defensoras que se encarga de
	 * cargar las direcciones de movimiento de cada Personaje Defensor.
	 */
	protected abstract void cargarDirecciones();
	
	/**Metodo que dice de que tipo es el personaje
	 * 
	 * @return devuelve un string que contendrá el tipo del personaje
	 */
	public String tipoPersonaje(){
		return "defensor";
	}
	
	/**Metodo que interactua con la puerta llamando al cerrar de esta y reconfigurandola
	 * 
	 * @param s sala en la que nos encontramos
	 * @param salaPuerta numero de la sala que corresponde a la puerta
	 * @param salaConquistadores sala '1111' en la que se inserta a los que han conquistado el trono
	 * @param p puerta del trono
	 * @return devuelve true si estamos en la sala de la puerta y se ha podido ejecutar y false si no es así
	 */
	public boolean interactuarPuerta (Sala s, int salaPuerta,Puerta p, Sala salaConquistadores){
		
		System.out.println(" Interactuando con la puerta..");
		
		if(super.interactuarPuerta(s, salaPuerta, p, salaConquistadores)){
			p.reiniciarPuerta();
			p.cerrar();
			return true;
		}
		return false;
	}
	
	/** Metodo que permite moverse a los defensores por el mapa indefinidamente siguiendo la ruta marcada
	 * por ellos de forma ciclica, sin parar nunca.
	 * 
	 * 
	 * @param s sala en la que se encuentra en el momento de la llamada el personaje.
	 * @param m mapa sobre el que se esta ejecutando todo el juego.
	 * 
	 * @return devuelve la Sala en la que se ha insertado al personaje (==s si no se mueve)
	 * 
	 */
	public Sala movimiento(Sala s, Mapa m){
		Sala nuevaSala;
		char c=direcciones.primero(); //obtenemos la direccion que tiene que hacer ahora
		nuevaSala=super.movimiento(s, m); //aqui se le borrara la direccion dicha porque ya se ha hecho
		direcciones.encolar(c); //pero nosotros volvemos a insertarsela para que realicen el movimiento ciclicamente
		
		return nuevaSala;
	}
	/**Metodo que interactua con la sala y que será redefinido en cada una de las subclases
	 * de defensor
	 * 
	 * @param s sala sobre la que va a actuar el algoritmo
	 */
	public abstract void interactuarSala(Sala s);
}

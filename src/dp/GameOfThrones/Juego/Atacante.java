package dp.GameOfThrones.Juego;


/**
 * EC4- proyecto16_17
 * Implementacion de la clase Atacante (subclase de Personaje).
 *
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public abstract class Atacante extends Personaje{
	

	/**Constructor por defecto de la clase Atacante
	 * 
	 */
	public Atacante(){
	}
	
	/**Constructor parametrizado de la clase Atacante
	 * 
	 * @param nombre String que contiene el nombre que se le va a dar al personaje
	 * @param identificador char que contiene el identificador que se le va a dar al personaje
	 * @param turno int que contiene el turno en el que comenzará la simulacion el personaje
	 */
	public Atacante(String nombre, char identificador, int turno){
		super(nombre, identificador, turno);
	}
	
	/**Metodo abstracto que se implementará en las subclases atacantes que se encarga de
	 * cargar las direcciones de movimiento de cada Personaje Atacante.
	 */
	protected abstract void cargarDirecciones();
	
	/**Metodo que interactua con la puerta llamando al abrir de esta con la ultima llave que ha encontrado
	 * el personaje. 
	 * 
	 * Si la puerta ya esta abierta el personaje entrará directamente en la salaConquistadores.
	 * 
	 * @param s sala en la que nos encontramos
	 * @param salaPuerta numero de la sala que corresponde a la puerta
	 * @param salaConquistadores sala '1111' en la que se inserta a los que han conquistado el trono
	 * @param p puerta del trono
	 * @return devuelve true si estamos en la sala de la puerta y se ha podido ejecutar y false si no es así
	 */
	public boolean interactuarPuerta (Sala s, int salaPuerta, Puerta p, Sala salaConquistadores){
		
		System.out.println(" Interactuando con la puerta..");

		if(p.estaAbierta()){//si esta abierta entramso directos
			salaConquistadores.insertarPersonaje(this);
			System.out.println(" ENTRANDO EN LA SALA DE LOS CONQUISTADORES..");
			return true;
		}
		else{
			if(super.interactuarPuerta(s, salaPuerta, p, salaConquistadores)){//estamos en la sala de la puerta
				if(!llaves.isEmpty()){
					p.abrir(llaves.getTop());
					llaves.removeData();
				}
				if(p.estaAbierta()){
					salaConquistadores.insertarPersonaje(this);
					System.out.println(" ENTRANDO EN LA SALA DE LOS CONQUISTADORES..");
				}
				else{
					System.out.println(" Puerta no abierta");
				}
				return true;
			}
			System.out.println(" Esta no es la sala de la puerta, nos movemos");
			return false;
		}
	}
	
	/** Metodo que permite moverse a los atacantes por el mapa sino estan ya en la puerta del trono.
	 * 
	 * 
	 * @param s sala en la que se encuentra en el momento de la llamada el personaje.
	 * @param m mapa sobre el que se esta ejecutando todo el juego.
	 * @return devuelve la Sala en la que se ha insertado al personaje (==s si no se mueve)
	 */
	public Sala movimiento(Sala s, Mapa m){
		
		if((s.getNumero()!=m.getSalaPuerta())){ //si no estamos en la sala de la puerta se movera
			return super.movimiento(s, m);
		}
		else{//si estamos en la sala de la puerta no se movera, por lo que lo insertaremos en la misma sala otra vez
			s.insertarPersonaje(this);
			return s;
		}
	}
	
	/** Metodo que interactua con la sala, buscando si hay llaves en la sala.
	 * 
	 * @param s sala sobre la que se va a interactuar.
	 */
	public void interactuarSala(Sala s){

		System.out.println(" Buscando llaves en la sala..");
		
		if(s.tieneLlave()){
			llaves.addData(s.obtenerLlave());
			s.eliminarLlave();
			System.out.println("    --Llave recogida--");
		}
		
		else{
			System.out.println("    --Esta sala no tiene llaves--");
		}
	}
	
}

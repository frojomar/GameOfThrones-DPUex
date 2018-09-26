package dp.GameOfThrones.Juego;

import java.io.BufferedWriter;
import java.io.IOException;

import dp.GameOfThrones.EstructurasDatos.List;
import dp.GameOfThrones.EstructurasDatos.Queue;

/**
 * EC4 - proyecto16_17
* Implementacion de la clase Sala del proyecto 16-17.
* @version 2.0
* @author
* <b> Francisco Javier Rojo Martín</b><br>
* Proyecto GameOfThrones-DPUex<br>
* Curso 16/17
*/
public class Sala implements Comparable<Sala>{
	
	/**Variable que almacena el numero identificador de la sala*/
	private int numero;
	
	/**Atributo marca que nos servira para hacer las paredes del mapa*/
	private int marca;
	
	/**Cola que almacena el conjunto de personajes que hay en una sala*/
	private Queue<Personaje> personajes;
	
	/**Lista ordenada que almacena las llaves de la sala*/
	private List<Llave> llaves;
	
	/**Constructor por defecto de la clase Sala
	 * 
	 */
	public Sala(){
		numero=0;
		marca=0;
		personajes= new Queue<Personaje>();
		llaves= new List<Llave>();
	}
	
	/**Constructor parametrizado de la clase Sala
	 * 
	 * @param identificador int que contiene el valor que se le va a dar al atributo 'numero'
	 */
	public Sala(int identificador){
		numero=identificador;
		marca=identificador;
		personajes= new Queue<Personaje>();
		llaves= new List<Llave>();
	}
	
	/**Metodo que nos dice si hay llaves en la sala
	 * 
	 * @return devuelve true si hay llaves y false si no las hay
	 */
	public boolean tieneLlave(){
		return !llaves.estaVacia();
	}
	
	/**Metodo que nos devuelve la llave con menor idenficador de la sala pero NO LA ELIMINA DE LA SALA.
	 * Si no hay llaves devuelve 'null'.
	 * 
	 * @return devuelve la llave con menor identificador de la sala pero NO LA ELIMINA DE LA SALA
	 */
	public Llave obtenerLlave(){
		if(!llaves.estaVacia())
			return llaves.getFirst();
		return null;
	}
	
	/**Metodo que elimina la llave con menor identificador de la sala (si hay llaves)
	 * 
	 */
	public void eliminarLlave(){
		if(!llaves.estaVacia()){
			llaves.removeFirst();
		}		
	}
	
	/**Metodo que inserta una llave en orden en la coleccion de llaves de la sala
	 * 
	 * @param l llave a insertar en la coleccion
	 */
	public void insertarLlave(Llave l){
		llaves.insertarEnOrden(l);
	}
	
	/**Metodo selector del atributo 'numero'
	 * 
	 * @return devuelve el valro del numero identificador de la sala
	 */
	public int getNumero(){
		return numero;
	}
	
	/**Metodo selector del atributo 'marca'
	 * 
	 * @return devuelve un entero que es el valor de la marca
	 */
	public int getMarca(){
		return marca;
	}
	
	/**Metodo modificador del atributo 'marca'
	 * 
	 * @param marca int que contiene el nuevo valor que contendrá marca
	 */
	public void setMarca(int marca){
		this.marca=marca;
	}
	
	/**Metodo modificador del atributo 'numero'
	 * 
	 * @param numero int que contiene el valor que se la va a dar al atributo 'numero'
	 */
	public void setNumero(int numero){
		this.numero=numero;
	}
	
	/**Metodo que nos dice si hay personajes en la sala
	 * 
	 * @return devuelve true si hay personajes en la sala y false si no hay
	 */
	public boolean tienePersonajes(){
		return !personajes.estaVacia();
	}
	
	/**Metodo que nos devuelve cuantos personajes hay en la sala
	 * 
	 * @return devuelve el numero de personajes que hay en la sala (0 si no hay ninguno)
	 */
	public Integer cuantosPersonajes(){
		System.out.println("Numero de pj :" +personajes.size());
		return personajes.size();
	}
	
	/**Metodo que nos devuelve el personaje que primero llego a la sala pero NO LO ELIMINA DE LA SALA
	 * 
	 * @return devuelve el personaje que primero llego a la sala pero NO LO ELIMINA DE LA SALA. Si no hay
	 * personajes se devuelve un 'null'.
	 */
	public Personaje obtenerPersonaje(){
		if(this.tienePersonajes()){
			return personajes.primero();
		}
		return null;
	}
	
	/**Metodo que elimina el personaje que mas tiempo lleva en la sala de esta
	 * 
	 */
	public void eliminarPersonaje(){
		if(!personajes.estaVacia()){
			personajes.desencolar();
		}
	}
	
	/**Metodo que inserta un personaje en la sala
	 * 
	 * @param p personaje a insertar en la sala
	 */
	public void insertarPersonaje(Personaje p){
		personajes.encolar(p);
	}
	
	public void tratarPersonajes(int turno){
		Personaje pj;
		int cuantos=0;
		Mapa m= Mapa.getInstancia();
		
		if(this.tienePersonajes()){
			pj=this.obtenerPersonaje();
			while(this.tienePersonajes() && cuantos<m.getNumeroJugadores()){ //en el peor de los casos todos los
				pj=this.obtenerPersonaje();									//jugadores estaran en la misma sala
				this.eliminarPersonaje();
				cuantos++;
				
				if((pj.getTurno()<=turno)){
					
					pj.setTratado(true);
					System.out.println();
					System.out.println(pj);
					pj.realizarAcciones(this);
				}
				else{
					this.insertarPersonaje(pj);
				}
			}
		}
	}
	
	/**Metodo que muestra las llaves de la sala.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir las llaves de la sala.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void mostrarLlaves(BufferedWriter bw) throws IOException{
		llaves.mostrar(bw);
	}
	
	/**
	 * Comparando dos objetos.
	 * @param obj El objeto con el que comparar
	 * @return true, si son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object obj){ //Todas las clases en java heredan de la clase Objeto
		// Para optimizar, comparamos si las referencias de los dos objetos son iguales.
		//En este caso, los objetos son iguales siempre
		if (this == obj) 
			return true;
		// Siempre debemos comparar si el objeto pasado por parametro es del mismo tipo.
		if (!(obj instanceof Sala))
			return false;
		// Hacemos un casting... 
		Sala sAux = (Sala) obj;
		Integer n=this.numero;
		Integer n2=sAux.getNumero();
		return (n.equals(n2)); 	//Una vez sabemos que son de la misma clase,
														//comparamos segun los parametros que nosotros
														//queremos comparar y devolveremos true si los 
														//valores de todas las parejas de parametros son
														//iguales y false si no lo son.
	}

	/** 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Sala s2) {
		if (this == s2) 
			return 0;
		Integer n=this.numero;
		Integer n2=s2.getNumero();
		return (n.compareTo(n2));}	

}

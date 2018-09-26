package dp.GameOfThrones.Juego;

import dp.GameOfThrones.EstructurasDatos.Stack;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

import dp.GameOfThrones.EstructurasDatos.Queue;

/**
 * EC4 - proyecto16_17
 * Implementacion de la clase Personaje.
 *
 * @version 3.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public abstract class Personaje implements Comparable<Personaje>{
	
	/**Cadena que almacena el nombre del personaje*/
	protected String nombre;
	
	/**Caracter que toma el valor del caracter identificador del personaje*/
	protected char identificador;
	
	/**Pila que almacena las llaves que posee un personaje*/
	protected Stack<Llave> llaves;
	
	/**Cola que almacena las direcciones de movimiento de los personajes*/
	protected Queue<Character> direcciones;
	
	/**Variable que almacena el turno en el que esta este personaje*/
	protected int turno;
	
	/**Variable que nos dice si el personaje se ha empezado ya a tratar (si ya ha llegado su turno de movimiento)*/
	protected boolean tratado;
	
	/**Constructor por defecto de la clase Personaje
	 * 
	 */
	public Personaje(){
		nombre="";
		identificador=' ';
		llaves= new Stack<Llave>();
		direcciones= new Queue<Character>();
		turno=1;
		tratado=false;
	}
	
	/**Constructor parametrizado de la clase Personaje
	 * 
	 * @param nombre String que contiene el nombre que se le va a dar al personaje
	 * @param identificador char que contiene el identificador que se le va a dar al personaje
	 * @param turno int que contiene el turno en el que comenzará el personaje la simulacion
	 */
	public Personaje(String nombre, char identificador, int turno){
		this.nombre=nombre;
		this.identificador=identificador;
		llaves= new Stack<Llave>();
		direcciones= new Queue<Character>();
		this.turno=turno;
		tratado=false;
	}
	
	
	/**Metodo abstracto que carga las direcciones de movimiento del personaje. Será redefinido en las subclases,
	 * pues cada personaje seguirá un algoritmo distinto para moverse.
	 * 
	 */
	protected abstract void cargarDirecciones();
	
	/**Metodo que emplearan todos los personajes para transformar la Ruta de identificadores (ya calculada cada
	 * uno por su algoritmo para el movimiento) de identificadores en direcciones del tipo {N,E,S,O} y meterlas
	 * en la cola de direcciones que posee cada uno.
	 * 
	 * @param rutaFinal lista de enteros asociados a los identificadores de las salas por las que deben pasar
	 * los personajes para seguir su algoritmo de movimiento.
	 */
	protected void cargarRutaEnDirecciones(LinkedList<Integer> rutaFinal){
		int origen;
		int destino;
		int tamFila= Mapa.getInstancia().getDimX();
		
		if(rutaFinal.size()>1){
			while(!rutaFinal.isEmpty()){ //nunca va a tener menos de dos elementos la lista (el origen y el final)
				origen=rutaFinal.get(0);
				destino=rutaFinal.get(1);
				
				if((destino-origen)==1){  //casilla destino una posicion más al este (derecha) que la origen
					direcciones.encolar('E');
				}
				else{
					if((destino-origen)==-1){ //casilla destino una posicion más al oeste (izquierda) que la origen
						direcciones.encolar('O');
					}
					else{
						if((destino-origen)==tamFila){ //casilla destino una posicion más al sur (abajo) que la origen
							direcciones.encolar('S');
						}
						else{
							if((destino-origen)==-tamFila){//casilla destino una posicion más al norte (arriba) que la origen
								direcciones.encolar('N');
							}
						}
					}
				}
				
				rutaFinal.removeFirst(); //eliminamos esa posicion de la lista y continuamos
				if(rutaFinal.size()==1){
					rutaFinal.removeFirst(); //si ya solo queda 1 lo eliminamos, pues será ya solo la destino de verdad
				}
			}
		}
	}
		
	/**Metodo selector del atributo 'nombre'
	 * 
	 * @return devuelve un String que contiene el valor del atributo 'nombre' del personaje
	 */
	public String getNombre(){
		return this.nombre;
	}
	
	/**Metodo selector del atributo 'identificador'
	 * 
	 * @return devuelve el valor 'char' del atributo 'identificador' del personaje
	 */
	public char getIdentificador(){
		return this.identificador;
	}
	
	/**
	 * Metodo selector del atributo 'turno'
	 * 
	 * @return devuelve el valor 'int' del atributo 'turno' del personaje
	 */
	public int getTurno(){
		return this.turno;
	}
	
	/**Metodo modificador del atributo 'nombre'
	 * 
	 * @param nombre nuevo nombre que va a tomar el personaje en su atributo 'nombre' 
	 */
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	/**Metodo modificador del atributo 'identificador'
	 * 
	 * @param identificador nuevo valor que va a tomar 'identificador'
	 */
	public void setIdentificador(char identificador){
		this.identificador=identificador;
	}
	
	/**Metodo modificador del atributo 'turno'
	 * 
	 * @param turno nuevo valor que va a tomar 'turno'
	 */
	public void setTurno(int turno){
		this.turno=turno;
	}

	/**Metodo modificador del atributo 'tratado'
	 * 
	 * @param b boolean que contiene el valor al que se pondrá el 'tratado'
	 */
	public void setTratado(boolean b) {
		this.tratado=b;
	}	
	
	/**Metodo selector del atributo 'tratado'
	 * 
	 * @return devuelve el valor boolean asociado al atributo 'tratado'
	 */
	public boolean getTratado() {
		return tratado;
	}

	/**Metodo que dice de que tipo es el personaje
	 * 
	 * @return devuelve un string que contendrá el tipo del personaje
	 */
	public String tipoPersonaje(){
		return "personaje";
	}
	
	/**Metodo que muestra las llaves del personajes en el mismo orden en el que se probarán una vez
	 * llegen a la puerta o se soltaran en las salas.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir las llaves de los personajes
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void mostrarLlaves(BufferedWriter bw) throws IOException{
		llaves.mostrar(bw);
	}
	
	/**Metodo que muestra las direcciones que tomarán los personajes en orden. 
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir las direcciones de los personajes
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void mostrarDirecciones(BufferedWriter bw) throws IOException{
		direcciones.mostrar(bw);
	}

	/**Metodo que incrementa el turno en el que esta el personaje.
	 *
	 */
	public void incrementarTurno(){
		turno++;
	}
	
	/**Metodo que se encarga de realizar las 3 acciones de cada personaje (interactuar con la puerta, moverse y
	 * interactuar con la sala).
	 * 
	 * @param s sala actual en la que se encuentra el personaje.
	 */
	public void realizarAcciones(Sala s){
		Sala nuevaSala;
		Mapa m= Mapa.getInstancia();
		if(!this.interactuarPuerta(s, m.getSalaPuerta(), m.getPuerta(), m.getSalaConquistadores())
				  || !(m.getPuerta()).estaAbierta()){//accion 1
					//si el personajes esta en la sala de la puerta y la puerta tras su instancia en esta
					//sala esta abierta el personaje ya no realizara nada de esto, pues significa que es
					//un atacante y que, o bien ha abierto la puerta, o bien se la ha encontrado abierta;
					//de cualquiera de las dos formas este personaja ya estara en la sala de conquistadores.
					nuevaSala=this.movimiento(s, m);															//accion 2 
					this.interactuarSala(nuevaSala);											//accion 3
					this.incrementarTurno();										//incremento del turno
				}

	}
	
	/** Metodo que interactua con la puerta, indicandonos si estamos en la sala de la puerta
	 * 
	 * @param s sala en la que nos encontramos
	 * @param salaPuerta numero de la sala que corresponde a la puerta
	 * @param salaConquistadores sala '1111' en la que se inserta a los que han conquistado el trono
	 * @param p puerta del trono
	 * @return devuelve true si estamos en la sala de la puerta y false si no es así
	 */
	public boolean interactuarPuerta(Sala s, int salaPuerta,Puerta p, Sala salaConquistadores){
		return (salaPuerta == s.getNumero());
	}
	
	/** Metodo que permite moverse a los personajes por el mapa.
	 * 
	 * Este metodo sera redefinido en las subClases que heredan de Personaje (bien para añadirle 
	 * restricciones de movimiento o para realizar acciones extras).
	 * 
	 * @param s sala en la que se encuentra en el momento de la llamada el personaje.
	 * @param m mapa sobre el que se esta ejecutando todo el juego.
	 * @return devuelve la Sala en la que se ha insertado al personaje (==s si no se mueve)
	 */
	public Sala movimiento(Sala s, Mapa m){
	
		Character c;
		int fila= m.obtenerFila(s.getNumero());
		int columna= m.obtenerColumna(s.getNumero());
		int filaIni=fila;
		int columnaIni=columna;
		
		if(!direcciones.estaVacia()){
			c= direcciones.primero();
			direcciones.desencolar();
			
			switch (c) {
			case 'N':
				fila--;
				break;
				
			case 'S':
				fila++;
				break;
					
			case 'E':
				columna++;
				break;
					
			case 'O':
				columna--;
				break;
					
			default:
				System.out.println("  --La direccion de movimiento no era valida");
				break;
			}			
				
			if(columna<0 || columna>=m.getDimX() || fila<0 || fila>=m.getDimY()){
				System.out.println("  --El movimiento no era valido y no se ha realizado");
			}
			else{
				if(!m.existepared(filaIni,columnaIni, fila, columna)){ //si no existe pared entre ambas salas..
					s=m.getSala(fila,columna); //buscamos la nueva sala
				}
			}
		}
		s.insertarPersonaje(this);//lo insertamos en una de las salas, o la que tenia antes, o la nueva
		
		return s;
	}
	
	/** Metodo que abstracto interactua con la sala.
	 *  
	 * Será redefinido haciendo una u otra opcion segun el subtipo de Personaje al que pertenezcan
	 * 
	 * 
	 * @param s Sala actual en la que se encuentra el personaje
	 */
	public abstract void interactuarSala(Sala s);
	
	
	
	/**Metodo para imprimir por pantalla la informacion de un personaje*/
	
	public String toString(){
		return "Nombre: " + nombre.toString() + "   Identificador: " + identificador;
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
    	if (!(obj instanceof Personaje))
    		return false;
    	// Hacemos un casting... 
    	Personaje pAux = (Personaje) obj;
    	Character c= identificador;
    	Character c2=pAux.getIdentificador();
    	return (c.equals(c2)); 	//Una vez sabemos que son de la misma clase,
    													//comparamos segun los parametros que nosotros
    													//queremos comparar y devolveremos true si los 
    													//valores de todas las parejas de parametros son
    													//iguales y false si no lo son.
    }
    
    
    
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Personaje p2) {
		if (this == p2) 
			return 0;
    	Character c= identificador;
    	Character c2= p2.getIdentificador();
		return (c.compareTo(c2));}

	


	
}

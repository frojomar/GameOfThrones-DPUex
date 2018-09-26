package dp.GameOfThrones.Juego;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.LinkedList;
import java.util.Set;

import dp.GameOfThrones.EstructurasDatos.Grafo;
import dp.GameOfThrones.EstructurasDatos.Queue;

/**
* EC4 - proyecto16_17
* Implementacion de la clase Mapa del proyecto 16-17.
* @version 4.0
* @author
* <b> Francisco Javier Rojo Martín</b><br>
* Proyecto GameOfThrones-DPUex<br>
* Curso 16/17
*/
public class Mapa {
	
	/**Variable que guarda el numero de columnas del mapa*/
	private Integer dimX;
	
	/**Variable que guarda el numero de filas del mapa*/
	private Integer dimY;
	
	/**Variable que guarda el numero de la sala donde se encuentra la puerta*/
	private Integer salaPuerta;
	
	/**Variable que guarda la puerta del mapa*/
	private Puerta puerta;

	/**Matriz de salas*/
	private Sala[][] salas;
	
	/**Sala especial que contiene los conquistadores del trono*/
	private Sala salaConquistadores;
	
	/**Grafo que contiene las paredes que existen en el mapa*/
	private Grafo paredes;
	
	/**Variable que almacena el numero de jugadores que hay jugando*/
	private int numeroJugadores;
	
	/**Variable que contiene la unica instancia de la clase mapa que se va a generar*/
	private static Mapa instancia= null;
	
	/** Constructor privado por defecto del mapa.Inicializa todas las variables a 0.
	 * 
	 */
	private Mapa(){
		dimX=0;
		dimY=0;
		salaPuerta=0;
		puerta= new Puerta();
		salas= new Sala[dimX][dimY];
		salaConquistadores= new Sala(1111);
		paredes=new Grafo();
		numeroJugadores=0;
		this.numerarSalas();
	}
	
	/** Constructor privado parametrizado del mapa.
	 *
	 *@param salaPuerta numero de la sala en la que se encontrara la puerta situada
	 *@param dimX numero de columnas que tendra el mapa
	 *@param dimY numero de filas que tendra el mapa
	 *@param cond_alt int que contiene la condicion de altura de apertura de la puerta
	 */
	private Mapa (Integer salaPuerta, Integer dimX, Integer dimY, Integer cond_alt){
		this.dimX=dimX;
		this.dimY=dimY;
		this.salaPuerta=salaPuerta;
		puerta= new Puerta(cond_alt);
		salas= new Sala[dimY][dimX];
		salaConquistadores= new Sala(1111);
		paredes=new Grafo();
		numeroJugadores=0;
		this.numerarSalas();
	}
	

	/**Metodo que nos devuelve la unica instancia posible de existir de la clase Mapa y, si no existe aún,
	 * se crea.
	 * 
	 * @return devuelve la instancia de Mapa
	 */
	public static Mapa getInstancia(){
		if(instancia==null){
			instancia= new Mapa();
		}
		return instancia;
	}
	
	/**Metodo que nos devuelve la unica instancia posible de existir de la clase Mapa y, si no existe aún,
	 * se crea con los parametros de entrada dados.
	 * 
	 *@param salaPuerta numero de la sala en la que se encontrara la puerta situada
	 *@param dimX numero de columnas que tendra el mapa
	 *@param dimY numero de filas que tendra el mapa
	 *@param cond_alt int que contiene la condicion de altura de apertura de la puerta
	 * @return devuelve la instancia de Mapa
	 */
	public static Mapa getInstancia(Integer salaPuerta, Integer dimX, Integer dimY, Integer cond_alt){
		if(instancia==null){
			instancia= new Mapa(salaPuerta, dimX, dimY, cond_alt);
		}
		return instancia;
	}
	
	/**Metodo que numera la sala, dandole a estas el valor de la posicion que ocupa en la matriz que forman.
	 * 
	 */
	private void numerarSalas(){
		for(int i=0; i<dimY; i++){
			for(int j=0; j<dimX; j++){
				salas[i][j]= new Sala();
				salas[i][j].setNumero( ((i)*dimX) +  j );
				salas[i][j].setMarca( ((i)*dimX) +  j );
			}
		}
	}
	
	/**
	 * Metodo que genera las paredes del laberinto del mapa.
	 * Genera todas las paredes y luego las va tirando siguiendo el algoritmo de Kruskal para poder
	 * generar un unico camino entre cada sala del mapa.
	 */
	public void generarParedes(){
		LinkedList<int[]> colPar= new LinkedList<int[]>();
		
		for(int i=0; i<dimY; i++){ //metemos todos los nodos en la sala
			for(int j=0; j<dimX; j++){
				paredes.nuevoNodo(salas[i][j].getNumero());
			}
		} //cargamos en el grafo de paredes todos los nodos (1 nodo por sala)
		
		this.generarColeccionParedes(colPar); //obtenemos todas las paredes que existen
	
		paredes.Kruskal(colPar,this); //llamamos a Kruskal para derribar paredes de forma que obtengamos un camino
	
	}
	
	/**Metodo que genera atajos en el mapa tras haber formado un camino unico mediante las paredes del mapa.
	 * 
	 * El algoritmo tira un numero de paredes igual al 5% de las salas del mapa. La pared se seleciona mediante
	 * un numero aleatorio generado por GenAleatorios (al igual que pasaba en el alg. de Kruskal)
	 * 
	 */
	public void formarAtajos() {
		int cuantos= (int) ((dimX*dimY)*0.05); //lo guardamos en un entero, ignorando la parte decimal
		int numAleatorio;
		int fila, columna, filaD, columnaD;
		boolean tirada;
		Sala s,s2;
		
		while (cuantos>0){
			tirada=false;
			
			numAleatorio=GenAleatorios.generarNumero(dimX*dimY);
			fila=this.obtenerFila(numAleatorio);
			columna=this.obtenerColumna(numAleatorio);
			s=this.getSala(fila, columna);
			
			if(numAleatorio==34){
				numAleatorio++;
				numAleatorio--;
			}
			
			filaD=fila-1;
			columnaD=columna;
			s2=this.getSala(filaD, columnaD); //N
			if(s2!=null && !tirada){ //si existe sala norte y no se ha tirado aun ninguna pared
				if(!paredes.adyacente(s.getNumero(), s2.getNumero())){ //y hay pared
					tirada=this.tirarPared5(s.getNumero(), s2.getNumero());//la intentamos tirar.
				}
			}
			
			filaD=fila+1;
			columnaD=columna;
			s2=this.getSala(filaD, columnaD); //S
			if(s2!=null && !tirada){ //si existe sala norte y no se ha tirado aun ninguna pared
				if(!paredes.adyacente(s.getNumero(), s2.getNumero())){ //y hay pared
					tirada=this.tirarPared5(s.getNumero(), s2.getNumero());//la intentamos tirar.
				}
			}
			
			filaD=fila;
			columnaD=columna-1;
			s2=this.getSala(filaD, columnaD); //O
			if(s2!=null && !tirada){ //si existe sala norte y no se ha tirado aun ninguna pared
				if(!paredes.adyacente(s.getNumero(), s2.getNumero())){ //y hay pared
					tirada=this.tirarPared5(s.getNumero(), s2.getNumero());//la intentamos tirar.
				}
			}
			
			filaD=fila;
			columnaD=columna+1;
			s2=this.getSala(filaD, columnaD); //E
			if(s2!=null && !tirada){ //si existe sala norte y no se ha tirado aun ninguna pared
				if(!paredes.adyacente(s.getNumero(), s2.getNumero())){ //y hay pared
					tirada=this.tirarPared5(s.getNumero(), s2.getNumero());//la intentamos tirar.
				}
			}
			
			if(tirada) //si se ha podido tirar alguna
				cuantos--; //indicamos que queda un atajo menos por tirar
			
		}
		
	}

	/**Metodo privado al que llama formar atajo para tirar una pared despues de aver comprobado que existe una
	 * pared para tirar entre 'origen' y 'destino'.
	 * 
	 * Dentro del metodo se comprueba si una vez que se tire la pared se formará una "superSala" (cuadrado
	 * formado por la conjuncion de 4 salas). Si es así, devolverá 'false' y no se habrá tirado la 
	 * pared. Sino, se tirará la pared y se devolverá 'true'.
	 * 
	 * @param origen int identificador de la sala de la que se va a partir para tirar la pared.
	 * @param destino int identificador de la sala colindante con la origen mediante la pared que se intenta tirar.
	 * @return devuelve 'true' si se ha podido tirar la pared y 'false' si no se ha podido.
	 */
	private boolean tirarPared5(int origen, int destino) {
		int sig=origen;
		int contador=1;
		
		paredes.warshall();
		paredes.floyd();	
		
		while(sig!=destino){
			sig=paredes.siguiente(sig, destino);
			contador++;
		}

		if(contador==4)
			return false;
		
		paredes.nuevoArco(origen, destino, 1);
		paredes.nuevoArco(destino, origen, 1);
		
		return true;
	}

	/**Metodo que genera el conjunto de paredes que pueden llegar a haber en el mapa siguiendo el orden
	 * ascendente de las salas por su numero identificador y para cada sala poniendo las paredes en el orden NESO
	 * 
	 *  Hay que tener en cuenta que en el conjunto estara tanto la pared 0-1 como la 1-0 contemplada, aunque sea
	 *  la misma. Por esto, al terminar, el conjunto debería tener ([(dimX-1)*dimY]+[dimX*(dimY-1)])*2 paredes
	 * 
	 * @param colPar lista de vectores de dos posiciones que almacenara los dos valores del conjunto 0-1. Por ejemplo,
	 * la pared 3-4 se almacenara en la lista como un vector con v[0]=3 y v[1]=4.
	 */
	private void generarColeccionParedes(LinkedList<int[]> colPar) {
		int[] pared;
		
		for(int i=0; i<dimY; i++){
			for(int j=0; j<dimX; j++){
				
				pared= new int[2];
				pared[0]=salas[i][j].getNumero();
				if(i-1>=0){//comprobacion de si existe pared por el N
					if(!paredes.adyacente((i*dimX+j), ((i-1)*dimX +j))){ //para que sirva para generar atajos
						pared[1]=salas[i-1][j].getNumero();
						colPar.addLast(pared);
					}
				}
				pared= new int[2];
				pared[0]=salas[i][j].getNumero();				
				
				if(j+1<dimX){//comprobacion de si existe pared por el E
					if(!paredes.adyacente((i*dimX+j), (i*dimX +j+1))){ //para que sirva para generar atajo
						pared[1]=salas[i][j+1].getNumero();
						colPar.addLast(pared);
					}
				}
				
				pared= new int[2];
				pared[0]=salas[i][j].getNumero();
				if(i+1<dimY){//comprobacion de si existe pared por el S
					if(!paredes.adyacente((i*dimX+j), ((i+1)*dimX +j))){ //para que sirva para generar atajos
						pared[1]=salas[i+1][j].getNumero();
						colPar.addLast(pared);
					}
				}
				
				pared= new int[2];
				pared[0]=salas[i][j].getNumero();
				if(j-1>=0){//comprobacion de si existe pared por el O
					if(!paredes.adyacente((i*dimX+j), (i*dimX +j-1))){ //para que sirva para generar atajos
						pared[1]=salas[i][j-1].getNumero();
						colPar.addLast(pared);
					}
				}
				
			}
		}
	}

	/** Metodo que inserta una puerta en la variable puerta del mapa.
	 *
	 * @param puerta puerta creada fuera del mapa que pasa a sustituir a la creada en el constructor del mapa
	 */
	public void setPuerta(Puerta puerta){
		this.puerta=puerta;
	}
	
	/** Metodo que devuelve la puerta de la variable puerta almacenada en el mapa.
	 *
	 * @return devuelve la puerta de la variable puerta almacenada en el mapa.
	 */
	public Puerta getPuerta(){
		return this.puerta;
	}
	
	/** Metodo que devuelve el identificador de la sala en la que esta la puerta.
	 *
	 * @return devuelve el identificador de la sala en la que esta la puerta.
	 */
	public int getSalaPuerta(){
		return this.salaPuerta;
	}
	
	/** Metodo que devuelve la sala de los conquistadores.
	 *
	 * @return devuelve la sala de los conquistadores.
	 */
	public Sala getSalaConquistadores(){
		return this.salaConquistadores;
	}
	
	/** Metodo que devuelve el numero de filas del mapa.
	 *
	 * @return devuelve el numero de filas del mapa.
	 */
	public int getDimX(){
		return this.dimX;
	}
	
	/** Metodo que devuelve el numero de columnas del mapa.
	 *
	 * @return devuelve el numero de columnas del mapa.
	 */
	public int getDimY(){
		return this.dimY;
	}
	
	/**Metodo que nos devuelve el numero de jugadores que hay en el juego.
	 * 
	 * @return devuelve un entero con el numero de juegadores que hay en el juego.
	 */
	public int getNumeroJugadores(){
		return this.numeroJugadores;
	}
	
	/**Metodo selector que nos devuelve el grafo de los caminos (o paredes, segun se mire).
	 * 
	 * @return grafo que contiene las paredes del mapa.
	 */
	public Grafo getParedes(){
		return this.paredes;
	}
	
	/**Metodo que nos devuelve la fila de la matriz en la que esta la sala asociada a 'numSala'
	 * 
	 * @param numSala sala de la que queremos saber su fila
	 * @return int que contiene el numero de la fila (siendo la primera 0) en la que esta la 
	 * 		sala identificada con 'numSala'
	 */
	public int obtenerFila(int numSala){
		return (numSala/ this.dimX);
	}
	
	/**Metodo que nos devuelve la columna de la matriz en la que esta la sala asociada a 'numSala'
	 * 
	 * @param numSala sala de la que queremos saber su columna
	 * @return int que contiene el numero de la columna (siendo la primera 0) en la que esta la 
	 * 		sala identificada con 'numSala'
	 */
	public int obtenerColumna(int numSala){
		return (numSala % this.dimX);
	}
	
	/**Metodo que nos devuelve la sala que pertenece a la fila y columna dadas
	 * 
	 * @param fila fila a la que pertenece la sala que queremos que nos devuelva
	 * @param columna columna a la que pertenecea la sala que queremos que nos devuelva
	 * @return Sala que esta en la posicion marcada por fila y columna
	 */
	public Sala getSala(int fila, int columna){
		if(fila<0 || fila>=dimY || columna<0 || columna>=dimX)
			return null;
		else
			return salas[fila][columna];
	}
	
	/**Metodo que nos dice si existe una pared entre la sala de la posicion [filaIni][columnaIni] y
	 * la sala de la posicion [filaFin][columnaFin]
	 * 
	 * @param filaIni fila en la que se encuentra la sala de la que se va a partir
	 * @param columnaIni columna en la que se encuentra la sala de la que se va a partir
	 * @param filaFin fila en la que se encuentra la sala a la que se va a ir
	 * @param columnaFin columna en la que se encuentra la sala a la que se va a ir
	 * @return boolean que contiene 'true' si hay pared entre las dos salas y 'false' si no.
	 */
	public boolean existepared(int filaIni, int columnaIni, int filaFin, int columnaFin) {
		Sala s1;
		Sala s2;
		
		s1=this.getSala(filaIni, columnaIni);
		s2=this.getSala(filaFin, columnaFin);
		
		return !paredes.adyacente(s1.getNumero(), s2.getNumero());
	}
	
	/**Metodo que inserta un personaje en el mapa y en la sala
	 * 
	 * @param p personaje a insertar en la sala
	 * @param numSala identificador de la sala donde vamos a insertar al personaje
	 */
	public void insertarPersonaje(Personaje p, int numSala){	
		int indSalaI=this.obtenerFila(numSala);
		int indSalaJ=this.obtenerColumna(numSala);
		
		salas[indSalaI][indSalaJ].insertarPersonaje(p);
		numeroJugadores++;
	}
	
	/**Metodo que inserta un personaje que ha abierto la puerta en la salaConquistadores
	 * 
	 * @param p personaje que ha conquistado el trono
	 */
	public void insertarConquistador(Personaje p){
		salaConquistadores.insertarPersonaje(p);
	}
	
	/**Metodo que inserta una llave en la sala con numero 'numSala'
	 * 
	 * @param llave llave a insertar en la sala
	 * @param numSala numero de la sala donde vamos a insertar la Llave
	 */
	public void insertarLlave(Llave llave, int numSala){
		salas[this.obtenerFila(numSala)][this.obtenerColumna(numSala)].insertarLlave(llave);	
	}
	
	/**Metodo que crea las llaves que se van a repartir por el mapa
	 * 
	 * @param combinacion vector de llaves que forman la cerradura de la puerta
	 * @return devuelve un vector ordenado con las llaves que se van a rapartir por el mapa ya listas
	 */
	public Llave[] creacionLlaves(Llave[] combinacion){
      	int valor= combinacion[combinacion.length-1].getValor();//valor maximo de la cerradura
      	valor++; //ahora valor contiene  el valor 30 si antes contenia el 29
      	valor= valor + (valor/2); //ahora contiene el valor 45 si antes era el 30
      	//si el numero de elementos de la cerradura es impar, se van a generar 1.5 veces esas llaves
      	//ya que la mitad de las llaves seran impares y las otras no
      	
      	Llave[] llaves= new Llave[valor];//creacion de un vector
      	
      	int i=0; //contador
      	int vAux=0; //variable que almacenara el valor de la llave a insertar en cada posicion
      	while(i<valor){
      		llaves[i]= new Llave(vAux);
      		i++;
      		if( (vAux%2) != 0){
      			llaves[i]=new Llave(vAux);
      			i++;
      		}
      		vAux++;
      	}
      	
      	return llaves;
	}
	
	/**Metodo que tiene obtiene las salas frecuentadas y cuanto se frecuentan.
	 * 
	 * Coge las salas que se muestran en los caminos y va contando cuantas veces se frecuentan.
	 * 
	 * @param mapFrec clase TreeMap que tiene como clave la frecuencia y como valor el numero de sala
	 * @param caminos cola de colas que contiene los caminos mas frecuentados pro los personajes atacantes
	 */
	private void cargarFrecuenciaSalas(TreeMap<Integer, Integer> mapFrec, LinkedList<LinkedList<Integer>> caminos){
		LinkedList<Integer> cAux= new LinkedList<Integer>();
		Integer sala;
		Integer frec;
		
		while(!caminos.isEmpty()){
			cAux=caminos.getFirst();
			
			while(!cAux.isEmpty()){
				sala=cAux.getFirst();
				frec=mapFrec.put(sala, 1);
				if(frec!=null){ //si el conjunto ya tenia esa sala..
					frec++; //aumentamos en uno la frecuencia con la que ya había aparecido esa sala..
					mapFrec.put(sala, frec); //y la insertamos de nuevo, modificando la que habiamos metido con frec 1
				}
				cAux.removeFirst();
			}
			
			caminos.removeFirst();
		}
		System.out.println("--Mapa Cargado");
	    Set<Entry<Integer, Integer>> pares = mapFrec.entrySet();    
	    for (Entry<Integer, Integer> p : pares){        //Recorrer los pares 
	        System.out.print(p.getKey() + ":" + p.getValue() + " - ");    
	    }
	}
	
	/**Metodo que reparte las llaves creadas en 'creacionLlaves(Llave[] combinacion)' entre las distintas salas
	 * 
	 * Para saber sobre que salas se debe repartir se calcularan todos los caminos entre la sala 0 y la sala de
	 * la puerta y, despues, se ordenarán las salas que aparecen en estos según la frecuencia con la que aparecen
	 * en los caminos.
	 * Se irán insertando llaves de 5 en 5 en las salas con más frecuencia hasta haber repartido todas las
	 * llaves (suponemos que nunca habrá menos salas de las necesarias para repartir todas las llaves). 
	 * 
	 * @param llaves vector ordenado de llaves que se repartira por las distintas salas
	 */
	public void repartoLlaves(Llave[] llaves){
		LinkedList<LinkedList<Integer>> caminos= new LinkedList<LinkedList<Integer>>();
		TreeMap<Integer,Integer> mapFrec= new TreeMap<Integer,Integer>();
		int origen=0;
		int destino=this.getSalaPuerta();
		int[] numSala= new int[9];
		int frecMax;
		int frec;
		boolean yaSel=false;
		
		paredes.profundidad(paredes, origen, destino, caminos);
		
		this.cargarFrecuenciaSalas(mapFrec, caminos);
		

		for(int k=0; k<numSala.length; k++){ //suponemos, como se dice en una entrega, que el numero de salas frecuentadas nunca sera menor a 9
			if(!mapFrec.isEmpty()){
				frecMax=0;
				for(int i=0; i<dimY; i++){
					for(int j=0; j<dimX; j++){
						if(mapFrec.get(salas[i][j].getNumero())!=null){//si esa sala es de las frecuentadas
							frec=mapFrec.get(salas[i][j].getNumero());
							if(frec>frecMax){ //comprueba si es mas frecuentada que las frecuentadas hasta ahora
								for(int l=0; l<k; l++){
									if(numSala[l]==salas[i][j].getNumero()){
										yaSel=true;
									}
								}
								if(!yaSel){
									frecMax=frec;
									numSala[k]=salas[i][j].getNumero();
								}
							}
							yaSel=false;
						}
					}
				}
				mapFrec.remove(numSala[k]); //eliminamos del mapFrec la sala que ya hemos seleccionado como más frecuentada de entre las que teniamos
				System.out.println("--Mapa Cargado (K="+k+")");
			    Set<Entry<Integer, Integer>> pares = mapFrec.entrySet();    
			    for (Entry<Integer, Integer> p : pares){        //Recorrer los pares 
			        System.out.print(p.getKey() + ":" + p.getValue() + " - ");    
			    }
			}
			else{
				boolean encontrado=false;
				System.out.println("Con k= "+k+" buscamos una sala fuera mapa:");
				for(int i=0; i<dimY; i++){
					for(int j=0; j<dimX; j++){
						yaSel=false;
						for(int l=0; l<k; l++){
							if(numSala[l]==salas[i][j].getNumero())
								yaSel=true;
						}
						if(!yaSel && !encontrado){
							numSala[k]=salas[i][j].getNumero();
							System.out.println("seleccionado "+salas[i][j].getNumero());
							encontrado=true;
						}
					}
				}
			}
		}
	
		System.out.println("Salas mas frecuentadas:");
		for(int i=0; i<9; i++){
			System.out.print(numSala[i]);
		}
		
		int j=0;
		int k=0;
	    for(int i=0; i<llaves.length; i++){ //para insertar todas las llaves de la coleccion
	    	this.insertarLlave(llaves[i], numSala[j]); //vamos insertando en la sala mas frecuentada hasta la menos frecuentada
	      	k++; //se incrementa el numero de llaves insertada en esa sala
	      	if(k==5){
	      		j++;
	      		k=0;
	      	} 	
		}
		
		
	}
	
	/**Metodo que se mueve por todo el mapa para buscar los distintos personajes que se han insertado en 
	 * este y generar su Ruta de Movimiento.
	 * 
	 */
	public void cargarRutas() {
		Sala sAux;
		Personaje pj;
		Queue<Personaje> qAux= new Queue<Personaje> ();
		
		for(int i=0; i<this.dimY; i++){
			for(int j=0; j<this.dimX; j++){	
				sAux=salas[i][j];
				while(sAux.tienePersonajes()){
					pj=sAux.obtenerPersonaje();
					sAux.eliminarPersonaje();
					qAux.encolar(pj);
					pj.cargarDirecciones();
				}
				while(!qAux.estaVacia()){
					sAux.insertarPersonaje(qAux.primero());
					qAux.desencolar();
				}
			}
		}
		
	}
	
	/**Metodo privado que muestra las llaves de cada sala. Muestra solo las salas que tienen llaves.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir la informacion de las llaves de
	 * cada sala.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	private void mostrarLlavesSalas(BufferedWriter bw) throws IOException{
		Sala sAux;
		
		for(int i=0; i<this.dimY; i++){
			for(int j=0; j<this.dimX; j++){	
				sAux=salas[i][j];
				if(sAux.tieneLlave()){
					System.out.print("(sala:"+sAux.getNumero()+":");
					bw.write("(sala:"+sAux.getNumero()+":");
					sAux.mostrarLlaves(bw);
					System.out.println(")");
					bw.write(")");bw.newLine();
				}
			}
		}
	}
	
	/**Metodo privado que muestra la informacion de los personajes de cada sala.
	 *
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir la informacion de cada
	 * personajes siguiendo el orden de la sala en la que estan y, dentro de estas, la posicion que ocupan.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'.
	 */
	private void mostrarPersonajesSalas(int turno,BufferedWriter bw) throws IOException{
		Sala sAux;
		Personaje pj;
		int turno_pj;
		Queue<Personaje> qAux= new Queue<Personaje> ();
		
		for(int i=0; i<this.dimY; i++){
			for(int j=0; j<this.dimX; j++){	
				sAux=salas[i][j];
				while(sAux.tienePersonajes()){
					pj=sAux.obtenerPersonaje();
					sAux.eliminarPersonaje();
					turno_pj=pj.getTurno();
					if(pj.getTratado()==true)
						turno_pj--;
					qAux.encolar(pj);
					System.out.print("("+pj.tipoPersonaje()+":"+pj.getIdentificador()+":"+sAux.getNumero()+":"+
							turno_pj+":");
					bw.write("("+pj.tipoPersonaje()+":"+pj.getIdentificador()+":"+sAux.getNumero()+":"+
							turno_pj+":");
					pj.mostrarLlaves(bw); //en el caso del caminante blanco tambien mostrara los personajes
					System.out.println(")");
					bw.write(")");bw.newLine();
				}
				while(!qAux.estaVacia()){
					sAux.insertarPersonaje(qAux.primero());
					qAux.desencolar();
				}
			}
		}
	}
	
	/**Metodo que muestra los conquistadores del trono.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir la informacion respectiva
	 * a los conquistadores del trono.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'.
	 */
	public void mostrarConquistadores(BufferedWriter bw) throws IOException{
		Personaje pj;
		Queue<Personaje> qAux= new Queue<Personaje>();
		
		System.out.println("(miembrostrono)");
		bw.write("(miembrostrono)");

		
		if(salaConquistadores.tienePersonajes()){
			bw.newLine();
			pj=salaConquistadores.obtenerPersonaje();
			qAux.encolar(pj);
			salaConquistadores.eliminarPersonaje();
			System.out.print("(nuevorey:"+pj.tipoPersonaje()+":"+pj.getIdentificador()+":1111:"+
			pj.getTurno()+":");
			bw.write("(nuevorey:"+pj.tipoPersonaje()+":"+pj.getIdentificador()+":1111:"+
					pj.getTurno()+":");
			pj.mostrarLlaves(bw);
			System.out.println(")");
			bw.write(")");
			while(salaConquistadores.tienePersonajes()){
				pj=salaConquistadores.obtenerPersonaje();
				qAux.encolar(pj);
				salaConquistadores.eliminarPersonaje();
				System.out.print("("+pj.tipoPersonaje()+":"+pj.getIdentificador()+":1111:"+
				pj.getTurno()+":");bw.newLine();
				bw.write("("+pj.tipoPersonaje()+":"+pj.getIdentificador()+":1111:"+
						pj.getTurno()+":");
				pj.mostrarLlaves(bw);
				System.out.println(")");
				bw.write(")");
			}
			while(!qAux.estaVacia()){
				salaConquistadores.insertarPersonaje(qAux.primero());
				qAux.desencolar();
			}
		}
		bw.newLine();
	}
	
	/**Metodo que pinta el mapa del juego con el identificador de la sala, los identificadores de los
	 *  personajes en la sala en la que se encuentran y, si en una sala hay más de un personajes, se muestra
	 *  el numero de personajes que hay en esa sala.
	 *
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir en él el mapa con las
	 * paredes y los personajes sobre esta.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'.
	 */
	public void pintar(BufferedWriter bw) throws IOException{
		Sala sAux;
		
		bw.write(" ");
		
		for(int j=0; j<this.dimX; j++){	
			if(j!=0){
				bw.write(" ");
			}
			bw.write("_");
		}
		bw.newLine();
		
		for(int i=0; i<this.dimY; i++){
			bw.write("|");
			for(int j=0; j<this.dimX; j++){
				if(j!=0){
					if(paredes.adyacente(((i)*dimX) +  j-1, ((i)*dimX) +  j))
						bw.write(" ");
					else
						bw.write("|");
				}
				sAux=this.salas[i][j];
				if(sAux.tienePersonajes()){
					if(sAux.cuantosPersonajes()>1){
						bw.write((sAux.cuantosPersonajes()).toString());
					}
					else{
						Personaje pAux= sAux.obtenerPersonaje();
						bw.write(pAux.getIdentificador());						
					}


				}	
				else{
					/*if(sAux.getNumero() == this.salaPuerta){
							bw.write("@");						
					}
					else{*/
						if(i==this.dimY-1){
							bw.write("_");
						}
						else{
							if(paredes.adyacente(((i)*dimX) +  j, ((i+1)*dimX) +  j))
								bw.write(" ");
							else
								bw.write("_");
						}
					//}
				}
			}
			bw.write("|");bw.newLine();
		}
		
	}
	
	/**Metodo que pinta la estructura del mapa.
	 * 
	 * Pinta un mapa vacio en el que solo existen salas vacías y paredes.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir en él la estructura del
	 * mapa.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void pintarEstructura(BufferedWriter bw) throws IOException{
		
		bw.write(" ");
		
		for(int j=0; j<this.dimX; j++){	
			if(j!=0){	
				bw.write(" ");
			}
			bw.write("_");
		}
		bw.newLine();
		
		for(int i=0; i<this.dimY; i++){
			bw.write("|");
			for(int j=0; j<this.dimX; j++){
				if(j!=0){
					if(paredes.adyacente(((i)*dimX) +  j-1, ((i)*dimX) +  j))
						bw.write(" ");
					else
						bw.write("|");
				}	
				if(i==this.dimY-1){
					bw.write("_");
				}
				else{
					if(paredes.adyacente(((i)*dimX) +  j, ((i+1)*dimX) +  j))
						bw.write(" ");
					else
						bw.write("_");
				}
				
			}
			bw.write("|");bw.newLine();
		}
	}

	/**Metodo encargado de pintar las rutas de los personajes.
	 * 
	 * Se mueve por todo el mapa buscando los personajes y pintando las rutas de estos en el orden 
	 * en el que se encuentran dentro del mapa y de la simulación.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir las rutas de los personajes
	 * de la simulacion.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 * 
	 */
	public void pintarRutas(BufferedWriter bw) throws IOException {
		Sala sAux;
		Personaje pj;
		Queue<Personaje> qAux= new Queue<Personaje> ();
		
		for(int i=0; i<this.dimY; i++){
			for(int j=0; j<this.dimX; j++){	
				sAux=salas[i][j];
				while(sAux.tienePersonajes()){
					pj=sAux.obtenerPersonaje();
					sAux.eliminarPersonaje();
					qAux.encolar(pj);
					System.out.print("(ruta:"+pj.getIdentificador()+":");
					bw.write("(ruta:"+pj.getIdentificador()+":");
					pj.mostrarDirecciones(bw);
					System.out.println(")");
					bw.write(")");bw.newLine();
				}
				while(!qAux.estaVacia()){
					sAux.insertarPersonaje(qAux.primero());
					qAux.desencolar();
				}
			}
		}
	}

	
	/** Metodo que pinta los datos del turno de la simulacion en el que nos encontramos.
	 *
	 * Esta información esta formada por:(turno, estado puerta, mapa, llaves de las salas, personajes).
	 * 
	 * @param turno turno en el que se ha terminado la simulacion
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir en cada turno la informacion
	 * del turno en el que nos encontramos.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void pintarInfoTurno(int turno, BufferedWriter bw) throws IOException{
		System.out.println("(turno:"+turno+")");
		bw.write("(turno:"+turno+")");bw.newLine();
		
		System.out.println("(mapa:"+this.getSalaPuerta()+")");
		bw.write("(mapa:"+this.getSalaPuerta()+")");bw.newLine();

		System.out.print("(puerta:");
		bw.write("(puerta:");

			if(getPuerta().estaAbierta()){
				System.out.print("abierta:");
				bw.write("abierta");

			}
			else{
				System.out.print("cerrada:");
				bw.write("cerrada");

			}
		System.out.print(":"+getPuerta().getCondAlt()+":");
		bw.write(":"+getPuerta().getCondAlt()+":");

		puerta.mostrarCerradura();
		puerta.mostrarCerradura(bw);
		System.out.print(":");
		bw.write(":");

		puerta.mostrarProbadas();
		puerta.mostrarProbadas(bw);
		System.out.println(")");
		bw.write(")");bw.newLine();

		
		this.pintar(bw);
		
		this.mostrarLlavesSalas(bw);
		
		this.mostrarPersonajesSalas(turno,bw);
		
	}
	
	
	
	/**
    * Programa principal - EC2.
    * @param args Argumentos que recibe el programa principal
    */
	public static void main (String args[]) {
    	
    	int numLlavesPrub = 15;
    	
    	// Creación de la lista de identificadores impares
    	// {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29}
    	int [] listaIdLlaves = new int [numLlavesPrub];
    	
    	int j=1;
    	for (int i = 0; i < numLlavesPrub; i++) {
    			listaIdLlaves[i] = j;
    	j=j+2;
    	}
    	
        // Creación del tablero del mapa
      	// Parámetros: sala de la puerta, nº columnas, nº filas y profundidad de combinación secreta
        int dimX = 6;
      	int dimY = 6;
      	int salaPuerta = (dimX * dimY) - 1; //en las primeras entregas la puerta siempre estara en la ultima sala
      	int alturaArbol = 4;
        
        Mapa mapaPrub= new Mapa(salaPuerta, dimX, dimY, alturaArbol);

        //mapaPrub.pintar();
        
      	// Combinación de llaves que se insertarán en la puerta del Trono (aun sin ordenar)
      	Llave [] combinacionPrub = new Llave [numLlavesPrub];
      	for (int i = 0; i < combinacionPrub.length; i++) {
      	      combinacionPrub [i] = new Llave(listaIdLlaves[i]);
      	}
      	
      	
      	//obtener la puerta del mapa
      	Puerta puertaPrub= mapaPrub.getPuerta();
      	
      	// Configurar la puerta introduciendo la combinación de llaves
      	puertaPrub.configurar(combinacionPrub);
      	
      	// Cerrar la puerta, por si inicialmente está abierta
      	puertaPrub.cerrar();
      	
      	// Cuando se abra la puerta mostramos mensaje de apertura de la puerta 
      	if (puertaPrub.estaAbierta()) 
 	     	puertaPrub.mostrarCerradura();
      	       
      	Stark persStarkPrub= new Stark("Stark", 'S', 0);
      	Lannister persLannisterPrub= new Lannister();
      	CaminanteBlanco persCaminanteBlancoPrub= new CaminanteBlanco();
      	Targaryen persTargaryenPrub= new Targaryen();
      	
      	mapaPrub.insertarPersonaje(persStarkPrub, 0);
      	mapaPrub.insertarPersonaje(persLannisterPrub, 1);
      	mapaPrub.insertarPersonaje(persTargaryenPrub, 2);
      	mapaPrub.insertarPersonaje(persCaminanteBlancoPrub, 3);
      	
      	//mapaPrub.pintar();
      	
      	System.out.println("  FIN DE PRUEBAS");
    	System.out.println();
    	System.out.println();
      	
      	//TODO // Realizar más pruebas
      	
      	/////////////////////////////////////
      	//********************CONSIDERACIONES
      	//*************//////////////////////
      	//////////////////////////////////////
      	//
      	//CUANDO SE HABRA LA PUERTA HAY QUE SABER QUIEN LA HA ABIERTO PARA AÑADIRLO A SALA_CONQUISTADORES
      	//
      	//NO PODEMOS CREAR MENOS DE 14 SALAS
      	
      	//////////////////////////////////
      	////*****************************
      	//Implementacion de la funcionalidad
      	//********************************/
      	//////////////////////////////////
      	
      	//Creacion o lectura de los datos principales del mapa
      	int numColumnas=5;
      	int numFilas=5;
      	int numeroPuerta= (numFilas * numColumnas) -1;
      	int altura= 4;
      	
      	//Eleccion del numero de llaves que tendra la combinacion de la cerradura
      	int numLlaves=15;
      	
      	//Fabricacion del conjunto ordenado ascendentemente de llaves que contendra la cerradura
      	Llave [] combinacion = new Llave [numLlaves];
      	int k=1;
      	for (int i = 0; i < numLlaves; i++) {
      	      combinacion [i] = new Llave(k);
      	      k=k+2;
      	}
      	
      	//Creacion del mapa
      	Mapa mapa= new Mapa(numeroPuerta,numColumnas,numFilas, 4);
      	
      	//Creacion de la puerta
      	Puerta puerta= new Puerta(altura);
      	
      	//Configuracion de la puerta
      	puerta.configurar(combinacion);
      	
      	//Insercion de la puerta ya configurada en el mapa
      	mapa.setPuerta(puerta);
      	
      	//Creacion de los personajes de la simulacion
      	Stark persStark= new Stark("Stark", 'S', 0);
      	Lannister persLannister= new Lannister();
      	CaminanteBlanco persCaminanteBlanco= new CaminanteBlanco();
      	Targaryen persTargaryen= new Targaryen();
      	
      	//Insercion de los personajes creados en el mapa y en las salas que lo conforman
      	mapa.insertarPersonaje(persStark, 0);
      	mapa.insertarPersonaje(persLannister, (numFilas*numColumnas -1));
      	mapa.insertarPersonaje(persTargaryen, 0);
      	mapa.insertarPersonaje(persCaminanteBlanco, (numColumnas* (numFilas-1)));
      	
      	//Creacion de las llaves que se repartiran en el mapa
      	Llave[] llaves= mapa.creacionLlaves(combinacion);
      	
      	//Reparto de las llaves por el mapa
      	mapa.repartoLlaves(llaves);
      	
      	//Pintando mapa
      	//mapa.pintar();
      	System.out.println("    --Mapa generado y todo listo para la simulacion");
       	System.out.println();
      	
      	//Bucle de funcionamiento
       	
       	int turno=1;

		
      	while(!puerta.estaAbierta() && turno<=100){
      		System.out.println("   		 --Turno " + turno +" --");
    		
      		//mapa.interaccionTurno(turno);
      		//mapa.pintar();
      		System.out.println();
      		turno++;
      	}
      	
      	//Fin de simulacion
    	System.out.println();
      	System.out.println();
    	System.out.println(" -Finalizando simulacion...");
      	System.out.println();
      	
      	//mapa.pintarInfo(turno-1,System.out);
      	
      	System.out.println();
      	System.out.println();
    	System.out.println(" Gracias por jugar.");
   
  }






}
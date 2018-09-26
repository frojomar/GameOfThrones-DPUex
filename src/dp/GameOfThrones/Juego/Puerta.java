package dp.GameOfThrones.Juego;
import java.io.BufferedWriter;
import java.io.IOException;

import dp.GameOfThrones.EstructurasDatos.Arbol;
import dp.GameOfThrones.EstructurasDatos.Queue;

/**
 * EC4 - proyecto16_17
 * Implementacion de los metodos de la clase Puerta.
 *
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public class Puerta {
	
	/**Variable que almacena la altura de la condicion de apertura*/
	private Integer COND_ALT;
	
	/**Arbol de Llaves que forma la  cerradura*/
	private Arbol<Llave> cerradura;
	
	/**Arbol de Llaves que almacena las llaves ya probadas*/
	private Arbol<Llave> llavesprob;
	
	/**Indicador de si la puerta se encuentra abierta o no*/
	private boolean abierta;
	
	/**Variable que guarda la combinacion ya lista para meter en el arbol*/
	private Llave[] combinacion;
	
	
	
	/** Constructor por defecto que inicializa la puerta con una cerradura sin llaves.
	 *
	 */
	public Puerta(){
		cerradura= new Arbol<Llave>();
		llavesprob= new Arbol<Llave>();
		abierta= true;
		combinacion= null;
		COND_ALT=4; //por defecto pondremos que la condicion de altura sea cuatro
	
	}
	
	/** Constructor parametrizado que inicializa la puerta y establece la condicion de altura para esta simulacion.
	 *
	 * @param altura valor de la condicion de altura
	 */
	public Puerta(int altura){
		cerradura= new Arbol<Llave>();
		llavesprob= new Arbol<Llave>();
		abierta= true;
		combinacion= null;
		COND_ALT=altura;
		
	}
	
	/** Constructor parametrizado que inicializa la puerta y la configura, ademas de pasarle la condicion de altura.
	 *
	 * @param combinacion conjunto de llaves ordenadas ascendentemente que formaran la cerradura de la puerta
	 * @param altura valor de la condicion de altura
	 */
	public Puerta(Llave[] combinacion, int altura){
		cerradura= new Arbol<Llave>();
		llavesprob= new Arbol<Llave>();
		abierta= true;
		this.combinacion= null;
		COND_ALT=altura;

		this.configurar(combinacion);
	}
	
	/**Metodo selector de la condicion de altura
	 * 
	 * @return devuelve un entero que contiene la condicion de altura de la cerradura
	 */
	public int getCondAlt(){
		return COND_ALT;
	}
	
	/**Metodo modificador de la condicion de altura
	 * 
	 * @param cond_alt contiene la nueva condicion de altura
	 */
	public void setCondAlt (int cond_alt){
		this.COND_ALT=cond_alt;
	}
	
	/** Metodo que inserta de la forma que necesitamos las llaves de la combinacion en una cola
	 *
	 * @param combinacion vector de llaves ordenado ascendentemente
	 * @param caux cola de llaves que contendra al terminar el metodo las llaves ordenadas para pasarlas
	 * 	al vector final en el orden deseado
	 * @param cota_inf cota inferior de la posicion del vector en la que nos podemos mover en esta iteracion recursiva
	 * @param cota_sup cota superior de la posicion del vector en la que nos podemos mover en esta iteracion recursiva
	 */
	private void recolocarCombinacionREC(Llave[] combinacion, Queue<Llave> caux, Integer cota_inf, Integer cota_sup){
		
		Integer MITAD= (cota_inf+1+cota_sup)/2;
		caux.encolar(combinacion[MITAD]);
		
		if(cota_inf != cota_sup){ 
			this.recolocarCombinacionREC(combinacion, caux, cota_inf, MITAD-1);	
		}
		if( ((MITAD+1+cota_sup) /2) != MITAD){ 
			this.recolocarCombinacionREC(combinacion, caux, MITAD+1, cota_sup);
		}
		
	}
	
	/** Metodo que recoloca adecuadamente el vector de llaves para insertarlo en la cerradura
	 *
	 * @param combinacion vector de llaves ordenado ascendentemente
	 * @return Devuelve el vector de llaves ordenado para insertar directamente
	 */
	private Llave[] recolocarCombinacion(Llave[] combinacion){
		Queue<Llave> caux= new Queue<Llave>();
		Llave[] combOrd= new Llave[combinacion.length];
		
		this.recolocarCombinacionREC(combinacion,caux,0,combinacion.length-1);
		
		int i=0;
		while(!caux.estaVacia()){
			combOrd[i]=caux.primero();
			caux.desencolar();
			i++;
		}
		
		return combOrd;
		
	}
	
	/** Metodo que introduce en la cerradura las llaves que abriran la puerta siguiendo un algoritmo.
	 *
	 * @param combinacion vector de llaves de la cerradura ordenado ascendentemente
	 *
	 */
	public void configurar(Llave[] combinacion){
		
		this.combinacion= new Llave[combinacion.length];
		
		//recolocamos el vector de llaves para insertarlo tal y como se nos indica en los requisitos de sistema
		this.combinacion=this.recolocarCombinacion(combinacion);
		
		//insertamos las llaves del vector al arbol
		for(int i=0; i<combinacion.length;i++){
			cerradura.insertar(this.combinacion[i]);
		}
		
		//cerramos la puerta para evitar dejarla abierta si se nos olvida llamar a cerrar despues
		this.cerrar();
	}

	/** Metodo que reinicia la puerta, volviendola a su estado inicial.
	 *
	 */
	public void reiniciarPuerta(){
		//si el arbol de la cerradura no esta vacio lo vaciaremos primero
		if(!cerradura.vacio()){
			cerradura.vaciar();
		}
		//si el arbol de las llaves probadas no esta vacio lo vaciaremos
		if(!llavesprob.vacio()){ //esto es por si queremos volver a cerrar la puerta una vez abierta, para
			llavesprob.vaciar(); //que no haya problema al probar una llave que ya ha sido probada en la vez anterior
		}
		
		//insertamos las llaves del vector al arbol
		for(int i=0; i<combinacion.length;i++){
			cerradura.insertar(this.combinacion[i]);
		}
		
		this.cerrar();
	}
	
	/** Metodo que dice si esta abierta la puerta o no.
	 *
	 * @return Devuelve true si la puerta esta abierta y false si no lo esta.
	 */
	public boolean estaAbierta(){
		return abierta;
	}	
	
	/** Metodo que cierra la puerta.
	 *
	 */
	public void cerrar(){
		this.abierta= false;
	}

	/** Metodo que comprueba si se cumplen las condiciones de apertura.
	 *
	 * @return Devuelve true si se cumplen las condiciones de apertura y false si no.
	 */
	private boolean seCumplenCondiciones(){
		if(cerradura.profundidad() < COND_ALT){
			if(cerradura.nodosHoja() <= cerradura.nodosInternos()){
				System.out.println(cerradura.nodosHoja()+"-"+cerradura.nodosInternos());
				return true;
			}
		}
		return false;
	}

	/** Metodo que abre la puerta.
	 * 
	 * Comprueba si la llave se encuentra en la cerradura. Si es asi, la borra de esta, la inserta
	 *  entre las llaves probadas y comprueba si se cumplen las condiciones para abrir la puerta. Si 
	 *  es asi, la abre.
	 *
	 *	@param l llave a probar en la cerradura
	 */
	public void abrir(Llave l){
		
		System.out.println("               Intentando abrir con la llave "+ l.getValor());
		if(!llavesprob.pertenece(l)){//sistema de seguridad que impide que se prueben una llave dos veces
			System.out.println(" 	Intentado abrir la puerta..");
			//Probamos la llave en la cerradura
			if(cerradura.pertenece(l)){
				cerradura.borrar(l);
				//Comprobamos si se cumplen las condiciones y la abrimos si es así
				if(this.seCumplenCondiciones()){
					this.abierta=true;
					System.out.println("    --Cerradura abierta--");
				}
			}
			
			//Insertamos la llave probada al arbol de llaves probadas
			llavesprob.insertar(l);
			

			
		}	
	}
	
	/** Metodo que muestra el identificador de las llaves que estan en la cerradura en orden
	 *
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir las llaves de la cerradura.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void mostrarCerradura(BufferedWriter bw) throws IOException{
		cerradura.inOrden(bw);
	}
	
	/** Metodo que muestra el identificador de las llaves que estan en la cerradura en orden
	 *
	 */
	public void mostrarCerradura(){
		cerradura.inOrden();
	}
	
	/** Metodo que muestra el identificador de las llaves que se han probado en orden
	 *
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir las llaves probadas en la cerradura.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void mostrarProbadas(BufferedWriter bw) throws IOException{
		llavesprob.inOrden(bw);
	}
	
	/** Metodo que muestra el identificador de las llaves que se han probado en orden
	 *
	 */
	public void mostrarProbadas(){
		llavesprob.inOrden();
	}
	
	
	//Main de pruebas de Puerta
	public static void main (String[] args){
		
		int tamComb=15;
		
		Puerta puerta = new Puerta();
		Llave[] combinacion= new Llave[tamComb];
		
		int j=1;
		
		for(int i=0; i<tamComb; i++){
			combinacion[i]= new Llave(j);
			j=j+2;
		}
		
		System.out.println("Llaves de la combinacion: " +combinacion.length);
		
		for(int i=0; i<tamComb; i++){
			System.out.print(combinacion[i]);
			System.out.print(" - ");
		}
		System.out.println();
		
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		System.out.println("La puerta esta abierta debe ser true: "+ puerta.estaAbierta());
		
		puerta.configurar(combinacion);
		System.out.println(" --Puerta configurada");
		
		System.out.println("La puerta esta abierta debe ser false: "+ puerta.estaAbierta());
		
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(5));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(7));
		puerta.abrir(new Llave(3));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(9));
		puerta.abrir(new Llave(21));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(20));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(29));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(1));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(17));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(17));
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();

		puerta.reiniciarPuerta();
		System.out.println("--Cerradura reiniciada");
		
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		System.out.println();
		System.out.println();
		System.out.println("ESTA NUEVA SECUENCIA DEBE ABRIR LA PUERTA");
		System.out.println();
		
		puerta.abrir(new Llave(1));
		System.out.println("  Llamando a abrir con la Llave "+ 1);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();

		puerta.abrir(new Llave(5));
		System.out.println("  Llamando a abrir con la Llave "+ 5);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(4));
		System.out.println("  Llamando a abrir con la Llave "+ 4);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(9));
		System.out.println("  Llamando a abrir con la Llave "+ 9);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(6));
		System.out.println("  Llamando a abrir con la Llave "+ 6);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(17));
		System.out.println("  Llamando a abrir con la Llave "+ 17);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(13));
		System.out.println("  Llamando a abrir con la Llave "+ 13);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(20));
		System.out.println("  Llamando a abrir con la Llave "+ 20);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(21));
		System.out.println("  Llamando a abrir con la Llave "+ 21);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(2));
		System.out.println("  Llamando a abrir con la Llave "+ 1);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(8));
		System.out.println("  Llamando a abrir con la Llave "+ 8);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(27));
		System.out.println("  Llamando a abrir con la Llave "+ 27);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		puerta.abrir(new Llave(25));
		System.out.println("  Llamando a abrir con la Llave "+ 25);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		System.out.println("  Llamando a abrir con la Llave "+ 29);
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.abrir(new Llave(29));
		puerta.mostrarCerradura();
		
		puerta.reiniciarPuerta();
		System.out.println("--Cerradura reiniciada");
		
		System.out.println("El arbol In Orden de la cerradura es:");
		puerta.mostrarCerradura();
		
		//Se puede probar que el arbol queda correctamente ordenado depurando el codigo y viendo como se 
		//van insertando en el arbol los distintos datos, dibujando en un papel este para compararlo con el
		//dado por los profesores en la documentacion
		
		//EL RESTO DE PRUEBAS SOBRE PUERTA SE REALIZARAN EN LE MAIN DE MAPA
	}
	
}
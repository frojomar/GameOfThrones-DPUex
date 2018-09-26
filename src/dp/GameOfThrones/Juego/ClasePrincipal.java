package dp.GameOfThrones.Juego;

import dp.GameOfThrones.Juego.Mapa;
import dp.GameOfThrones.CargaFicheros.*;
import dp.GameOfThrones.Excepciones.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
* EC4 - proyecto16_17
* Implementacion de la clase Principal del proyecto 16-17.
* @version 2.0
* @author
* <b> Francisco Javier Rojo Martín</b><br>
* Proyecto GameOfThrones-DPUex<br>
* Curso 16/17
*/
public class ClasePrincipal {
		
	/**Instancia de mapa que contendra todo para la simulacion*/
	Mapa m;
	
	/**Constructor por defecto de la Clase Principal*/
	public ClasePrincipal(){
		
	}
	
	/**Metodo que carga los datos del fichero de inicio al mapa m de la ClasePrincipal. 
	 * 
	 * Si no se puede cargar el fichero se crea un mapa por defecto.
	 *
	 * @param nombreF String que contiene el nombre del fichero de inicio que se empleará 
	 * en esta simulación para cargar los datos.
	 * 
	 * */
	public void cargaDesdeFichero(String nombreF){

		Cargador cargador = new Cargador();
		try {
			/**  
			Método que procesa línea a línea el fichero de entrada inicio.txt
			*/
		     FicheroCarga.procesarFichero(nombreF, cargador);
		}
		catch (FileNotFoundException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
		catch (IOException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
		catch(DimensionesException e1){
			System.err.println("Error con los datos del fichero: "+ e1.mensaje());
		}
		catch(SalaPuertaException e2){
			System.err.println("Error con los datos del fichero: "+ e2.mensaje());
		}
		catch(CondAltException e3){
			System.err.println("Error con los datos del fichero: "+ e3.mensaje());
		}
		catch(TurnoException e4){
			System.err.println("Error con los datos del fichero: "+ e4.mensaje());
		}
		
		m= Mapa.getInstancia(); //una vez que ya tenemos todo cargado ya copiamos a m la instancia que va a ser
								//única de Mapa. Si no se hubiesen cargado los datos ahora lo que pasaría
								//es que se estaría creando un mapa nuevo, pues aun no se habría creado ninguno
								//Podemos hacer esto ademas porque a partir de aqui ya podemos garantizar que no se
								//va a generar ningun otro mapa, al ser el constructor private. 
								//	(Uso de Singleton)
		
	}
	
	/**Metodo que crea las llaves de la puerta, la configura y reparte las llaves para abrirla por
	 * el mapa.
	 */
	public void prepararPuertaYLlaves(){
		//Eleccion del numero de llaves que tendra la combinacion de la cerradura
      	int numLlaves=15;
      	
      	//Fabricacion del conjunto ordenado ascendentemente de llaves que contendra la cerradura
      	Llave [] combinacion = new Llave [numLlaves];
      	int k=1;
      	for (int i = 0; i < numLlaves; i++) {
      	      combinacion [i] = new Llave(k);
      	      k=k+2;
      	}
      	
      	(m.getPuerta()).configurar(combinacion);
      	  	
      	//Creacion de las llaves que se repartiran en el mapa
      	Llave[] llaves= m.creacionLlaves(combinacion);
      	
      	//Reparto de las llaves por el mapa
      	m.repartoLlaves(llaves);
	}
	
	/**
	 * Metodo que implementa la funcionalidad del juego.
	 * 
	 * Realiza los pasos necesarios para ejecutar cada turno, y los ejecuta tantas veces como turnos
	 * queremos que tenga la simulacion.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir en cada turno la informacion
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void emular(BufferedWriter bw) throws IOException{
      	Sala sAux;
      	//Pintando mapa
      	System.out.println("    --Mapa generado y todo listo para la simulacion");
       	System.out.println();
      	
      	//Bucle de funcionamiento
       	
       	int turno=1;

      	this.volcarAFicheroTurno(turno-1, bw);
      	while(!(m.getPuerta()).estaAbierta() && turno<100){
      		System.out.println("   		 --Turno " + turno +" --");
      		

      		System.out.println();
      		
      		for(int i=0; i<m.getDimY(); i++){
      			for(int j=0; j<m.getDimX(); j++){
      				sAux= m.getSala(i,j);
      				sAux.tratarPersonajes(turno);
      			}
      		}
      		this.volcarAFicheroTurno(turno, bw);
      		turno++;
      	}
  		this.mostrarConquistadores(bw);
  		System.out.println();
      	//Fin de simulacion
    	System.out.println();
      	System.out.println();
    	System.out.println(" -Finalizando simulacion...");
      	System.out.println();
      	
      	System.out.println();
      	System.out.println();
    	System.out.println(" Gracias por jugar.");
	}
	
	
	/**Metodo que: genere las paredes iniciales (antes de formar atajos), pinta la estructura del mapa,
	 * forma atajos, carga las rutas de los personajes y las pinta. Todo se pinta en el 'registro.log'.
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir la informacion inicial (estructura
	 * del mapa y rutas de los personajes).
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void prepararParedesYRutas(BufferedWriter bw) throws IOException{
		m.generarParedes();
		m.pintarEstructura(bw);
		m.formarAtajos();
		m.cargarRutas();
		m.pintarRutas(bw);
		
	}
	
	/**Metodo que imprime en el fichero de salida la informacion respectiva a los conquistadores en el ultimo turno
	 * 
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir la informacion de los conquistadores
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void mostrarConquistadores(BufferedWriter bw) throws IOException{
		m.mostrarConquistadores(bw);
	}
	
	/**Metodo que imprime en el fichero la informacin respectiva a un turno de la simulacion.
	 * 
	 * @param turno turno de la simulacion en el que nos encontramos.
	 * @param bw BufferedWriter asociado al fichero 'registro.log' para imprimir la informacion del turno.
	 * @throws IOException puede lanzar una excepcion de Entrada Salida al escribir sobre 'registro.log'
	 */
	public void volcarAFicheroTurno(int turno, BufferedWriter bw) throws IOException{
		m.pintarInfoTurno(turno, bw);
	}
	

	public static void main(String[] args) {
		ClasePrincipal cp= new ClasePrincipal();
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter("registro.log"));
			
			if(args.length>0){
				cp.cargaDesdeFichero(args[0]);
			}
			else{
				cp.cargaDesdeFichero("inicio.txt"); //si no se pasa como argumento el fichero se ejecuta este por defecto
			}
				cp.prepararParedesYRutas(bw);
				cp.prepararPuertaYLlaves();
				cp.emular(bw);
			
			bw.close();
		}
		catch(IOException e){
			System.err.println("Error E/S: "+e);
		}


		
			

		


	}
}

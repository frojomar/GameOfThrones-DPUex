package dp.GameOfThrones.Juego;

import java.util.LinkedList;

import dp.GameOfThrones.EstructurasDatos.Grafo;

/**
 * EC4 - proyecto16_17
 * Implementacion de la clase Lannister(subclase de Defensor).
 *
 * @version 1.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public class Lannister extends Defensor{
	
	/**Constructor por defecto de la clase Lannister
	 * 
	 */
	public Lannister(){
		super("Lannister", 'L', 1);
		this.cargarLlaves();
	}
	
	/**Constructor parametrizado de la clase Lannister
	 * 
	 * @param nombre nombre que recibirá el personaje
	 * @param c identificador del personaje
	 * @param turno int que contiene el turno en el que comenzará el personaje la simulacion
	 */
	public Lannister(String nombre, char c, int turno) {
		super(nombre, c, turno);
		this.cargarLlaves();
	}
	
	/**Método encargado de cargar las direcciones de movimiento de los personajes de tipo Lannister.
	 * 
	 * Primero genera una ruta tal y como se pide para este (SE - NE - NO - SO - SE) y despues llama al 
	 * metodo común a todos los personajes que transforma la ruta formada por identificadores de salas
	 * en direcciones {N,E,S,O}.
	 * 
	 */
	protected void cargarDirecciones(){
		Mapa m= Mapa.getInstancia();
		Grafo g= m.getParedes();
		LinkedList<Integer> ruta= new  LinkedList<Integer>();
		int origen= (m.getDimX()*m.getDimY())-1;
		int destino;
		
		g.warshall();
		g.floyd();
		ruta.addLast(origen); //metemos el nodo origen al principio
		
		destino= m.getDimX()-1; //vamos de la sala más al SE a la sala más al NE
		while(origen!=destino){
			origen=g.siguiente(origen, destino);
			ruta.addLast(origen);
		}
		
		origen= m.getDimX()-1; //vamos de la sala más al NE a la sala más al NO
		destino= 0;
		while(origen!=destino){
			origen=g.siguiente(origen, destino);
			ruta.addLast(origen);
		}
		
		origen= 0; //vamos de la sala más al NO a la sala más al SO
		destino= m.getDimX()*(m.getDimY()-1);
		while(origen!=destino){
			origen=g.siguiente(origen, destino);
			ruta.addLast(origen);
		}
		
		origen= m.getDimX()*(m.getDimY()-1); //vamos de la sala más al SO a la sala más al SE(inicio)
		destino= (m.getDimX()*m.getDimY())-1;
		while(origen!=destino){
			origen=g.siguiente(origen, destino);
			ruta.addLast(origen);
		}
		
		this.cargarRutaEnDirecciones(ruta);
	}

	/**Metodo que dice de que tipo es el personaje
	 * 
	 * @return devuelve un string que contendrá el tipo del personaje
	 */
	public String tipoPersonaje(){
		return "lannister";
	}
	
	/**Metodo privado que carga la pila de llaves de los Lannister.
	 * 
	 * Los Lannister llevan desde el principio de la simulacion la pila de llaves llena, y seran
	 * esas llaves las que dejaran caer en las salas pares.
	 */
	private void cargarLlaves(){
		for(int i=1; i<=29; i=i+2){
			llaves.addData(new Llave(i));
		}
	}
	
	/**Metodo que interactua con la sala, dejando una llave en la sala si el personaje de tipo
	 * Lannister posee llaves en su pila y el numero de la sala es par
	 * 
	 * @param s sala sobre la que va a actuar el algoritmo
	 */
	public void interactuarSala(Sala s){
		if (!llaves.isEmpty() &&  ( (s.getNumero()%2) ==0) ){
			s.insertarLlave(llaves.getTop());
			llaves.removeData();
			System.out.println("    --Llave depositada en la sala");
		}
		else{
			System.out.println("    --El personaje ya no tiene llaves que soltar o la sala no es par");
		}
	}
}

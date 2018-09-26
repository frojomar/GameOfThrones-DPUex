package dp.GameOfThrones.Juego;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

import dp.GameOfThrones.EstructurasDatos.Grafo;
import dp.GameOfThrones.EstructurasDatos.Queue;

/**
 * EC4 - proyecto16_17
 * Implementacion de la clase CaminanteBlanco (subclase de Defensor).
 *
 * @version 3.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public class CaminanteBlanco extends Defensor{
	
	/**Cola donde almacenara los personajes capturados*/
	Queue<Personaje> personajesCapturados;
	
	
	/**Constructor por defecto de la clase CaminanteBlanco
	 * 
	 */
	public CaminanteBlanco(){
		super("CaminanteBlanco", 'C', 1);
		personajesCapturados= new Queue<Personaje>();
	}
	
	/**Constructor parametrizado de la clase CaminanteBlanco
	 * 
	 * @param nombre nombre que recibirá el personaje
	 * @param c identificador del personaje
	 * @param turno int que contiene el turno en el que comenzará la simulación el personaje
	 */
	public CaminanteBlanco(String nombre, char c, int turno) {
		super(nombre, c, turno);
		personajesCapturados= new Queue<Personaje>();
	}
	
	/**Método encargado de cargar las direcciones de movimiento de los personajes de tipo CaminanteBlanco.
	 * 
	 * Primero genera una ruta tal y como se pide para este (SO - NO - NE - SE - SO) y despues llama al 
	 * metodo común a todos los personajes que transforma la ruta formada por identificadores de salas
	 * en direcciones {N,E,S,O}.
	 * 
	 */
	protected void cargarDirecciones(){
		Mapa m= Mapa.getInstancia();
		Grafo g= m.getParedes();
		LinkedList<Integer> ruta= new  LinkedList<Integer>();
		int origen= m.getDimX()*(m.getDimY()-1);
		int destino;
		//int sig=origen;
		
		g.warshall();
		g.floyd();
		ruta.addLast(origen); //metemos el nodo origen al principio
		
		destino= 0; //vamos de la sala más al SO a la sala más al NO
		while(origen!=destino){
			origen=g.siguiente(origen, destino);
			ruta.addLast(origen);
		}
		
		origen= 0; //vamos de la sala más al NO a la sala más al NE
		destino= m.getDimX()-1;
		while(origen!=destino){
			origen=g.siguiente(origen, destino);
			ruta.addLast(origen);
		}
		
		origen= m.getDimX()-1; //vamos de la sala más al NE a la sala más al SE
		destino= (m.getDimX()*m.getDimY())-1;
		while(origen!=destino){
			origen=g.siguiente(origen, destino);
			ruta.addLast(origen);
		}
	
		origen= (m.getDimX()*m.getDimY())-1; //vamos de la sala más al SE a la sala más al SO(inicio)
		destino= m.getDimX()*(m.getDimY()-1);
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
		return "caminante";
	}

	/**Metodo que interactua con la sala, capturando al personaje que mas tiempo lleva en la sala (si
	 * es que hay personajes en la sala) siempre y cuando no sea un Caminante Blanco. 
	 * Si el personaje que más tiempo lleva es un CaminanteBlanco, buscará el siguiente que más tiempo 
	 * lleve en la Sala para capturarlo, haciendo esto una y otra vez hasta que no queden personajes que 
	 * mirar en la sala o encuentre un no CaminanteBlanco que capturar.
	 * 
	 * @param s sala sobre la que va a actuar el algoritmo
	 */
	public void interactuarSala(Sala s){
		Queue<Personaje> colaAux= new Queue<Personaje>();
		boolean encontrado=false;
		
		if(s.tienePersonajes()){//si tiene personajes la sala
			if(s.obtenerPersonaje() instanceof CaminanteBlanco){  //si el primer personaje es un CaminanteBlanco
				while(s.tienePersonajes()){//buscamos el siguiente personaje que este en la sala y no sea Caminante
					if(!encontrado && !(s.obtenerPersonaje() instanceof CaminanteBlanco)){//si el personaje no es caminente blanco y aun no habiamos encontrado a ninguno para capturar
						encontrado=true;
						personajesCapturados.encolar(s.obtenerPersonaje()); //capturamos
						System.out.println("    --Personaje capturado");
					}
					else{//si ya hemos capturado alguno o el personaje es caminante lo insertamos en la cola auxiliar para volver a meterlo luego
						colaAux.encolar(s.obtenerPersonaje());
					}
					s.eliminarPersonaje();
				}
				while(!colaAux.estaVacia()){//volvemos a meter en orden los personajes no capturados
					s.insertarPersonaje(colaAux.primero());
					colaAux.desencolar();
				}
			}
			else{//si el primer personaje que hay en la sala no es un caminante blanco
				personajesCapturados.encolar(s.obtenerPersonaje()); //capturamos
				s.eliminarPersonaje(); //eliminamos
				System.out.println("    --Personaje capturado");
			}
		}
		else{
			System.out.println("    --No hay personaje que capturar");
		}
	}
	
	/**Metodo que muestra los personajes capturados por el CaminanteBlanco.
	 * 
	 * Se llama mostrar llaves porque redefine el método que muestra las llaves en el resto de los personajes.
	 * 
	 */
	public void mostrarLlaves(BufferedWriter bw) throws IOException{
		Queue<Personaje> pjAux= new Queue<Personaje> ();
		
		while(!personajesCapturados.estaVacia()){
			System.out.print(" "+(personajesCapturados.primero()).getIdentificador());
			bw.write(" "+(personajesCapturados.primero()).getIdentificador());
			pjAux.encolar(personajesCapturados.primero());
			personajesCapturados.desencolar();
		}
		while(!pjAux.estaVacia()){
			personajesCapturados.encolar(pjAux.primero());
			pjAux.desencolar();
		}
	}
}

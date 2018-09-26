package dp.GameOfThrones.Juego;

import java.util.LinkedList;

import dp.GameOfThrones.EstructurasDatos.Grafo;

/**
 * EC4 - proyecto16_17
 * Implementacion de la clase Stark (subclase de Atacante).
 *
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public class Stark extends Atacante{
	
	/**Constructor por defecto de la clase Stark
	 * 
	 */
	public Stark(){
		super("Stark", 'S', 1);
	}
	
	/**Constructor parametrizado de la clase Stark
	 * 
	 * @param nombre nombre que recibirá el personaje
	 * @param c identificador del personaje
	 * @param turno int que contiene el turno en el que comenzará el personaje la simulacion
	 */
	public Stark(String nombre, char c, int turno) {
		super(nombre, c, turno);
	}

	/**Método encargado de cargar las direcciones de movimiento de los personajes de tipo Stark.
	 * 
	 * Primero genera una ruta tal y como se pide para este (siguiendo el camino más corto entre la sala
	 * donde inicia, por defecto la sala con identificador 0, y la sala del Trono, dada en el fichero inicio) 
	 * y despues llama al metodo común a todos los personajes que transforma la ruta formada 	
	 * por identificadores de salas en direcciones {N,E,S,O}.
	 */
	protected void cargarDirecciones(){
		Grafo g= Mapa.getInstancia().getParedes();
		LinkedList<LinkedList<Integer>> caminos= new  LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> ruta= new  LinkedList<Integer>();
		LinkedList<Integer> rutaFinal= new  LinkedList<Integer>();
		int menorS; //mejor hasta ahora. Contiene la sala con menor indice a la que acceder
		boolean llegada=false;
		
		g.profundidad(g, 0, Mapa.getInstancia().getSalaPuerta(), caminos);
		
		while(!caminos.isEmpty() && !llegada){
			menorS=99999999; //numero que obligará a que la sala sea menor que este numero.
			for(int i=0; i<caminos.size(); i++){
				ruta=caminos.get(i);
				if(ruta.getFirst()<menorS){ 	//buscamos entre las rutas que nos quedan cual es la 
					menorS=ruta.getFirst(); 	//siguiente sala a la que ir con menor indice.
				}
			}
			
			rutaFinal.addLast(menorS); //insertamos en la ruta final la sala a la que debemos ir
			if(menorS==Mapa.getInstancia().getSalaPuerta()){
				llegada=true;
			}
			
			for(int i=0; i<caminos.size(); i++){
				ruta=caminos.get(i);
				if(ruta.getFirst()==menorS){ 	//si esta ruta sigue el buen camino la dejamos, 
					ruta.removeFirst();			//eliminamos tan solo la primera posicion para pasar a mirar luego la otra posicion sobre todas
					if(ruta.isEmpty()){
						caminos.remove(i);
						i--; //para que la siguiente a comprobar no se salte, pues sera igual que esta ahora
					}
				}
				else{	//si la ruta no va por el mejor camino se elimina.
					caminos.remove(i);
					i--; //mismo motivo que antes
				}
			}
		}
		
		this.cargarRutaEnDirecciones(rutaFinal);
	}
	
	/**Metodo que dice de que tipo es el personaje
	 * 
	 * @return devuelve un string que contendrá el tipo del personaje
	 */
	public String tipoPersonaje(){
		return "stark";
	}

}

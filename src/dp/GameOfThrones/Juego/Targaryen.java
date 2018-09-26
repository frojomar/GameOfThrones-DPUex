package dp.GameOfThrones.Juego;

import java.util.LinkedList;

import dp.GameOfThrones.EstructurasDatos.Grafo;

/**
 * EC4 - proyecto16_17
 * Implementacion de la clase Targaryen(subclase de Atacante).
 *
 * @version 2.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public class Targaryen extends Atacante{
	
	/**Constructor por defecto de la clase Targaryen
	 * 
	 */
	public Targaryen(){
		super("Targaryen", 'T', 1);
	}
	
	/**Constructor parametrizado de la clase Targaryen
	 * 
	 * @param nombre nombre que recibirá el personaje
	 * @param c identificador del personaje
	 * @param turno int que contiene el turno en el que comenzará el personaje la simulacion
	 */
	public Targaryen(String nombre, char c, int turno) {
		super(nombre, c, turno);
	}

	/**Método encargado de cargar las direcciones de movimiento de los personajes de tipo Targaryen.
	 * 
	 * Primero genera una ruta tal y como se pide para este (siguiendo el algoritmo de la mano derecha o
	 * Wall Follower ) y despues llama al metodo común a todos los personajes que transforma la ruta formada 	
	 * por identificadores de salas en direcciones {N,E,S,O}.
	 */
	protected void cargarDirecciones(){
		LinkedList<Integer> ruta= new  LinkedList<Integer>();
		boolean posible;
		int origen= 0;
		
		ruta.addLast(origen);
		posible=this.cargarDireccionesRec(ruta,origen);
		
		System.out.print("Ruta Targaryen: ");
		for(int i=0; i<ruta.size(); i++){
			System.out.print(ruta.get(i)+"-");
		}
		
		if(posible){
			this.cargarRutaEnDirecciones(ruta); //convierte la ruta de ident. de salas en direcciones {'N','E','S','O'}
		}
	}
	
	private boolean cargarDireccionesRec(LinkedList<Integer> ruta, int origen) {
		/*Mapa m= Mapa.getInstancia();
		Grafo g= m.getParedes();
		int destino;
		boolean posible=false;
		
	
		if(origen==m.getSalaPuerta()) //si hemos llegado a la sala de la Puerta se acabo
			return true;
		
		destino=origen-1;
		if(destino!=anterior && !posible && g.adyacente(origen, destino)){ //0
			ruta.addLast(destino);
			posible=this.cargarDireccionesRec(ruta, destino, origen);
		}

		destino=origen+m.getDimX();
		if(destino!=anterior && !posible && g.adyacente(origen, destino)){ //S
			ruta.addLast(destino);
			posible=this.cargarDireccionesRec(ruta, destino, origen);
		}
		
		destino=origen+1;
		if(destino!=anterior && !posible && g.adyacente(origen, destino)){ //E
			ruta.addLast(destino);
			posible=this.cargarDireccionesRec(ruta, destino, origen);
		}
		
		destino=origen-m.getDimX();
		if(destino!=anterior && !posible && g.adyacente(origen, destino)){ //N
			ruta.addLast(destino);
			posible=this.cargarDireccionesRec(ruta, destino, origen);
		}
		

		
		if(!posible)
			ruta.addLast(anterior);
		
		return posible;*/
		
		Mapa m= Mapa.getInstancia();
		Grafo g= m.getParedes();
		int destino;
		boolean giro;
		int oActual=0; //contiene el indice del vector de orientaciones que contiene la direccion en la que mira el pj
			//char[] orientaciones= {'S', 'O', 'N', 'E'}; el oActual se asociaría como indice de este vector
		
		while(origen!=m.getSalaPuerta()){
			
			giro=false;

			switch(oActual){
				case(0):
					if(g.adyacente(origen, origen-1))
						giro=true;
					break;
				
				case(1):
					if(g.adyacente(origen, origen-m.getDimX()))
						giro=true;
					break;
					
				case(2):
					if(g.adyacente(origen, origen+1))
						giro=true;
					break;
				
				case(3):
					if(g.adyacente(origen, origen+m.getDimX()))
						giro=true;
					break;
				
				default:
					break;
			}
			if(giro){
				if(oActual<3)
					oActual++;
				else
					oActual=0;
			}

			switch(oActual){
				case(0):
					destino=origen + m.getDimX();
					break;
				
				case(1):
					destino=origen - 1;
					break;
					
				case(2):
					destino=origen - m.getDimX();
					break;
				
				case(3):
					destino=origen + 1;
					break;
				
				default:
					destino=origen;
					break;
			}
			if(g.adyacente(origen, destino)){
				ruta.addLast(destino);
				System.out.print(":"+destino+" ");
				origen=destino;
			}
			else{
				if(oActual>0)
					oActual--;
				else
					oActual=3;
			}
		}
		return true;
	}

	/**Metodo que dice de que tipo es el personaje
	 * 
	 * @return devuelve un string que contendrá el tipo del personaje
	 */
	public String tipoPersonaje(){
		return "targaryen";
	}
	
}

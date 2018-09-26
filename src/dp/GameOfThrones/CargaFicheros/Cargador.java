package dp.GameOfThrones.CargaFicheros;

import java.util.List;

import dp.GameOfThrones.Excepciones.*;
import dp.GameOfThrones.Juego.*;

/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Estacion, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 6.0 -  27/10/2016 
 * @author Profesores DP
 * * Ampliacion realizada por Francisco Javier Rojo Martín
 */
public class Cargador {
	/**  
	número de elementos distintos que tendrá la simulación - Mapa, Stark, Lannister, Caminante Blanco, Targaryen
	*/
	static final int NUMELTOSCONF  = 6;//6
	/**  
	atributo para almacenar el mapeo de los distintos elementos
	*/
	static private DatoMapeo [] mapeo;
	
	//crear una referencia a un mapa que apuntara a null hasta que no creemos el mapa
	/**
	 *  constructor parametrizado 
	 */
	public Cargador()  {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		mapeo[0]= new DatoMapeo("MAPA", 5);
		mapeo[1]= new DatoMapeo("STARK", 4);
		mapeo[2]= new DatoMapeo("TARGARYEN", 4);
		mapeo[3]= new DatoMapeo("LANNISTER", 4);
		mapeo[4]= new DatoMapeo("CAMINANTE", 4);
		mapeo[5]= new DatoMapeo("LANNISTERNEO", 4);
		
	}
	
	/**
	 *  busca en mapeo el elemento leído del fichero inicio.txt y devuelve la posición en la que está 
	 *  @param elto elemento a buscar en el array
	 *  @return res posición en mapeo de dicho elemento
	 */
	private int queElemento(String elto)  {
	    int res=-1;
	    boolean enc=false;

	    for (int i=0; (i < NUMELTOSCONF && !enc); i++)  {
	        if (mapeo[i].getNombre().equals(elto))      {
	            res=i;
	            enc=true;
	        }
	    }
	    return res;
	}
	
	/**
	 *  método que crea las distintas instancias de la simulación 
	 *  @param elto nombre de la instancia que se pretende crear
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo de la instancia
	 */
	public void crear(String elto, int numCampos, List<String> vCampos)	throws DimensionesException, SalaPuertaException, CondAltException, TurnoException{
	    //Si existe elemento y el n�mero de campos es correcto, procesarlo... si no, error
	    int numElto = queElemento(elto);

	    //Comprobaci�n de datos básicos correctos
	    if ((numElto!=-1) && (mapeo[numElto].getCampos() == numCampos))   {
	        //procesar
	        switch(numElto){
	        case 0:	   
	            crearMapa(numCampos,vCampos);
	            break;
	        case 1:
	            crearStark(numCampos,vCampos);
	            
	            break;
	        case 2:
	            crearTargaryen(numCampos,vCampos);
	            break;
	        case 3:
	            crearLannister(numCampos,vCampos);
	            break;
	        case 4:
	            crearCaminante(numCampos,vCampos);
	            break;
	        case 5:
	        	crearLannisterNeo(numCampos,vCampos);
	        	break;
	     	}
	    }
	    else
	        System.out.println("ERROR Cargador::crear: Datos de configuración incorrectos... " + elto + "," + numCampos+"\n");

	}

	/**
	 *  método que crea una instancia de la clase Planta
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearMapa(int numCampos, List<String> vCampos) throws DimensionesException, SalaPuertaException, CondAltException{
		Integer dimX, dimY, salaPuerta, cond_alt;
		String cadena;
		
		System.out.println("Creando Mapa\n");
		
		cadena=(vCampos.get(1)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el numero solo
		dimX= Integer.parseInt(cadena);
		
		cadena=(vCampos.get(2)).replaceAll(" ", "");
		dimY= Integer.parseInt(cadena);
		
		cadena=(vCampos.get(3)).replaceAll(" ", "");
		salaPuerta= Integer.parseInt(cadena);
		
		cadena=(vCampos.get(4)).replaceAll(" ", "");
		cond_alt= Integer.parseInt(cadena);
		
		if(dimX<3 || dimY<3)
			throw new DimensionesException();
		
		if(salaPuerta<0 || salaPuerta>(dimX*dimY-1))
			throw new SalaPuertaException();
		
		if(cond_alt<1)
			throw new CondAltException();
		
		Mapa.getInstancia(salaPuerta, dimX, dimY, cond_alt);
		
	    System.out.println("--Mapa Creado--\n");
	}

	/**
	 *  método que crea una instancia de la clase Stark
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearStark(int numCampos, List<String> vCampos) throws TurnoException
	{
		Integer turno;
		String nombre;
		char[] identificador;
		String cadena; //cadena auxiliar para transformar a Integer y a Character
		Mapa m= Mapa.getInstancia();
		
		System.out.println("Creando Stark\n");
		
		nombre=vCampos.get(1);
		
		cadena=(vCampos.get(2)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el caracter solo
		identificador= cadena.toCharArray();
		if (identificador.length!=1)
			System.out.println("IDENTIFICADOR DE PERSONAJE "+ vCampos.get(1)+" NO VALIDO");
		
		cadena=(vCampos.get(3)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el numero solo
		turno= Integer.parseInt(cadena);
		
		if(turno<=0)
			throw new TurnoException();
		
		Stark pj= new Stark(nombre, identificador[0], turno);
		m.insertarPersonaje(pj, 0);
		
	    System.out.println("--Stark Creado: " + vCampos.get(1) + "--\n");
	}

	/**
	 *  método que crea una instancia de la clase Targaryen
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearTargaryen(int numCampos, List<String> vCampos) throws TurnoException
	{
		Integer turno;
		String nombre;
		char[] identificador;
		String cadena; //cadena auxiliar para transformar a Integer y a Character
		Mapa m= Mapa.getInstancia();
		
		System.out.println("Creando Targaryen\n");
		
		nombre=vCampos.get(1);
		
		cadena=(vCampos.get(2)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el caracter solo
		identificador= cadena.toCharArray();
		if (identificador.length!=1)
			System.out.println("IDENTIFICADOR DE PERSONAJE "+ vCampos.get(1)+" NO VALIDO");
		
		cadena=(vCampos.get(3)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el numero solo
		turno= Integer.parseInt(cadena);
		
		if(turno<=0)
			throw new TurnoException();
		
		Targaryen pj= new Targaryen(nombre, identificador[0], turno);
		m.insertarPersonaje(pj, 0);
		
	    System.out.println("--Targaryen Creado: " + vCampos.get(1) + "--\n");
	}	

	/**
	 *  método que crea una instancia de la clase Lannister
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearLannister(int numCampos, List<String> vCampos) throws TurnoException
	{
		Integer turno;
		String nombre;
		char[] identificador;
		String cadena; //cadena auxiliar para transformar a Integer y a Character
		Mapa m= Mapa.getInstancia();
		
		System.out.println("Creando Lannister\n");
		
		nombre=vCampos.get(1);
		
		cadena=(vCampos.get(2)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el caracter solo
		identificador= cadena.toCharArray();
		if (identificador.length!=1)
			System.out.println("IDENTIFICADOR DE PERSONAJE "+ vCampos.get(1)+" NO VALIDO");
		
		cadena=(vCampos.get(3)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el numero solo
		turno= Integer.parseInt(cadena);
		
		if(turno<=0)
			throw new TurnoException();
		
		Lannister pj= new Lannister(nombre, identificador[0], turno);
		m.insertarPersonaje(pj, m.getDimX()*m.getDimY()-1);
		
	    System.out.println("--Lannister Creado: " + vCampos.get(1) + "--\n");
	}	

	/**
	 *  método que crea una instancia de la clase White Walker
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearCaminante(int numCampos, List<String> vCampos) throws TurnoException
	{
		Integer turno;
		String nombre;
		char[] identificador;
		String cadena; //cadena auxiliar para transformar a Integer y a Character
		Mapa m= Mapa.getInstancia();
		
		System.out.println("Creando Caminante Blanco\n");
		
		nombre=vCampos.get(1);
		
		cadena=(vCampos.get(2)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el caracter solo
		identificador= cadena.toCharArray();
		if (identificador.length!=1)
			System.out.println("IDENTIFICADOR DE PERSONAJE "+ vCampos.get(1)+" NO VALIDO");
		
		cadena=(vCampos.get(3)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el numero solo
		turno= Integer.parseInt(cadena);
		
		if(turno<=0)
			throw new TurnoException();
		
		CaminanteBlanco pj= new CaminanteBlanco(nombre, identificador[0], turno);
		m.insertarPersonaje(pj, (m.getDimX()* (m.getDimY()-1)));
		
	    System.out.println("--Caminante Blanco Creado: " + vCampos.get(1) + "--\n");
	}

	/**
	 *  método que crea una instancia de la clase LannisterNeo
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearLannisterNeo(int numCampos, List<String> vCampos) throws TurnoException
	{
		Integer turno;
		String nombre;
		char[] identificador;
		String cadena; //cadena auxiliar para transformar a Integer y a Character
		Mapa m= Mapa.getInstancia();
		
		System.out.println("Creando LannisterNeo\n");
		
		nombre=vCampos.get(1);
		
		cadena=(vCampos.get(2)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el caracter solo
		identificador= cadena.toCharArray();
		if (identificador.length!=1)
			System.out.println("IDENTIFICADOR DE PERSONAJE "+ vCampos.get(1)+" NO VALIDO");
		
		cadena=(vCampos.get(3)).replaceAll(" ", ""); //eliminamos espacios si existen para quedar el numero solo
		turno= Integer.parseInt(cadena);
		
		if(turno<=0)
			throw new TurnoException();
		
		LannisterNeo pj= new LannisterNeo(nombre, identificador[0], turno);
		m.insertarPersonaje(pj, m.getDimX()*m.getDimY()-1);
		
	    System.out.println("--LannisterNeo Creado: " + vCampos.get(1) + "--\n");
	}
}

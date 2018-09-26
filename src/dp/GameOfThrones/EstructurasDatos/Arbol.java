package dp.GameOfThrones.EstructurasDatos;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Implementacion de arbol binario de busqueda.
 * @version 2.0
 * @author
 * Asignatura Desarrollo de Programas<br>
 * <b> Profesores DP </b></br>
 * Ampliacion realizada por Francisco Javier Rojo Martín
 * Curso 14/15
 */
public class Arbol<TipoDato extends Comparable<TipoDato>> {
	
	
	/** Dato almacenado en cada nodo del árbol. */
	private TipoDato datoRaiz;
	
	/** Atributo que indica si el árbol está vacío. */
	boolean esVacio;
	
	/** Hijo izquierdo del nodo actual */
	private Arbol<TipoDato> hIzq;
	
	/** Hijo derecho del nodo actual */
	private Arbol<TipoDato> hDer;
	
	/**
	 * Constructor por defecto de la clase. Inicializa un árbol vacío.
	 */
	public Arbol(){
	    this.esVacio=true;
	    this.hIzq = null;
	    this.hDer = null;
	}

	/**
	 * Crea un nuevo árbol a partir de los datos pasados por parámetro.
	 *
	 * @param hIzq El hijo izquierdo del árbol que se está creando 
	 * @param datoRaiz Raíz del árbol que se está creando
	 * @param hDer El hijo derecho del árbol que se está creando
	 */
	public Arbol (Arbol<TipoDato> hIzq, TipoDato datoRaiz, Arbol<TipoDato> hDer){
	        this.esVacio=false;
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer=hDer;
	}
	
	/**
	 * Devuelve el hijo izquierdo del árbol
	 *
	 * @return El hijo izquierdo
	 */
	public Arbol<TipoDato> getHijoIzq(){
		return hIzq;
	}
	
	/**
	 * Devuelve el hijo derecho del árbol
	 *
	 * @return Hijo derecho del árbol
	 */
	public Arbol<TipoDato> getHijoDer(){
		return hDer;
	}
	
	/**
	 * Devuelve la raíz del árbol
	 *
	 * @return La raíz del árbol
	 */
	public TipoDato getRaiz(){
		return datoRaiz;
	}
	
	/**
	 * Comprueba si el árbol está vacío.
	 * @return verdadero si el árbol está vacío, falso en caso contrario
	 */
	public boolean vacio(){
		return esVacio;
	}
	
	/**
	 * Inserta un nuevo dato en el árbol.
	 *
	 * @param dato El dato a insertar
	 * @return verdadero si el dato se ha insertado correctamente, falso en caso contrario
	 */
	public boolean insertar(TipoDato dato){
	    boolean resultado=true;
	    if (vacio()) {
	        datoRaiz = dato;
			esVacio = false;
		}
	    else {
	        if (!(this.datoRaiz.equals(dato))) {
	            Arbol<TipoDato> aux;
	            if (dato.compareTo(this.datoRaiz)<0) { //dato < datoRaiz //para que funcione esto tenemos que tener el compare to implementdao en la clase que contiene el arbol
	                if ((aux=getHijoIzq())==null)
	                    hIzq = aux = new Arbol<TipoDato>();
	            }
	            else {									//dato > datoRaiz
	                if ((aux=getHijoDer())==null)
	                    hDer = aux = new Arbol<TipoDato>();
	            }
	            resultado = aux.insertar(dato);
	        }
	        else
	            resultado=false;
	    }
	    return resultado;
	}
	
	/**
	 * Comprueba si un dato se encuentra almacenado en el árbol
	 *
	 * @param dato El dato a buscar
	 * @return verdadero si el dato se encuentra en el árbol, falso en caso contrario
	 */
	public boolean pertenece(TipoDato dato){
	    Arbol<TipoDato> aux=null;
	    boolean encontrado=false;
	    if (!vacio()) {
	        if (this.datoRaiz.equals(dato))
	            encontrado = true;
	        else {
	            if (dato.compareTo(this.datoRaiz)<0)	//dato < datoRaiz
	                aux=getHijoIzq();
	            else									//dato > datoRaiz
	                aux = getHijoDer();
	            if (aux!=null)
	                encontrado = aux.pertenece(dato);
	        }
	    }
	    return encontrado;
	}

	/**
	 * Comprueba si un dato se encuentra almacenado en una hoja del arbol
	 *
	 * @param dato El dato a buscar
	 * @return verdadero si el dato se encuentra en una hoja en el árbol, falso en caso contrario
	 */
	public boolean esHoja(TipoDato dato){
	    Arbol<TipoDato> aux=null;
	    boolean hoja=false;
	    if (!vacio()) {
	        if (this.datoRaiz.equals(dato)){
	        	if(hIzq == null && hDer == null){
	        		hoja=true;
	        	}
	        }
	        else {
	            if (dato.compareTo(this.datoRaiz)<0)	//dato < datoRaiz
	                aux=getHijoIzq();
	            else									//dato > datoRaiz
	                aux = getHijoDer();
	            if (aux!=null)
	                hoja= aux.esHoja(dato);
	        }
	    }
	    return hoja;
	}
	
	/**
	 * Borrar un dato del árbol.
	 *
	 * @param dato El dato que se quiere borrar
	 */
	public void borrar(TipoDato dato){
	    if (!vacio()) {
	        if (dato.compareTo(this.datoRaiz)<0){			//dato<datoRaiz
				if(hIzq!=null)	
					hIzq = hIzq.borrarOrden(dato);
			}	
	        else
	            if (dato.compareTo(this.datoRaiz)>0) {		//dato>datoRaiz 
	            	if (hDer!=null)	
	            		hDer = hDer.borrarOrden(dato);
				}
	            else //En este caso el dato es datoRaiz
	            {
	                if (hIzq==null && hDer==null)
	                {
	                    esVacio=true;
	                }
	                else
	                    borrarOrden(dato);
	            }
	    }
	}
	

	/**
	 * Borrar un dato. Este método es utilizado por el método borrar anterior.
	 *
	 * @param dato El dato a borrar
	 * @return Devuelve el árbol resultante después de haber realizado el borrado
	 */
	private Arbol<TipoDato> borrarOrden(TipoDato dato)
	{
		TipoDato datoaux;
	    Arbol<TipoDato> retorno=this;
	    Arbol<TipoDato> aborrar, candidato, antecesor;

	    if (!vacio()) {
	        if (dato.compareTo(this.datoRaiz)<0){		// dato<datoRaiz
		    	if(hIzq!=null)       
		    		hIzq = hIzq.borrarOrden(dato);
	        }
			else
	            if (dato.compareTo(this.datoRaiz)>0) {	// dato>datoRaiz
	    	        if(hDer!=null)   
	    	        	hDer = hDer.borrarOrden(dato);
	            }
				else {
	                aborrar=this;
	                if ((hDer==null)&&(hIzq==null)) { /*si es hoja*/
	                    retorno=null;
	                }
	                else {
	                    if (hDer==null) { /*Solo hijo izquierdo*/
	                        aborrar=hIzq;
	                        datoaux=this.datoRaiz;
	                        datoRaiz=hIzq.getRaiz();
	                        hIzq.datoRaiz = datoaux;
	                        hIzq=hIzq.getHijoIzq();
	                        hDer=aborrar.getHijoDer();

	                        retorno=this;
	                    }
	                    else
	                        if (hIzq==null) { /*Solo hijo derecho*/
	                            aborrar=hDer;
	                            datoaux=datoRaiz;
	                            datoRaiz=hDer.getRaiz();
	                            hDer.datoRaiz = datoaux;
	                            hDer=hDer.getHijoDer();
	                            hIzq=aborrar.getHijoIzq();

	                            retorno=this;
	                        }
	                        else { /* Tiene dos hijos */
	                            candidato = this.getHijoIzq();
	                            antecesor = this;
	                            while (candidato.getHijoDer()!=null) {
	                                antecesor = candidato;
	                                candidato = candidato.getHijoDer();
	                            }

	                            /*Intercambio de datos de candidato*/
	                            datoaux = datoRaiz;
	                            datoRaiz = candidato.getRaiz();
	                            candidato.datoRaiz=datoaux;
	                            aborrar = candidato;
	                            if (antecesor==this)
	                                hIzq=candidato.getHijoIzq();
	                            else
	                                antecesor.hDer=candidato.getHijoIzq();
	                        } //Eliminar solo ese nodo, no todo el subarbol
	                    aborrar.hIzq=null;
	                    aborrar.hDer=null;
	                }
	            }
	    }
	    return retorno;
	}
	
	
	/**
	 * Vaciar. Este metodo es usado para eliminar todos los datos del Arbol y dejarlo vacio
	 *
	 */
	public void vaciar(){
		while (!this.esVacio){
			TipoDato dato=this.datoRaiz;
			this.borrar(dato);
		}
	}
	
	
	/**
	 * Cuenta los nodos que tiene el arbol.Calcula recursivamente para cuantosNodos
	 *
	 *@param abb es el arbol sobre el que vamos a contar
	 * @return Devuelve un integer con el numero de nodos
	 */	
	private Integer cuantosNodosR(Arbol<TipoDato> abb){
		Integer cuantos=0;
		
		if(!this.vacio()){
			Arbol<TipoDato> izq= abb.getHijoIzq();
			Arbol<TipoDato> der= abb.getHijoDer();
			cuantos++;
			
			if(izq != null){
				cuantos= cuantos+ cuantosNodosR(izq);
			}
			if(der != null){
				cuantos= cuantos+ cuantosNodosR(der);
			}
		}
		
		return cuantos;
	}
	/**
	 * Cuenta los nodos que tiene el arbol.
	 *
	 * @return Devuelve un integer con el numero de nodos
	 */
	public Integer cuantosNodos(){
		return cuantosNodosR(this);
	}
	
	/**
	 * Profundidad del arbol. Este método es utilizado por el método profundidad.
	 *
	 * @return Devuelve un integer con la profundidad del arbol
	 */
	private Integer profundidadR (Arbol<TipoDato> abb){
		Integer prof=0, profder=0, profizq=0;
		
		if(!abb.vacio()){
			Arbol<TipoDato> izq= abb.getHijoIzq();
			Arbol<TipoDato> der= abb.getHijoDer();
			
			if(izq !=null){
				profizq= profundidadR(izq);
			}
			
			if(der!=null){
				profder= profundidadR(der);
			}
			
			if(profizq>profder){
				prof=1+profizq;
			}
			else{
				prof=1+profder;
			}
			
		}
		return prof;
	}
	
	/**
	 * Profundidad del arbol. Este método calcula la profundidad del arbol.
	 *
	 * @return Devuelve un integer con la profundidad del arbol
	 */
	public Integer profundidad (){
		return profundidadR(this);
	}
	
	/**
	 * Recorrido inOrden del árbol.
	 */
	public void inOrden(BufferedWriter bw) throws IOException{
	    Arbol<TipoDato> aux=null;
	    
	    if (!vacio()) {
	      
	    	if ((aux=getHijoIzq())!=null) {
	            aux.inOrden(bw);
	        }    
	    	
	        System.out.print(" ");
	        bw.write(" ");	      
	        System.out.print(this.datoRaiz);
	        bw.write(this.datoRaiz.toString());

	        
	        if ((aux=getHijoDer())!=null){
	            aux.inOrden(bw);
	        }    
	    
	    }
	
	}
	
	/**
	 * Recorrido inOrden del árbol.
	 */
	public void inOrden(){
	    Arbol<TipoDato> aux=null;
	    
	    if (!vacio()) {
	      
	    	if ((aux=getHijoIzq())!=null) {
	            aux.inOrden();
	        }    
	      
	        System.out.print(this.datoRaiz);
	        System.out.print(" ");
	        
	        if ((aux=getHijoDer())!=null){
	            aux.inOrden();
	        }    
	    
	    }
	
	}

	/**
	 * Recorrido preOrden del árbol.
	 */
	public void preOrden(){
	    Arbol<TipoDato> aux=null;
	    
	    if (!vacio()) {
	    	
	        System.out.print(this.datoRaiz);
	        System.out.print(" - ");
	    	
	    	if ((aux=getHijoIzq())!=null) {
	            aux.preOrden();
	        }    
	        
	        if ((aux=getHijoDer())!=null){
	            aux.preOrden();
	        }
	        
	    }
	    
	}
	
	/**
	 * Nodos internos del arbol. Este método calcula el numero de nodos internos del arbol.
	 *
	 * @return Devuelve un integer con el numero de nodos internos del arbol
	 */
	public Integer nodosInternos(){
		Integer cuantos=0;
		
		if(!vacio()){
			if(hIzq ==null && hDer == null)
				cuantos=0;
			else{
				cuantos++;
				if(hIzq!=null)
					cuantos=cuantos + hIzq.nodosInternos();
				if(hDer!=null)
					cuantos=cuantos + hDer.nodosInternos();
			}
		}
		return cuantos;
	}
	
	/**
	 * Nodos hoja del arbol. Este método calcula el numero de hojas del arbol.
	 *
	 * @return Devuelve un integer con el numero de hojas del arbol
	 */
	public Integer nodosHoja(){
		Integer cuantos=0;
		
		if(!vacio()){
			if(hIzq ==null && hDer == null)
				cuantos=1;
			else{
				if(hIzq!=null)
					cuantos=cuantos + hIzq.nodosHoja();
				if(hDer!=null)
					cuantos=cuantos + hDer.nodosHoja();
			}
		}
		return cuantos;
	}
	
	/**
	 * Recorrido postOrden del árbol.
	 */
	public void postOrden(){
	    Arbol<TipoDato> aux=null;
	    
	    if (!vacio()) {
	    	
	        if ((aux=getHijoIzq())!=null) {
	            aux.postOrden();
	        }    
	        
	        if ((aux=getHijoDer())!=null){
	            aux.postOrden();
	        }  
	        
	        System.out.print(this.datoRaiz);
	        System.out.print(" - ");
	        
	        
	    }
	}   

	
/*	/**
	 * Método que main que realiza las pruebas con el árbol.
	 * @param args Argumentos del main
	 */
/*	public static void main(String[] args){
	    Arbol arbol = new Arbol();
	    System.out.println("Ejemplos sesion árbol binario de búsqueda");

		Llave[] datos = {new Llave(20), new Llave(7), new Llave(18),
	    					   new Llave(6), new Llave(5), new Llave(1),
	    					   new Llave(22)};
	    
	    for (int i = 0; i < datos.length; i++) {
			arbol.insertar(datos[i]);
		}
	    
		// Insertando datos repetidos
	    if (arbol.insertar(new Llave(22))==false)
	        System.out.println("El ABB no admite elementos duplicados");

		// Pertenencia de un dato
	    if (arbol.pertenece(new Llave(22)))
	        System.out.println("Pertenece");
	    else
	        System.out.println("NO Pertenece");

	    // Recorrido en inOrden
		System.out.println("InOrden");
	    arbol.inOrden();
	    
	    //Probando cuantaNodos
	    System.out.println("Tenemos " + arbol.cuantosNodos() + " nodos");

	    //Probando el metodo esHoja
	    if (arbol.esHoja(new Llave(20)))//tiene dos hijos, asi que NO ES HOJA
	        System.out.println("Es HOJA");
	    else
	        System.out.println("NO es HOJA");
	    
	    if (arbol.esHoja(new Llave(18)))//debe dar que ES HOJA
	        System.out.println("Es HOJA");
	    else
	        System.out.println("NO es HOJA");
	    
	    if (arbol.esHoja(new Llave(6))) //solo tiene un hijo, pero NO ES HOJA
	        System.out.println("Es HOJA");
	    else
	        System.out.println("NO es HOJA");
	    
	    //Probando el calculo de la profundidad
	    System.out.println("La profundidad del arbol es " + arbol.profundidad());
	    
	    //Probando el calculo del numero de nodos internos (nodos que nos son hojas)
	    System.out.println("El numero de nodos internos es " + arbol.nodosInternos());
	    
	    //Probando el calculo del numero de hojas
	    System.out.println("El numero de hojas es " + arbol.nodosHoja());

	    //Probando el borrado de diferentes datos -- Descomentar estas líneas para ver qué ocurre
		arbol.borrar(new Llave(20));
		System.out.println("Borrado " + 20);
		arbol.borrar(new Llave(15));
		System.out.println("Borrado " + 15);
	    
	    //Probando el metodo que elimina todos los datos del arbol
		System.out.println("La profundidad del arbol es " + arbol.profundidad());	
		arbol.vaciar();
		arbol.inOrden();
	    System.out.println("La profundidad del arbol es " + arbol.profundidad());
	    
	    //Borrando datos del árbol
	    for (int i = 0; i < datos.length; i++) {
			arbol.borrar(datos[i]);
			System.out.println("Borrado " + datos[i]);
			arbol.inOrden();
		    System.out.println("La profundidad del arbol es " + arbol.profundidad());
		}


	}*/
}

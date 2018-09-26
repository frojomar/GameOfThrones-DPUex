package dp.GameOfThrones.Juego;

/**
 * EC4 - proyecto16_17
 * Implementacion de los metodos de la clase Llave del proyecto 2016-17.
 *
 * @version 1.0
 * @author
 * <b> Francisco Javier Rojo Martín</b><br>
 * Proyecto GameOfThrones-DPUex<br>
 * Curso 16/17
 */
public class Llave implements Comparable<Llave>{
	
	/** Identificador numerico de la Llave. */
	private Integer valor;
	
	/** Constructor por defecto de una llave. Crea una llave sin valor numerico */
	public Llave(){
		valor= null;
	}
	
	/**Constructor parametrizado de una llave. Crea una llave con el valor pasado por parametro
	 * 
	 * @param valor contiene el valor que se le va a asignar a la llave
	 */
	public Llave(Integer valor){
		this.valor= valor;
	}

	/** Metodo que devuelve el valor de la llave.
	 *
	 * @return Devuelve el valor de la llave
	 */
	public Integer getValor(){
		return valor;
	}
	
	/**Permite modificar el valor de la Llave
	 * @param valor valor de la llave
	 */
	public void setValor(Integer valor){
		this.valor= valor;
	}
	
	/**Metodo para imprimir por pantalla el valor de la llave*/
	
	public String toString(){
		return valor.toString();
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
    	if (!(obj instanceof Llave))
    		return false;
    	// Hacemos un casting... 
    	Llave lAux = (Llave) obj;
    	return (this.valor.equals(lAux.getValor())); 	//Una vez sabemos que son de la misma clase,
    													//comparamos segun los parametros que nosotros
    													//queremos comparar y devolveremos true si los 
    													//valores de todas las parejas de parametros son
    													//iguales y false si no lo son.
    }
    
	/** 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Llave l2) {
		if (this == l2) 
			return 0;
		return (this.valor.compareTo(l2.getValor()));}
	

}

	

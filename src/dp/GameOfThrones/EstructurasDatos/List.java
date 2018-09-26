package dp.GameOfThrones.EstructurasDatos;

import java.io.BufferedWriter;
import java.io.IOException;

/**
* Implementation of the method for the List class.
*
* @version 4.0
* @author
* <b> Profesores DP </b><br>
* Program Development<br/>
* Ampliacion realizada por Francisco Javier Rojo Martín
* 16/17 Course
*/
public class List <TipoDato extends Comparable<TipoDato>> {
	/** Reference to the first element in the list*/
	private Node first;
	
	/** Reference to the last element in the list*/
	private Node last;
	
	/** List size*/
	Integer size=0;
	
    public class Node {
    	/** Data stored in each node */
        private TipoDato Data;
    	/** Reference to the next node */
        private Node next;
    	/** Reference to the previous node */
        private Node prev;

        /**
    	 * Parametrized Constructor for the Node class
    	 */
        public Node(Node prev, TipoDato Data, Node next) {
            this.Data = Data;
            this.next = next;
            this.prev = prev;
        }
    	/**
    	 * Method that returns the next node in the list (for traversing the list)
    	 *
    	 * @return the next node in the list
    	 */
        public Node next() {
        		return next;
        }
    	/**
    	 * Method that returns the previous node in the list (for traversing the list)
    	 *
    	 * @return the previous node
    	 */
        public Node prev() {
        		return prev;
        }

    	/**
    	 * Method to obtain a data
    	 *
    	 * @return the data contained in the current node
    	 */        
        public TipoDato get() {
        		return Data;
        }
    }//class Node

		
	/**
	 * Default Constructor for the List class
	 */
	public List() {
		first = last = null;
		size = 0;
	}

	
	/**
	 * Parametrized method for the List class
	 *
	 * @param data the data that the List will store
	 */
	public List(TipoDato data) {
		addLast(data);
	}
	
	/**
	 * Method that returns the element that is stored at the beginning of the list
	 *
	 * @return the first element
	 */
	public TipoDato getFirst() {
		return first.Data;
	}

	/**
	 * Method that returns the data that is stored at the end of the list
	 *
	 * @return the last data
	 */
	public TipoDato getLast() {
		return last.Data;
	}
	/**
	 * Method that returns the first node 
	 *
	 * @return first node
	 */
	public Node first() {
		return first;
	}

	/**
	 * Method that returns the node at the end of the list
	 *
	 * @return last node
	 */
	public Node last() {
		return last;
	}
	/**
	 *  Method to check whether the list is empty
	 *
	 * @return true if the list is empty and otherwise false 
	 */
	public boolean estaVacia (){
		return (size == 0);
	}
	
	/**
	 * Method that returns the size of the list
	 *
	 * @return the size of the list
	 */
	public Integer size (){
		return size;
	}
	
	/**
	 * Method that returns the data contained in the position passed as parameter
	 * @param pos the position of the element to be returned 
	 * @return the data contained in the position passed as parameter
	 */
	public TipoDato get (Integer pos){
		TipoDato d = null; 
		Node iter=first;
		Integer i=0; 
		boolean encontrado = false;
		while(i<=pos && !encontrado && iter!= null) {
			if(i==pos) {
				encontrado = true;
				d=iter.Data;
			}
			i++;
			iter=iter.next;
		}
		return d;
	}
	
	
	/**
	 * Method to add a data by the end of the list
	 *
	 * @param Data value that is going to be added to the list
	 */
	public void addLast(TipoDato Data) {
        Node l = last;
        Node nodo = new Node(l, Data, null);
        last = nodo;
        if (l == null)
            first = nodo;
        else
            l.next = nodo;
        size++;
	}
	
	/**
	 * It removes the last data in the list
	 *
	 */
	public void removeLast() {
		if (last != null){
			last = last.prev();
		}	
		//there are not elements
		if (last == null) 	first = null;
		else last.next=null;
		size --;
	}

	// ************************************************************************************
	// ***** Additional exercises for the students ****************************************
 	// ************************************************************************************
	/**
	 * It removes the first element in the list
	 *
	 */
	public void removeFirst() {

		if (first != null){
			first = first.next();
		}	
		//there are not elements
		if (first == null) 	last = null;
		else first.prev=null;
		size --;
	}
	
	private void eliminarPosicion (Node l){
		(l.next).prev = l.prev;
		(l.prev).next = l.next;
		size--;
	}	
	
	/**
	 * It removes the data passed as parameter (if it is stored in the list)
	 *
	 * 
	 */
	public int removeDato(TipoDato dato) {
		Node l= first;
		int encontrado=-1;
		
		while (l !=null){ //busca por toda la lista, por si esta repetido
			if (l.Data == dato){
				encontrado=0;
				if(l == first){
					this.removeFirst();
					l=first;
				}
				else{
					if (l == last){
						this.removeLast();
						l= null;
					}
					else{
						this.eliminarPosicion(l);
						l=l.next;
					}
				}
			}
			else{
				l=l.next;
			}
		}
		return encontrado;
	}
	private void insertarDelanteEnElMedio(Node l, TipoDato Data){
		Node nodo= new Node(l.prev, Data, l);
		(l.prev).next=nodo;
		l.prev=nodo;
		size++;
	}
	/**
	* It adds an element to the list in a sorted way
	*
	*/
	public void sortedAdd(TipoDato Data) {
		if(!this.estaVacia()){
			Node l= first;
			int pos= 12345678 % (size+1);//size+1 para poder añadir al final tambien
			
			if(pos==0){
				this.addFirst(Data);
			}
			else{
				if(pos == size){
					this.addLast(Data);
				}
				else{
					while( pos>=0){
						if(pos==0){
							this.insertarDelanteEnElMedio(l, Data);
						}
						pos--;
						l=l.next;
					}
				}
			}
		}
		else{
			this.addFirst(Data);
		}
	}
	
	/**
	 * It adds an element to the list by the beginning
	 *
	 * @param Data valor que se va a insertar
	 */
	public void addFirst(TipoDato Data) {
		Node nodo= new Node(null,Data,first);
		first=nodo;
		if(first.next==null){
			last=first;
		}
		else{
			(first.next).prev= first;
		}
		size++;
	}

	
	/**
	 * Checks whether a data is contained in the list
	 *
	 */
	public boolean contains(TipoDato Data) {
		if(this.estaVacia())
			return false;
		else{	
			Node l;
			l=first;
			
			while (l != null){
				if (l.Data == Data)
					return true;
				l= l.next;
			}
			return false;
		}
   }
   
	/**
	 * It adds a data into the list before the value passed as parameter (searchedValue) 
	 *
	 * @param Data valor que se va a insertar
	 * @param searchedValue valor delante del cual se insertará el nuevo dato
	 */
	public void addBefore(TipoDato Data, TipoDato searchedValue ) {
		Node l;
		l=first;
		
		while (l != null){ //si estuviese vacía no se entraría
			if (l.Data == searchedValue){
				if (l==first){
					this.addFirst(Data);
				}
				else{
					this.insertarDelanteEnElMedio(l, Data);
				}
				l=null; //para salir
			}
			else{
				l= l.next;
			}	
		}
	}

	/**
	 * It adds a data into the list after the value passed as parameter (searchedValue) 
	 *
	 */
	public void addAfter(TipoDato Data, TipoDato searchedValue ) {
		Node l;
		l=first;
		
		while (l != null){ //si estuviese vacía no se entraría
			if (l.Data == searchedValue){
				if (l==last){
					this.addLast(Data);
				}
				else{
					this.insertarDelanteEnElMedio(l.next, Data);
				}
				l=null; //para salir
			}
			else{
				l= l.next;
			}	
		}
	}
	
	/**
	 * It adds a data into the list before the position passed as parameter (index) 
	 *
	 */
	//PRE: size>=index
	public void addBeforeIndex(TipoDato Data, int index) {
		Node l=first;
		
		if (index == 1){//si queremos añadir delante del primero
			this.addFirst(Data);
		}
		else{
			if (index == size){//si queremos añadir delante del ultimo dato
				l=last;
				this.insertarDelanteEnElMedio(l, Data);
			}
			else{
				if (index == (size+1)){//si queremos añadir al final, delante de la "posicion" que ocupa el null final
					this.addLast(Data);
				}
				else{
					while (index >=0 && first!=null){//si queremos en otra posicion cualquiera
						if (index==0){
							this.insertarDelanteEnElMedio(l, Data);
						}
						l=l.next;
						index--;
					}
				
				}
			}
		}
	}
   
	/**
	 * It adds a data into the list after the position passed as parameter (index)
	 *
	 */
	//PRE: size>=index
	public void addAfterIndex(TipoDato Data, int index ) {
		Node l=first;	
		
		if (index == 1){//si queremos añadir detras del primero
			this.insertarDelanteEnElMedio(first.next, Data);
		}
		else{
			if (index == size){//si queremos añadir detras del ultimo dato
				this.addLast(Data);
			}
			else{
				if (index == 0){//si queremos añadir al principio, detras de la "posicion" que ocupa el null inicial
					this.addFirst(Data);
				}
				else{
					while (index >=0 && first!=null){//si queremos en otra posicion cualquiera del medio
						if (index==0){
							this.insertarDelanteEnElMedio(l.next, Data);
						}
						l=l.next;
						index--;
					}
				
				}
			}
		}
	}


	/**
	 * It adds a data into the list in the position passed as parameter (index)
	 *
	 */
	//PRE: size>=index
	public void addIndex(TipoDato Data, int index ) {
		Node l=first;
		
		if (index == 1){
			this.addFirst(Data);
		}
		else{
			if (index== size){
				this.insertarDelanteEnElMedio(last, Data);
			}
			else{
				while (l!=null && index>=0){
					if(index ==0){
						this.insertarDelanteEnElMedio(l, Data);
					}
					index--;
					l=l.next;
				}
			}
		}
	}

	/**
	 * Permite eliminar el Data almacenado en una posición dada
	 *
	 * @param index posición del Data que se eliminará
	 * @return el dato que está al inicio de la lista
	 */
	public TipoDato removeIndex(int index) {
		Node l=first;
		
		if (index == 1){
			this.removeFirst();
			return this.getFirst();
		}
		else{
			if (index== size){
				this.removeLast();
				return this.getFirst();
			}
			else{
				while (l!=null && index>=0){
					if(index ==0){
						this.eliminarPosicion(l);
						return this.getFirst();
					}
					index--;
					l=l.next;
				}
			}
		}
		return this.getFirst();
	}

	
	/**
	* Change the value stored in the index position by the data passed as parameter
	*/
	public void set(int index, TipoDato Data) {
		Node l=first;
		
		if (index == 1){
			first.Data= Data;
		}
		else{
			if (index== size){
				last.Data= Data;
			}
			else{
				while (l!=null && index>=0){
					if(index ==0){
						l.Data= Data;
					}
					index--;
					l=l.next;
				}
			}
		}
	}

	/**
	 * Inserta el valor en el lugar que le corresponde ascendentemente en una lista ordenada
	 * ascendentemente con elementos repetidos
	 * 
	 * @param data dato a insertar
	 */
	public void insertarEnOrden(TipoDato data){
		Node l=first;
		boolean insertado=false;
		
		if (this.estaVacia()){
			this.addFirst(data);
		}
		else{
			while(l!=null && !insertado){
				if(l.Data.compareTo(data)!= -1){
					if(l==first)
						this.addFirst(data);
					else
						this.insertarDelanteEnElMedio(l, data);
					insertado=true;
				}
				l=l.next;
			}
			if(!insertado){
				this.addLast(data);
			}
		}
	}
	
	/**
	 * Muestra los datos de la lista
	 */
	public void mostrar(BufferedWriter bw) throws IOException{
		Node l=first;
		
		while(l!=null){
			System.out.print(l.Data+" ");
			bw.write(" "+l.Data);
			l=l.next;
		}
	}
	
	
 /**
  * Main method. It tests the list and shows the results 
  *
  * @param args An array of String that the main method receives as parameter
  */
	public static void main (String args[]) {
		Integer[] dataSet = {new Integer(2), new Integer(8), 
							new Integer(3), new Integer(1),
							new Integer(4), new Integer(5),
							new Integer(6), new Integer(7),
							new Integer(9), new Integer(0)};
		
		//Testing the addition by the end 
		List<Integer> list = new List<Integer>();
		for (int i = 0; i < dataSet.length; i++) {
			list.addLast(dataSet[i]);
		}
		
		//Showing the list
        List<Integer>.Node iteratorNode = list.first();
        while (iteratorNode!= null) {
        		System.out.print(iteratorNode.get() + " : ");
        		iteratorNode = iteratorNode.next();
        }
		System.out.println("\n--------------------------");

		//Showing the list
        for (int i=0;i<list.size();i++) {
    			System.out.print(list.get(i)+ " : ");
        }
		System.out.println("\n--------------------------");
        
		for (int i = 0; i < 5; i++)
			list.removeLast();
		
		//Showing the list
        for (int i=0;i<list.size();i++) {
    			System.out.print(list.get(i)+ " : ");
        }
		System.out.println("\n--------------------------");
	}
}




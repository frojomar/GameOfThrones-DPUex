package dp.GameOfThrones.EstructurasDatos;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Implementation of the method for the Queue class.
*
* @version 2.0
* @author
* <b> Francisco Javier Rojo Martin</b><br>
* Program Development<br/>
* 16/17 Course
 */
public class Queue<TipoDato> {

	/**Reference to the start of the queue*/
	private Node front;
	
	/**Reference to the final of the queue*/
	private Node end;
	
	private class Node{
    	/** Data stored in each node */
        private TipoDato data;
    	/** Reference to the next node */
        private Node next;
        
        /**
    	 * Parametrized Constructor for the Node class
    	 */
        Node (TipoDato data) {
            this.data = data;
            this.next = null;
        }
    }//class Node
	
	/**
	 * Default Constructor for the Stack class
	 */
	public Queue() {
		front = end = null;
	}

	/**
	 * Parametrized method for the List class
	 *
	 * @param data the data that the Stack will store
	 */
	public Queue(TipoDato data) {
		Node node = new Node(data);
		front= end =node;
	}
	
	/**
	 * Method that return true if the queue is empty
	 *
	 * @return the boolean that pointed out if the queue is empty or not
	 */
	public boolean estaVacia(){
		return (front == null);
	}
	
	/**
	 * Method that returns the data in the front of the queue
	 *
	 * @return the data in the front of the queue
	 */
	public TipoDato primero(){
		return front.data;
	}
	
	/**
	 * Method that add a new data in the end of the queue
	 *
	 */
	public void encolar(TipoDato data){
		Node nodo = new Node(data);
		
		if(!this.estaVacia()){
			end.next= nodo;
		}
		else{
			front= nodo;
		}
		end= nodo;
	}
	
	/**
	 * Method that delete the data of the front of the queue
	 *
	 */
	public void desencolar(){
		front= front.next;
	}
	
	/**
	 *Print the queue content in the console
	 */
	public void mostrar(){
		Queue<TipoDato> qaux=new Queue<TipoDato>();
		while(!this.estaVacia()){
			qaux.encolar(this.primero());
			System.out.print(this.primero()+" ");
			this.desencolar();
			
		}
		while(!qaux.estaVacia()){
			this.encolar(qaux.primero());
			qaux.desencolar();
		}
	}
	
	/**
	 *Print the queue content in the file
	 */
	public void mostrar(BufferedWriter bw) throws IOException{
		Queue<TipoDato> qaux=new Queue<TipoDato>();
		while(!this.estaVacia()){
			qaux.encolar(this.primero());
			bw.write(" "+this.primero());
			this.desencolar();
			
		}
		while(!qaux.estaVacia()){
			this.encolar(qaux.primero());
			qaux.desencolar();
		}
	}

	/**Count how many nodes there are
	 * 
	 * @return int that contains how many nodes there are
	 */
	public int size() {
		Queue<TipoDato> qaux=new Queue<TipoDato>();
		int cuantos=0;
		
		while(!this.estaVacia()){
			qaux.encolar(this.primero());
			cuantos=cuantos+1;
			this.desencolar();
			
		}
		while(!qaux.estaVacia()){
			this.encolar(qaux.primero());
			qaux.desencolar();
		}
		
		return cuantos;
	}
}

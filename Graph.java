import java.util.HashSet;
import java.util.LinkedList;

/**
 * Creates a Graph with nodes and edges
 * 
 * @author Tinli Yarrington
 * @date 21 April 2016
 * 
 */

public class Graph<V,E> {
	protected LinkedList<Node> nodes;
	protected LinkedList<Edge> edges;
	
	public Graph(){
		nodes = new LinkedList<Node>();
		edges = new LinkedList<Edge>();
	}
	
	public void addEdge(E data, Node head, Node tail){
		Edge edge = new Edge(data, head, tail);
		
		if(!this.edges.contains(edge) && head!=tail && this.nodes.contains(head) && this.nodes.contains(tail)){
			this.edges.add(edge);
			head.addEdge(edge);
			tail.addEdge(edge);
		}
	}
	
	public void addNode(E data){
		Node node = new Node(data);
		nodes.add(node);
	}
	
	public int numEdges(){
		return this.edges.size();
	}
	
	public int numNodes(){
		return this.nodes.size();
	}
	
	public boolean check(){ 
//		HashSet<Edge> checkingEdges = new HashSet<Edge>();
//		for(Edge e : this.edges){
//			if(checkingEdges.contains(e) || e.equalTo()){
//				return false;
//			}
//			else{
//				checkingEdges.add(e);
//			}
//		}
//		return true;
		
		for(Edge e : edges){
			LinkedList<Edge> runEdges = (LinkedList<Edge>) (this.edges.clone());
			runEdges.remove(e);
			for(Edge edge : runEdges){
				if(e.totalEqual(edge) || e.equalTo()){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Edge getEdge(int i){
		return this.edges.get(i);
	}
	
	public Edge getEdgeRef(E data, Node head, Node tail){
		Edge edge = new Edge(data, head, tail);
		if(contains(this.edges, edge)){
			return edge;
		}
		else{
			return null;
		}
	}
	
	public Node getNode(int i){
		return this.nodes.get(i);
	}
	
	public void print(){ 
		System.out.println("Nodes:");
		for(int x = 0; x < this.nodes.size(); x++){
			System.out.println("	" + this.nodes.get(x).getData());
		}
		
		System.out.println("Edges:");
		for(int x = 0; x < this.edges.size(); x++){
			System.out.println("	Data: " + this.edges.get(x).getData() + " Head: " + this.edges.get(x).getHeadData() + " Tail: " + this.edges.get(x).getTailData());
		}
	}
	
	public void removeEdge(Edge edge){
		this.edges.remove(edge);
	}
	
	public void removeEdge(E data, Node head, Node tail){
		Edge e = new Edge(data, head, tail);
		if(contains(this.edges, e)){
			removeEdge(e);
		}
	}
	
	public void removeNode(Node node){
		this.nodes.remove(node);
		for(Edge e : this.edges){
			if(e.getHead().equals(node)){
				e.resetHead();
			}
			else if(e.getTail().equals(node)){
				e.resetTail();
			}
		}
	}
	
	private class Node{ 
		private LinkedList<Edge> edges;
		private E data;
		
		private Node(E d){
			this.data = d;
			this.edges = new LinkedList<Edge>();
		}
		
		public E getData(){
			return data;
		}
		
		public void addEdge(Edge e){
			this.edges.add(e);
		}
	}
	
	private class Edge{ 
		private E data;
		private Node head;
		private Node tail;
		
		private Edge(E d, Node h, Node t){
			this.data = d;
			this.head = h;
			this.tail = t;
		}
		
		public E getData(){
			return this.data;
		}
		
		public Node getHead(){
			return this.head;
		}
		
		public Node getTail(){
			return this.tail;
		}
		
		public E getHeadData(){
			if(this.head == null){
				return (E) "null";
			}
			else{
				return this.head.getData();
			}
		}
		
		public E getTailData(){
			if(this.tail == null){
				return (E) "null";
			}
			else{
				return this.tail.getData();
			}
		}
		
		public void resetHead(){
			this.head = null;
		}
		
		public void resetTail(){
			this.tail = null;
		}
		
		public boolean equalTo(){
			if(this.head.getData().equals(this.tail.getData())){
				return true;
			}
			else{
				return false;
			}
		}
		
		public boolean totalEqual(Edge e){
			if(this.head.getData().equals(e.getHeadData()) && this.tail.getData().equals(e.getTailData()) && this.data.equals(e.getData())){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	public boolean contains(LinkedList<Edge> edges, Edge e){
		for(Edge edge : edges){
			if(edge.totalEqual(e)){
				return true;
			}
		}
		return false;
	}
}

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
//import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JComponent;


public class JGraph<V,E> extends JComponent {
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<DisplayNode> nodes;
	protected LinkedList<DisplayEdge> edges;
	protected Queue<DisplayNode> queue = new LinkedList<DisplayNode>();
	protected String dft;
	protected String bft;
	
	public JGraph(){
		super();
		nodes = new LinkedList<DisplayNode>();
		edges = new LinkedList<DisplayEdge>();
		dft = "";
		bft = "";
	}
	
	public void paintComponent(Graphics g){
		g.drawRect(0, 0, 499, 499);
		for(DisplayNode n : nodes){
			n.drawNode(g);
			g.setColor(Color.BLACK);
			g.setFont(new Font("default", Font.BOLD, 16));
			g.drawString(Integer.toString(n.getData()), n.getX()+n.diameter/2-5, n.getY()+n.diameter/2+5);
			repaint();
		}
		for(DisplayEdge e: edges){
			e.drawEdge(g);
			repaint();
		}
		g.drawString(dft, 10, 480);
		g.drawString(bft, 10, 480);
	}
	
	public void addEdge(int data, DisplayNode head, DisplayNode tail){
		DisplayEdge edge = new DisplayEdge(data, head, tail);
		//System.out.println("out here - " + edge);
		//System.out.println(edge + ": is not in list? " + !this.edges.contains(edge) + "     head and tail equal? " + !head.equals(tail) + "     head exists? " + this.nodes.contains(head) + "     tail exists? " + this.nodes.contains(tail));
		//System.out.println(edge + " does this work? " + (!this.edges.contains(edge) && (head!=tail) && this.nodes.contains(head) && this.nodes.contains(tail)));
		if(!this.edges.contains(edge) && (!head.equals(tail)) && this.nodes.contains(head) && this.nodes.contains(tail)){
			//System.out.println("in here - " + edge);
			edges.add(edge);
			head.addEdge(edge);
			tail.addEdge(edge);
			repaint();
		}
	}
	
	public void addNode(int data, Color c, Point p){
		DisplayNode node = new DisplayNode(data, c, p, false);
		nodes.add(node);
		repaint();
	}
	
	public int numEdges(){
		return this.edges.size();
	}
	
	public int numNodes(){
		return this.nodes.size();
	}
	
	public void check(){ 
		for(DisplayNode n : nodes){
			for(DisplayEdge e : (LinkedList<DisplayEdge>) n.edges){
				if(!edges.contains(e)){
					System.err.println("Unknown edge in node");
				}
			}
		}
		
		for(DisplayEdge e : edges){
			if(!nodes.contains(e.head) || !nodes.contains(e.tail)){
				System.err.println("Unknown node in edge");
			}
		}
	}
	
	public DisplayEdge getEdge(int i){
		return this.edges.get(i);
	}
	
	public DisplayNode getNode(int i){
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
	
	public void removeEdge(DisplayEdge edge){
		edge.head.removeEdgeRef(edge);
		edge.tail.removeEdgeRef(edge);
		this.edges.remove(edge);
	}
	
	public void removeEdge(DisplayNode head, DisplayNode tail){
		DisplayEdge edge = head.edgeTo(tail);
		if(edge != null){
			removeEdge(edge);
		}
		else{
			System.err.println("Null edge");
		}
	}
	
	public void removeNode(DisplayNode node){
		while(!node.edges.isEmpty()){
			//System.out.println("here");
			removeEdge((DisplayEdge) node.edges.getFirst());
		}
		this.nodes.remove(node);
	}
	
	public LinkedList<DisplayNode> getNodes(){
		return this.nodes;
	}
	
	public void resetNodes(){
		this.nodes = new LinkedList<DisplayNode>();
		repaint();
	}
	
	public void resetEdges(){
		this.edges = new LinkedList<DisplayEdge>();
		repaint();
	}
	
	public void autoHavelHakimi(LinkedList<DisplayNode> nodes){
		//System.out.println(nodes.toString());
		//Collections.sort(nodes, Collections.reverseOrder());
		//System.out.println(nodes.toString());
		if(nodes.peekFirst().data == nodes.peekFirst().edges.size()){
			return;
		}
		else{
			DisplayNode node = nodes.peekFirst();
			for(int index = 1; index <= node.data; index++){
				DisplayNode n = getNode(index);
				//if(!node.equals(n)){
				if(n.data > n.edges.size()){
					addEdge(0, node, n);
//					n.setData(-1);
//					node.setData(-1);
				}
				//}
			}
			nodes.removeFirst();
			nodes.addLast(node);
			autoHavelHakimi(nodes);
		}
	}
	
	public void degreeHH(DisplayNode node){
		if(node.data == node.edges.size()){
			return;
		}
		else{
			for(int index = 1; index <= node.data; index++){
				DisplayNode n = getNode(index);
				//if(!node.equals(n)){
				if(n.data > n.edges.size()){
					addEdge(0, node, n);
//					n.setData(-1);
//					node.setData(-1);
				}
				//}
			}
		}
	}
	
	public void breadthFirstTraversal(DisplayNode node){
		queue.add(node);
		node.color = Color.WHITE;
		while(!queue.isEmpty()){
			DisplayNode newNode = queue.poll();
			//System.out.print(newNode + " ");
			bft = bft + node + "  ";
			for(DisplayEdge e : newNode.edges){
				if(!queue.contains(e.tail)){
					queue.add(e.tail);
					//System.out.println("Tail: " + e.tail.data + " Color: " + e.tail.color.toString());
				}
				else if(!queue.contains(e.head)){
					queue.add(e.head);
					//System.out.println("Head: " + e.head.data + " Color: " + e.head.color.toString());
				}
				//System.out.println(queue);
			}
		}
	}
	
	public void depthFirstTraversal(DisplayNode node){
		node.marked = true;
		node.color = Color.WHITE;
		//System.out.print(node + " ");
		dft = dft + node + "  ";
		for(DisplayEdge e : node.edges){
			if(!e.tail.marked){
				depthFirstTraversal(e.tail);
			}
			else if(!e.head.marked){
				depthFirstTraversal(e.head);
			}
		}
	}
	
	public Dimension getMinimumSize() {
		return new Dimension(500, 500);
	}

	/**
	 *  The component will look best at this size
	 *  @returns The preferred dimension
	 */
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
}

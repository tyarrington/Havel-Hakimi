import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

public class DisplayNode  implements Comparable<DisplayNode>{
	protected int data;
	protected Color color;
	protected Point position;
	protected LinkedList<DisplayEdge> edges;
	protected final int diameter;
	protected boolean marked;
	
	public DisplayNode(int d, Color c, Point p, boolean m){
		this.data = d;
		this.color = c;
		this.position = p;
		this.diameter = 50;
		this.edges = new LinkedList<DisplayEdge>();
		this.marked = false;
	}
	
	public void drawNode(Graphics g){
		g.setColor(this.color);
		g.fillOval(getX(), getY(), this.diameter, this.diameter);
	}
	
	public int getData(){
		return this.data;
	}
	
	public void setData(int diff){
		if(data >= 0){
			data+=diff;
		}
	}
	
	public DisplayEdge edgeTo(DisplayNode neighbor){
		DisplayEdge result = null;
		for(DisplayEdge edge: this.edges){
			if(edge.oppositeTo(this) == neighbor){
				result = edge;
			}
		}
		return result;
	}
	
	public void addEdge(DisplayEdge e){
		this.edges.add(e);
	}
	
	public void removeEdgeRef(DisplayEdge edge){
		this.edges.remove(edge);
	}
	
	public int getX(){
		return (int) this.position.getX();
	}
	
	public int getY(){
		return (int) this.position.getY();
	}
	
	public int compareTo(DisplayNode node){
		if(this.data > node.data){
			return 1;
		}
		else if(this.data == node.data){
			return 0;
		}
		else{
			return -1;
		}
	}
	
	public boolean equals(DisplayNode node){
		if(node.data != this.data || node.position.getX() != this.position.getX() || node.position.getY() != this.position.getY() || node.color != this.color){
			return false;
		}
		else{
			return true;
		}
	}
	
	public String toString(){
		return "Node[" + this.data + "]";
	}
}

import java.awt.Graphics;

public class DisplayEdge {
	protected int data;
	protected DisplayNode head;
	protected DisplayNode tail;
	private final int diameter = 50;
	
	public DisplayEdge(int d, DisplayNode h, DisplayNode t){
		this.data = d;
		this.head = h;
		this.tail = t;
	}
	
	public int getData(){
		return this.data;
	}
	
	public void drawEdge(Graphics g){
		g.drawLine(this.head.getX()+diameter/2, this.head.getY()+diameter/2, this.tail.getX()+diameter/2, this.tail.getY()+diameter/2);
	}
	
	public DisplayNode getHead(){
		return this.head;
	}
	
	public DisplayNode getTail(){
		return this.tail;
	}
	
	public int getHeadData(){
		if(this.head == null){
			return -1;
		}
		else{
			return this.head.getData();
		}
	}
	
	public DisplayNode oppositeTo(DisplayNode node){
		if(node == this.head){
			return this.tail;
		}
		else if(node == this.tail){
			return this.head;
		}
		else{
			System.err.println("Bad opposite");
			return null;
		}
	}
	
	public int getTailData(){
		if(this.tail == null){
			return -1;
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
	
	public boolean equals(Object o){
		boolean result = false;
		if(getClass() == o.getClass()){
			DisplayEdge edge = (DisplayEdge) o;
			result = ((this.head == edge.head && this.tail == edge.tail) || (this.tail == edge.head && this.head == edge.tail));
		}
		return result;
	}
	
	public int hashCode(){
		return this.head.hashCode()*this.tail.hashCode();
	}
	
	public String toString(){
		return "Edge from " + this.head + " to " + this.tail;
	}
}

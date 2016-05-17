import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class GraphGUI extends JApplet{
	
	private int buttonClicked;
	private final int diameter = 50;
	private String nodeAction;
	private int nodeClick;
	private int trackNodes;
	private DisplayNode nodeClicked;
	private Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.LIGHT_GRAY, Color.PINK, Color.MAGENTA, Color.GRAY, Color.CYAN};
	
	private static final long serialVersionUID = 1L;
	
	private JGraph<DisplayNode, DisplayEdge> graph;
	
	public GraphGUI(){
		this.graph = new JGraph<DisplayNode, DisplayEdge>();
		this.buttonClicked = 0;
		this.nodeAction = "";
		this.nodeClicked = null;
		this.nodeClick = 0;
		this.trackNodes = 0;
	}
	
	public void createAndShowGUI() {
        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        JFrame frame = new JFrame("Sample GUI Application");
        try { 
        		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {}

        // Add components
        createComponents(frame.getContentPane());

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	public void createComponents(Container pane) {
		pane.setLayout(new FlowLayout());
		pane.add(graph);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		
		JButton autoHH = new JButton("Havel-Hakimi(auto)");
		pane.add(autoHH);
		panel.add(autoHH, BorderLayout.NORTH);
		autoHH.addActionListener(new autoHavelHakimi());
		
		JButton automaticHH = new JButton("Automatic graph");
		pane.add(automaticHH);
		panel.add(automaticHH, BorderLayout.NORTH);
		automaticHH.addActionListener(new automaticHavelHakimi());
		JButton degreeHH = new JButton("Click to activate edges");
		pane.add(degreeHH);
		panel.add(degreeHH, BorderLayout.NORTH);
		degreeHH.addActionListener(new clickHavelHakimi());
		
		JButton userHH = new JButton("Havel-Hakimi (user)");
		pane.add(userHH);
		panel.add(userHH, BorderLayout.SOUTH);
		userHH.addActionListener(new userHavelHakimi());
		
		JButton BFT = new JButton("Breadth-first traversal");
		pane.add(BFT);
		panel.add(BFT, BorderLayout.WEST);
		BFT.addActionListener(new breadthFirstTraversal());
		
		JButton DFT = new JButton("Depth-first traversal");
		pane.add(DFT);
		panel.add(DFT, BorderLayout.EAST);
		DFT.addActionListener(new depthFirstTraversal());
		
		pane.add(panel);
		this.graph.addMouseListener(new SampleMouseListener());
		
	}

	/** This is the entry point for the applet version */
	public void init() {
			//Execute a job on the event-dispatching thread:
			//creating this applet's GUI.
			try {
				javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						// line below would create separate window
						//gui.createAndShowGUI();
	
						// this line creates applet in browser window
						createComponents(getContentPane());
					}
				});
			} catch (Exception e) {
				System.err.println("createGUI didn't successfully complete");
			}
	}
	
	/** This is the entry point for the application version */
	public static void main(String[] args) {
	    final GraphGUI gui = new GraphGUI();
	    
	    // Schedule a job for the event-dispatching thread:
	    // creating and showing this application's GUI.
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    		public void run() {
	    			gui.createAndShowGUI();
	    		}
	    });
	}
	
	public boolean contained(int x, int y){
		LinkedList<DisplayNode> nodes = graph.nodes;
		for(DisplayNode n : nodes){
			int nodeX = n.getX();
			int nodeY = n.getY();
			if(x > nodeX && x < nodeX+diameter && y > nodeY && y < nodeY+diameter){
				nodeClicked = n;
				return true;
			}
		}
		return false;
	}
	
    private class autoHavelHakimi implements ActionListener {

        /**
         *  @param e  Holds information about the button-push event
         */
        public void actionPerformed(ActionEvent e) {
        	trackNodes = 0;
        	graph.dft = "";
    		graph.bft = "";
        	buttonClicked = 1;
        	graph.resetNodes();
    		graph.resetEdges();
    		Random rand = new Random();
    		int whichGraph = rand.nextInt(10);
    		
    		if(whichGraph >= 5){
	    		graph.addNode(4, Color.RED, new Point(250,100));
	    		graph.addNode(4, Color.ORANGE, new Point(350,200));
	    		graph.addNode(3, Color.YELLOW, new Point(300,350));
	    		graph.addNode(2, Color.GREEN, new Point(175,350));
	    		graph.addNode(3, Color.BLUE, new Point(125,200));
    		}
    		else{
	    		graph.addNode(3, Color.YELLOW, new Point(125, 150));
	    		graph.addNode(5, Color.PINK, new Point(225, 150));
	    		graph.addNode(3, Color.BLUE, new Point(325, 150));
	    		graph.addNode(4, Color.CYAN, new Point(125, 350));
	    		graph.addNode(4, Color.GRAY, new Point(225, 300));
	    		graph.addNode(5, Color.ORANGE, new Point(325, 350));
    		}
        }
    }
    
    private class automaticHavelHakimi implements ActionListener {

        /**
         *  @param e  Holds information about the button-push event
         */
        public void actionPerformed(ActionEvent e) {
        	graph.dft = "";
    		graph.bft = "";
        	if(buttonClicked == 1){
        		Collections.sort(graph.nodes, Collections.reverseOrder());
        		graph.autoHavelHakimi(graph.nodes);
        	}
    		
        }
    }
    
    private class clickHavelHakimi implements ActionListener {

        /**
         *  @param e  Holds information about the button-push event
         */
        public void actionPerformed(ActionEvent e) {
        	graph.dft = "";
    		graph.bft = "";
        	if(buttonClicked == 1){
        		if(trackNodes < graph.numNodes()){
	        		Collections.sort(graph.nodes, Collections.reverseOrder());
	        		//graph.print();
	        		graph.degreeHH(graph.getNode(trackNodes));
	        		trackNodes++;
        		}
        		else{
        			trackNodes = 0;
        		}
        	}
    		
        }
    }

    private class userHavelHakimi implements ActionListener {

        /**
         *  @param e  Holds information about the button-push event
         */
        public void actionPerformed(ActionEvent e) {
        	graph.dft = "";
    		graph.bft = "";
            buttonClicked = 2;
            graph.resetNodes();
            graph.resetEdges();
        }
    }
    
    private class breadthFirstTraversal implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           buttonClicked = 3;
           graph.dft = "";
           graph.bft = "Coming Soon! :)";
        }
    }
    
    private class depthFirstTraversal implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            buttonClicked = 4;
            graph.bft = "";
        }
    }
	/** Mouse event handlers */
    private class SampleMouseListener extends MouseAdapter {
        	
        /** Click event handler prints a message with the event location */
        public void mouseClicked(MouseEvent e) {
        	if(buttonClicked == 1){
        		//graph.autoHavelHakimi(graph.nodes);
        		if(trackNodes < graph.numNodes()){
	        		Collections.sort(graph.nodes, Collections.reverseOrder());
	        		graph.degreeHH(graph.getNode(trackNodes));
	        		trackNodes++;
        		}
        		else{
        			trackNodes = 0;
        		}
        	}
        	else if(buttonClicked == 2){
        		if(nodeAction.equals("node") && nodeClick >= 2){
        			graph.removeNode(nodeClicked);
        			nodeAction = "";
        			nodeClick = 0;
        			repaint();
        		}
        		else if(contained(e.getX(), e.getY())){
        			nodeAction = "node";
        			nodeClick += 1;
        			//System.out.println(nodeClick);
        		}
   		
        		else{
        			Random rand = new Random();
        			int data = rand.nextInt(10) + 1;
        			graph.addNode(data, colors[data-1], new Point(e.getX()-diameter/2, e.getY()-diameter/2));
        			nodeClick = 0;
        		}
        	}
        	else if(buttonClicked == 3){
        		int x = e.getX();
        		int y = e.getY();
        		if(contained(x,y)){
        			//graph.breadthFirstTraversal(nodeClicked);
        		}
        	}
        	else if(buttonClicked == 4){
        		int x = e.getX();
        		int y = e.getY();
        		if(contained(x,y)){
        			graph.depthFirstTraversal(nodeClicked);
        		}
        		
        	}
        }

        /** Press event handler prints a message with the event location */
        public void mousePressed(MouseEvent e) {
        	if(buttonClicked == 2){
        		if(contained(e.getX(), e.getY())){
        			nodeAction = "node";
        			nodeClick += 1;
        			//System.out.println(nodeClick);
        		}
        	}
        	
        }

        /** Release event handler prints a message with the event location */
        public void mouseReleased(MouseEvent e) {
        	DisplayNode node = nodeClicked;
        	if(nodeAction.equals("node") && contained(e.getX(), e.getY())){
        		if(node.edgeTo(nodeClicked) != null){
        			graph.removeEdge(node, nodeClicked);
        			node.setData(1);
        			nodeClicked.setData(1);
        			nodeAction = "";
            		repaint();
        		}
        		else if(node.getData() != 0 && nodeClicked.getData() != 0 && node != nodeClicked){
        			if(!graph.edges.contains(new DisplayEdge(nodeClicked.getData() - node.getData(), node, nodeClicked))){
        				graph.addEdge(nodeClicked.getData() - node.getData(), node, nodeClicked);
        				node.setData(-1);
	        			nodeClicked.setData(-1);
        			}
        			//System.out.println(node + "----->" + nodeClicked);
        			nodeAction = "";
            		repaint();
        		}
        	}
        }

        public void mouseDragged(MouseEvent e){
        	
        }
    }
}

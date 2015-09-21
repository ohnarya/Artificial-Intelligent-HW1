package AI_923009191;

import java.util.*;

public class Graph {
	int totalVertice;
	int totalEdges;
	int iter        = 0;
	int maxfrontier = 0;
	int depth       = 0;
	String type;
	
	HashMap<Integer, int[]> vertices;
	HashMap<Integer, int[]> edges;	
	
	int [][]  matrix;
	boolean[] explored = {false};
	int       goal;
	
	List<Integer> successors;
	
	Graph(String type){
		vertices  = new HashMap<Integer, int[]>();
		edges     = new HashMap<Integer, int[]>();
		this.type = type;
	}
	public Node GBFS(int sx, int sy, int gx, int gy){
		
		System.out.format ("Vertices=%d, Edges = %d\n", this.totalVertice, this.totalEdges );

		if(!onCoordinateSystem(sx,sy))
			return null;
		
		if(!onCoordinateSystem(gx,gy))
			return null;
		
		CompareDistance cd = new CompareDistance();		
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(10,cd);
	
		int svid = getVID(sx, sy);
		goal     = getVID(gx, gy);
	   
		System.out.format("start= %d (%d,%d)\n", svid,sx,sy);
		System.out.format("goal = %d (%d,%d)\n", goal,gx,gy);
		
		Node n = new Node(svid, null, 0, sx,sy);
		explored[svid]=true;
		n.heur = getHeur(n.x,n.y, gx,gy);
		
		frontier.add(n);
		while(!frontier.isEmpty()){
			n = frontier.poll();
			
	    	System.out.format("iter=%d, frontier=%d, popped=%d (%d,%d) , depth= %d, dist2goal=%.2f\n",
			          (++iter),       frontier.size(),    n.vid,n.x,n.y, n.depth, n.heur);
	
			if(n.vid == goal){
				System.out.println("Goal Found.");
				depth = n.depth;
				return n;
			}
			ArrayList<Node> s = n.getSuccessor(this , n);
			
		    for(Node nn : s){
		    	explored[nn.vid] = true;
		    	if(frontier.contains(nn))
		    		continue;
		    	nn.heur = getHeur(nn.x,nn.y, gx,gy);
		    	frontier.add(nn);
		    	System.out.format("pushed %d (%d,%d)\n", nn.vid , vertices.get(nn.vid)[0],vertices.get(nn.vid)[1]);
		    }	  
		    maxfrontier = maxfrontier > frontier.size() ? maxfrontier :frontier.size();
		    			
		}		
		return null;
	}
	/* DFS */
	public Node DFS(int sx, int sy, int gx, int gy){
		
		System.out.format ("Vertices=%d, Edges = %d\n", this.totalVertice, this.totalEdges );
		
		if(!onCoordinateSystem(sx,sy))
			return null;
		
		if(!onCoordinateSystem(gx,gy))
			return null;
		
		/* frontier - Stack */
		Stack<Node> frontier = new Stack<Node>();
		
		int svid = getVID(sx, sy);
		goal     = getVID(gx, gy);
	    
		System.out.format("start= %d (%d,%d)\n", svid,sx,sy);
		System.out.format("goal = %d (%d,%d)\n", goal,gx,gy);
		
		Node n = new Node(svid, null, 0, sx,sy);
		explored[svid]=true;
		
		frontier.push(n);
		
		while(!frontier.isEmpty()){
			n = frontier.pop();
			n.heur = getHeur(n.x,n.y, gx,gy);
	    	System.out.format("iter=%d, frontier=%d, popped=%d (%d,%d) , depth= %d, dist2goal=%.2f\n",
			          (++iter),       frontier.size(),    n.vid,n.x,n.y, n.depth, n.heur);
	
			if(n.vid == goal){
				System.out.println("Goal Found.");
				depth = n.depth;
				return n;
			}
			ArrayList<Node> s = n.getSuccessor(this , n);
			
		    for(Node nn : s){
		    	explored[nn.vid] = true;
		    	if(frontier.contains(nn))
		    		continue;
		    	frontier.push(nn);
		    	System.out.format("pushed %d (%d,%d)\n", nn.vid , vertices.get(nn.vid)[0],vertices.get(nn.vid)[1]);
		    }	  
		    maxfrontier = maxfrontier > frontier.size() ? maxfrontier :frontier.size();
		    			
		}
		
		return null;
	}
	
	/* BFS */
	public Node BFS(int sx, int sy, int gx, int gy){
		
		System.out.format("Vertices=%d, Edges = %d\n", this.totalVertice, this.totalEdges );
		if(!onCoordinateSystem(sx,sy))
			return null;
		
		if(!onCoordinateSystem(gx,gy))
			return null;
		
		/* frontier - Queue */
		Queue<Node> frontier = new LinkedList<Node>();
		
		int svid = getVID(sx, sy);
		goal     = getVID(gx, gy);
	    
		System.out.format("start= %d (%d,%d)\n", svid,sx,sy);
		System.out.format("goal = %d (%d,%d)\n", goal,gx,gy);
	    
		Node n = new Node(svid,null,0,sx,sy);
		
	    explored[svid]=true;
	    
	    frontier.add(n);
	    
	    while(!frontier.isEmpty()){
	    	n = frontier.remove();
	    	n.heur = getHeur(n.x,n.y, gx,gy);
	    	System.out.format("iter=%d, frontier=%d, popped=%d(%d,%d), depth= %d, dist2goal=%.2f\n",
	    			          (++iter), frontier.size(),    n.vid,n.x,n.y,     n.depth, n.heur);
	    	
	    	if(n.vid == goal){
	    		System.out.println("Goal Found.");
	    		depth = n.depth;
	    		return n;
	    	}
	    	ArrayList<Node> s = n.getSuccessor(this,n);
		    
		    for(Node nn : s){
		    	explored[nn.vid] = true;
		    	if(frontier.contains(nn))
		    		continue;
		    	frontier.add(nn);
		    	System.out.format("pushed %d (%d,%d)\n", nn.vid , vertices.get(nn.vid)[0],vertices.get(nn.vid)[1]);
		    }	  
		    maxfrontier = maxfrontier > frontier.size() ? maxfrontier :frontier.size();
		    
	    }
	    return null;
	}
	
	/* GBFS */
	public boolean onCoordinateSystem(int x, int y){
		boolean ret = true;

		if(x>this.totalVertice||y>this.totalVertice){	
			ret =  false;
		}
		if ( getVID(x,y) < 0 ){
			ret =  false;
		}
		if( !ret ){
			System.out.format("(%d,%d) is not on the cordination\n",x,y);
		}
		return ret;
	}
	
	/* getHeur */
	public double getHeur(int x, int y, int gx, int gy){
		return Math.sqrt(Math.pow((gx - x), 2)
			         	+ Math.pow((gy - y), 2));
		
	}
	public int getVID(int sx, int sy){
		for (Map.Entry<Integer,int[]> entry : vertices.entrySet()) {

			  if (    entry.getValue()[0] == sx
				   && entry.getValue()[1] == sy) {
			    return entry.getKey();
			  }
		}
		return -1;
	}
	
	/* getEID */
	public int getEID(int v1, int v2){
		for (Map.Entry<Integer,int[]> entry : edges.entrySet()) {

			  if (    entry.getValue()[0] == v1
				   && entry.getValue()[1] == v2) {
			    return entry.getKey();
			  }
		}
		return -1;
	}

	/* createMatrix */
	public void createMatrix(){
		matrix = new int[totalVertice][totalVertice];
		for(int i = 0;i<totalVertice;i++){
			Arrays.fill(matrix[i],-1);
		}
		explored = new boolean[totalVertice];
	}
	
	/* countVisited Vertices */
	public int countvisited(){
		int i=0;
		for(boolean b : explored){
			if(b)
				i++;
			
		}
		return i;
	}
	
	/* print Summary */
	public void complete(){
		System.out.println("Search algorithm = " + type);
		System.out.println("Total iteration = " + iter);
		System.out.println("Max frontier size = " + maxfrontier);
		System.out.println("vertices visited = " + countvisited()+"/"+this.totalVertice);
		System.out.println("path length = " + depth);
		System.out.println("==========================");
	}
}

class CompareDistance implements Comparator<Node>{
	public int compare(Node n1, Node n2){
		if(n1.heur < n2.heur )
			return -1;
		else if(n1.heur == n2.heur)
			return 0;
		else
			return 1;
	}
}

/*
 * Name   : Search.java
 * Desc   : take a graph from arguments and find a goal
 * Author : Jiyoung Hwang (UIN : 923009191)
 */

package AI_923009191;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Search {

	public static void main(String args[]){
		/* Check input format */
		if (args.length < 6 ){
			System.out.println(" Input example : ATM.graph 1 1 4 4 B(D or G)");
			return;
		}else if(    !args[1].matches("\\d+")
				  || !args[2].matches("\\d+")
				  || !args[3].matches("\\d+")
				  || !args[4].matches("\\d+")
			    ){
			System.out.println(" Input example : ATM.graph 1 1 4 4 B(D or G)");
			return;
		}
		/* Set Search Engine "B" => BFS "D" => DFS "G" => GBFS */
		Graph g = new Graph(args[5]);
		
		
		/* Read A ATM.graph*/
		try(BufferedReader br = new BufferedReader(new FileReader(args[0])))
		{
			String sCurLine;
			String[] numbers;
			
			boolean isVertex = true;
			while((sCurLine = br.readLine()) != null){
				/* get Total Vertex number */
				if(sCurLine.matches("^[v|V].*$")){
					isVertex = true;					
					g.totalVertice = Integer.parseInt(sCurLine.replaceAll("\\D+", ""));
					g.createMatrix();
				/* get Total Edge Number */
				}else if(sCurLine.matches("^[e|E].*$")){
					isVertex = false;
					g.totalEdges = Integer.parseInt(sCurLine.replaceAll("\\D+", ""));
				}else if(isVertex){
					/* get each Vertex info */
					numbers = sCurLine.split("\\s+");
					
					int[] v = new int[2];
					v[0]    = Integer.parseInt(numbers[1]);
					v[1]    = Integer.parseInt(numbers[2]);
					int vid = Integer.parseInt(numbers[0]);
					
					g.vertices.put(vid, v);
					
				}else if(!isVertex){
					/* get each edge info */
					numbers = sCurLine.split("\\s+");
					int[] e = new int[2];
					e[0]    = Integer.parseInt(numbers[1]);
					e[1]    = Integer.parseInt(numbers[2]);
					int eid = Integer.parseInt(numbers[0]);
					
					g.edges.put(eid, e);
					
					/*build matrix*/
					g.matrix[e[0]][e[1]] = eid;
					g.matrix[e[1]][e[0]] = eid;
				}
			}
		}catch(IOException e){
			System.out.println("Cannot Open the input file. \nput an input file in the same directory of jar file or /src/");
			return;
		}
		int sx = Integer.parseInt(args[1]);
		int sy = Integer.parseInt(args[2]);
		int gx = Integer.parseInt(args[3]);
		int gy = Integer.parseInt(args[4]);
		
		Node n = null;
		switch(args[5]){
		case "B" :
			System.out.println("===========+BFS+=============");
			n = g.BFS(sx,sy,gx,gy);
			break;
		case "D" :
			System.out.println("===========+DFS+=============");
			n = g.DFS(sx,sy,gx,gy);

			break;
		case "G" :
			System.out.println("===========+GFBS+=============");
			n = g.GBFS(sx,sy,gx,gy);			

			break;
		default:
			System.out.println(" Input example : ATM.graph 1 1 4 4 B(D or G)");
			break;
		}
		
		if(n!= null){
			n.traceback();
			g.complete();
		}else{
			System.out.println("Goal is NOT found");
		}
		for(int i=0;i<g.matrix[39].length;i++)
			if(g.matrix[39][i]>0)
				System.out.println("matrix " + i + " : " + g.matrix[39][i] );
		
	}
}
class Node
{
	int vid;
	int x;
	int y;
	
    Node parent;
    int depth;
    
    double heur;
    Node(int vid) 
    {
    	this.vid = vid;
    } 
    Node(int vid, Node p) 
    {
    	this.vid    = vid;
    	this.parent = p;
    } 
    Node(int vid, Node p, int d, int x, int y) 
    {
    	this.vid    = vid;
    	this.parent = p;
    	this.depth  = d;
    	this.x = x;
    	this.y = y;
    }    
    Node(int vid, Node p, int d) 
    {
    	this.vid    = vid;
    	this.parent = p;
    	this.depth  = d;
    }
    
	ArrayList<Node> getSuccessor(Graph g, Node p){		
		ArrayList<Node> s = new ArrayList<Node>();
		
		for(int i = 0; i< g.matrix[p.vid].length;i++ ){
			
			if(g.matrix[p.vid][i]>=0){				
				if(g.explored.containsKey(i))
					continue;
				if(g.vertices.containsKey(i)){
					int[] vpair = g.vertices.get(i);
					s.add(new Node(i,p,p.depth+1, vpair[0], vpair[1]));
				}else{
					s.add(new Node(i,p,p.depth+1));
				}
			}
		}

		return s; 		
	}
	
    public void traceback() 
    {
    	System.out.println("==========================");

    	Node n = this;
    	while(n != null){
	   		System.out.format("Vertex %d (%d,%d)\n", n.vid, n.x, n.y);
	    	n = n.parent;
    	}
    	
    	System.out.println("==========================");
    	
    }
    public double getHeur(){
    	return this.heur;
    }
    
    public boolean equals(Object o){
    	if((o instanceof Node ) && (((Node)o).vid == this.vid ))
    		return true;
    	else 
    		return false;
    	
    }
    
}	





import java.util.List;
import java.util.ArrayList;

public class Node implements Comparable{
	int id;    /* increase by 1 */
	List<List<Integer>> state; /* state of the node */
	float h; /*h(n) */
    float g; /*g(n) */
    
    List<Node> getSuccessor(){
    	
    	return null;
    }
    
    public void setH(){
    	
    }
    
    public void setG(){
    	
    }
    public float getH(){
    	return this.h;
    }
    public float getG(){
    	return this.g;
    }
    public boolean equals(Object o){
    	if((o instanceof Node ) && (((Node)o).id == this.id ))
    		return true;
    	else 
    		return false;
    	
    }
    
    public int compareTo(Person per) {


    		return 1;
//		if ((Node)o. + (Node)o.getH() > this.g + this.h)
//			return -1;
//		else if ( (Node)o.getG() + (Node)o.getH() == this.g + this.h)
//			return 0;
//		else 
//			return 1;

    }

}

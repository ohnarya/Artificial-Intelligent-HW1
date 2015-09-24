import java.util.*;

public class AstarSearch {
	public static void main(String args[]){
	
		BlockWorld b = new BlockWorld();
	
		List<List<Integer>> ll = b.generateProblem(5,3);
		b.printProblem();
	}
}

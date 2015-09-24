import java.util.List;
import java.util.ArrayList;
//import java.util.Random;

public class BlockWorld {

	List<List<Integer>> problem = new ArrayList<List<Integer>>();
	public List<List<Integer>> generateProblem(int blocks, int stacks)
	{
//		Random r = new Random();
//		
//		int j = 0;
//		while(j<3){
//			problem.add(j, new ArrayList<Integer>());
//			j++;
//		}
//		while(blocks>0){
//			int i = r.nextInt(stacks);
//			List<Integer> aStack  = new ArrayList<Integer>();
//			aStack = problem.get(i);
//			aStack.add(blocks);
//			problem.remove(i);
//			problem.add(i,aStack);
//			blocks--;
//		}

		
		List<Integer> s1 = new ArrayList<Integer>();
		s1.add(4);
		List<Integer> s2 = new ArrayList<Integer>();
		s2.add(3);
		s2.add(1);
		List<Integer> s3 = new ArrayList<Integer>();
		s3.add(2);
		s3.add(5);
		problem.add(s1);
		problem.add(s2);
		problem.add(s3);
		return problem;
	}
	public void printProblem(){
		for(int i=0;i<problem.size();i++){
			System.out.format("%d | %s \n", i,problem.get(i) );
		}
	}
	
}

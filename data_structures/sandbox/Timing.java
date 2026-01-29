import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Timing {

	public static void main(String[] args)
	{
		int size = 5;
		int trials = 10000;
		for(int i = 0; i < 30; i++)
		{
			System.out.println(trials + " trials on " + size + " items:");
			experiments(size, true, trials);
			experiments(size, false, trials);
			experiments(size, true, trials);
			experiments(size, false, trials);
			experiments(size, true, trials);
			experiments(size, false, trials);
			size*=1.414;
		}
				
		
	}

	public static void experiments(int size, boolean isLL, int repetitions) {
		List<Integer> list;
	    long startTime, endTime, duration;
	    
	    startTime = System.nanoTime();

		if(isLL)
		{
			list = new LinkedList<Integer>();
			System.out.print("   LinkedList, average time:  ");
		}
		else
		{
			list = new ArrayList<Integer>();
			System.out.print("   ArrayList, average time: ");
		}
		
		for(int count = 0; count<repetitions; count++)
		{
			for(int i=0; i<size; i++)
				list.add(i);
			while(!list.isEmpty())
				list.remove(0);
		}
		
		endTime = System.nanoTime();
		duration = (endTime - startTime)/repetitions;
		System.out.println("" + duration + ", time per element: " + duration/size);
	}

}

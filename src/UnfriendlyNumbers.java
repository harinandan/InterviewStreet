import java.util.*;


public class UnfriendlyNumbers {
	
	static List<Integer> factors(int K)
	{
		List<Integer> ret = new LinkedList<Integer>();
		for (int i = 1; i <= K; i++) {
			if((K%i) == 0)
			{
				ret.add(i);
			}
		}
		
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = s.nextInt();
		int K = s.nextInt();
		int unfriendly[] = new int[N];
		for (int i = 0; i < unfriendly.length; i++) {
			unfriendly[i] = s.nextInt();
		}
		
		int count = 0;
		List<Integer> factors = factors(K);
		for (Integer factor : factors) {
			int i;
			for (i = 0; i < unfriendly.length; i++) {
				if((unfriendly[i] % factor) == 0)
					break;
			}
			if(i == unfriendly.length)
				count++;
		}
		
		System.out.println(count);
		s.close();
	}
}

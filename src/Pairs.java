import java.math.BigInteger;
import java.util.*;


public class Pairs {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = s.nextInt();
		BigInteger K = s.nextBigInteger();
		Set<BigInteger> set = new HashSet<BigInteger>();
		for (int i = 0; i < N; i++) {
			set.add(s.nextBigInteger());
		}
		
		int count = 0;
		for (BigInteger i : set) {
			if(set.contains(i.add(K)))
				count++;
			if(set.contains(i.subtract(K)))
				count++;
			
		}
		count /= 2;
		System.out.println(count);
		s.close();
	}
}

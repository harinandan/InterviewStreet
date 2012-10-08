import java.math.BigInteger;
import java.util.*;


public class UnfriendlyNumbers {
	 static BigInteger ONE = new BigInteger("1");
	 static BigInteger ZERO = new BigInteger("0");
	 static BigInteger TWO = new BigInteger("2");
	
	static List<BigInteger> factors(BigInteger K, Set<BigInteger> unfriendly)
	{
		List<BigInteger> ret = new LinkedList<BigInteger>();
		BigInteger KBY2 = K.divide(TWO);
		
		for (BigInteger i = TWO; i.compareTo(KBY2) <= 0; i = i.add(ONE)) {
			if(K.remainder(i).compareTo(ZERO) == 0)
			{
				if(!unfriendly.contains(i))
					ret.add(i);
			}
		}
		ret.add(K);
		
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = s.nextInt();
		BigInteger K = new BigInteger(s.next());
		Set<BigInteger> unfriendly = new HashSet<BigInteger>();
		for (int i = 0; i < N; i++) {
			unfriendly.add(new BigInteger(s.next()));
		}
		
		int count = 0;
		List<BigInteger> factors = factors(K, unfriendly);
		for (BigInteger factor : factors) {
			int i = 0;
			for (BigInteger u : unfriendly) {
				if(u.remainder(factor).compareTo(ZERO) == 0)
					break;
				i++;
			}
			if(i == unfriendly.size())
				count++;
		}
		
		System.out.println(count);
		s.close();
	}
}

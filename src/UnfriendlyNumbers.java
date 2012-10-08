import java.math.BigInteger;
import java.util.*;


public class UnfriendlyNumbers {
	 static BigInteger ONE = new BigInteger("1");
	 static BigInteger ZERO = new BigInteger("0");
	
	static List<BigInteger> factors(BigInteger K)
	{
		List<BigInteger> ret = new LinkedList<BigInteger>();
		
		for (BigInteger i = ONE; i.compareTo(K) <= 0; i = i.add(ONE)) {
			if(K.remainder(i).compareTo(ZERO) == 0)
			{
				ret.add(i);
			}
		}
		
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = s.nextInt();
		BigInteger K = new BigInteger(s.next());
		BigInteger unfriendly[] = new BigInteger[N];
		for (int i = 0; i < unfriendly.length; i++) {
			unfriendly[i] = new BigInteger(s.next());
		}
		
		int count = 0;
		List<BigInteger> factors = factors(K);
		for (BigInteger factor : factors) {
			int i;
			for (i = 0; i < unfriendly.length; i++) {
				if(unfriendly[i].remainder(factor).compareTo(ZERO) == 0)
					break;
			}
			if(i == unfriendly.length)
				count++;
		}
		
		System.out.println(count);
		s.close();
	}
}

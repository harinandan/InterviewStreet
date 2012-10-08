import java.math.BigInteger;
import java.util.*;

public class UnfriendlyNumbers {

	static BigInteger sqrt(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8"))
				.toString());
		while (b.compareTo(a) >= 0) {
			BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			if (mid.multiply(mid).compareTo(n) > 0)
				b = mid.subtract(BigInteger.ONE);
			else
				a = mid.add(BigInteger.ONE);
		}
		return a.subtract(BigInteger.ONE);
	}

	static List<BigInteger> factors(BigInteger K, Set<BigInteger> unfriendly) {
		List<BigInteger> ret = new LinkedList<BigInteger>();
		// BigInteger KBY2 = K.divide(TWO);
		BigInteger KSQRT = sqrt(K);
		KSQRT.add(BigInteger.ONE);

		for (BigInteger i = new BigInteger("2"); i.compareTo(KSQRT) <= 0; i = i
				.add(BigInteger.ONE)) {
			if (K.remainder(i).compareTo(BigInteger.ZERO) == 0) {
				if (!unfriendly.contains(i))
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
				if (u.remainder(factor).compareTo(BigInteger.ZERO) == 0)
					break;
				i++;
			}
			if (i == unfriendly.size())
				count++;
		}

		System.out.println(count);
		s.close();
	}
}

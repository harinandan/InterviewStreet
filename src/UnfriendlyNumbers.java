import java.math.*;
import java.util.*;

public class UnfriendlyNumbers {
	 public static final BigDecimal TWO = BigDecimal.valueOf(2);

    /**
     * Calcualtes the square root of the number.
     *
     * @param number the input number.
     * @return the square root of the input number.
     */
    public static BigDecimal sqrt(BigDecimal number) {
        int digits; // final precision
        BigDecimal numberToBeSquareRooted;
        BigDecimal iteration1;
        BigDecimal iteration2;
        BigDecimal temp1 = null;
        BigDecimal temp2 = null; // temp values

        int extraPrecision = number.precision();
        MathContext mc = new MathContext(extraPrecision, RoundingMode.HALF_UP);
        numberToBeSquareRooted = number;                                   // bd global variable
        double num = numberToBeSquareRooted.doubleValue();             // bd to double

        if (mc.getPrecision() == 0)
            throw new IllegalArgumentException("\nRoots need a MathContext precision > 0");
        if (num < 0.)
            throw new ArithmeticException("\nCannot calculate the square root of a negative number");
        if (num == 0.)
            return number.round(mc);                    // return sqrt(0) immediately

        if (mc.getPrecision() < 50)                // small precision is buggy..
            extraPrecision += 10;                    // ..make more precise
        int startPrecision = 1;                   // default first precision

        /* create the initial values for the iteration procedure:
        * x0:  x ~ sqrt(d)
        * v0:  v = 1/(2*x)
        */
        if (num == Double.POSITIVE_INFINITY)       // d > 1.7E308
        {
            BigInteger bi = numberToBeSquareRooted.unscaledValue();
            int biLen = bi.bitLength();
            int biSqrtLen = biLen / 2;                // floors it too

            bi = bi.shiftRight(biSqrtLen);          // bad guess sqrt(d)
            iteration1 = new BigDecimal(bi);                 // x ~ sqrt(d)

            MathContext mm = new MathContext(5, RoundingMode.HALF_DOWN);   // minimal precision
            extraPrecision += 10;                   // make up for it later

            iteration2 = BigDecimal.ONE.divide(TWO.multiply(iteration1, mm), mm);   // v = 1/(2*x)
        }
        else                                      // d < 1.7E10^308  (the usual numbers)
        {
            double s = Math.sqrt(num);
            iteration1 = new BigDecimal(s);                  // x = sqrt(d)
            iteration2 = new BigDecimal(1. / 2. / s);            // v = 1/2/x
            // works because Double.MIN_VALUE * Double.MAX_VALUE ~ 9E-16, so: v > 0

            startPrecision = 64;
        }

        digits = mc.getPrecision() + extraPrecision;        // global limit for procedure

        // create initial MathContext(precision, RoundingMode)
        MathContext n = new MathContext(startPrecision, mc.getRoundingMode());

        return sqrtProcedure(n, digits, numberToBeSquareRooted, iteration1, iteration2, temp1, temp2);           // return square root using argument precision
    }

    /**
     * Square root by coupled Newton iteration, sqrtProcedure() is the iteration part I adopted the Algorithm from the
     * book "Pi-unleashed", so now it looks more natural I give sparse math comments from the book, it assumes argument
     * mc precision >= 1
     *
     * @param mc
     * @param digits
     * @param numberToBeSquareRooted
     * @param iteration1
     * @param iteration2
     * @param temp1
     * @param temp2
     * @return
     */
    private static BigDecimal sqrtProcedure(MathContext mc, int digits, BigDecimal numberToBeSquareRooted, BigDecimal iteration1,
                                            BigDecimal iteration2, BigDecimal temp1, BigDecimal temp2) {
        // next v                                         // g = 1 - 2*x*v
        temp1 = BigDecimal.ONE.subtract(TWO.multiply(iteration1, mc).multiply(iteration2, mc), mc);
        iteration2 = iteration2.add(temp1.multiply(iteration2, mc), mc); // v += g*v        ~ 1/2/sqrt(d)

        // next x
        temp2 = numberToBeSquareRooted.subtract(iteration1.multiply(iteration1, mc), mc); // e = d - x^2
        iteration1 = iteration1.add(temp2.multiply(iteration2, mc), mc); // x += e*v        ~ sqrt(d)

        // increase precision
        int m = mc.getPrecision();
        if (m < 2)
            m++;
        else
            m = m * 2 - 1; // next Newton iteration supplies so many exact digits

        if (m < 2 * digits) // digits limit not yet reached?
        {
            mc = new MathContext(m, mc.getRoundingMode()); // apply new precision
            sqrtProcedure(mc, digits, numberToBeSquareRooted, iteration1, iteration2, temp1, temp2); // next iteration
        }

        return iteration1; // returns the iterated square roots
    }

	static List<BigInteger> factors(BigInteger K, Set<BigInteger> unfriendly) {
		List<BigInteger> ret = new LinkedList<BigInteger>();
		// BigInteger KBY2 = K.divide(TWO);
		BigDecimal KD = new BigDecimal(K);
		BigDecimal KSQRT = sqrt(KD);
		KSQRT.add(BigDecimal.ONE);

		for (BigDecimal i = new BigDecimal("2"); i.compareTo(KSQRT) <= 0; i = i
				.add(BigDecimal.ONE)) {
			if (KD.remainder(i).compareTo(BigDecimal.ZERO) == 0) {
				if (!unfriendly.contains(i))
					ret.add(i.toBigInteger());
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

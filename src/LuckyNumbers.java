import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LuckyNumbers {
	static class Range{
		long A;
		long B;
		short cDigitsInA;
		short cDigitsInB;
		
		long count;
		
		Range(long A, long B)
		{
			this.A=A;
			this.B=B;
			cDigitsInA = (short)(Math.log10(A)+1);
			cDigitsInB = (short)(Math.log10(B)+1);
		}

		short[] digits(long x)
		{
			short cDigitsInX = (short)(Math.log10(x)+1);
			short [] dArr = new short[cDigitsInX];
			int i = cDigitsInX;
			while(i-- > 0)
			{
				short digit = (short)(x%10);
				x /= 10;
				dArr[i] = digit;
			}
			
			return dArr;
		}
		
		short[] digitCount(long x)
		{
			short digitCountInX[] = new short[10];
			int i = (short)(Math.log10(x)+1);
			while(i-- > 0)
			{
				short digit = (short)(x%10);
				digitCountInX[digit]++;
				x /= 10;
			}
			
			return digitCountInX;
		}

		
		void add(long x)
		{
			long lBound = A;
			long uBound = B;
			short cDigitsInX = (short)(Math.log10(x)+1);

			if(cDigitsInA < cDigitsInX)
			{
				lBound = (long) Math.pow(10, cDigitsInX-1);
			}
			
			if(cDigitsInX < cDigitsInB)
			{
				uBound = (long) (Math.pow(10, cDigitsInX))-1;
			}
			
			if(lBound > uBound)
				return;

			short dArrX[] = digitCount(x);
			count += count(dArrX, uBound, digits(lBound), 0);
		}
		
		long toLong(short num[])
		{
			long ret = 0;
			int l = num.length;
			for (int i = 0; i < num.length; i++) {
				ret += num[l-i-1] * Math.pow(10, i);
			}
			
			return ret;
		}
		
		
		
		int count(short digits[], long U, short num[], int index) {
			if (num.length-1 == index) {
				int count = 0;
				
				for(; num[index] <=9 && toLong(num) <= U; num[index]++)
				{
					//System.out.println(Arrays.toString(num));
					if(digits[num[index]] == 1)
					{
						count++;
					}
				}
				num[index] = 0;
				
				return count;
			} else {
				int count = 0;
				
				for(;num[index]<=9 && toLong(num) <= U;num[index]++)
				{
					//System.out.println(Arrays.toString(num));
					if(digits[num[index]] != 0)
					{
						digits[num[index]]--;
						count+=count(digits, U, num, index+1);
						digits[num[index]]++;
					}
				}
				num[index] = 0;
				
				return count;
			}
		}

		void addold(long x)
		{
			long lBound = A;
			long uBound = B;
			short cDigitsInX = (short)(Math.log10(x)+1);

			if(cDigitsInA < cDigitsInX)
			{
				lBound = (long) Math.pow(10, cDigitsInX-1);
			}
			
			if(cDigitsInX < cDigitsInB)
			{
				uBound = (long) (Math.pow(10, cDigitsInX))-1;
			}
			
			if(lBound > uBound)
				return;
			
			short digitCountInX[] = new short[10];
			boolean digitProcessedInX[] = new boolean[10];
			short[] digitsInX = new short[cDigitsInX];
			int i = cDigitsInX;
			while(i-- > 0)
			{
				short digit = (short)(x%10);
				digitCountInX[digit]++;
				digitsInX[i] = digit;
				x /= 10;
			}
			
			short[] digitsInL = digits(lBound);
			short[] digitsInU = digits(uBound);
			
			long n = 1;
			long d = 1;
			for (i = 0; i < digitsInX.length; i++) {
				int c = countold(digitCountInX, digitsInL[i], digitsInU[i], i);
				if(c == 0)
					return;
				else
				{
					n *= c;
					if(!digitProcessedInX[digitsInX[i]])
					{
						d *= digitCountInX[digitsInX[i]];
						digitProcessedInX[digitsInX[i]] = true;
					}
				}
			}
			count += (n/d);
		}

		private int countold(short[] digitCountInX, short L,
				short U, int i) {
			int total = 0;
			for (int j = L; j <= U; j++) {
				if(digitCountInX[j] != 0)
				total += digitCountInX[j];
			}
			
			total -= i;
			return total;
		}
		
		public String toString()
		{
			return "["+A+","+B+"]" + "("+count+")";
		}
		
	}
	

	static List<Range> ranges = new ArrayList<Range>();
	public static void main(String[] args) throws NumberFormatException, IOException {
		genPrimes();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		MAXDIGITS = -1;
		MINDIGITS = 18;
		for (int i = 0; i < T; i++) {
			String temp[] = br.readLine().trim().split(" ");
			long A = Long.parseLong(temp[0]);
			long B = Long.parseLong(temp[1]);
			Range r = new Range(A,B);
			if(MAXDIGITS < r.cDigitsInB)
				MAXDIGITS = r.cDigitsInB;
			if(MINDIGITS > r.cDigitsInA)
				MINDIGITS = r.cDigitsInA;
			ranges.add(r);
		}
		//long start = System.currentTimeMillis();
		backtrack(1, 0, 9);
		for (Range r : ranges) {
			System.out.println(r.count);
		}
		//System.out.println("did "+prime_checks+" signature checks, found "+found+" lucky signatures");
		//System.out.println((System.currentTimeMillis()-start));
	}

	static final int MAX_SQUARE_SUM = 1458;
	static int MINDIGITS = 1;
	static int MAXDIGITS = 18;
	static boolean primes[] = new boolean[1460];

	static void genPrimes() {
		for (int i = 0; i <= MAX_SQUARE_SUM; ++i) {
			primes[i] = true;
		}
		primes[0] = primes[1] = false;

		for (int i = 2; i * i <= MAX_SQUARE_SUM; ++i) {
			if (!primes[i]) {
				continue;
			}
			for (int j = 2; i * j <= MAX_SQUARE_SUM; ++j) {
				primes[i * j] = false;
			}
		}
	}

	static int prime_checks, found;

	static int sum, square_sum;
	static String num = "";
	static int numLen = 0;

	static void backtrack(int startdigit, int ndigits, int maxdigit) {
		ndigits++;

		for (int i = startdigit; i <= maxdigit; i++) {
			num += i;
			sum += i;
			square_sum += squares[i];
			numLen += 1;
			
			if(numLen > MINDIGITS)
			{
				prime_checks++;
				if (primes[sum] && primes[square_sum]) {
					found++;
		        	long l = Long.parseLong(num);
		        	for(Range r: ranges)
		        	{
		        		r.add(l);
		        		//System.out.println(num+" : "+r);
		        	}
				}
			}
			if (ndigits < MAXDIGITS)
				backtrack(0, ndigits, i);
			sum -= i;
			square_sum -= squares[i];
			num = num.substring(0, num.length() - 1);
			numLen -= 1;
		}
	}

	static final int squares[] = { 0, 1, 4, 9, 16, 25, 36, 49, 64, 81 };
}

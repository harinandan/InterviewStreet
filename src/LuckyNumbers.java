import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LuckyNumbers {
	static boolean[] isComposite;
	public static void runEratosthenesSieve(int upperBound) {
	      int upperBoundSquareRoot = (int) Math.sqrt(upperBound);
	      isComposite = new boolean[upperBound + 1];
	      for (int m = 2; m <= upperBoundSquareRoot; m++) {
	            if (!isComposite[m]) {
	                  //System.out.print(m + " ");
	                  for (int k = m * m; k <= upperBound; k += m)
	                        isComposite[k] = true;
	            }
	      }
	      /*
	      for (int m = upperBoundSquareRoot; m <= upperBound; m++)
	            if (!isComposite[m])
	                  System.out.print(m + " ");
	                  */
	}

	static byte squares[] = {0, 1, 4, 9, 16, 25, 36, 49, 64, 81};
	static boolean isPrime(int n) {
		if (n < 2)
			return false;
		return !isComposite[n];
	}
	
	static long solve(long A, long B)
	{
		long count = 0;
		for (long i = A; i <= B; i++) {
			long temp = i;
			int sumDigits = 0;
			int sumSqrsDigits = 0;
			byte[] digits = new byte[20];
			int j = 0;
			while(temp > 0)
			{
				digits[j++] = (byte)(temp%10);
				temp /=10;
			}
			int maxj = j;
			
			for (j = maxj-1; j >= 0; j--) {
				sumDigits += digits[j];
			}
			if(isPrime(sumDigits))
			{
				for (j = maxj-1; j >= 0; j--) {
					sumSqrsDigits += squares[digits[j]];
				}
				if(isPrime(sumSqrsDigits))
					count++;
			}
				
		}
		
		return count;
		
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine().trim());
		for (int i = 0; i < T; i++) {
			String temp [] = br.readLine().trim().split(" ");
			long A = Long.parseLong(temp[0]);
			long B = Long.parseLong(temp[1]);
			runEratosthenesSieve((int)Math.log10(B)*81);
			System.out.println(solve(A,B));
		}
	
	}
}
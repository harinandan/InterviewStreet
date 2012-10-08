import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LuckyNumbers {
	static boolean isPrime(long n) {
		if (n < 2)
			return false;
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		long sqrtN = (long) Math.sqrt(n) + 1;
		for (long i = 6L; i <= sqrtN; i += 6) {
			if (n % (i - 1) == 0 || n % (i + 1) == 0)
				return false;
		}
		return true;
	}
	
	static long solve(long A, long B)
	{
		long count = 0;
		for (long i = A; i <= B; i++) {
			long temp = i;
			long sumDigits = 0;
			long sumSqrsDigits = 0;
			while(temp > 0)
			{
				long R = temp%10;
				sumDigits += R;
				sumSqrsDigits += R * R;
				temp /=10;
			}
			if(isPrime(sumDigits) && isPrime(sumSqrsDigits))
				count++;
				
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
			System.out.println(solve(A,B));
		}
	
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LuckyNumbers {
	static int MAX_LENGTH = 18;
	static int MAX_SUM = 162;
	static int MAX_SQUARE_SUM = 1458;
	static boolean primes[] = new boolean[1459];
	static long dyn_table[][][] = new long[19][163][1459];

	static void gen_primes() {
	    for (int i = 0; i <= MAX_SQUARE_SUM; ++i) {
	        primes[i] = true;
	    }
	    primes[0] = primes[1] = false;

	    for (int i = 2; i * i <= MAX_SQUARE_SUM; ++i) {
	        if (!primes[i]) {
	            continue;
	        }
	        for (int j = 2; i * j <= MAX_SQUARE_SUM; ++j) {
	            primes[i*j] = false;
	        }
	    }
	}

	static void gen_table() {
	    dyn_table[0][0][0] = 1;

	    for (int i = 0; i < MAX_LENGTH; ++i) {
	        for (int j = 0; j <= 9 * i; ++j) {
	            for (int k = 0; k <= 9 * 9 * i; ++k) {
	                for (int l = 0; l < 10; ++l) {
	                    dyn_table[i + 1][j + l][k + l*l] += dyn_table[i][j][k];
	                }
	            }
	        }
	    }
	}

	static long count_lucky (long max) {
	            long result = 0;
	    int len = 0;
	    int split_max[]= new int[MAX_LENGTH+1];
	    while (max>0) {
	        split_max[len] = (int) (max % 10);
	        max /= 10;
	        ++len;
	    }
	    int sum = 0;
	    int sq_sum = 0;
	    for (int i = len-1; i >= 0; --i) {
	        long step_result = 0;
	        for (int l = 0; l < split_max[i]; ++l) {
	            for (int j = 0; j <= 9 * i; ++j) {
	                for (int k = 0; k <= 9 * 9 * i; ++k) {
	                    if (primes[j + l + sum] && primes[k + l*l + sq_sum]) {
	                        step_result += dyn_table[i][j][k];
	                    }
	                }
	            }
	        }
	        result += step_result;

	        sum += split_max[i];
	        sq_sum += split_max[i] * split_max[i];
	    }

	    if (primes[sum] && primes[sq_sum]) {
	        ++result;
	    }

	    return result;
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		//long start = System.currentTimeMillis();
		gen_primes();
		gen_table();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine().trim());
		for (int i = 0; i < T; i++) {
			String temp[] = br.readLine().trim().split(" ");
			long A = Long.parseLong(temp[0]);
			long B = Long.parseLong(temp[1]);

			System.out.println(count_lucky(B) - count_lucky(A - 1));
		}
		//System.out.println(System.currentTimeMillis()-start);

	}

}
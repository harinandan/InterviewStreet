import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LuckyNumbers {
	static final int MAX_LENGTH = 18;
	static final int MAX_SUM = 162;
	static final int MAX_SQUARE_SUM = 1458;
	static boolean primes[] = new boolean[1460];
	static long dyn_table[][][] = new long[20][164][1461];
	static long ans[][][][] = new long[19][10][164][1461]; // about 45 MB

	static int start[][] = new int[19][163];
	static int end[][] = new int[19][163];

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
				primes[i * j] = false;
			}
		}
	}

	static void gen_table() {
		for (int i = 0; i <= MAX_LENGTH; ++i) {
			for (int j = 0; j <= MAX_SUM; ++j) {
				for (int k = 0; k <= MAX_SQUARE_SUM; ++k) {
					dyn_table[i][j][k] = 0;
				}
			}
		}
		dyn_table[0][0][0] = 1;

		for (int i = 0; i < MAX_LENGTH; ++i) {
			for (int j = 0; j <= 9 * i; ++j) {
				for (int k = 0; k <= 9 * 9 * i; ++k) {
					for (int l = 0; l < 10; ++l) {
						dyn_table[i + 1][j + l][k + l * l] += dyn_table[i][j][k];
					}
				}
			}
		}
	}

	static long count_lucky(long maxp) {
		long result = 0;
		int len = 0;
		int split_max[] = new int[MAX_LENGTH];
		while (maxp > 0) {
			split_max[len] = (int) (maxp % 10);
			maxp /= 10;
			++len;
		}
		int sum = 0;
		int sq_sum = 0;
		long step_result;
		long step_;
		for (int i = len - 1; i >= 0; --i) {
			step_result = 0;
			int x1 = 9 * i;
			for (int l = 0; l < split_max[i]; ++l) {
				step_ = 0;
				if (ans[i][l][sum][sq_sum] != 0) {
					step_result += ans[i][l][sum][sq_sum];
					continue;
				}
				int y = l + sum;
				int x = l * l + sq_sum;
				for (int j = 0; j <= x1; ++j) {
					if (primes[j + y])
						for (int k = start[i][j]; k <= end[i][j]; ++k) {
							if (primes[k + x]) {
								step_result += dyn_table[i][j][k];
								step_ += dyn_table[i][j][k];
							}
						}

				}
				ans[i][l][sum][sq_sum] = step_;
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

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		gen_primes();
		gen_table();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i <= 18; i++)
			for (int j = 0; j <= 163; j++) {
				for (int k = 0; k <= 1458; k++)
					if (dyn_table[i][j][k] != 0l) {
						start[i][j] = k;
						break;
					}

				for (int k = 1460; k >= 0; k--)
					if (dyn_table[i][j][k] != 0l) {
						end[i][j] = k;
						break;
					}
			}
		int T = Integer.parseInt(br.readLine().trim());
		for (int i = 0; i < T; i++) {
			String temp[] = br.readLine().trim().split(" ");
			long A = Long.parseLong(temp[0]);
			long B = Long.parseLong(temp[1]);

			System.out.println(count_lucky(B) - count_lucky(A - 1));
		}

	}

}
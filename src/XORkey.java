import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class XORkey {
	
	static void solve(long x[], long a, int p, int q)
	{
		long maxval = 0; 
		for (int j = p-1; j <= q-1;j++) {
			long val = a ^ x[j];
			
			if(val > maxval)
				maxval = val;
				
		}
		System.out.println(maxval);
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		for (int i = 0; i < T; i++) {

			String[] temp = br.readLine().trim().split(" ");
			int N = Integer.parseInt(temp[0].trim());
			int Q = Integer.parseInt(temp[1].trim());
			temp = br.readLine().trim().split(" ");
			long x[] = new long[N];

			for (int j = 0; j < N; j++) {
				x[j] = Long.parseLong(temp[j].trim());
			}
			
			for (int j = 0; j < Q; j++) {
				temp = br.readLine().trim().split(" ");
				solve(x, Long.parseLong(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
			}
		}
	}
}

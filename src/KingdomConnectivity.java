import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;



public class KingdomConnectivity {
	static int amatrix[][];
	static int N;
	

	static int [][] apow(int a[][])
	{
		int ret[][] = new int[N][N];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				for (int k = 0; k < a[i].length; k++) {
					ret[i][j] += a[i][k] * amatrix[k][j];
				}
			}
		}
		
		return ret;
	}
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		N = scanner.nextInt();
		amatrix = new int[N][N];
		int M = scanner.nextInt();
		for (int i = 0; i < M; i++) {
			int S = scanner.nextInt();
			int T = scanner.nextInt();
			amatrix[S-1][T-1] = 1;
		}
		//print(amatrix);
		int[][] temp = amatrix;
		int count = 0;
		for (int i = 0; i < N*N; i++) {
			temp = apow(temp);
			if(i>= N && temp[0][N-1] > 0)
			{
				System.out.println("INFINITE PATHS");
				return;
			}
			count += temp[0][N-1]; 
			count %= 1000000000; 
			//print(temp);
		}
		System.out.println(count);
	}

}

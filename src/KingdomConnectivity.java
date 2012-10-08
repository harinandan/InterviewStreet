import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;



public class KingdomConnectivity {
	static Map<Integer,BitSet> graph = new HashMap<Integer, BitSet>();
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		for (int i = 0; i < M; i++) {
			int S = scanner.nextInt();
			int T = scanner.nextInt();
			BitSet edges = graph.get(S);
			if(edges==null)
			{
				edges = new BitSet();
				graph.put(S, edges);
			}
			edges.set(T);
		}
		
		int val = solve(1, N, new BitSet());
		System.out.println(val == -1? "INFINITE PATHS" : val);
	}

	private static int solve(int S, int N, BitSet exclude) {
		int ret = 0;

		Stack<Integer> s = new Stack<Integer>();
		Stack<BitSet> sVisited = new Stack<BitSet>();
		Stack<List<Integer>> sPath = new Stack<List<Integer>>();
		s.push(S);
		BitSet b = new BitSet();
		b.set(S);
		sVisited.push(b);
		List<Integer> path = new ArrayList<Integer>();
		path.add(1);
		sPath.push(path);
		while(!s.empty())
		{
			BitSet edges = graph.get(s.pop());
			BitSet visited = sVisited.pop(); 
			path = sPath.pop();
			for (int i = edges.nextSetBit(0); i >= 0; i = edges.nextSetBit(i+1)) {
				if(i == N)
					ret++;
				else
				{
					if(!visited.get(i))
					{
						if(!exclude.get(i))
						{
							s.push(i);
							b = new BitSet();
							b.or(visited);
							b.set(i);
							sVisited.push(b);
							List<Integer> newpath = new ArrayList<Integer>();
							newpath.addAll(path);
							newpath.add(i);
							sPath.push(newpath);
							//System.out.println(newpath);
						}
					}
					else
					{
						LinkedList<Integer> newpath = new LinkedList<Integer>();
						newpath.addAll(path);
						while((newpath.peek()) != i)
							newpath.poll();
						//System.out.println("Cycle : " + newpath);
						if(isCycleConnected(N, newpath))
							return -1;
					}
				}
			}
		}

		return ret;
	}

	private static boolean isCycleConnected(int N,LinkedList<Integer> cycle) {
		BitSet b = new BitSet();
		for (Integer i : cycle) {
			b.set(i);
		}
		for (Integer i : cycle) {
			if(solve(i, N, b) != 0)
				return true;
		}
		
		return false;
	}
}

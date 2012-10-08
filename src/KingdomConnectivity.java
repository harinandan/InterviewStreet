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
	static Map<Integer,BitSet> connected = new HashMap<Integer, BitSet>();
	static int N;
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		N = scanner.nextInt();
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
		for (Map.Entry<Integer,BitSet> entry : graph.entrySet()) {
			BitSet bs = new BitSet();
			BitSet c = entry.getValue();
			if(c != null)
			{
				bs.or(entry.getValue());
				for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
					c = graph.get(i);
					if(c != null)
						bs.or(c);
				}
			}
			connected.put(entry.getKey(), bs);
		}
		
		int val = solve(1, N);
		System.out.println(val == -1? "INFINITE PATHS" : val);
	}

	private static int solve(int S, int T) {
		int ret = 0;

		Stack<Integer> s = new Stack<Integer>();
		Stack<BitSet> sVisited = new Stack<BitSet>();
		Stack<LinkedList<Integer>> sPath = new Stack<LinkedList<Integer>>();
		s.push(S);
		BitSet b = new BitSet();
		b.set(S);
		sVisited.push(b);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.add(S);
		sPath.push(path);
		while(!s.empty())
		{
			BitSet edges = graph.get(s.pop());
			BitSet visited = sVisited.pop(); 
			path = sPath.pop();
			if(edges != null)
			for (int i = edges.nextSetBit(0); i >= 0; i = edges.nextSetBit(i+1)) {
				if(i == T)
				{
					ret++;
					ret %= 1000000000;
					for (Integer j : path) {
						BitSet bs = null;
						if(((bs = connected.get(T)) != null) && bs.get(j))
							return -1;
					}
				}
				else
				{
					if(!visited.get(i))
					{
						s.push(i);
						b = new BitSet();
						b.or(visited);
						b.set(i);
						sVisited.push(b);
						LinkedList<Integer> newpath = new LinkedList<Integer>();
						newpath.addAll(path);
						newpath.add(i);
						sPath.push(newpath);
						//System.out.println(newpath);
					}
					else
					{
						while((path.peek()) != i)
							path.poll();
						//System.out.println("Cycle : " + newpath);
						for (Integer j : path) {
							BitSet bs = null;
							if(((bs = connected.get(j)) != null) && bs.get(T))
								return -1;
						}
					}
				}
			}
		}

		return ret;
	}
}

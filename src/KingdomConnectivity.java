import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class KingdomConnectivity {

	static int count(int N, Map<Integer, BitSet> edges) {
		Queue<Integer> q = new LinkedList<Integer>();
		Queue<BitSet> pathq = new LinkedList<BitSet>();
		List<BitSet> paths = new ArrayList<BitSet>();
		List<BitSet> cycles = new ArrayList<BitSet>();
		int count = 0;
		q.add(1);
		BitSet p = new BitSet();
		p.set(1);
		pathq.add(p);
		while (!q.isEmpty()) {
			int t = q.poll();
			p = pathq.poll();
			if (t == N) {
				// System.out.println(p);
				paths.add(p);
				count++;
			} else {
				BitSet s = edges.get(t);
				if (s != null) {
					for (int i = s.nextSetBit(0); i >= 0; i = s.nextSetBit(i + 1)) {
						if (p.get(i)) {
							// System.out.println("Cycle detected : " + p);
							cycles.add(p);
							continue;
						}
						q.add(i);
						BitSet p1 = new BitSet();
						p1.or(p);
						p1.set(i);
						pathq.add(p1);
					}
				}
			}
		}

		for (BitSet cycle : cycles) {
			for (BitSet path : paths) {
				int cardinality = cycle.cardinality();
				cycle.and(path);
				if (cycle.cardinality() == cardinality)
					return -1;
			}
		}
		return count;
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		Map<Integer, BitSet> edges = new HashMap<Integer, BitSet>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String temp[] = br.readLine().trim().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		for (int i = 0; i < M; i++) {
			temp = br.readLine().trim().split(" ");
			int C1 = Integer.parseInt(temp[0].trim());
			int C2 = Integer.parseInt(temp[1].trim());
			BitSet s = edges.get(C1);
			if (s == null) {
				s = new BitSet();
				edges.put(C1, s);
			}
			s.set(C2);
		}

		int count = count(N, edges);
		System.out.println((count > 0 ? count : "INFINITE PATHS"));
	}

}

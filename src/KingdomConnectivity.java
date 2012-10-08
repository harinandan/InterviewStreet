import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class KingdomConnectivity {
	static enum Visit
	{
		CAN_REACH_TARGET,
		UNVISITED
	}
	static class Node
	{
		BitSet children = new BitSet();
		BitSet parents = new BitSet();
		Visit visit = Visit.UNVISITED;
		int position;
		int paths;

		int node = 0;

		int getNode()
		{
			return node;
		}

		void setNode(int node)
		{
			this.node = node;
		}

		List<Node> topologicalSort(BitSet visited)
		{
			visited.set(node);
			LinkedList<Node> sortedNodes = new LinkedList<Node>();
			for (int i = children.nextSetBit(0); i >= 0; i = children.nextSetBit(i+1)) {
				if(!visited.get(i))
					sortedNodes.addAll(0, graph.get(i).topologicalSort(visited));
			}
			sortedNodes.addFirst(this);


			return sortedNodes;
		}
		
		void prune()
		{
			visit = Visit.CAN_REACH_TARGET;
			for (int i = parents.nextSetBit(0); i >= 0; i = parents.nextSetBit(i+1)) {
				Node parent = graph.get(i);
				if(parent.visit == Visit.UNVISITED)
					parent.prune();
			}
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "["+node+"("+visit+")"+"]";
		}
	}

	static Map<Integer,Node> graph = new HashMap<Integer, Node>();

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int N = s.nextInt();
		int M = s.nextInt();

		for (int i = 0; i < M; i++) {
			int S = s.nextInt();
			int T = s.nextInt();
			Node sNode = graph.get(S);
			if(sNode == null)
			{
				sNode = new Node();
				sNode.setNode(S);
				graph.put(S, sNode);
			}
			Node tNode = graph.get(T);
			if(tNode == null)
			{
				tNode = new Node();
				tNode.setNode(T);
				graph.put(T, tNode);
			}
			sNode.children.set(T);
			tNode.parents.set(S);
		}
		
		
		//System.out.println();
		solve(N);
	}
	static void solve(int N)
	{
		graph.get(N).prune();
		if (!(graph.get(1).visit == Visit.CAN_REACH_TARGET))
		{
			System.out.println(0);
			return;
		}

		List<Node> sorted = graph.get(1).topologicalSort(new BitSet());
		int startPos = -1;
		for(int i = 0; i <sorted.size();i++)
		{
			Node node = sorted.get(i);
			node.position = i;
			if(node.node == 1)
			{
				startPos = i;
			}
		}
		Node init = sorted.get(startPos);
		init.paths = 1;
		for(int j = startPos+1; j <sorted.size();j++)
		{
			Node cur = sorted.get(j);
			for (int i = cur.children.nextSetBit(0); i >= 0; i = cur.children.nextSetBit(i+1)) {
				Node child = graph.get(i);
				if(child.visit != Visit.UNVISITED && child.position <= j)
				{
					System.out.println("INFINITE PATHS");
					return;
				}
			}
			int paths = 0;
			
			for (int i = cur.parents.nextSetBit(0); i >= 0; i = cur.parents.nextSetBit(i+1)) {
				Node prev = graph.get(i);
				paths += prev.paths;
			}
			cur.paths = paths;
		}
		
		System.out.println(graph.get(N).paths);
	}

}

import java.util.*;


public class KingdomConnectivity {
	static class Graph {
		Map<Integer,Node> nodes = new HashMap<Integer,Node>();
		
		void addEdge(int S, int T)
		{
			Node sNode = nodes.get(S);
			if(sNode == null)
			{
				sNode = new Node(S);
				nodes.put(S, sNode);
			}
			Node tNode = nodes.get(T);
			if(tNode == null)
			{
				tNode = new Node(T);
				nodes.put(T, tNode);
			}
			sNode.addChild(T);
			tNode.addParent(S);
		}
		
		String countPaths(int S, int T)
		{
			Node sNode = nodes.get(S);
			Node tNode = nodes.get(T);
			
			if(sNode == null || tNode == null)
				return "0";
			
			tNode.pruneUnreachableNodes(this);
			if(sNode.visit != VisitEnum.CAN_REACH_TARGET)
				return "0";
			
			List<Node> sortedNodes = sNode.topologicalSort(this, new BitSet());
			sortedNodes.remove(sNode);
			sNode.paths = 1;
			
			for (int i = 0; i < sortedNodes.size();i++) {
				Node cur = sortedNodes.get(i);
				cur.position = i+1;
			}
			
			for (int i = 0; i < sortedNodes.size();i++) {
				Node cur = sortedNodes.get(i);
				for (Integer child : cur.children.keySet()) {
					Node childNode = nodes.get(child);
					if(childNode.visit != VisitEnum.UNVISITED && childNode.position <= (i+1))
						return "INFINITE PATHS";
				}
				int paths = 0;
				for (Map.Entry<Integer,Integer> entry : cur.parents.entrySet()) {
					paths = (paths + nodes.get(entry.getKey()).paths * entry.getValue())%1000000000;
				}
				cur.paths = paths;
			}
			
			if(tNode.paths != 0 && sNode.parents.get(sNode.nodeValue) != null)
				return "INFINITE PATHS";
			return ""+(tNode.paths);
		}
	}
	enum VisitEnum{
		CAN_REACH_TARGET,
		UNVISITED
	}
	static class Node
	{
		int nodeValue;
		
		Map<Integer,Integer> children = new HashMap<Integer,Integer>();
		Map<Integer,Integer> parents = new HashMap<Integer,Integer>();
		
		VisitEnum visit = VisitEnum.UNVISITED; 
		int paths;
		int position;
		
		Node(int nodeValue)
		{
			this.nodeValue = nodeValue;
		}
		
		void addChild(int c)
		{
			Integer cCount = children.get(c);
			if(cCount == null)
				children.put(c, 1);
			else
				children.put(c, cCount+1);
		}
		
		void addParent(int p)
		{
			Integer pCount = parents.get(p);
			if(pCount == null)
				parents.put(p, 1);
			else
				parents.put(p, pCount+1);
		}
		
		List<Node> topologicalSort(Graph g, BitSet visited)
		{
			LinkedList<Node> sorted = new LinkedList<Node>();
			visited.set(nodeValue);
			for (Integer child:children.keySet()) {
				if(!visited.get(child))
				{
					sorted.addAll(0, g.nodes.get(child).topologicalSort(g, visited));
				}
			}
			sorted.addFirst(g.nodes.get(nodeValue));
			return sorted;
		}
		
		void pruneUnreachableNodes(Graph g)
		{
			visit = VisitEnum.CAN_REACH_TARGET;
			for(Integer parent:parents.keySet())
			{
				Node parentNode = g.nodes.get(parent);
				if(parentNode.visit == VisitEnum.UNVISITED)
					parentNode.pruneUnreachableNodes(g);
			}
		}
		
		@Override
		public String toString() {
			return "["+nodeValue+"]";
		}
	}
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Graph g = new Graph();
		int N = s.nextInt();
		int M = s.nextInt();
		for (int i = 0; i < M; i++) {
			int S = s.nextInt();
			int T = s.nextInt();
			g.addEdge(S, T);
		}
		s.close();
		System.out.println(g.countPaths(1, N));
	}
}

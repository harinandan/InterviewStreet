import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;

class Node
{
	BitSet index = new BitSet();
	public Node left;
	public Node right;
	
	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	boolean indexInRange(BitSet set)
	{
		return index.intersects(set);
	}
	void setIndex(int i)
	{
		index.set(i);
	}
	
	BitSet getIndex(){
		return index;
	}
}

public class XORkey {
	static boolean debug = false;
	static int bestMatch(Node pRoot, int m, int p, int q) {
		if(debug)
		{
			printBits(~m);
			printBits(m);
		}
		BitSet range = new BitSet();
		range.set(p, q+1);
		Node node = pRoot;

		for (int i = 15; i >= 0; i--) {
			byte bit = (byte) ((m >> i) & 0x01);

			if (bit != 0) {
				if (node.right != null) {
					if(node.right.indexInRange(range))
						node = node.right;
					else
						node = node.left;
				} else {
					node = node.left;
				}
			} else {
				if (node.left != null) {
					if(node.left.indexInRange(range))
						node = node.left;
					else
						node = node.right;
				} else {
					node = node.right;
				}
			}
		}

		return node.getIndex().nextSetBit(0);
	}

	static void printBits(int x)
	{
		int maxx = 1<<15;
		for (int i = 15; i >= 0; i--) {
			int bit = (maxx & x);
			System.out.print( ((bit == 0)?0:1));
			maxx >>= 1;
		}
		System.out.println();
	}
	static void insertNode(Node root, int x, int index)
	{
		int maxx = 1<<15;
		Node node = root;
		for (int i = 15; i >= 0; i--) {
			node.setIndex(index);
			int bit = (maxx & x);
			if(debug)
				System.out.print( ((bit == 0)?0:1));
			if(bit == 0)
			{
				if(node.left == null)
					node.left = new Node();
				node = node.left;
			}
			else
			{
				if(node.right == null)
					node.right = new Node();
				node = node.right;
			}
			maxx >>= 1;
		}
		node.setIndex(index);
		if(debug)
			System.out.println();
	}
	static Node buildTree(int x[])
	{
		Node root = new Node();
		for (int i = 0; i < x.length; i++) {
			insertNode(root, x[i], i);
		}
		
		return root;
	}
	
	
	static void solve(int x[], long a, int p, int q)
	{
		if(debug)
		{
			for (int i = 0; i < x.length; i++) {
				System.out.print( (a ^ x[i]) + ",") ;
			}
			System.out.println();
		}
		if(p == q)
		{
			System.out.println((a ^ x[p-1]));
			return ;
		}
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
		long start = System.currentTimeMillis();
		for (int i = 0; i < T; i++) {

			String[] temp = br.readLine().trim().split(" ");
			int N = Integer.parseInt(temp[0].trim());
			int Q = Integer.parseInt(temp[1].trim());
			temp = br.readLine().trim().split(" ");
			int x[] = new int[N];

			for (int j = 0; j < N; j++) {
				x[j] = Integer.parseInt(temp[j].trim());
			}
			Node root = buildTree(x);
			
			for (int j = 0; j < Q; j++) {
				temp = br.readLine().trim().split(" ");
				int a = Integer.parseInt(temp[0]);
				int p = Integer.parseInt(temp[1]);
				int q = Integer.parseInt(temp[2]);;
				//solve(x,a,p,q);
				System.out.println(a ^ x[bestMatch(root, ~a, p-1, q-1)]);
			}
		}
		if(debug)
		System.out.println("Time : "+(System.currentTimeMillis() - start));
	}
}
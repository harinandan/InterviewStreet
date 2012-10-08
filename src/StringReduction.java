import java.util.Scanner;

public class StringReduction {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int T = scanner.nextInt();
		String s = scanner.nextLine();
		for (int i = 0; i < T; i++) {
			s = scanner.nextLine();
			//System.out.println(s);
			System.out.println(solve(s));
		}
		scanner.close();
	}

	static char reduce(char one, char two)
	{
		switch(one)
		{
		case 'a':
			switch(two)
			{
			case 'a': return '\0';
			case 'b': return 'c';
			case 'c': return 'b';
			}
		case 'b':
			switch(two)
			{
			case 'a': return 'c';
			case 'b': return '\0';
			case 'c': return 'a';
			}
		case 'c':
			switch(two)
			{
			case 'a': return 'b';
			case 'b': return 'a';
			case 'c': return '\0';
			}
		}
		return '\0';
	}

	static int findNextChangableChar(char p[], int r[])
	{
		int rMax = -1;
		int ret = -1;
		for (int j = 0; j < p.length; j++) {
			if(p[j] != '\0')
			{
				if(r[j] > rMax)
				{
					ret = j;
					rMax = r[j];
				}
			}
		}
		return ret;
	}

	private static int solve(String s) {
		int j = -1;
		char replace = '\0';
		do
		{
			if(j >= 0)
			{
				s = reduceString(s, replace, j);
			}
			char p[] = new char[s.length()-1];
			int r[] = new int[s.length()-1];
			for (int i = 1; i < s.length(); i++) {
				p[i-1] = reduce(s.charAt(i-1), s.charAt(i));
				int k = i-2;
				int l = i+1;
				if(p[i-1] != '\0')
				{
					if(k > 0 && s.charAt(k) != p[i-1])
					{
						r[i-1]++;
					}
					if(l < s.length()  && s.charAt(l) != p[i-1])
					{
						r[i-1]++;
					}
				}
			}

			//System.out.println(Arrays.toString(p));
			//System.out.println(Arrays.toString(r));
			j = findNextChangableChar(p ,r);
			if(j>=0)
			replace = p[j];
		}while(j != -1);
		return s.length();
	}

	private static String reduceString(String s, char r, int j) {
		StringBuffer sb = new StringBuffer(s);
		sb.replace(j, j+2, String.valueOf(r));

		return sb.toString();
	}

}

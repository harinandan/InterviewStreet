import java.io.*;
import java.util.*;

public class MeetingPoint
{
	static class Point
	{
		long x;
		long y;
		
		long xSum = 0;
		long ySum = 0;
		long mDistance = 0;
		
		boolean v = false;

		Point(long x, long y)
		{
			this.x = x;
			this.y =y;
		}

		long distance(Point p)
		{
			return Math.max(Math.abs(p.x-x),Math.abs(p.y-y));
		}

		boolean equals(Point p)
		{
			if(p == null) return false;
			return p.x == x && p.y == y; 
		}

		void visit()
		{
			v = true;
		}

		public String toString()
		{
			return "[" + x+ "," + y + "("+xSum+")" +"]";
		}
	}


	private static void calculateXDistance(List<Point> points)
	{
		Collections.sort(points, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				return (int) (o1.x - o2.x);
			}
		});
		long L[] = new long[points.size()+1];
		long R[] = new long[points.size()+2];
		L[0] = 0;
		L[1] = points.get(0).x;
		for (int i = 1; i<=points.size();i++) {
			L[i] = L[i-1] + points.get(i-1).x; 
		}
		
		for (int i = 1; i < L.length; i++) {
			R[i] = L[points.size()] - L[i-1];
		}

		for (int k = 1; k<=points.size();k++) {
			points.get(k-1).xSum = R[k+1] - L[k-1] - ((points.size() - 2*k + 1)* points.get(k-1).x); 
			points.get(k-1).mDistance += points.get(k-1).xSum; 
		}
	}
	private static void calculateYDistance(List<Point> points)
	{
		Collections.sort(points, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				return (int) (o1.y - o2.y);
			}
		});
		long L[] = new long[points.size()+1];
		long R[] = new long[points.size()+2];
		L[0] = 0;
		L[1] = points.get(0).y;
		for (int i = 1; i<=points.size();i++) {
			L[i] = L[i-1] + points.get(i-1).y; 
		}
		
		for (int i = 1; i < L.length; i++) {
			R[i] = L[points.size()] - L[i-1];
		}

		for (int k = 1; k<=points.size();k++) {
			points.get(k-1).ySum = R[k+1] - L[k-1] - ((points.size() - 2*k + 1)* points.get(k-1).y); 
			points.get(k-1).mDistance += points.get(k-1).ySum; 
		}
	}
	public static List<Point> findMeetingPoint(List<Point> points)
	{
		calculateXDistance(points);
		calculateYDistance(points);
		
		List<Point> meetingPoints = new ArrayList<Point>();
		long min = Long.MAX_VALUE;
		for (Point p : points) {
			if(p.mDistance < min)
			{
				min = p.mDistance;
				meetingPoints.clear();
				meetingPoints.add(p);
			}
			if(p.mDistance == min)
			{
				meetingPoints.add(transform(p, true));
			}
		}
		
		return meetingPoints;
	}

	private static Point transform(Point p, boolean b) {
		
		Point ret = new Point(0,0);
		if(b)
		{
			ret.x = (p.x + p.y)/2;
			ret.y = (p.x - p.y)/2;
		}
		else
		{
			ret.x = (p.x + p.y);
			ret.y = (p.x - p.y);
		}
		return ret;
	}
	private static List<Point> transformPoints(List<Point> points) {
		List<Point> ret = new ArrayList<Point>();
		for (Point p : points) {
			ret.add(transform(p, false));
		}
		return ret;
	}
	public static long totalDistance(List<Point> points, Point center)
	{
		long distance = 0;
		for(Point p : points)
		{
			if(!center.equals(p))
			{
				distance += center.distance(p);
			}
		}

		return distance;
	}

	public static void main (String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


		try {
			List<Point> points = new ArrayList<Point>();
			long N = Long.parseLong(br.readLine());
			for ( int i = 0; i < N ;i++)
			{
				String line = br.readLine();
				String coordinates[] = line.split(" ");
				Point p = new Point(Long.parseLong(coordinates[0]),Long.parseLong(coordinates[1]));
				points.add(p);
			}

			List<Point> meetingPoints = findMeetingPoint(transformPoints(points));
			Long min = Long.MAX_VALUE;
			for (Point p : meetingPoints) {
				long temp = totalDistance(points, p );
				if(temp < min)
				{
					min = temp;
				}
			}
			System.out.println(min);
		} catch (IOException ioe) {
			System.out.println("IO error trying to read input!");
			System.exit(1);
		}

	}
}

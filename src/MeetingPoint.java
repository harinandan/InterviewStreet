import java.io.*;
import java.util.*;

public class MeetingPoint
{
	static class Point
	{
	    long x;
	    long y;
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
	    	return "[" + x+ "," + y + "]";
	    }
	}

    public static Point findMeetingPoint(List<Point> points)
    {
    	long minX;
    	long maxX;
    	long minY;
    	long maxY;
    	
		Long[] xArr = xList.toArray(new Long[xList.size()]);
		Long[] yArr = yList.toArray(new Long[yList.size()]);
		
		Arrays.sort(xArr);
		Arrays.sort(yArr);
    	if(xList.size()%2 == 0)
    	{
    		minX = xArr[xList.size()/2-1];
    		maxX = xArr[xList.size()/2];
    	}
    	else
    	{
    		minX = maxX =  xArr[xList.size()/2];
    	}
    	if(yList.size()%2 == 0)
    	{
    		minY = yArr[yList.size()/2-1];
    		maxY = yArr[yList.size()/2];
    	}
    	else
    	{
    		minY = maxY =  yArr[yList.size()/2];
    	}
    	
    	
    	List<Point> tPoints = new ArrayList<Point>(); 
    	for (Point p: points) {
			if(p.x >= minX && p.x <= maxX)
				tPoints.add(p);
		}
    	
    	
    	for (Point p: tPoints) {
			if(p.y >= minY && p.y <= maxY)
				return p;
		}
    	return null;
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
    
    static List<Long> xList = new ArrayList<Long>();
    static List<Long> yList = new ArrayList<Long>();
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
              xList.add(p.x);
              yList.add(p.y);
          }
          

          Point meetingPoint = findMeetingPoint(points);
          System.out.println(totalDistance(points, meetingPoint ));
      } catch (IOException ioe) {
         System.out.println("IO error trying to read input!");
         System.exit(1);
      }

    }
}

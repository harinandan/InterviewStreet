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

    public static List<Point> findNearestNeighbours(List<Point> points, Point p)
    {
        List<Point> neighbours = new ArrayList<Point>();
        
        long distance = Long.MAX_VALUE;
        for(Point p1 : points)
        {
        	if(!p1.equals(p))
        	{
	            long p1distance = p.distance(p1);
	            if(p1distance < distance)
	            {
	                distance = p1distance;
	                neighbours.clear();
	                neighbours.add(p1);
	            }
	            else if(p1distance == distance)
	            {
	                neighbours.add(p1);
	            }
        	}
        }          
        return neighbours;
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
      //  open up standard input
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


      //  read the username from the command-line; need to use try/catch with the
      //  readLine() method
      try {
          List<Point> points = new ArrayList<Point>();
          Point mean = new Point(0, 0);
          long N = Long.parseLong(br.readLine());
          for ( int i = 0; i < N ;i++)
          {
              String line = br.readLine();
              String coordinates[] = line.split(" ");
              Point p = new Point(Long.parseLong(coordinates[0]),Long.parseLong(coordinates[1]));
              points.add(p);
              mean.x += p.x; 
              mean.y += p.y;
          }
          mean.x /= N;
          mean.y /= N;
          
          Point center = mean;
          Point prevCenter = mean;
          long distance = Long.MAX_VALUE;
          while(true)
          {
              prevCenter = center;
              List<Point> neighbours = findNearestNeighbours(points, center);
              for (Point neighbour: neighbours)
              {
            	  if(!neighbour.v)
            	  {
	                  long neighbourTotalDistance = totalDistance(points, neighbour);
	                  if(neighbourTotalDistance < distance)
	                  {
	                      center = neighbour;
	                      distance = neighbourTotalDistance;
	                  }
	                  neighbour.visit();
            	  }
              }
              if(center.equals(prevCenter))
                  break;
          }

          System.out.println(distance);
      } catch (IOException ioe) {
         System.out.println("IO error trying to read input!");
         System.exit(1);
      }

    }
}

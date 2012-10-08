import java.io.*;
import java.util.*;

class Point
{
    long x;
    long y;
    
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
        return p.x == x && p.y == y; 
    }
}

public class MeetingPoint
{
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
          
          Point center = null;
          long distance = Long.MAX_VALUE;
          for(Point p1 : points)
          {
              long p1distance = mean.distance(p1);
              if(p1distance < distance)
              {
                  distance = p1distance;
                  center = p1;
              }
          }          
          
          distance = 0;
          for(Point p2 : points)
          {
              if(!center.equals(p2))
              {
                  distance += center.distance(p2);
              }
          }
          System.out.println(distance);
      } catch (IOException ioe) {
         System.out.println("IO error trying to read input!");
         System.exit(1);
      }

    }
}

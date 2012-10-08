/*
Please write complete compilable code.
Your class should be named Solution
Read input from standard input (STDIN) and print output to standard output(STDOUT).
For more details, please check http://www.interviewstreet.com/recruit/challenges/faq/view#stdio
*/
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
          long N = Long.parseLong(br.readLine());
          for ( int i = 0; i < N ;i++)
          {
              String line = br.readLine();
              String coordinates[] = line.split(" ");
              Point p = new Point(Long.parseLong(coordinates[0]),Long.parseLong(coordinates[1]));
              points.add(p);
          }
          
          long minDistance = Long.MAX_VALUE;
          for(Point p1 : points)
          {
              long distance = 0;
              for(Point p2 : points)
              {
                  if(!p1.equals(p2))
                  {
                      distance += p1.distance(p2);
                  }
              }
              if(distance < minDistance)
                  minDistance = distance;
          }
          System.out.println(minDistance);
      } catch (IOException ioe) {
         System.out.println("IO error trying to read input!");
         System.exit(1);
      }

    }
}

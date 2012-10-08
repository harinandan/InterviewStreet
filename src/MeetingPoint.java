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
        if(p == null) return false;
        return p.x == x && p.y == y; 
    }
    
    public String toString()
    {
        return "["+x+","+y+"]";
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
          int N = Integer.parseInt(br.readLine());
          Point [] pointsx = new Point[N];
          Point [] pointsy = new Point[N];
          for ( int i = 0; i < N ;i++)
          {
              String line = br.readLine();
              String coordinates[] = line.split(" ");
              Point p = new Point(Long.parseLong(coordinates[0]),Long.parseLong(coordinates[1]));
              pointsx[i] = p;
              pointsy[i] = p;
          }
          
          Arrays.sort(pointsx, new Comparator<Point>()
                      {
                          public int compare(Point o1, Point o2) 
                          {
                              if(o1.x < o2.x)
                                  return -1;
                              else if (o1.x > o2.x)
                                  return 1;
                              else
                              {
                                  if(o1.y < o2.y)
                                      return -1;
                                  else if (o1.y > o2.y)
                                      return 1;
                                  else
                                      return 0;
                              }
                          }
                      });
          Arrays.sort(pointsy, new Comparator<Point>()
                      {
                          public int compare(Point o1, Point o2) 
                          {
                              if(o1.y < o2.y)
                                  return -1;
                              else if (o1.y > o2.y)
                                  return 1;
                              else
                              {
                                  if(o1.x < o2.x)
                                      return -1;
                                  else if (o1.x > o2.x)
                                      return 1;
                                  else
                                      return 0;
                              }
                          }
                      });
          Point p1 = null;
          Point p2 = null;
          
          
          int i = 0;
          int j = 0;
          if(N%2 == 0)
          {
              i = N/2 - 1;
              j = N/2;
          }
          else
          {
              if(pointsx[N/2].equals(pointsy[N/2]))
              {
                  i = N/2;
                  j = N/2;
              }
              else
              {
                  i = N/2 - 1;
                  j = N/2 + 1;
              }
          }
          for(;i>=0;i--,j++)
          {
              if(pointsx[i].equals(pointsy[i]))
              {
                  p1 = pointsx[i];
                  if(pointsx[j].equals(pointsy[j]))
                  {
                      p2 = pointsx[j];
                  }
                  break;
              }
              if(pointsx[j].equals(pointsy[j]))
              {
                  p2 = pointsx[j];
                  break;
              }
          }
              
          long minDistancep1 = Long.MAX_VALUE;
          long minDistancep2 = Long.MAX_VALUE;
          if(p1 != null)
          {
              minDistancep1 = distance(p1, pointsx);
          }
          if (p2 != null && !p2.equals(p1))
          {
              minDistancep2 = distance(p2, pointsx);
          }

          System.out.println(Math.min(minDistancep1 ,minDistancep2));
      } catch (IOException ioe) {
         System.out.println("IO error trying to read input!");
         System.exit(1);
      }

    }
    
    public static long distance(Point p1, Point[] points)
    {
        long dist = 0;
        for(Point p: points)
        {
            dist += p1.distance(p);
        }
        return dist;
    }
}

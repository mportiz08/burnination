package com.marcusortiz.burnination;

import android.graphics.Point;
import android.graphics.PointF;

public class Geometry
{
  public static double getDistance(Point a, Point b)
  { 
    return Math.sqrt( (b.y - a.y)*(b.y - a.y) + (b.x - a.x)*(b.x - a.x) );
  }
  
  public static double getDistance(PointF a, PointF b)
  {
    return Math.sqrt( (b.y - a.y)*(b.y - a.y) + (b.x - a.x)*(b.x - a.x) );
  }
  
  public static Direction findDir(Point a, Point b)
  {
    int xDir = 0;
    int yDir = 0;
    int xlen = b.x - a.x;
    int ylen = b.y - a.y;
    double halfPi = Math.PI / 2;
    double twoPi = 2 * Math.PI;
    double angle = Math.atan2(ylen, xlen);
    
    // my old friend, the unit circle
    if(angle == 0 || angle == twoPi)
    {
      xDir = Direction.RIGHT;
      yDir = 0;
    }
    else if(angle > 0 && angle < halfPi)
    {
      xDir = Direction.RIGHT;
      yDir = Direction.UP;
    }
    else if(angle == halfPi)
    {
      xDir = 0;
      yDir = Direction.UP;
    }
    else if(angle > halfPi && angle < Math.PI)
    {
      xDir = Direction.LEFT;
      yDir = Direction.UP;
    }
    else if(angle == Math.PI)
    {
      xDir = Direction.LEFT;
      yDir = 0;
    }
    else if(angle < 0 && angle > (-(halfPi)))
    {
      xDir = Direction.RIGHT;
      yDir = Direction.DOWN;
    }
    else if(angle == -(halfPi))
    {
      xDir = 0;
      yDir = Direction.DOWN;
    }
    else if(angle < (-(halfPi)) && angle > (-(Math.PI)))
    {
      xDir = Direction.LEFT;
      yDir = Direction.DOWN;
    }
    else if(angle == -(Math.PI))
    {
      xDir = Direction.LEFT;
      yDir = 0;
    }
    
    return new Direction(xDir, yDir);
  }
}

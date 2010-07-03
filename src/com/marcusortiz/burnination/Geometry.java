package com.marcusortiz.burnination;

import android.graphics.Point;
import android.graphics.PointF;

public class Geometry
{
  public static final double halfPi = Math.PI / 2;
  
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
    int xlen = b.x - a.x;
    int ylen = b.y - a.y;
    
    int xDir = (xlen > 0) ? Direction.RIGHT : Direction.LEFT;
    int yDir = (ylen > 0) ? Direction.DOWN : Direction.UP;
    
    return new Direction(xDir, yDir);
  }
}

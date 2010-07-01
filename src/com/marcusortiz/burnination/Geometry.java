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
}

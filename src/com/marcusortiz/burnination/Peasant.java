package com.marcusortiz.burnination;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class Peasant extends Sprite
{
  public static final int SEGMENT_SPACING = 10;
  public static final int SPEED = 1;
  public static final int INIT_COUNT = 3;
  
  private View view;
  private Path line = null;
  private LinkedList<Point> waypoints = null;
  private boolean lineCompleted = false;
  private Direction newDir = null;
  private Velocity savedVel = null;
  private Point lastPoint = null;
  
  public Peasant(Bitmap bitmap, Point location)
  {
    super(bitmap, location);
  }

  public Peasant(Bitmap bitmap, Point location, Velocity speed, View view)
  {
    super(bitmap, location, speed);
    this.view = view;
  }

  @Override
  public void update()
  {
    super.checkBorders(view);
    checkCollisions();
    followLine();
  }

  public void checkCollisions()
  {
    // TODO: check for collisions with trogdor
  }
  
  public void drawLine(MotionEvent event, List<Path> lines)
  { 
    if(event.getAction() == MotionEvent.ACTION_DOWN)
    {
      Bitmap bmp = getGraphic();
      Point loc = getLocation();
      int width = bmp.getWidth();
      int height = bmp.getHeight();
      int x = (int)event.getX();
      int y = (int)event.getY();
      Velocity currVel = getVelocity();
      
      if( (x >= (loc.x - width) && x <= (loc.x + width*2)) &&
          (y >= (loc.y - height) && y <= (loc.y + height*2)))
      {
        savedVel = currVel;
        setVelocity(0, 0, currVel.getxDir(), currVel.getyDir());
        waypoints = new LinkedList<Point>();
        line = new Path();
        line.moveTo((float)x, (float)y);
        lastPoint = new Point(x, y);
        waypoints.add(lastPoint);
      }
    }
    
    if(event.getAction() == MotionEvent.ACTION_MOVE && line != null && waypoints != null)
    {
      int x = (int)event.getX();
      int y = (int)event.getY();
      Point curr = new Point(x, y);
      
      if(Geometry.getDistance(curr, lastPoint) > SEGMENT_SPACING)
      {
        line.incReserve(1);
        line.lineTo((float)x, (float)y);
        waypoints.add(curr);
      }
    }
    
    if(event.getAction() == MotionEvent.ACTION_UP && line != null && waypoints != null)
    {
      int x = (int)event.getX();
      int y = (int)event.getY();
      
      line.lineTo((float)x, (float)y);
      line.setLastPoint((float)x, (float)y);
      
      int numPoints = waypoints.size();
      if(numPoints > 1)
      {
        newDir = Geometry.findDir(waypoints.get(numPoints - 2), waypoints.get(numPoints - 1));
      }
      
      waypoints.add(new Point(x, y));
      lines.add(line);
      line = null;
      lineCompleted = true;
      lastPoint = null;
    }
  }
  
  public void followLine()
  {
    if(waypoints != null && !waypoints.isEmpty() && lineCompleted)
    {
      setLocation(waypoints.removeFirst());
    }
    else if(waypoints != null && waypoints.isEmpty() && lineCompleted)
    {
      waypoints = null;
      lineCompleted = false;
      //setVelocity(savedVel);
      if(newDir != null)
      {
        setVelocity(SPEED, SPEED, newDir.xDir, newDir.yDir);
        newDir = null;
      }
      else
      {
        setVelocity(savedVel);
      }
    }
  }
}

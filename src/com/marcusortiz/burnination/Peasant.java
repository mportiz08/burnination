package com.marcusortiz.burnination;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class Peasant extends Sprite
{
  public static final int SEGMENT_SPACING = 50;
  
  private View view;
  private Path line = null;
  private Velocity savedVel;
  private Point lastPoint = null;
  
  public Peasant(Bitmap bitmap)
  {
    super(bitmap);
  }

  public Peasant(Bitmap bitmap, Velocity speed, View view)
  {
    super(bitmap, speed);
    this.view = view;
  }

  @Override
  public void update()
  {
    super.checkBorders(view);
    checkCollisions();
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
        line = new Path();
        line.moveTo((float)x, (float)y);
        lastPoint = new Point(x, y);
      }
    }
    
    if(event.getAction() == MotionEvent.ACTION_MOVE && line != null)
    {
      int x = (int)event.getX();
      int y = (int)event.getY();
      Point curr = new Point(x, y);
      
      if(Geometry.getDistance(curr, lastPoint) > SEGMENT_SPACING)
      {
        line.incReserve(1);
        line.lineTo((float)x, (float)y);
      }
    }
    
    if(event.getAction() == MotionEvent.ACTION_UP && line != null)
    {
      int x = (int)event.getX();
      int y = (int)event.getY();
      
      line.lineTo((float)x, (float)y);
      line.setLastPoint((float)x, (float)y);
      lines.add(line);
      line = null;
      setVelocity(savedVel);
      lastPoint = null;
    }
  }
}

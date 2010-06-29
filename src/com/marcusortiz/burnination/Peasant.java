package com.marcusortiz.burnination;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class Peasant extends Sprite
{
  private View view;
  private Path line = null;
  
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
      if( (x >= (loc.x - width) && x <= (loc.x + width*2)) &&
          (y >= (loc.y - height) && y <= (loc.y + height*2)))
      {
        setVelocity(0, 0, getVelocity().getxDir(), getVelocity().getyDir());
        line = new Path();
        line.moveTo(event.getX(), event.getY());
      }
    }
    
    if(event.getAction() == MotionEvent.ACTION_MOVE && line != null)
    {
      line.lineTo(event.getX(), event.getY());
    }
    
    if(event.getAction() == MotionEvent.ACTION_UP && line != null)
    {
      line.lineTo(event.getX(), event.getY());
      line.setLastPoint(event.getX(), event.getY());
      lines.add(line);
      line = null;
    }
  }
}

package com.marcusortiz.burnination;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public abstract class Sprite
{ 
  private Bitmap bitmap;
  private Point location;
  private Velocity velocity;
  private Rect rect;

  public Sprite(Bitmap bitmap, Point location)
  {
    this.bitmap = bitmap;
    this.location = new Point(location);
    this.velocity = new Velocity();
    this.rect = new Rect(this.location.x, this.location.y,
      this.location.x + this.bitmap.getWidth(), this.location.y + this.bitmap.getHeight());
  }
  
  public Sprite(Bitmap bitmap, Point location, Velocity speed)
  {
    this.bitmap = bitmap;
    this.location = new Point(location);
    this.rect = new Rect(this.location.x, this.location.y,
        this.location.x + this.bitmap.getWidth(), this.location.y + this.bitmap.getHeight());
    this.velocity = new Velocity(speed.getX(), speed.getY(), speed.getxDir(), speed.getyDir());
  }

  public Bitmap getGraphic()
  {
    return bitmap;
  }
  
  public void setGraphic(Bitmap graphic)
  {
    bitmap = graphic;
  }

  public Point getLocation()
  {
    return location;
  }
  
  public void setLocation(int x, int y)
  {
    location.set(x, y);
    rect.set(location.x, location.y, location.x + bitmap.getWidth(), location.y + bitmap.getHeight());
  }
  
  public void setLocation(Point point)
  {
    location.set(point.x, point.y);
    rect.set(location.x, location.y, location.x + bitmap.getWidth(), location.y + bitmap.getHeight());
  }
  
  public Rect getRect()
  {
    return rect;
  }

  public Velocity getVelocity()
  {
    return velocity;
  }
  
  public void setVelocity(int dx, int dy, int xDir, int yDir)
  {
    this.velocity = new Velocity(dx, dy, xDir, yDir);
  }
  
  public void setVelocity(Velocity other)
  {
    setVelocity(other.getX(), other.getY(), other.getxDir(), other.getyDir());
  }
  
  public Point getRandomPoint(View view)
  {
    Point point = new Point();
    Random gen = new Random();
    Bitmap graphic = getGraphic();
    
    int x = gen.nextInt(view.getWidth() - graphic.getWidth());
    int y = gen.nextInt(view.getHeight() - graphic.getHeight());
    
    point.set(x, y);
    
    return point;
  }
  
  public void checkBorders(View view)
  {
    Bitmap graphic = getGraphic();
    int gWidth = graphic.getWidth();
    int gHeight = graphic.getHeight();
    int vWidth = view.getWidth();
    int vHeight = view.getHeight();

    // Direction
    if(velocity.getxDir() == Direction.RIGHT)
    {
      location.x = location.x + velocity.getX();
    }
    else
    {
      location.x = location.x - velocity.getX();
    }
    if(velocity.getyDir() == Direction.DOWN)
    {
      location.y = location.y + velocity.getY();
    }
    else
    {
      location.y = location.y - velocity.getY();
    }

    // X Borders
    if(location.x < 0)
    {
      velocity.toggleXDir();
      location.x = 0 - location.x;
    }
    else
      if( (location.x + gWidth) > vWidth )
      {
        velocity.toggleXDir();
        location.x = location.x + vWidth - (location.x + gWidth);
      }

    // Y Borders
    if(location.y < 0)
    {
      velocity.toggleYDir();
      location.y = 0 - location.y;
    }
    else
      if( (location.y + gHeight) > vHeight )
      {
        velocity.toggleYDir();
        location.y = location.y + vHeight - (location.y + gHeight);
      }
  }
  
  public abstract void update();
}

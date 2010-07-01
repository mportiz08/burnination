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
    Point loc = getLocation();
    Velocity speed = getVelocity();
    Bitmap graphic = getGraphic();
    int width = graphic.getWidth();
    int height = graphic.getHeight();

    // Direction
    if(speed.getxDir() == Direction.RIGHT)
    {
      loc.x = loc.x + speed.getX();
    }
    else
    {
      loc.x = loc.x - speed.getX();
    }
    if(speed.getyDir() == Direction.DOWN)
    {
      loc.y = loc.y + speed.getY();
    }
    else
    {
      loc.y = loc.y - speed.getY();
    }

    // X Borders
    if(loc.x < 0)
    {
      speed.toggleXDir();
      loc.x = 0 - loc.x;
    }
    else
      if(loc.x + width > view.getWidth())
      {
        speed.toggleXDir();
        loc.x = loc.x + view.getWidth() - (loc.x + width);
      }

    // Y Borders
    if(loc.y < 0)
    {
      speed.toggleYDir();
      loc.y = 0 - loc.y;
    }
    else
      if(loc.y + height > view.getHeight())
      {
        speed.toggleYDir();
        loc.y = loc.y + view.getHeight() - (loc.y + height);
      }
  }
  
  public abstract void update();
}

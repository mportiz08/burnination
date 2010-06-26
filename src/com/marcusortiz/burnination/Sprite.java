package com.marcusortiz.burnination;

import android.graphics.Bitmap;
import android.view.View;

public abstract class Sprite
{ 
  private Bitmap bitmap;
  private Location location;
  private Speed speed;

  public Sprite(Bitmap bitmap)
  {
    this.bitmap = bitmap;
    this.location = new Location();
    this.speed = new Speed();
  }
  
  public Sprite(Bitmap bitmap, Speed speed)
  {
    this.bitmap = bitmap;
    this.location = new Location();
    this.speed = new Speed(speed.getX(), speed.getY(), speed.getxDir(), speed.getyDir());
  }

  public Bitmap getGraphic()
  {
    return bitmap;
  }
  
  public void setGraphic(Bitmap graphic)
  {
    bitmap = graphic;
  }

  public Location getLocation()
  {
    return location;
  }
  
  public void setLocation(int x, int y)
  {
    location.setX(x);
    location.setY(y);
  }

  public Speed getSpeed()
  {
    return speed;
  }
  
  public void checkBorders(View view)
  {
    Location loc = getLocation();
    Speed speed = getSpeed();
    Bitmap graphic = getGraphic();
    int width = graphic.getWidth();
    int height = graphic.getHeight();

    // Direction
    if(speed.getxDir() == Direction.RIGHT)
    {
      loc.setX(loc.getX() + speed.getX());
    }
    else
    {
      loc.setX(loc.getX() - speed.getX());
    }
    if(speed.getyDir() == Direction.DOWN)
    {
      loc.setY(loc.getY() + speed.getY());
    }
    else
    {
      loc.setY(loc.getY() - speed.getY());
    }

    // X Borders
    if(loc.getX() < 0)
    {
      speed.toggleXDir();
      loc.setX(-loc.getX());
    }
    else
      if(loc.getX() + width > view.getWidth())
      {
        speed.toggleXDir();
        loc.setX(loc.getX() + view.getWidth() - (loc.getX() + width));
      }

    // Y Borders
    if(loc.getY() < 0)
    {
      speed.toggleYDir();
      loc.setY(-loc.getY());
    }
    else
      if(loc.getY() + height > view.getHeight())
      {
        speed.toggleYDir();
        loc.setY(loc.getY() + view.getHeight() - (loc.getY() + height));
      }
  }
  
  public abstract void update();
}

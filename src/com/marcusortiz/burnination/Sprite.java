package com.marcusortiz.burnination;

import android.graphics.Bitmap;

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
  
  public Sprite(Bitmap bitmap, int dx, int dy, int xDir, int yDir)
  {
    this.bitmap = bitmap;
    this.location = new Location();
    this.speed = new Speed(dx, dy, xDir, yDir);
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
  
  public abstract void update();
}

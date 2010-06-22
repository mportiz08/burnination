package com.marcusortiz.burnination;

import android.graphics.Bitmap;

public class Sprite
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

  public class Location
  {
    private int x = 0;
    private int y = 0;

    public int getX()
    {
      return x + bitmap.getWidth() / 2;
    }

    public void setX(int x)
    {
      this.x = x - bitmap.getWidth() / 2;
    }

    public int getY()
    {
      return y + bitmap.getHeight() / 2;
    }

    public void setY(int y)
    {
      this.y = y - bitmap.getHeight() / 2;
    }
  }

  public class Speed
  {
    public static final int X_DIRECTION_RIGHT = 1;
    public static final int X_DIRECTION_LEFT = -1;
    public static final int Y_DIRECTION_DOWN = 1;
    public static final int Y_DIRECTION_UP = -1;

    private int x = 1;
    private int y = 1;
    private int xDir = X_DIRECTION_RIGHT;
    private int yDir = Y_DIRECTION_DOWN;

    public int getxDir()
    {
      return xDir;
    }

    public void setxDir(int xDir)
    {
      this.xDir = xDir;
    }

    public void toggleXDir()
    {
      xDir = (xDir == X_DIRECTION_RIGHT) ? X_DIRECTION_LEFT : X_DIRECTION_RIGHT;
    }

    public int getyDir()
    {
      return yDir;
    }

    public void setyDir(int yDir)
    {
      this.yDir = yDir;
    }

    public void toggleYDir()
    {
      yDir = (yDir == Y_DIRECTION_DOWN) ? Y_DIRECTION_UP : Y_DIRECTION_DOWN;
    }

    public int getX()
    {
      return x;
    }

    public void setX(int x)
    {
      this.x = x;
    }

    public int getY()
    {
      return y;
    }

    public void setY(int y)
    {
      this.y = y;
    }

    @Override
    public String toString()
    {
      String xDir = (this.xDir == X_DIRECTION_RIGHT) ? "right" : "left";
      return "Speed: x: " + this.x + " | y: " + this.y + " | xDirection: " + xDir;
    }
  }
}

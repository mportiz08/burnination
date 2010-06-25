package com.marcusortiz.burnination;

import android.graphics.Bitmap;
import android.view.View;

public class Trogdor extends Sprite
{
  private View view;
  
  public Trogdor(Bitmap bitmap)
  {
    super(bitmap);
  }

  public Trogdor(Bitmap bitmap, int dx, int dy, int xDir, int yDir, View view)
  {
    super(bitmap, dx, dy, xDir, yDir);
    this.view = view;
  }

  public void update()
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
}

package com.marcusortiz.burnination;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;

public class Trogdor extends Sprite
{
  public static final int SPEED = 2;
  
  private View view;
  
  public Trogdor(Bitmap bitmap, Point location)
  {
    super(bitmap, location);
  }

  public Trogdor(Bitmap bitmap, Point location, Velocity speed, View view)
  {
    super(bitmap, location, speed);
    this.view = view;
  }

  public void update()
  {
    super.checkBorders(view);
  }
}

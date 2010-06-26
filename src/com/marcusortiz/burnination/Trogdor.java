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

  public Trogdor(Bitmap bitmap, Velocity speed, View view)
  {
    super(bitmap, speed);
    this.view = view;
  }

  public void update()
  {
    super.checkBorders(view);
  }
}

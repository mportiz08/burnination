package com.marcusortiz.burnination;

import android.graphics.Bitmap;
import android.view.View;

public class Peasant extends Sprite
{
  private View view;
  
  public Peasant(Bitmap bitmap)
  {
    super(bitmap);
  }

  public Peasant(Bitmap bitmap, Speed speed, View view)
  {
    super(bitmap, speed);
    this.view = view;
  }

  @Override
  public void update()
  {
    super.checkBorders(view);
  }

}

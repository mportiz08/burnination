package com.marcusortiz.burnination;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback
{
  public Panel(Context context)
  {
    super(context);
    getHolder().addCallback(this);
    setFocusable(true);
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder)
  {
    // TODO Auto-generated method stub
    
  }
}

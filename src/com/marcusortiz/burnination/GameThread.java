package com.marcusortiz.burnination;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
  private SurfaceHolder sHolder;
  private MainView panel;
  private boolean isRunning;
  
  public GameThread(SurfaceHolder sHolder, MainView panel)
  {
    this.sHolder = sHolder;
    this.panel = panel;
  }
  
  public void setRunning(boolean isRunning)
  {
    this.isRunning = isRunning;
  }
  
  public SurfaceHolder getSurfaceHolder()
  {
    return sHolder;
  }

  @Override
  public void run()
  {
    Canvas canvas;
    while(isRunning)
    {
      canvas = null;
      try
      {
        canvas = sHolder.lockCanvas(null);
        synchronized(sHolder)
        {
          for(Sprite s : panel.getSprites())
          {
            s.update();
          }
          panel.onDraw(canvas);
        }
      }
      finally
      {
        if(canvas != null)
        {
          sHolder.unlockCanvasAndPost(canvas);
        }
      }
    }
  }
}

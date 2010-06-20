package com.marcusortiz.burnination;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback
{
  private GameThread thread;
  private Map<Integer, Bitmap> bitmapCache;
  
  public Panel(Context context)
  {
    super(context);
    fillCache();
    getHolder().addCallback(this);
    this.thread = new GameThread(getHolder(), this);
    setFocusable(true);
  }
  
  private void fillCache()
  {
    bitmapCache = new HashMap<Integer, Bitmap>();
    bitmapCache.put(R.drawable.trogdor, BitmapFactory.decodeResource(getResources(), R.drawable.trogdor));
    bitmapCache.put(R.drawable.background, BitmapFactory.decodeResource(getResources(), R.drawable.background));
  }

  @Override
  protected void onDraw(Canvas canvas)
  {
    canvas.drawBitmap(bitmapCache.get(R.drawable.background), 0, 0, null);
    canvas.drawBitmap(bitmapCache.get(R.drawable.trogdor), 0, 0, null);
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder)
  {
    thread.setRunning(true);
    thread.start();
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder)
  {
    boolean retry = true;
    thread.setRunning(false);
    while(retry)
    {
      try
      {
        thread.join();
        retry = false;
      }
      catch(InterruptedException e) {} // TODO: log exception
    }
  }
}

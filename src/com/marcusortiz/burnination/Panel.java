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
  public static final int TROGDOR_SPEED = 2;
  public static final String DEBUG = "DEBUG";
  
  private GameThread thread;
  private Map<Integer, Bitmap> bitmapCache;
  private Sprite trogdor;
  
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
    bitmapCache.put(R.drawable.trogdorleft, BitmapFactory.decodeResource(getResources(), R.drawable.trogdorleft));
    bitmapCache.put(R.drawable.background, BitmapFactory.decodeResource(getResources(), R.drawable.background));
  }

  @Override
  protected void onDraw(Canvas canvas)
  {
    canvas.drawBitmap(bitmapCache.get(R.drawable.background), 0, 0, null);
    if(trogdor.getSpeed().getxDir() > 0)
    {
      canvas.drawBitmap(bitmapCache.get(R.drawable.trogdor), trogdor.getLocation().getX(), trogdor.getLocation().getY(), null);
    }
    else
    {
      canvas.drawBitmap(bitmapCache.get(R.drawable.trogdorleft), trogdor.getLocation().getX(), trogdor.getLocation().getY(), null);
    }
  }
  
  public void updateTrogdor()
  {
    Location loc = trogdor.getLocation();
    Speed speed = trogdor.getSpeed();
    Bitmap graphic = trogdor.getGraphic();
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
      if(loc.getX() + width > getWidth())
      {
        speed.toggleXDir();
        loc.setX(loc.getX() + getWidth() - (loc.getX() + width));
      }

    // Y Borders
    if(loc.getY() < 0)
    {
      speed.toggleYDir();
      loc.setY(-loc.getY());
    }
    else
      if(loc.getY() + height > getHeight())
      {
        speed.toggleYDir();
        loc.setY(loc.getY() + getHeight() - (loc.getY() + height));
      }
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder)
  {
    // start thread
    thread.setRunning(true);
    thread.start();
    
    // create trogdor
    trogdor = new Sprite(bitmapCache.get(R.drawable.trogdor), TROGDOR_SPEED, TROGDOR_SPEED, Direction.randomX(), Direction.randomY());
    trogdor.setLocation((this.getWidth() / 2) - (trogdor.getGraphic().getWidth() / 2), (this.getHeight() / 2) - (trogdor.getGraphic().getHeight() / 2));
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
      catch(InterruptedException e){}
    }
  }
}

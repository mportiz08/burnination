package com.marcusortiz.burnination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback
{
  public static final int TROGDOR_SPEED = 2;
  public static final String DEBUG = "DEBUG";
  
  private GameThread thread;
  private Map<Integer, Bitmap> bitmapCache;
  private ArrayList<Sprite> sprites;
  private Trogdor trogdor;
  private Path line = null;
  private boolean completeLine = false;
  
  public MainView(Context context)
  {
    super(context);
    fillCache();
    getHolder().addCallback(this);
    this.thread = new GameThread(getHolder(), this);
    setFocusable(true);
  }
  
  public ArrayList<Sprite> getSprites()
  {
    return sprites;
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
    
    if(completeLine)
    {
      Paint linePaint = new Paint();
      linePaint.setStyle(Paint.Style.STROKE);
      linePaint.setStrokeWidth(2);
      linePaint.setColor(Color.WHITE);
      linePaint.setShadowLayer(1, 1, 1, Color.BLACK);
      linePaint.setAntiAlias(true);
      
      canvas.drawPath(line, linePaint);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    synchronized(thread.getSurfaceHolder())
    {
      if(event.getAction() == MotionEvent.ACTION_DOWN)
      {
        line = new Path();
        line.moveTo(event.getX(), event.getY());
      }
      if(event.getAction() == MotionEvent.ACTION_MOVE)
      {
        line.lineTo(event.getX(), event.getY());
      }
      if(event.getAction() == MotionEvent.ACTION_UP)
      {
        line.lineTo(event.getX(), event.getY());
        line.setLastPoint(event.getX(), event.getY());
        //line.close();
        completeLine = true;
      }
    }
    
    return true;
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
    
    sprites = new ArrayList<Sprite>();
    
    // create trogdor
    trogdor = new Trogdor(bitmapCache.get(R.drawable.trogdor), 0, 0, Direction.RIGHT, 0, this);
    trogdor.setLocation((this.getWidth() / 2) - (trogdor.getGraphic().getWidth() / 2), (this.getHeight() / 2) - (trogdor.getGraphic().getHeight() / 2));
    sprites.add(trogdor);
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

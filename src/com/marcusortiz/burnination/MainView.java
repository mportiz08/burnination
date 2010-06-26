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
  private Map<ID, Bitmap> bitmapCache;
  private ArrayList<Sprite> sprites;
  private Trogdor trogdor;
  private Path line;
  private ArrayList<Path> lines;
  
  public enum ID
  {
    TROGDOR_R, TROGDOR_L, PEASANT, BACKGROUND;
  }
  
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
    bitmapCache = new HashMap<ID, Bitmap>();
    bitmapCache.put(ID.TROGDOR_R, BitmapFactory.decodeResource(getResources(), R.drawable.trogdor));
    bitmapCache.put(ID.TROGDOR_L, BitmapFactory.decodeResource(getResources(), R.drawable.trogdorleft));
    bitmapCache.put(ID.PEASANT, BitmapFactory.decodeResource(getResources(), R.drawable.peasant));
    bitmapCache.put(ID.BACKGROUND, BitmapFactory.decodeResource(getResources(), R.drawable.background));
  }

  @Override
  protected void onDraw(Canvas canvas)
  {
    canvas.drawBitmap(bitmapCache.get(ID.BACKGROUND), 0, 0, null);
    if(trogdor.getSpeed().getxDir() > 0)
    {
      canvas.drawBitmap(bitmapCache.get(ID.TROGDOR_R), trogdor.getLocation().x, trogdor.getLocation().y, null);
    }
    else
    {
      canvas.drawBitmap(bitmapCache.get(ID.TROGDOR_L), trogdor.getLocation().x, trogdor.getLocation().y, null);
    }
    
    if(!lines.isEmpty())
    {
      Paint linePaint = new Paint();
      linePaint.setStyle(Paint.Style.STROKE);
      linePaint.setStrokeWidth(2);
      linePaint.setColor(Color.WHITE);
      linePaint.setShadowLayer(1, 1, 1, Color.BLACK);
      linePaint.setAntiAlias(true);
      
      canvas.drawPath(lines.get(lines.size() - 1), linePaint);
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
        lines.add(line);
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
    lines = new ArrayList<Path>();
    
    // create trogdor
    Velocity trogdorSpeed = new Velocity(TROGDOR_SPEED, TROGDOR_SPEED, Direction.RIGHT, Direction.DOWN);
    trogdor = new Trogdor(bitmapCache.get(ID.TROGDOR_R), trogdorSpeed, this);
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

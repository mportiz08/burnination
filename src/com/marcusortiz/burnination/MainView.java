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
  public static final int SPEED_TROGDOR = 2;
  public static final int SPEED_PEASANT = 1;
  public static final int NUM_PEASANTS = 3;
  public static final String DEBUG = "DEBUG";
  
  private GameThread thread;
  private Map<ID, Bitmap> bitmapCache;
  private ArrayList<Sprite> sprites;
  private ArrayList<Peasant> peasants;
  private Trogdor trogdor;
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
    // draw background
    canvas.drawBitmap(bitmapCache.get(ID.BACKGROUND), 0, 0, null);
    
    // draw lines
    if(lines != null && !lines.isEmpty())
    {
      Paint linePaint = new Paint();
      linePaint.setStyle(Paint.Style.STROKE);
      linePaint.setStrokeWidth(2);
      linePaint.setColor(Color.WHITE);
      linePaint.setShadowLayer(1, 1, 1, Color.BLACK);
      linePaint.setAntiAlias(true);
      
      canvas.drawPath(lines.get(lines.size() - 1), linePaint);
    }
    
    // draw peasants
    for(Peasant p : peasants)
    {
      canvas.drawBitmap(bitmapCache.get(ID.PEASANT), p.getLocation().x, p.getLocation().y, null);
    }
    
    // draw trogdor
    if(trogdor.getVelocity().getxDir() > 0)
    {
      canvas.drawBitmap(bitmapCache.get(ID.TROGDOR_R), trogdor.getLocation().x, trogdor.getLocation().y, null);
    }
    else
    {
      canvas.drawBitmap(bitmapCache.get(ID.TROGDOR_L), trogdor.getLocation().x, trogdor.getLocation().y, null);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    synchronized(thread.getSurfaceHolder())
    {
      for(Peasant p : peasants)
      {
          p.drawLine(event, lines);
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
    Velocity trogdorVel = new Velocity(SPEED_TROGDOR, SPEED_TROGDOR, Direction.RIGHT, Direction.DOWN);
    trogdor = new Trogdor(bitmapCache.get(ID.TROGDOR_R), trogdorVel, this);
    trogdor.setLocation((this.getWidth() / 2) - (trogdor.getGraphic().getWidth() / 2), (this.getHeight() / 2) - (trogdor.getGraphic().getHeight() / 2));
    sprites.add(trogdor);
    
    // create peasants
    peasants = new ArrayList<Peasant>();
    for(int i = 0; i < NUM_PEASANTS; i++)
    {
      Velocity pVel = new Velocity(SPEED_PEASANT, SPEED_PEASANT, Direction.randomX(), Direction.randomY());
      Peasant p = new Peasant(bitmapCache.get(ID.PEASANT), pVel, this);
      p.setLocation(p.getRandomPoint(this).x, p.getRandomPoint(this).y);
      peasants.add(p);
    }
    sprites.addAll(peasants);
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

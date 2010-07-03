package com.marcusortiz.burnination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback
{
  public static final int TRANSPARENCY = 50;
  public static final int CORNER_RADIUS = 50;
  public static final String DEBUG = "DEBUG";
  
  private MediaPlayer song;
  private GameThread thread;
  private Map<ID, Bitmap> bitmapCache;
  private ArrayList<Sprite> sprites;
  private ArrayList<Peasant> peasants;
  private Trogdor trogdor;
  private ArrayList<Path> lines;
  
  public enum ID
  {
    TROGDOR_R, TROGDOR_L, PEASANT_L, PEASANT_R, PEASANT_F, PEASANT_B, BACKGROUND;
  }
  
  public MainView(Context context)
  {
    super(context);
    fillCache();
    song = MediaPlayer.create(getContext(), R.raw.trogdor);
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
    bitmapCache.put(ID.PEASANT_R, BitmapFactory.decodeResource(getResources(), R.drawable.peasant_right));
    bitmapCache.put(ID.PEASANT_L, BitmapFactory.decodeResource(getResources(), R.drawable.peasant_left));
    bitmapCache.put(ID.PEASANT_F, BitmapFactory.decodeResource(getResources(), R.drawable.peasant_front));
    bitmapCache.put(ID.PEASANT_B, BitmapFactory.decodeResource(getResources(), R.drawable.peasant_back));
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
      linePaint.setAlpha(TRANSPARENCY);
      linePaint.setPathEffect(new CornerPathEffect(CORNER_RADIUS));
      linePaint.setAntiAlias(true);
      
      canvas.drawPath(lines.get(lines.size() - 1), linePaint);
    }
    
    // draw peasants
    for(Peasant p : peasants)
    {
      if(p.getVelocity().getyDir() > 0)
      {
        canvas.drawBitmap(bitmapCache.get(ID.PEASANT_F), p.getLocation().x, p.getLocation().y, null);
      }
      else
      {
        canvas.drawBitmap(bitmapCache.get(ID.PEASANT_B), p.getLocation().x, p.getLocation().y, null);
      }
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
    sprites = new ArrayList<Sprite>();
    lines = new ArrayList<Path>();
    Point middle = new Point((this.getWidth() / 2) - (bitmapCache.get(ID.TROGDOR_L).getWidth() / 2),
                             (this.getHeight() / 2) - (bitmapCache.get(ID.TROGDOR_L).getHeight() / 2));
    
    // create trogdor
    Velocity trogdorVel = new Velocity(Trogdor.SPEED, Trogdor.SPEED, Direction.RIGHT, Direction.DOWN);
    trogdor = new Trogdor(bitmapCache.get(ID.TROGDOR_R), middle, trogdorVel, this);
    sprites.add(trogdor);
    
    // create peasants
    peasants = new ArrayList<Peasant>();
    for(int i = 0; i < Peasant.INIT_COUNT; i++)
    {
      Velocity pVel = new Velocity(Peasant.SPEED, Peasant.SPEED, Direction.randomX(), Direction.randomY());
      Peasant p = new Peasant(bitmapCache.get(ID.PEASANT_F), new Point(), pVel, this);
      p.setLocation(p.getRandomPoint(this).x, p.getRandomPoint(this).y);
      peasants.add(p);
    }
    sprites.addAll(peasants);
    
    // play song
    song.seekTo(0);
    song.start();
    
    // start thread
    thread.setRunning(true);
    thread.start();
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder)
  {
    song.stop();
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

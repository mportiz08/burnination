package com.marcusortiz.burnination;

public class Velocity
{
  private int dx;
  private int dy;
  private int xDir;
  private int yDir;
  
  public Velocity()
  {
    dx = 1;
    dy = 1;
    xDir = Direction.RIGHT;
    yDir = Direction.DOWN;
  }
  
  public Velocity(int dx, int dy, int xDir, int yDir)
  {
    this.dx = dx;
    this.dy = dy;
    this.xDir = xDir;
    this.yDir = yDir;
  }

  public int getxDir()
  {
    return xDir;
  }

  public void setxDir(int xDir)
  {
    this.xDir = xDir;
  }

  public void toggleXDir()
  {
    xDir = (xDir == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
  }

  public int getyDir()
  {
    return yDir;
  }

  public void setyDir(int yDir)
  {
    this.yDir = yDir;
  }

  public void toggleYDir()
  {
    yDir = (yDir == Direction.DOWN) ? Direction.UP : Direction.DOWN;
  }

  public int getX()
  {
    return dx;
  }

  public void setX(int dx)
  {
    this.dx = dx;
  }

  public int getY()
  {
    return dy;
  }

  public void setY(int dy)
  {
    this.dy = dy;
  }

  @Override
  public String toString()
  {
    String xDir = (this.xDir == Direction.RIGHT) ? "right" : "left";
    return "Speed: x: " + dx + " | y: " + dy + " | xDirection: " + xDir;
  }
}

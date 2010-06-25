package com.marcusortiz.burnination;

public class Location
{
  private int x;
  private int y;
  
  public Location()
  {
    x = 0;
    y = 0;
  }
  
  public Location(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public int getX()
  {
    return x;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public int getY()
  {
    return y;
  }

  public void setY(int y)
  {
    this.y = y;
  }
  
  public double getDistanceTo(Location other)
  {
    int x = other.getX();
    int y = other.getY();
    
    return Math.sqrt( (y - this.y)*(y - this.y) + (x - this.x)*(x - this.x) );
  }

  @Override
  public String toString()
  {
    return "(" + x + ", " + y + ")";
  }
}

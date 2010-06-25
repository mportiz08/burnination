package com.marcusortiz.burnination;

import java.util.Random;

public class Direction
{
  public static final int RIGHT = 1;
  public static final int LEFT = -1;
  public static final int DOWN = 1;
  public static final int UP = -1;
  
  public static int randomX()
  {
    int[] directions = {RIGHT, LEFT};
    Random generator = new Random();
    
    return directions[generator.nextInt(directions.length)];
  }
  
  public static int randomY()
  {
    int[] directions = {UP, DOWN};
    Random generator = new Random();
    
    return directions[generator.nextInt(directions.length)];
  }
}

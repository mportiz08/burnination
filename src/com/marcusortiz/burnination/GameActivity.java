package com.marcusortiz.burnination;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class GameActivity extends Activity
{ 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(new MainView(this));
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    finish(); // kill task to avoid bug when resuming activity
  }
}
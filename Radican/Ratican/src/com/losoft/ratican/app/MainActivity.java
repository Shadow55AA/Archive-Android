package com.losoft.ratican.app;

import com.losoft.ratican.engine.GameEngine;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity
{
	private GameEngine gengine;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gengine = new GameEngine(this);
        setContentView(gengine.glsview);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

	@Override
    public void onResume()
    {
    	gengine.onResume();
    	super.onResume();
    }
	
	@Override
    protected void onPause()
    {
    	gengine.onPaused();
    	super.onPause();
    }
    
    @Override
    public void onBackPressed()
    {
    	gengine.onBackPressed();
    	super.onBackPressed();
    }
  
}

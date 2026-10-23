package com.lotech.gameexample;

import com.lotech.gameexample.GameApp;
import com.lotech.gameexample.GameEngine;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameExample extends Activity
{
	private GameEngine gengine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GameApp gapp = (GameApp)getApplication();
        gapp.gengine = new GameEngine(this);
        gengine = gapp.gengine;
        setContentView(gengine.glsview);
        //setContentView(R.layout.activity_game_example);
    }
    
    @Override
    public void onRestart()
    {
    	super.onRestart();
    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	GameApp gapp = (GameApp)getApplication();
        gapp.gengine = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_game_example, menu);
        return true;
    }
    
}

package com.lotech.tubesurfer;

import com.lotech.tubesurfer.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TubeSurfer extends Activity
{
	private GameEngine gengine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gengine = new GameEngine(this);
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
    	gengine.onResume();
    	super.onResume();
    }
    
    @Override
    public void onPause()
    {
    	gengine.onPause();
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
        gengine = null;
    	super.onDestroy();
    }
    
    @Override
    public void onBackPressed()
    {
    	gengine.onBackPressed();
    	super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_tube_surfer, menu);
        return true;
    }
    
}

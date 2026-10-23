package com.lotech.androidstates;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class Page2Activity extends Activity
{
	public static String aname = "ActivityStates";
	private StatesApp sapp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page2);
        Log.v(aname,"P2onCreate()");
        sapp = (StatesApp)getApplication();
        Log.v(aname,"P2count="+sapp.counter);
        sapp.counter++;
	}
	
	@Override
    public void onStart()
    {
    	super.onStart();
        Log.v(aname,"P2onStart()");
    }

    @Override
    public void onResume()
    {
    	super.onResume();
        Log.v(aname,"P2onResume()");
        Log.v(aname,"P2count="+sapp.counter);
    }

    @Override
    public void onPause()
    {
    	super.onPause();
        Log.v(aname,"P2onPause()");
    }

    @Override
    public void onStop()
    {
    	super.onStop();
        Log.v(aname,"P2onStop()");
    }

    @Override
    public void onRestart()
    {
    	super.onRestart();
        Log.v(aname,"P2onRestart()");
    }

    @Override
    public void onDestroy()
    {
    	super.onDestroy();
        Log.v(aname,"P2onDestroy()");
        //sapp.counter--;
    }

    @Override
    public void onSaveInstanceState(Bundle bund)
    {
    	super.onSaveInstanceState(bund);
        Log.v(aname,"P2onSaveInstanceState()");
    }

    @Override
    public void onRestoreInstanceState(Bundle bund)
    {
    	super.onRestoreInstanceState(bund);
        Log.v(aname,"P2onRestoreInstanceState()");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_page2, menu);
		return true;
	}

}

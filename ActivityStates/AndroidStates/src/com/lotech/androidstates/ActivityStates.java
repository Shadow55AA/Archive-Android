package com.lotech.androidstates;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class ActivityStates extends Activity
{
	public static String aname = "ActivityStates";
	private StatesApp sapp;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_states);
        Log.v(aname,"onCreate()");
        sapp = (StatesApp)getApplication();
        Log.v(aname,"count="+sapp.counter);
        sapp.counter++;
    }

    @Override
    public void onStart()
    {
    	super.onStart();
        Log.v(aname,"onStart()");
    }

    @Override
    public void onResume()
    {
    	super.onResume();
        Log.v(aname,"onResume()");
        Log.v(aname,"count="+sapp.counter);
    }

    @Override
    public void onPause()
    {
    	super.onPause();
        Log.v(aname,"onPause()");
    }

    @Override
    public void onStop()
    {
    	super.onStop();
        Log.v(aname,"onStop()");
    }

    @Override
    public void onRestart()
    {
    	super.onRestart();
        Log.v(aname,"onRestart()");
    }

    @Override
    public void onDestroy()
    {
    	super.onDestroy();
        Log.v(aname,"onDestroy()");
        sapp.counter--;
    }

    @Override
    public void onSaveInstanceState(Bundle bund)
    {
    	super.onSaveInstanceState(bund);
        Log.v(aname,"onSaveInstanceState()");
    }

    @Override
    public void onRestoreInstanceState(Bundle bund)
    {
    	super.onRestoreInstanceState(bund);
        Log.v(aname,"onRestoreInstanceState()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_activity_states, menu);
        return true;
    }
    
    public void onNext(View view)
    {
    	Intent intent = new Intent(this,Page2Activity.class);
    	startActivity(intent);
    }
}

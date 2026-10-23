package com.lotech.sudd;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class CoreActivity extends Activity
{
	private SUDDCore core;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        core = new SUDDCore(this);
        
        registerForContextMenu(core.glview);
        
        setContentView(core.glview);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.activity_core_context, menu);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_core, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_settings:
                openContextMenu(core.glview);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

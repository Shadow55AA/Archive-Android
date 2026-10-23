package com.lotech.graphics;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GraphicsActivity extends Activity
{
	private MySurfaceView sview;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sview = new MySurfaceView(this);
        setContentView(sview);
        //setContentView(R.layout.activity_graphics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_graphics, menu);
        return true;
    }
}

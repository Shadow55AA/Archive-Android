package com.example.opengltest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OpenGLTest extends Activity
{
	private MyGLView glview;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        glview = new MyGLView(this);
        setContentView(glview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_open_gltest, menu);
        return true;
    }
}

package com.lotech.sudd;

import android.os.Bundle;
import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SUDD extends Activity
{	
	boolean cwarn;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        cwarn = false;
        setContentView(R.layout.activity_sudd);
        
//        Intent intent = new Intent(this, CoreActivity.class);
//        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_sudd, menu);
        return true;
    }
    
    protected void onPause()
    {
    	super.onPause();
    }
    
    public void ViewOnClick(View iview)
    {
        Intent intent = new Intent(this, CoreActivity.class);
        startActivity(intent);
    }
    
    public void onBackPressed()
    {
    	if(cwarn==true)
    		super.onBackPressed();
    	else	
    	{
    		openOptionsMenu();
    	}
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_close:
            	cwarn = true;
            	this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void MainMenuNewOnClick(View iview)
    {
        Intent intent = new Intent(this, CreateNewGame.class);
        startActivity(intent);
        
/*    	String filename = ".saves";
    	String data = "0";
    	
    	try
    	{
			FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();
		}
    	catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
    	catch (IOException e)
    	{
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}*/
    }
    
    public void MainMenuLoadOnClick(View iview)
    {
        Intent intent = new Intent(this, LoadSavedGame.class);
        startActivity(intent);
        
/*    	String filename = ".saves";
    	StringBuffer data = new StringBuffer();
    	
    	try
    	{
			FileInputStream fis = openFileInput(filename);
			int c = 0;
			while((c = fis.read()) !=-1)
			{
				data.append((char)c);
			}
			TextView tv = (TextView)findViewById(R.id.mm_status);
			tv.setText(data.toString());
		}
    	catch (FileNotFoundException e)
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (IOException e)
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
}

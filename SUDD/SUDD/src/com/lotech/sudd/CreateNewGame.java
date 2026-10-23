package com.lotech.sudd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;

public class CreateNewGame extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_game);
    }
    
    public void ngmBtnCreateOnClick(View iview)
    {
    	String filename = ".saves";
    	StringBuffer data = new StringBuffer();
        
    	FileInputStream fis;
    	
    	try
    	{
			fis = openFileInput(filename);
			
			int c = 0;
			while((c = fis.read()) !=-1)
			{
				data.append((char)c);
			}
			fis.close();
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
		}
        
    	String datastr = data.toString();
    	String saves[] = datastr.split("\n");
    	int nsaves = saves.length;
    	
    	EditText etcharname = (EditText)findViewById(R.id.ngm_txtbox_charname);
    	String charname = etcharname.getText().toString();
    	boolean nameused = false;
    	for(int i=0;i<nsaves&&!nameused;i++)
    	{
    		if(saves[i].equals(charname))
    		{
    			nameused = true;
    		}
    	}
    	
    	if(!nameused&&nsaves<10)
    	{
	    	try
	    	{
				FileOutputStream fos = openFileOutput(filename, Context.MODE_APPEND);
				fos.write(charname.getBytes());
				fos.write('\n');
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
	    	}
    	}
    }

}

package com.lotech.sudd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;

public class LoadSavedGame extends Activity
{
	ListView dlview;
	LinearLayout dlayoutlist[];
	TextView savetxtviews[];
	Button contsavebtns[];
	Button delsavebtns[];
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_saved_game);
        
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
    	String filelist[] = fileList();
    	
    	dlview = new ListView(this);
    	dlview.setAdapter(new LoadGameAdapter(this,saves));
    	
/*    	dlayoutlist = new LinearLayout[nsaves];
    	savetxtviews = new TextView[nsaves];
    	contsavebtns = new Button[nsaves];
    	delsavebtns = new Button[nsaves];
	    for(int i=0;i<nsaves;i++)
        {
	    	dlayoutlist[i] = new LinearLayout(this);
	    	dlayoutlist[i].setOrientation(LinearLayout.HORIZONTAL);
	    	dlayoutlist[i].setGravity(Gravity.RIGHT);
	    	savetxtviews[i] = new TextView(this);
	    	savetxtviews[i].setText(saves[i]);
	    	savetxtviews[i].setTextColor(0xffffffff);
	    	contsavebtns[i] = new Button(this);
	    	contsavebtns[i].setText("Continue");
	    	delsavebtns[i] = new Button(this);
	    	delsavebtns[i].setText("Delete");
        	dlayoutlist[i].addView(contsavebtns[i]);
        	dlayoutlist[i].addView(delsavebtns[i]);
        }*/

        LinearLayout lview = (LinearLayout)findViewById(R.id.lsg_linlayout);
        lview.addView(dlview);
/*        for(int i=0;i<nsaves;i++)
        {
        	lview.addView(savetxtviews[i]);
        	lview.addView(dlayoutlist[i]);
        }*/
    }
    
    public void LsgDelOnclick(View iview)
    {
    	long item = dlview.getSelectedItemId();
    	if(item == 2)
    	{
    		onBackPressed();
    	}
    }

}

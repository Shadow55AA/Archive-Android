package example.myfirstapp;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class DisplayMessageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        TextView textview = new TextView(this);
        textview.setTextSize(40);
        textview.setText(message);
        
        setContentView(textview);
    }

}

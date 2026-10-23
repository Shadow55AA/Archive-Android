package com.losoft.mathpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lozenskysoftware.mathpractice.R;

import java.util.Random;

/**
 * Created by Alex on 5/5/2015.
 */
public class PracticingActivity extends Activity
{
    private Random rand;
    private int maintype = 1; // 0 = custom, 1 = Arithmetic, 2 = Algebra
    private int curprobnum,numprobs,correctansweri,numcorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_practicing);
        rand = new Random();
        curprobnum = 1;
        numprobs = 10;
        numcorrect=0;
        correctansweri=0;

        Intent myintent = getIntent();
        String ptype = myintent.getStringExtra("maintype");
        if(ptype.equals("arithmetic"))
        {
            maintype = 1;
            genArithmeticProblem();
        }
        else if(ptype.equals("alg"))
        {
            maintype = 2;
            genAlgebraProblem();
        }
        else if(ptype.equals("custom"))
        {
            maintype = 0;
        }

        if (savedInstanceState == null)
        {
        }
    }

    private void genArithmeticProblem()
    {
        rand.setSeed(System.currentTimeMillis());
        int pt = rand.nextInt(3);
        if(pt==0)       // Add/Subtract
        {
            int a = rand.nextInt(199)-99;
            int b = rand.nextInt(199)-99;
            String str = ""+curprobnum+") "+a;
            if(b>=0)
                str += "+";
            str += ""+b+"=";
            correctansweri = a+b;
            TextView tv = (TextView)findViewById(R.id.problem);
            tv.setText(str);
        }
        else if(pt==1)  // Multiply
        {
            int a = rand.nextInt(25)-12;
            int b = rand.nextInt(25)-12;
            String str = ""+curprobnum+") "+a+" * "+b+"=";
            correctansweri = a*b;
            TextView tv = (TextView)findViewById(R.id.problem);
            tv.setText(str);
        }
        else if(pt==2) // Divide
        {
            int c = rand.nextInt(25)-12;
            int b = rand.nextInt(25)-12;
            int a = b*c;
            String str = ""+curprobnum+") "+a+" / "+b+"=";
            correctansweri = c;
            TextView tv = (TextView)findViewById(R.id.problem);
            tv.setText(str);
        }
    }

    private void genAlgebraProblem()
    {
        rand.setSeed(System.currentTimeMillis());
        int x = rand.nextInt(41)-20;
        int k = rand.nextInt(19)-9;
        int b = rand.nextInt(199)-99;
        int y = x*k+b;
        String str = ""+curprobnum+") "+k+"x";
        if(b>=0)
            str += "+";
        str += ""+b+"="+y;
        correctansweri = x;
        TextView tv = (TextView)findViewById(R.id.problem);
        tv.setText(str);
    }

    public void onClickNext(View v)
    {
        curprobnum++;
        if(maintype==1)
        {
            if (curprobnum <= numprobs)
            {
                EditText et = (EditText) findViewById(R.id.answer);
                int ti = Integer.MAX_VALUE;
                try
                {
                    ti = Integer.parseInt(et.getText().toString());
                } catch (NumberFormatException e)
                {

                }
                if (ti == correctansweri)
                    numcorrect++;
                et.setText("");
                genArithmeticProblem();
            }
        }
        else if(maintype==2)
        {
            EditText et = (EditText) findViewById(R.id.answer);
            int ti = Integer.MAX_VALUE;
            try
            {
                ti = Integer.parseInt(et.getText().toString());
            } catch (NumberFormatException e)
            {

            }
            if (ti == correctansweri)
                numcorrect++;
            et.setText("");
            genAlgebraProblem();
        }
        else if(maintype==0)
        {

        }
        if (curprobnum > numprobs)
        {
            TextView tv = (TextView) findViewById(R.id.problem);
            float score = numcorrect / (float) numprobs;
            score *= 100;
            tv.setText("" + score + "% Press back to continue.");
        }
    }
}

package com.atl.ltc;

import java.util.HashMap;
import java.util.Iterator;

import com.atl.equation.EqFunction;
import com.atl.ltc.R;
import com.atl.ltc.R.id;
import com.atl.ltc.R.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class FuncList2Adapter extends BaseAdapter implements SpinnerAdapter
{
	private Context context;
	private HashMap<String,EqFunction> funcs;
	
	public FuncList2Adapter(Context icontext,HashMap<String,EqFunction> ifuncs)
	{
		context = icontext;
		funcs = ifuncs;
	}

	@Override
	public int getCount()
	{
		Iterator<EqFunction> itt = funcs.values().iterator();
		EqFunction tfunc = null;
		int count = 0;
		while(itt.hasNext())
		{
			tfunc = itt.next();
			if(tfunc.GetNumVars()==2)
				count++;
		}
		
		return count;
	}

	@Override
	public Object getItem(int position)
	{
		Iterator<EqFunction> itt = funcs.values().iterator();
		EqFunction tfunc = null;
		int count = -1;
		while(itt.hasNext()&&count!=position)
		{
			tfunc = itt.next();
			if(tfunc.GetNumVars()==2)
				count++;
		}
		return tfunc;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		FuncList2Holder holder = null;
		if(convertView==null)
		{
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.funclist2item, null);
			holder = new FuncList2Holder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (FuncList2Holder)convertView.getTag();
		}
		
		EqFunction tfunc = (EqFunction)getItem(position);
		holder.tview.setText(tfunc.GetName());
		return convertView;
	}

}

class FuncList2Holder
{
	public TextView tview;
	
	public FuncList2Holder(View iview)
	{
		tview = (TextView)iview.findViewById(R.id.funclist2tview);
	}
}

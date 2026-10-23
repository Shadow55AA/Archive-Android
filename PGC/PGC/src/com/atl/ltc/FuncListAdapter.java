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

public class FuncListAdapter extends BaseAdapter implements SpinnerAdapter
{
	Context context;
	HashMap<String,EqFunction> funcs;
	GraphedFuncs gfuncs;
	
	public FuncListAdapter(Context icontext,HashMap<String,EqFunction> ifuncs,GraphedFuncs igfuncs)
	{
		context = icontext;
		funcs = ifuncs;
		gfuncs = igfuncs;
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
			if(tfunc.GetNumVars()==1)
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
			if(tfunc.GetNumVars()==1)
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
		FuncListHolder holder = null;
		if(convertView==null)
		{
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.funclistitem, null);
			holder = new FuncListHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (FuncListHolder)convertView.getTag();
		}
		
		//EqFunction tfunc = (EqFunction)getItem(position);
		holder.tview.setText("f(x)");
		holder.tview.setVisibility(View.VISIBLE);
		holder.cbox.setVisibility(View.INVISIBLE);
		return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		FuncListHolder holder = null;
		if(convertView==null)
		{
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.funclistitem, null);
			holder = new FuncListHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (FuncListHolder)convertView.getTag();
		}
		EqFunction tfunc = (EqFunction)getItem(position);
		holder.cbox.setText(tfunc.GetName());
		holder.cbox.setTextColor(Color.parseColor("#ff000000"));
		if(gfuncs.containsFunction(tfunc))
			holder.cbox.setChecked(true);
		else
			holder.cbox.setChecked(false);
		holder.tview.setVisibility(View.INVISIBLE);
		holder.cbox.setVisibility(View.VISIBLE);
		return convertView;
	}

}

class FuncListHolder
{
	public TextView tview;
	public CheckBox cbox;
	
	public FuncListHolder(View iview)
	{
		tview = (TextView)iview.findViewById(R.id.funclisttview);
		cbox = (CheckBox)iview.findViewById(R.id.funclistcbox);
	}
}

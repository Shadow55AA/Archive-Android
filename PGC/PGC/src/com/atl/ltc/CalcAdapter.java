package com.atl.ltc;

import com.atl.equation.EqCalculator;
import com.atl.ltc.R;
import com.atl.ltc.R.id;
import com.atl.ltc.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalcAdapter extends BaseAdapter
{
	LayoutInflater linflater;
	EqCalculator calc;
	Context context;
	
	CalcAdapter(Context icontext,EqCalculator icalc)
	{
		super();
		context = icontext;
		linflater = LayoutInflater.from(icontext);
	}
	
	@Override
	public int getCount()
	{
		return 1;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if(convertView == null)
		{
			convertView = linflater.inflate(R.layout.activity_main,null);
			holder = new ViewHolder();
			convertView.setTag(holder);
			ViewGroup mlayout = (ViewGroup)convertView.findViewById(R.id.mathlayout);
			View pbtnview = linflater.inflate(R.layout.primarybtns,null);
			((ViewGroup)mlayout).addView(pbtnview);
			View fbtnview = linflater.inflate(R.layout.funcbtns,null);
			((ViewGroup)mlayout).addView(fbtnview);
			ViewGroup glayout = (ViewGroup)convertView.findViewById(R.id.graphlayout);
			View graphmenuview = linflater.inflate(R.layout.graphmenu,null);
			((ViewGroup)glayout).addView(graphmenuview);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	
	static class ViewHolder
	{
		public TextView text1;
	}

}

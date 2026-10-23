package com.lotech.sudd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoadGameAdapter extends BaseAdapter
{
	LayoutInflater linflater;
	String data[];
	
	public LoadGameAdapter(Context icontext,String idata[])
	{
		linflater = LayoutInflater.from(icontext);
		data = new String[idata.length];
		for(int i=0;i<idata.length;i++)
			data[i] = new String(idata[i]);
	}

	public int getCount()
	{
		// TODO Auto-generated method stub
		return data.length;
	}

	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if(convertView == null)
		{
			convertView = linflater.inflate(R.layout.lsg_row, null);
			holder = new ViewHolder();
			holder.text1 = (TextView) convertView.findViewById(R.id.lsg_txtview);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.text1.setText(data[position]);
		return convertView;
	}
	
	static class ViewHolder
	{
		TextView text1;
	}

}

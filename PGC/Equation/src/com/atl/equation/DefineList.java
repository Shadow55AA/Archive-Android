package com.atl.equation;

import java.util.HashMap;

public class DefineList
{
	private HashMap<String,Integer> defines;
	
	public DefineList()
	{
		defines = new HashMap<String,Integer>();
	}
	
	public boolean isDefined(String idefine)
	{
		if(defines.containsKey(idefine))
			return true;
		return false;
	}
	
	public void addDefine(String idefine)
	{
		if(!isDefined(idefine))
			defines.put(idefine, 0);
	}
}

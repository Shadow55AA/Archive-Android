package com.lotech.r3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class RenderObjectHashList
{
	HashMap<String,RenderObject> robjs;
	
	public RenderObjectHashList()
	{
		robjs = new HashMap<String,RenderObject>(10);
	}
	
	public void Destroy()
	{
		Collection<RenderObject> col = robjs.values();
		Iterator<RenderObject> it = col.iterator();
		while(it.hasNext())
		{
			RenderObject robj = it.next();
			robj.Destroy();
		}
	}
	
	public void Add(String key,RenderObject itex)
	{
		if(robjs.get(key)==null)
			robjs.put(key, itex);
	}
	
	public RenderObject Get(String key)
	{
		return robjs.get(key);
	}

}

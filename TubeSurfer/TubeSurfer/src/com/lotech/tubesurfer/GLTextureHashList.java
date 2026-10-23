package com.lotech.tubesurfer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class GLTextureHashList
{
	HashMap<String,GLTexture> textures;
	
	public GLTextureHashList()
	{
		textures = new HashMap<String,GLTexture>(10);
	}
	
	public void Destroy()
	{
		Collection<GLTexture> col = textures.values();
		Iterator<GLTexture> it = col.iterator();
		while(it.hasNext())
		{
			GLTexture tex = it.next();
			tex.Destroy();
		}
	}
	
	public void Add(String key,GLTexture itex)
	{
		if(textures.get(key)==null)
			textures.put(key, itex);
	}
	
	public GLTexture Get(String key)
	{
		return textures.get(key);
	}
}

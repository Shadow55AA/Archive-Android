package com.lotech.gameexample;

import java.util.HashMap;

public class GLTextureHashList
{
	HashMap<String,GLTexture> textures;
	
	public GLTextureHashList()
	{
		textures = new HashMap<String,GLTexture>(10);
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

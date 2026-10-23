package com.lotech.gameexample;

import java.util.HashMap;

public class GLMaterialHashList
{
	HashMap<String,GLMaterial> materials;
	
	public GLMaterialHashList()
	{
		materials = new HashMap<String,GLMaterial>(10);
	}
	
	public void Add(String key,GLMaterial imat)
	{
		if(materials.get(key)==null)
			materials.put(key, imat);
	}
	
	public GLMaterial Get(String key)
	{
		return materials.get(key);
	}
}

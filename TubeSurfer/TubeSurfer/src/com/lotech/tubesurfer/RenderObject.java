package com.lotech.tubesurfer;

public abstract class RenderObject
{
	protected GLMaterial material = null;
	private int nrbuffs,maxnrbuffs;
	private RenderBuffer rbuffs[] = null;
	private RenderBuffer crbuffer = null;
	
	public RenderObject(GameRenderer irenderer)
	{
		nrbuffs=0;
		maxnrbuffs=10;
		rbuffs = new RenderBuffer[maxnrbuffs];
	}
	
	public void Destroy()
	{
		if(rbuffs!=null)
		{
			for(int i=0;i<nrbuffs;i++)
				rbuffs[i].Destroy();
		}
	}
	
	public void AddRenderBuffer(RenderBuffer irbuffer)
	{
		if(nrbuffs<maxnrbuffs)
		{
			rbuffs[nrbuffs++]=irbuffer;
		}
		crbuffer=irbuffer;
	}
	
	public void SetRenderBuffer(int ind)
	{
		if(ind<0||ind>=nrbuffs)
			return;
		crbuffer = rbuffs[ind];
	}
	
	public boolean Render(GameRenderer irenderer)
	{
		if(material!=null)
		{
			irenderer.SetMaterial(material);
		}
		if(crbuffer!=null)
		{
			GLShader sh = irenderer.GetShader();
			if(crbuffer.Render(sh.GetProgram())==true);
				return true;
		}
		return false;
	}
}

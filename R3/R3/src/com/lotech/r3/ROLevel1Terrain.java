package com.lotech.r3;

public class ROLevel1Terrain extends RenderObject
{
	public ROLevel1Terrain(GameRenderer irenderer)
	{
		super(irenderer);
		float vec[] = {50,50,0,0};
		irenderer.rgconstructor.Clear();
		RGPlane plane = new RGPlane();
		plane.SetSize(vec);
		vec[0] = 5f;
		vec[1] = 5f;
		plane.SetUVScale(vec);
		irenderer.rgconstructor.AddGeometry(plane);
		AddRenderBuffer(irenderer.rgconstructor.GenerateBuffer());
	}
}

package com.lotech.tubesurfer;

public class ROLevel1Track extends RenderObject
{
	public ROLevel1Track(GameRenderer irenderer)
	{
		super(irenderer);
		float vec[] = {0.2f,3,0,0};
		irenderer.rgconstructor.Clear();
		RGPlane plane = new RGPlane();
		plane.SetSize(vec);
		vec[0] = -1.4f;
		vec[1] = 0f;
		plane.SetPos(vec);
		irenderer.rgconstructor.AddGeometry(plane);
		plane = new RGPlane();
		vec[0] = 0.2f;
		vec[1] = 3f;
		plane.SetSize(vec);
		vec[0] = 1.4f;
		vec[1] = 0f;
		plane.SetPos(vec);
		irenderer.rgconstructor.AddGeometry(plane);
		plane = new RGPlane();
		vec[0] = 3f;
		vec[1] = 0.2f;
		plane.SetSize(vec);
		vec[0] = 0f;
		vec[1] = 1.6f;
		plane.SetPos(vec);
		irenderer.rgconstructor.AddGeometry(plane);
		AddRenderBuffer(irenderer.rgconstructor.GenerateBuffer());
	}
}

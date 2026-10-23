package com.lotech.r3;

public class ROTubeSurfer extends RenderObject
{

	public ROTubeSurfer(GameRenderer irenderer)
	{
		super(irenderer);
		float vec[] = {0.5f,1f,0,0};
		irenderer.rgconstructor.Clear();
		/*RGPlane plane = new RGPlane();
		plane.SetSize(vec);
		vec[0] = 1f;
		vec[1] = 0.2f;
		plane.SetUVScale(vec);
		Quaternion.QRot(vec, 1, 0, 0, -(float)Math.PI/2.0f);
		plane.SetRot(vec);
		irenderer.rgconstructor.AddGeometry(plane);*/
		RGCapsule capsule = new RGCapsule();
		capsule.SetSize(vec);
		irenderer.rgconstructor.AddGeometry(capsule);
		AddRenderBuffer(irenderer.rgconstructor.GenerateBuffer());

		irenderer.rgconstructor.Clear();
		vec[0] = 0.1f; vec[1] = 0.5f;
		RGSphere sphere = new RGSphere();
		sphere.SetRadius(0.25f);
		irenderer.rgconstructor.AddGeometry(sphere);
		AddRenderBuffer(irenderer.rgconstructor.GenerateBuffer());
	}

}

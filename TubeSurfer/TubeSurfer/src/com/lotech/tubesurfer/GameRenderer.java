package com.lotech.tubesurfer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.lotech.tubesurfer.GameEngine;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class GameRenderer implements GLSurfaceView.Renderer
{
	private GameEngine gengine;
	private GLShader glshader;
	private GLPlane glplane;
	private GLBox glbox;
	private GLMaterialHashList defaultmaterials;
	private GLTextureHashList defaulttextures;
	public RGConstructor rgconstructor;
	private float vpos[] = {0,0,0,0};
	private float vrot[] = {0,0,0,1};
	
	public GameRenderer(Context icontext,GameEngine igengine)
	{
		super();
		gengine = igengine;
		rgconstructor = new RGConstructor();
		rgconstructor.Init(1000, 1000, true, true, true);
		defaulttextures = new GLTextureHashList();
		LoadDefaultTextureResources(icontext);
	}
	
	/*public void Destroy()
	{
		defaulttextures.Destroy();
		glbox.Destroy();
		glplane.Destroy();
		glshader.Destroy();
	}*/
	
	@Override
	public void finalize()
	{
		gengine.DestroyBuffers();
		defaulttextures.Destroy();
		glbox.Destroy();
		glplane.Destroy();
		glshader.Destroy();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		glshader = new GLShader();
		glplane = new GLPlane();
		glbox = new GLBox();
		defaultmaterials = new GLMaterialHashList();
		LoadDefaultMaterials();
		GenDefaultTextures();
		gengine.onSurfaceCreated(gl, config);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		gengine.onSurfaceChanged(gl, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		glshader.UseShader();
		gengine.onDrawFrame(gl);
	}
	
	public void SetViewProjection(float ivproj[])
	{
		int sp = glshader.GetProgram();
		int svproj = GLES20.glGetUniformLocation(sp, "vproj");
		GLES20.glUniformMatrix4fv(svproj, 1,false, ivproj,0);
	}
	
	public void SetViewPos(float ivpos[])
	{
		int sp = glshader.GetProgram();
		int svpos = GLES20.glGetUniformLocation(sp, "vpos");
		GLES20.glUniform4fv(svpos, 1, ivpos,0);
		for(int i=0;i<3;i++)
			vpos[i]=ivpos[i];
	}
	
	public void GetViewPos(float ovpos[])
	{
		for(int i=0;i<3;i++)
			ovpos[i]=vpos[i];
	}
	
	public void SetViewRot(float ivrot[])
	{
		float crot[] = {-ivrot[0],-ivrot[1],-ivrot[2],ivrot[3]};
		float tmat[] = {0,0,0,
				0,0,0,
				0,0,0};
		Quaternion.QtoMatrix(crot, tmat);
		int sp = glshader.GetProgram();
		int svrot = GLES20.glGetUniformLocation(sp, "vrot");
		GLES20.glUniformMatrix3fv(svrot, 1,false, tmat,0);
		for(int i=0;i<4;i++)
			vrot[i]=ivrot[i];
	}
	
	public void SetDrawPos(float idpos[])
	{
		int sp = glshader.GetProgram();
		int sdpos = GLES20.glGetUniformLocation(sp, "dpos");
		GLES20.glUniform4fv(sdpos, 1, idpos,0);
	}
	
	public void SetDrawRot(float idrot[])
	{
		float tmat[] = {0,0,0,
						0,0,0,
						0,0,0};
		Quaternion.QtoMatrix(idrot, tmat);
		int sp = glshader.GetProgram();
		int sdrot = GLES20.glGetUniformLocation(sp, "drot");
		GLES20.glUniformMatrix3fv(sdrot, 1,false, tmat,0);
	}
	
	public void SetLight(GLLight ilight)
	{
		ilight.Set(glshader.GetProgram());
	}
	
	public void SetMaterial(GLMaterial imat)
	{
		imat.Set(glshader.GetProgram());
	}
	
	public boolean SetTexture(GLTexture itex)
	{
		return itex.Set(glshader.GetProgram());
	}
	
	public boolean DrawObject(RenderObject irobj)
	{
		return irobj.Render(this);
	}
	
	public void DrawPlane(float size[])
	{
		glshader.UseShader();
		glplane.SetSize(size);
		glplane.Draw(glshader.GetProgram());
	}
	
	public void DrawBox(float size[])
	{
		glshader.UseShader();
		glbox.SetSize(size);
		glbox.Draw(glshader.GetProgram());
	}
	
	public GLShader GetShader()
	{
		return glshader;
	}
	
	public GLMaterial GetDefaultMaterial(String key)
	{
		return defaultmaterials.Get(key);
	}
	
	public GLTexture GetDefaultTexture(String key)
	{
		return defaulttextures.Get(key);
	}
	
	public void BuildPerspectiveProjection(float imat[],float fov,float aspect,float n,float f)
	{
	    float h = 1.0f/(float)Math.tan(fov*3.14159/360.0);
	    float neg_depth = n-f;

	    imat[0] = h / aspect;
	    imat[1] = 0;
	    imat[2] = 0;
	    imat[3] = 0;

	    imat[4] = 0;
	    imat[5] = h;
	    imat[6] = 0;
	    imat[7] = 0;

	    imat[8] = 0;
	    imat[9] = 0;
	    imat[10] = (f + n)/neg_depth;
	    imat[11] = -1;

	    imat[12] = 0;
	    imat[13] = 0;
	    imat[14] = 2.0f*(n*f)/neg_depth;
	    imat[15] = 0;
	}
	
	private void LoadDefaultMaterials()
	{
		float vec[] = {1,0,0,1};
		GLMaterial tmat = new GLMaterial();
		defaultmaterials.Add("white", tmat);
		tmat = new GLMaterial();
		tmat.SetAmbient(vec);
		tmat.SetDiffuse(vec);
		defaultmaterials.Add("red", tmat);
		vec[0]=0;	vec[1]=1;
		tmat = new GLMaterial();
		tmat.SetAmbient(vec);
		tmat.SetDiffuse(vec);
		defaultmaterials.Add("green", tmat);
		vec[1]=0;	vec[2]=1;
		tmat = new GLMaterial();
		tmat.SetAmbient(vec);
		tmat.SetDiffuse(vec);
		defaultmaterials.Add("blue", tmat);
		
		tmat.SetAmbient(vec);
	}

	private void LoadDefaultTextureResources(Context icontext)
	{
		GLTexture tmptex = new GLTexture();
		tmptex.LoadTextureResource(icontext.getResources(), R.drawable.fractalnoise);
		defaulttextures.Add("fractalnoise", tmptex);
	}
	
	private void GenDefaultTextures()
	{
		GLTexture tmptex = defaulttextures.Get("fractalnoise");
		if(tmptex!=null)
			tmptex.GenTexture();
	}
}

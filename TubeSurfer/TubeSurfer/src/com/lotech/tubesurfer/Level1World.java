package com.lotech.tubesurfer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

public class Level1World extends GameWorld
{
	private GameRenderer renderer;
	private RenderObjectHashList renderobjs;
	private GamePlayer gplayer;
	private int nobjs = 0,maxnobjs = 64;
	private GameObject objs[] = null;
	private int ncobjs = 0;
	private GameObject collidableobjs[] = null;
	private GLLight sunlight;

	public Level1World(Context icontext,GameRenderer irenderer,GamePlayer igplayer)
	{
		renderer = irenderer;
		renderobjs = new RenderObjectHashList();
		gplayer = igplayer;
	}

	@Override
	public void Destroy()
	{
		renderobjs.Destroy();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		objs = new GameObject[maxnobjs];
		collidableobjs = new GameObject[maxnobjs];
		GenerateRenderObjects();
		GenerateGameObjects();
		float fvec[] = {0,0,0,1};
		
		sunlight = new GLLight();
		fvec[0]=-1;	fvec[1]=-3;	fvec[2]=-2;	fvec[3]=1;
		LAVector.Vec3Normalize(fvec);
		sunlight.SetPos(fvec);
		fvec[0]=0.3f;	fvec[1]=0.3f;	fvec[2]=0.3f;	fvec[3]=1;
		sunlight.SetAmbient(fvec);
	}
	
	@Override
	public void FindCollisions(PhysicsContactList icontactlist)
	{
		for(int i=0;i<ncobjs;i++)
		{
			if(gplayer.Collide(collidableobjs[i],icontactlist))
			{
				gplayer.onCollide(collidableobjs[i]);
				collidableobjs[i].onCollide(gplayer);
			}
		}
	}

	@Override
	public void update(float dt)
	{
		gplayer.physicsStep(dt);
		
		gplayer.updateView();
	}
	
	@Override
	public void Render(GameRenderer irenderer)
	{
		irenderer.SetLight(sunlight);
		if(gplayer!=null)
		{
			gplayer.setView(irenderer);
			gplayer.Render(irenderer);
		}
		for(int i=0;i<nobjs;i++)
		{
			objs[i].Render(irenderer);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		gplayer.onTouchEvent(e);
		return false;
	}
	
	public void AddGameObject(GameObject igobj,boolean ibcollidable)
	{
		if(nobjs<maxnobjs)
		{
			objs[nobjs]=igobj;
			nobjs++;
			if(ibcollidable)
			{
				collidableobjs[ncobjs]=igobj;
				ncobjs++;
			}
		}
	}

	@Override
	public void GenerateRenderObjects()
	{
		ROTubeSurfer tubesurfer = new ROTubeSurfer(renderer);
		renderobjs.Add("rotubesurfer", tubesurfer);
		ROLevel1Terrain lvl1terrain = new ROLevel1Terrain(renderer);
		renderobjs.Add("rolvl1terrain", lvl1terrain);
		ROLevel1Track lvl1track = new ROLevel1Track(renderer);
		renderobjs.Add("rolvl1track", lvl1track);
	}

	@Override
	public void GenerateGameObjects()
	{
		gplayer.SetRenderObject(renderobjs.Get("rotubesurfer"));
		GOLevel1Terrain lvl1terrain = new GOLevel1Terrain();
		lvl1terrain.SetRenderObject(renderobjs.Get("rolvl1terrain"));
		AddGameObject(lvl1terrain,true);
		GOLevel1Track lvl1track = new GOLevel1Track();
		lvl1track.SetRenderObject(renderobjs.Get("rolvl1track"));
		AddGameObject(lvl1track,true);
	}

}

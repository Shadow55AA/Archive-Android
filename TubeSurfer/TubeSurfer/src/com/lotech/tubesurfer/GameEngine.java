package com.lotech.tubesurfer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.view.MotionEvent;

public class GameEngine implements SensorEventListener
{
	public GameSurfaceView glsview;
	public GameRenderer glrenderer = null;
	private GameThread gthread;
	private long counter,startcount;
	private float elapsedtime;
	private PhysicsContactList contactlist;
	private GamePlayer gplayer;
	private GameWorld activeworld = null;
	private Level1World lvl1w;
	private float[] viewproj = {1,0,0,0,
								0,1,0,0,
								0,0,1,0,
								0,0,0,1};
	private SensorManager smanager;
	private Sensor asensor;
	private float devacc[] = {0,0,0};
	
	public GameEngine(Context icontext)
	{
		Noise.Init();
		contactlist = new PhysicsContactList(100);
		glrenderer = new GameRenderer(icontext,this);
		glsview = new GameSurfaceView(icontext,this);
		glsview.setRenderer(glrenderer);
		glsview.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		gthread = new GameThread(this);
		counter = SystemClock.uptimeMillis();
		startcount = counter;
		elapsedtime = 0;
		gplayer = new GPTubeSurfer();
		lvl1w = new Level1World(icontext,glrenderer,gplayer);
		activeworld = lvl1w;
		smanager = (SensorManager)icontext.getSystemService(Context.SENSOR_SERVICE);
		asensor = smanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	public void DestroyBuffers()
	{
		lvl1w.Destroy();
	}
	
	public void onBackPressed()
	{
		//DestroyBuffers();
		//glrenderer.Destroy();
		
		int ntries = 0;
		while(ntries<3)
		{
			gthread.SetRunning(false);
			try
			{
				gthread.join();
				ntries=3;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			ntries++;
		}
	}
	
	/*@Override
	public void finalize()
	{
		smanager.unregisterListener(this);
		int ntries = 0;
		while(ntries<3)
		{
			gthread.SetRunning(false);
			try
			{
				gthread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			ntries++;
		}
	}*/
			
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		GLES20.glClearColor(0,0,0,1.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		//GLES20.glDisable(GLES20.GL_CULL_FACE);
		gthread.SetRunning(true);
		gthread.start();
		lvl1w.onSurfaceCreated(gl, config);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		GLES20.glViewport(0,0,width,height);
		glrenderer.BuildPerspectiveProjection(viewproj, 50, (float)width/(float)height, 0.1f, 1000);
	}
	
	public void Run()
	{
		counter = SystemClock.uptimeMillis();
		elapsedtime = counter-startcount;
		startcount = counter;
		elapsedtime/=1000.0f;
		
		contactlist.Clear();
		if(gplayer!=null)
		{
			gplayer.TiltUpdate(devacc);
		}
		if(activeworld!=null)
		{
			activeworld.FindCollisions(contactlist);
			Physics.ResolveImpulses(contactlist);
			Physics.SpringContactForces(contactlist, elapsedtime);
			activeworld.update(elapsedtime);
		}
		glsview.requestRender();
		try
		{
			GameThread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onDrawFrame(GL10 gl)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
		glrenderer.SetViewProjection(viewproj);
		if(activeworld!=null)
			activeworld.Render(glrenderer);
	}
	
	
	public void onResume()
	{
		smanager.registerListener(this, asensor, SensorManager.SENSOR_DELAY_UI);
	}
	
	public void onPause()
	{
		smanager.unregisterListener(this);
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		return false;
	}
	
	@Override
    public void onSensorChanged(SensorEvent e)
    {
		if(e.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			devacc[0]=e.values[0];
			devacc[1]=e.values[1];
			devacc[2]=e.values[2];
		}
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		// TODO Auto-generated method stub
		
	}
}

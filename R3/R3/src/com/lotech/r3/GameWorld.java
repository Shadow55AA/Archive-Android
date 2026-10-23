package com.lotech.r3;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

public abstract class GameWorld
{

	public abstract void Destroy();
	
	public abstract void onSurfaceCreated(GL10 gl, EGLConfig config);
	public abstract void FindCollisions(PhysicsContactList icontactlist);
	public abstract void update(float dt);
	public abstract void Render(GameRenderer irenderer);
	
	public abstract boolean onTouchEvent(MotionEvent e);
	
	public abstract void GenerateRenderObjects();
	public abstract void GenerateGameObjects();
}

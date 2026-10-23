package com.lotech.r3;

import android.view.MotionEvent;

public abstract class GamePlayer extends GameObject
{
	float vpos[] = {0,0,8,0};
	float vrot[] = {0,0,0,1};
	
	public abstract void setView(GameRenderer irenderer);
	public abstract void physicsStep(float dt);
	public abstract void updateView();
	public abstract boolean onTouchEvent(MotionEvent e);
	public abstract void TiltUpdate(float itilt[]);
}

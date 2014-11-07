package com.cn.lHClient.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

/**椭圆运行*/
public class OvalAction extends TemporalAction {

	private Vector2 centre;
	private float a,b;
	private Vector2 temp = new Vector2();
	
	public OvalAction(float time,Vector2 centre,float a,float b){
		super(time);
		this.centre = centre;
		if(a < 0 || b <0){
			throw new IllegalArgumentException();
		}
		this.a = a;
		this.b = b;
	}
	
	public OvalAction(float time,Vector2 centre,float a,float b,Interpolation interpolation){
		super(time,interpolation);
		this.centre = centre;
		if(a < 0 || b <0){
			throw new IllegalArgumentException();
		}
		this.a = a;
		this.b = b;
	}
	
	
	@Override
	protected void update(float percent) {
		valueAt(temp,percent);
		actor.setPosition(temp.x,temp.y);
	}

	private void valueAt(Vector2 temp2, float percent) {
		temp.x = (float) (centre.x+a*Math.cos(2*Math.PI*percent));
		temp.y = (float) (centre.y+b*Math.sin(2*Math.PI*percent));
	}

}

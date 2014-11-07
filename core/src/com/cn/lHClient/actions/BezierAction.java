package com.cn.lHClient.actions;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

/**贝塞尔曲线运行,传入,时间,起始点,控制点,结束点*/
public class BezierAction extends TemporalAction{

	private Bezier<Vector2> tempBezier;
	private final Vector2 tempVector2 = new Vector2();
	
	public BezierAction(float time,Vector2 startPoint,Vector2 controlPoint,Vector2 endPoint){
		super(time);
		tempBezier = new Bezier<Vector2>(startPoint,controlPoint,endPoint);
	}
	
	public BezierAction(float time,Vector2 startPoint,Vector2 controlPoint,Vector2 endPoint,Interpolation interpolation){
		super(time, interpolation);
		tempBezier = new Bezier<Vector2>(startPoint,controlPoint,endPoint);
	}
	
	public BezierAction(float time,Vector2 startPoint,Vector2 controlPoint,Vector2 controlPoint2,Vector2 endPoint){
		super(time);
		tempBezier = new Bezier<Vector2>(startPoint, controlPoint, controlPoint2,endPoint);
	}
	
	public BezierAction(float time,Vector2 startPoint,Vector2 controlPoint,Vector2 controlPoint2,Vector2 endPoint,Interpolation interpolation){
		super(time, interpolation);
		tempBezier = new Bezier<Vector2>(startPoint,controlPoint,controlPoint2,endPoint);
	}
	
	@Override
	protected void update(float percent) {
		tempBezier.valueAt(tempVector2, percent);
		actor.setPosition(tempVector2.x, tempVector2.y);
	}

}

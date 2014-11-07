package com.cn.lHClient.atcors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CreateProgressBar extends Actor {
	/**进度条*/
	private TextureRegion barRegion;
	/**是否显示进度值*/
	//private boolean isValueModel = false;
	/**最大进度条值*/
	private int maxBarValue;
	/**进度值*/
	private float value;
	
	/**
	 * 构造函数
	 * @background 背景
	 * */
	public CreateProgressBar(Texture progressbar){
		barRegion = new TextureRegion(progressbar);
		
		maxBarValue = barRegion.getRegionWidth();
		
		setSize(progressbar.getWidth(),progressbar.getHeight());
	}
	
	public void setValue(float value){
		this.value = value;
	}
	
	public float getValue(){
		return value;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		barRegion.setRegionWidth((int)(value*maxBarValue));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		batch.draw(barRegion, 
				getX(), getY(), 
				getOriginX(), getOriginY(), 
				barRegion.getRegionWidth(),
				barRegion.getRegionHeight()*1.05f, 
				getScaleX(), getScaleY(), 
				0);
	}
}

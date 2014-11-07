package com.cn.lHClient.atcors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.stage.StageAttribute;

/**是否中奖*/
public class PrizeAnimation extends Actor {
	
	private Animation longAnimation,heAnimation,huAnimation;
	private TextureRegion[] longTexure = new TextureRegion[3];
	private TextureRegion[] heTexure = new TextureRegion[3];
	private TextureRegion[] huTexure = new TextureRegion[3];
	
	private float stateTime;
	private TextureRegion keyFrame;
	
	private boolean[] num = {false,false,false}; 
	
	public PrizeAnimation(){
		for(int i=0;i<3;i++){
			heTexure[i] = new TextureRegion(Assets.get("LhPic/BetItem/"+(i+1)+".png", Texture.class));
			longTexure[i] = new TextureRegion(Assets.get("LhPic/BetItem/"+(6+i+1)+".png", Texture.class));
			huTexure[i] = new TextureRegion(Assets.get("LhPic/BetItem/"+(3+i+1)+".png", Texture.class));
		}
		
		heAnimation = new Animation(0.2f, heTexure);
		longAnimation = new Animation(0.2f, longTexure);
		huAnimation = new Animation(0.2f, huTexure);
	}
	
	/**
	 * 用来表示那个中奖
	 * 数组依次表示   和  闲 庄
	 * */
	public void getAnimation(int num){
		if(num >= 0)
		this.num[num] = true;
	}
	
	public void removeAllAnimation(){
		num[0] = false;
		num[1] = false;
		num[2] = false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		stateTime += Gdx.graphics.getDeltaTime();
		if(num[0]){
			keyFrame = heAnimation.getKeyFrame(stateTime,true);
			batch.draw(keyFrame, 330,StageAttribute.height-480,  keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
		}
		if(num[2]){
			keyFrame = huAnimation.getKeyFrame(stateTime,true);
			batch.draw(keyFrame, 90,StageAttribute.height-440,  keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
		}
		
		if(num[1]){
			keyFrame = longAnimation.getKeyFrame(stateTime,true);
			batch.draw(keyFrame, 580,StageAttribute.height-430,  keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
		}
		super.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		
		super.act(delta);
	}

	
}

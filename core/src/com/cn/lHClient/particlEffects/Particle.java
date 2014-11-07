package com.cn.lHClient.particlEffects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Particle extends Actor{
	private ParticleEffect particle;
	private ParticleEffectPool particlepool;
	private ArrayList<ParticleEffect> particleList;
	
	/**
	 * 传入粒子和粒子所需要图片的路径
	 * 然后调用addParticle函数即可
	 * */
	public Particle(String path ,String img){
		particle = new ParticleEffect();
		particle.load(Gdx.files.internal(path), Gdx.files.internal(img));
		
		particlepool = new ParticleEffectPool(particle,5,10);
		particleList = new ArrayList<ParticleEffect>();
	}
	
	/**
	 * @param添加粒子
	 * @ x ,y 粒子的位置
	 * */
	public void addParticle(float x,float y){
		particle = particlepool.obtain();
		particle.setPosition(x, y);
		particleList.add(particle);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		for(int i=0;i<particleList.size();i++){
			if(particleList.get(i).isComplete()){
				particleList.remove(i);
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		for(int i=0;i<particleList.size();i++){
			particleList.get(i).draw(batch, Gdx.graphics.getDeltaTime());
		}
	}

	public void dispose(){
		particle.dispose();
		particlepool.clear();
		particleList.clear();
	}
}

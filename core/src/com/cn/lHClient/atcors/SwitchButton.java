package com.cn.lHClient.atcors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**开关按钮*/
public class SwitchButton extends Actor{
	private  Image norImage,pressImage;
	private  Label label;
	private  boolean isTouchDown = false;

	public SwitchButton(String string,Texture norTexture,Texture pressTexture,Color color,int size){
		label = CreateLabel.getLabel(string,size, Align.center, color);
		if(norTexture != null){
			norImage = new Image(norTexture);
			setSize(norImage.getWidth(),norImage.getHeight());
		}
		
		if(pressTexture != null){
			pressImage = new Image(pressTexture);
			setSize(pressImage.getWidth(),pressImage.getHeight());
		}
	}
	
	/**设置文本内容*/
	public void setText(String string){
		label.setText(string);
	}
	
	public void setIsTouchDown(boolean isTouchDown){
		this.isTouchDown = isTouchDown;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		label.act(delta);
		if(norImage != null){
			norImage.act(delta);
		}
		
		if(pressImage != null){
			pressImage.act(delta);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(!isTouchDown && norImage != null){
			label.setPosition(getX()+norImage.getWidth()/2-label.getWidth()/2, getY()+norImage.getHeight()/2-label.getHeight()/2);
			norImage.setPosition(getX(), getY());
			norImage.draw(batch, parentAlpha);
		}else if(pressImage != null){
			label.setPosition(getX()+pressImage.getWidth()/2-label.getWidth()/2, getY()+pressImage.getHeight()/2-label.getHeight()/2);
			pressImage.setPosition(getX(), getY());
			pressImage.draw(batch, parentAlpha);
		}
		label.draw(batch, parentAlpha);
	}
}

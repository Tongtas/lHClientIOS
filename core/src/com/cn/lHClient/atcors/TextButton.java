package com.cn.lHClient.atcors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class TextButton extends Actor{
	/**正常图片*/
	private  Image normal;
	/**按下的图片*/
	private  Image pressed;
	/**图片文本内容*/
	private  Label textLabel;
	
	private  Image currentImage;
	
	public TextButton(Texture image,float alpha){
		this(null,40,image,null,alpha);
	}
	
	public TextButton(String text,int size,Texture image,float alpha){
		this(text,size,image,null,alpha);
	}
	
	public TextButton(Texture image,Texture normalImage){
		this(null,40,image,normalImage,1);
	}
	
	public TextButton(String text,Texture normalImage,Texture pressedImage){
		this(text,40,normalImage,pressedImage,1);
	}
	
	public void setText(String string){
		textLabel.setText(string);
	}
	
	public TextButton(String text,int size,Texture normalImage,final Texture pressedImage,float alpha){
		setSize(normalImage.getWidth(),normalImage.getHeight());
		
		final float myAlpha = alpha;
		normal = new Image(normalImage);
		if(pressedImage != null){
			pressed = new Image(pressedImage);
		}
		
		if(textLabel == null){
			textLabel = CreateLabel.getLabel(text,size,Align.center,Color.YELLOW,true);
		}
		currentImage = normal;
		
		addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				setEnabel(true,pressedImage == null, myAlpha);
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				setEnabel(false,pressedImage == null,1f);
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
	}

	/**设置按下的图片*/
	public void setEnabel(boolean flg,boolean isPressedEnpty,float alpha){
		if(flg){
			if(isPressedEnpty){
				currentImage.setColor(currentImage.getColor().r, currentImage.getColor().g, currentImage.getColor().b, alpha);
			}else{
				currentImage = pressed;
			}
			
		}else{
			currentImage.setColor(currentImage.getColor().r, currentImage.getColor().g, currentImage.getColor().b, alpha);
			currentImage = normal;
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(textLabel != null){
			textLabel.act(delta);
		}
		currentImage.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
		currentImage.setPosition(getX(), getY());
		currentImage.setRotation(getRotation());
		currentImage.draw(batch, parentAlpha);
		
		if(textLabel != null){
			textLabel.setPosition(getX()+currentImage.getWidth()/2-textLabel.getWidth()/2, getY()+currentImage.getHeight()/2-textLabel.getHeight()/2);
			textLabel.draw(batch, parentAlpha);
		}
	}

}

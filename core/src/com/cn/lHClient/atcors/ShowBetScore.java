package com.cn.lHClient.atcors;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.property.GameProperty;

/**显示分数的actor*/
public class ShowBetScore extends Actor{
	private Image image,image2,image3;
	private Label label;
	
	private int targetScore,flag;
	
	/**
	 * @flag 这个标志是显示什么分数
	 * 
	 * 0:表示和
	 * 1:表示龙
	 * 2:表示虎
	 * */
	public ShowBetScore(int flag){
		this.flag = flag;
		if(flag == 0){
			image = new Image(Assets.get("LhPic/Coin/1.png", Texture.class));
			image2 = new Image(Assets.get("LhPic/Coin/1.png", Texture.class));
			image3 = new Image(Assets.get("LhPic/Coin/1.png", Texture.class));
			targetScore = GameProperty.betItemScore[2];
		}else if(flag == 1){
			image = new Image(Assets.get("LhPic/Coin/5.png", Texture.class));
			image2 = new Image(Assets.get("LhPic/Coin/5.png", Texture.class));
			image3 = new Image(Assets.get("LhPic/Coin/6.png", Texture.class));
			targetScore = GameProperty.betItemScore[1];
		}else if(flag == 2){
			image = new Image(Assets.get("LhPic/Coin/3.png", Texture.class));
			image2 = new Image(Assets.get("LhPic/Coin/3.png", Texture.class));
			image3 = new Image(Assets.get("LhPic/Coin/4.png", Texture.class));
			targetScore = GameProperty.betItemScore[0];
		}
		
		label = CreateLabel.getLabel(Integer.toString(targetScore),18,Align.center, Color.BLACK);
		setSize(image.getWidth(), image.getHeight());
	}
	
	public void setScore(int score){
		targetScore = score;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(flag == 0){
			targetScore = GameProperty.betItemScore[2];
		}else if(flag == 1){
			targetScore = GameProperty.betItemScore[1];
		}else if(flag == 2){
			targetScore = GameProperty.betItemScore[0];
		}
		if(targetScore > 0)
		label.setText(Integer.toString(targetScore));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		label.setPosition(image.getX()+(image.getWidth()-label.getWidth())/2,image.getY()+(image.getHeight()-label.getHeight())/2);
		image.setPosition(getX(), getY());
		image2.setPosition(getX(), getY()-2);
		image3.setPosition(getX(), getY()-4);
		
		if(targetScore > 0){
			if(targetScore > 100){
				image2.draw(batch, parentAlpha);
			}
			if(targetScore > 300){
				image3.draw(batch, parentAlpha);
			}
			
			image.draw(batch, parentAlpha);
			label.draw(batch, parentAlpha);
		}
	}


	
	

}

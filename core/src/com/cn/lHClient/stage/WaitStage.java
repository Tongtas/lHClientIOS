package com.cn.lHClient.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.atcors.CreateLabel;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.manager.StageManager.Transition;
import com.cn.lHClient.property.GameProperty;

public class WaitStage extends AbstractStage{
	
	private Image bg;
	private Label label;
	public WaitStage(Viewport viewport) {
		super(viewport);
		bg = new Image(Assets.get("LhPic/Wait.png", Texture.class));
		label = CreateLabel.getLabel(Integer.toString(GameProperty.countTime), 40, Align.center, Color.YELLOW);
		label.setBounds(0, 0, 100, 40);
		setPosition();
		addActor();
	}
	
	private void setPosition() {
		bg.setPosition((StageAttribute.widgth - bg.getWidth())/2, (StageAttribute.height - bg.getHeight())/2);
		label.setPosition(bg.getX()+55,bg.getY()+72);
	}
	
	private void addActor() {
		addActor(bg);
		addActor(label);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(GameProperty.state != GameProperty.State.gsBoutEnd){
			final AbstractStage stage = StageManager.getStage(WaitStage.class);
			StageManager.stageSwitchOver(stage,Transition.BounceScaleOut,new Runnable() {
				public void run() {
					StageManager.removeStage(stage);
					System.out.println("remove waitStage");
				}
			});
		}
	}
	
	@Override
	public void draw() {
		super.draw();
		label.setText(Integer.toString(GameProperty.RoundTime));
	}
}

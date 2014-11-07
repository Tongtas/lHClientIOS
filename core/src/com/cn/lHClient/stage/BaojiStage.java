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

public class BaojiStage extends AbstractStage{
	
	private Image bg;
	private Label label;
	public BaojiStage(Viewport viewport) {
		super(viewport);
		bg = new Image(Assets.get("LhPic/error.png", Texture.class));
		label = CreateLabel.getLabel("游戏已爆机,请联系服务员", 40, Align.center, Color.YELLOW);
		
		setPosition();
		addActor();
	}
	private void setPosition() {
		bg.setPosition((StageAttribute.widgth - bg.getWidth())/2, (StageAttribute.height - bg.getHeight())/2);
		label.setPosition(bg.getX()+122,bg.getY()+75);
	}
	private void addActor() {
		addActor(bg);
		addActor(label);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(GameProperty.countTime == 0){
			final AbstractStage stage = StageManager.getStage(BaojiStage.class);
			StageManager.stageSwitchOver(stage,Transition.BounceScaleOut,new Runnable() {
				public void run() {
					StageManager.removeStage(stage);
				}
			});
		}
		label.setText(Integer.toString(GameProperty.countTime));
	}
	
}

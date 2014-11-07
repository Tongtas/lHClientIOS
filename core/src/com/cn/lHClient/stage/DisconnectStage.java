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
import com.cn.lHClient.utils.CreateMaskImage;

public class DisconnectStage extends AbstractStage{
	private Image bg1,bg;
	private Label label,label2;
	
	public DisconnectStage(Viewport viewport) {
		super(viewport);
		bg1 = CreateMaskImage.getMaskImage(StageAttribute.widgth,StageAttribute.height);
		
		bg = new Image(Assets.get("LhPic/error.png", Texture.class));
		label = CreateLabel.getLabel("与服务器断开连接",25,Align.center, Color.YELLOW,true);
		label2 = CreateLabel.getLabel("本机台号:"+(GameProperty.flagNum+1),30,Align.center, Color.YELLOW,true);
		setposition();
		addActor();
	}
	private void setposition() {
		bg1.setPosition(0, 0);
		bg.setPosition((StageAttribute.widgth-bg.getWidth())/2, (StageAttribute.height-bg.getHeight())/2);
		label.setPosition((StageAttribute.widgth-label.getWidth())/2, (StageAttribute.height-label.getHeight())/2+25);
		label2.setPosition((StageAttribute.widgth-label2.getWidth())/2, (StageAttribute.height-label2.getHeight())/2-50);
	}
	private void addActor() {
		addActor(bg1);
		addActor(bg);
		addActor(label);
		addActor(label2);
	}

	@Override
	public void act(float delta) {
		if(GameProperty.isConnect){
			final AbstractStage stage = StageManager.getStage(DisconnectStage.class);
			StageManager.stageSwitchOver(stage,Transition.FadeOut,new Runnable() {
				public void run() {
					StageManager.removeStage(stage);
				}
			});
		}
		super.act(delta);
	}
}

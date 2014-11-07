package com.cn.lHClient.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.atcors.TextButton;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.manager.StageManager.Transition;

public class AboutStage extends AbstractStage{

	private Image bg;
	private TextButton exit;
	public AboutStage(Viewport viewport) {
		super(viewport);
		
		bg = new Image(Assets.get("LhPic/explain.png", Texture.class));
		exit = new TextButton(Assets.get("LhPic/exit.png", Texture.class),Assets.get("LhPic/exitPress.png", Texture.class));
		
		setposition();
		addActor();
		addListener();
	}


	private void setposition() {
		bg.setPosition((StageAttribute.widgth-bg.getWidth())/2,(StageAttribute.height-bg.getHeight())/2);
		exit.setPosition(bg.getWidth()-10, bg.getHeight()-10);
	}
	
	private void addActor() {
		addActor(bg);
		addActor(exit);
	}
	
	private void addListener() {
		exit.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				final AbstractStage stage = StageManager.getStage(AboutStage.class);
				StageManager.stageSwitchOver(stage,Transition.FadeOut,new Runnable() {
					public void run() {
						StageManager.removeStage(stage);
					}
				});
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
	}


}

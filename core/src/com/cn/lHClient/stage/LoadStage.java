package com.cn.lHClient.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.atcors.CreateProgressBar;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.manager.StageManager.Transition;
import com.cn.lHClient.utils.FileUtils;

public class LoadStage extends AbstractStage{
	private Image background,progressBarBg;
	private CreateProgressBar progressBar;
	private boolean isfinish = true;
	
	public LoadStage(Viewport viewport) {
		super(viewport);
		/**读取配置文件*/
		int musicValue = FileUtils.shareFile().getPropertyInteger("Music");
		int soundValue = FileUtils.shareFile().getPropertyInteger("Sound");
		
		//FileUtils.shareFile("Setting").setProperty("Music", 0);
		
		if(musicValue == 0){
			//关闭音乐
		}else{
			//打开音乐
		}
		
		if(soundValue == 0){
			//关闭音效
		}else{
			//打开音效
		}
		
		Assets.load(Assets.loadScene, Texture.class);
		Assets.finishLoading();
		background = new Image(Assets.get("LhPic/loadBg.png", Texture.class));
		progressBarBg = new Image(Assets.get("LhPic/progressBarBg.png", Texture.class));
		progressBar = new CreateProgressBar(Assets.get("LhPic/progressBar.png", Texture.class));
		
		
		setposition();
		addActor();
		
		Assets.load(Assets.scene1, Texture.class);
		Assets.load(Assets.scene2, Texture.class);
		Assets.load(Assets.scene3, Texture.class);
	}

	/**设置坐标*/
	private void setposition() {
		background.setPosition(0,0);
		progressBarBg.setPosition(24,StageAttribute.height-501-50);
		progressBar.setPosition(35,StageAttribute.height-493-50);
	}

	/**添加到舞台*/
	private void addActor() {
		addActor(background);
		addActor(progressBarBg);
		addActor(progressBar);
	}

	@Override
	public void act() {
		super.act();
		if(Assets.update()){
			if(isfinish){
				isfinish = false;
				final AbstractStage stage = StageManager.getStage(MainStage.class);
				
				StageManager.stageSwitchOver(stage,Transition.FadeIn,new Runnable() {
					public void run() {
						StageManager.removeStage(StageManager.getStage(LoadStage.class));
					}
				});
			}
		}else{
			progressBar.setValue(Assets.getProgress());
		}
	}
	
	@Override
	public void draw() {
		super.draw();
	}

	public void disopose(){
		
	}
	
	
}

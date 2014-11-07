package com.cn.lHClient.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AbstractStage extends Stage{
	private boolean isPause = false;
	
	public AbstractStage(Viewport viewport){
		super(viewport);
		
	}

	/**暂停*/
	public void pause(){
		isPause = true;
	}
	
	/**继续*/
	public void resume(){
		isPause = false;
	}
	
	/**是否暂停*/
	public boolean getPause(){
		return isPause;
	}
}

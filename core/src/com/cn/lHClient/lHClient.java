package com.cn.lHClient;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.stage.LoadStage;

public class lHClient extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		StageManager.pushAndCleanStage(StageManager.getStage(LoadStage.class));
		batch = new SpriteBatch();
		System.out.println("Game Create");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		StageManager.render();
		
//		batch.begin();
//		FontUtilsTTF.getFont().draw(batch, "fps:"+Gdx.graphics.getFramesPerSecond()+" JavaHeap:"+Gdx.app.getJavaHeap()/1000000, 0, 50);
//		batch.end();
	}

	@Override
	public void resume() {
		super.resume();
		Assets.update();
		
	}

	@Override
	public void pause() {
		super.pause();
		if(Gdx.app.getType() == ApplicationType.iOS){
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		System.out.println("Game dispose");
	}
	
	
	
}

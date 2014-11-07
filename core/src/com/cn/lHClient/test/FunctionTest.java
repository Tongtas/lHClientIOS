package com.cn.lHClient.test;

import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.atcors.TextButton;
import com.cn.lHClient.handler.Handler;
import com.cn.lHClient.handler.Message;
import com.cn.lHClient.net.ConnectServer;
import com.cn.lHClient.utils.CreateUtils;

/**
 * @author Administrator
 *
 */
public class FunctionTest implements ApplicationListener{
	
	private Stage stage;
	private TextButton connect,send;
	private ConnectServer connectServer ;
	private ByteArray msg;
	
	@Override
	public void create() {
		Assets.load("LhPic/Button/11.png", Texture.class);
		Assets.finishLoading();
		stage = new Stage(new StretchViewport(1360,768));
		
		connect = new TextButton(Assets.get("LhPic/Button/11.png", Texture.class), 0.6f);
		connect.setPosition(100, 100);
		
		send = new TextButton(Assets.get("LhPic/Button/11.png", Texture.class), 0.6f);
		send.setPosition(200, 100);
			
		connectServer = new ConnectServer(mHandler);
		addHeartbeat();
		addListener();
		addActor();
		Gdx.input.setInputProcessor(stage);
	}


	public Handler mHandler = new Handler(){
		  public void handleMessage(Message msg) {		
			switch (msg.what) {
			case 1:
				break;
			case 2:
				break;
			default:
				break;
			}
		}
		};
	/**心跳包*/
	private void addHeartbeat() {
		TimePack t = new TimePack();
		t.loginCmd =  CreateUtils.intTobyte(586688);
		final ByteArray tmsg = new ByteArray();
		tmsg.addAll(t.loginCmd);
		tmsg.addAll(t.connect);
		
		new Timer().scheduleTask(new Task() {
			public void run () {
				try {
					connectServer.sendMsg(tmsg.toArray());
					System.out.println("已发送心跳包");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 0, 8);
	}

	private void addListener() {
		connect.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				connectServer.start();
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		send.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				sendMsg();
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	private void addActor() {
		stage.addActor(connect);
		stage.addActor(send);
	}
	
	protected void sendMsg() {
		
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.Name = CreateUtils.stringTobyte(loginInfo.Name, "BACRB_CLIENT15");
		loginInfo.Pwd = CreateUtils.stringTobyte(loginInfo.Pwd, "BACCARAT");
		loginInfo.loginCmd = CreateUtils.intTobyte(586686);
		loginInfo.length = CreateUtils.intTobyte(371);
		loginInfo.Playerid = CreateUtils.intTobyte(14);
		loginInfo.LifeTimeCount = CreateUtils.intTobyte(10000);
		
		msg = new ByteArray();
		msg.addAll(loginInfo.loginCmd);
		msg.addAll(loginInfo.length);
		msg.addAll(loginInfo.Name);
		msg.addAll(loginInfo.Pwd);
		msg.addAll(loginInfo.Level);
		msg.addAll(loginInfo.Ip);
		msg.addAll(loginInfo.Gameid);
		msg.addAll(loginInfo.Playerid);
		msg.addAll(loginInfo.LifeTimeCount);
		msg.addAll(loginInfo.LoginTime);
		msg.addAll(loginInfo.LoginSuccess);
		msg.addAll(loginInfo.LoginType);
		msg.addAll(loginInfo.other);
		
		try {
			if(connectServer != null){
				connectServer.sendMsg(msg.toArray());
			}else{
				System.out.println("socket失败!!");
			}
			System.out.println("msgSize:"+(msg.size-8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class LoginInfo {
		byte[] loginCmd = new byte[4];
		byte[] length = new byte[4];
		
		byte[] Name = new byte[33];
		byte[] Pwd = new byte[33];
		byte[] Level = new byte[4];
		byte[] Ip = new byte[20];
		byte[] Gameid = new byte[4];
		byte[] Playerid = new byte[4];
		byte[] LifeTimeCount = new byte[4];
		byte[] LoginTime  = new byte[8];
		byte[] LoginSuccess = new byte[1];
		byte[] LoginType = new byte[4];
		byte[] other = new byte[256];
	}
	
	class TimePack{
		byte[] loginCmd = new byte[4];
		byte[] connect = new byte[4];
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		connectServer.disConnnect();
	}

	@Override
	public void resize(int width, int height) {
		
	}

}

package com.cn.lHClient.desktop;

import java.io.BufferedOutputStream;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cn.lHClient.lHClient;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.plantform.Platform;

public class DesktopLauncher implements Platform {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		StageManager.registerPlatformInstance(new DesktopLauncher());
		new LwjglApplication(new lHClient(), config);
	}
	

	@Override
	public void callApp(String pageName,String activityName) {
		System.out.println("启动程序:"+activityName);
	}

	@Override
	public void share(String activityTitle, String msgTitle, String msgText, String imgPath) {
		System.out.println("share:"+msgText);
	}


	@Override
	public int getIp() {
		return 12;
	}


	@Override
	public void setIp(String ip) {
		
	}


	@Override
	public void toast(String msg) {
		System.out.println("Toast:"+msg);
	}


	@Override
	public void getOutputStream(BufferedOutputStream bufferedOutputStream) {
		
	}


	@Override
	public void exitGame() {
		// TODO Auto-generated method stub
		
	}
}

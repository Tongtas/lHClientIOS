package com.cn.lHClient;

import java.io.BufferedOutputStream;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.cn.lHClient.Ios.wifi.IosWifi;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.plantform.Platform;

public class IOSLauncher extends IOSApplication.Delegate implements Platform {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.useCompass = false;
        config.preventScreenDimming = false;
        StageManager.registerPlatformInstance(this);
        return new IOSApplication(new lHClient(), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

	@Override
	public void callApp(String pageName, String activityName) {
		
	}

	@Override
	public void share(String activityTitle, String msgTitle, String msgText,
			String imgPath) {
		
	}

	@Override
	public int getIp() {
		return new IosWifi().getInstance().getIp();
	}

	@Override
	public void setIp(String ip) {
		
	}

	@Override
	public void toast(String msg) {
		
	}

	@Override
	public void getOutputStream(BufferedOutputStream bufferedOutputStream) {
		
	}

	@Override
	public void exitGame() {
		
	}
}
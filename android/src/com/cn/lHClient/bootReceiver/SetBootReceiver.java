package com.cn.lHClient.bootReceiver;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.cn.lHClient.android.AndroidLauncher;

/**开机启动类*/
public class SetBootReceiver extends SysBootReceiver{
	
	@Override
	protected Class<? extends AndroidApplication> getStartActivity() {
		return AndroidLauncher.class;
	}
}



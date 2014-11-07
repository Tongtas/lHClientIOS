package com.cn.lHClient.bootReceiver;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public abstract class SysBootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			Intent actionIntent = new Intent(context,getStartActivity());
			actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			context.startActivity(actionIntent);
		}
	}
	
	/**取得启动Activity*/
	protected abstract  Class<? extends AndroidApplication> getStartActivity();
}

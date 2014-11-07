package com.cn.lHClient.android.service;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HeartbeatService extends Service implements Runnable{

	private String action = "com.cn.lHClient.android.service.HeartbeatService";
	private Timer timer;
	private Intent timerIntent;
	
	@Override
	public void onCreate() {
		timer = new Timer();
		run();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}

	@Override
	public void run() {
		timer.schedule(new TimerTask() {
			public void run() {
				sendHeartBeat();
			}
		}, 1000, 5000);
	}

	protected void sendHeartBeat() {
		timerIntent = new Intent(action);
		sendBroadcast(timerIntent);
	}
}

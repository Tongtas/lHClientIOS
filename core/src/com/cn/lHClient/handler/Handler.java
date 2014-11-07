package com.cn.lHClient.handler;

import com.badlogic.gdx.Gdx;

public class Handler {

	public void sendEmptyMessage(int what) {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
				handleMessage(obtainMessage());
			}
		});
	}

	public Message obtainMessage() {
		return new Message(this);
	}

	public void sendMessage(final Message msg) {
		Gdx.app.postRunnable(new Runnable() {
			public void run() {
				handleMessage(msg);
			}
		});
	}
	
	public void handleMessage(Message msg){
		
	}

}

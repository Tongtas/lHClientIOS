package com.cn.lHClient.android;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.ByteArray;
import com.cn.lHClient.lHClient;
import com.cn.lHClient.android.service.HeartbeatService;
import com.cn.lHClient.android.wifi.AndroidWifi;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.plantform.Platform;
import com.cn.lHClient.utils.CreateUtils;

public class AndroidLauncher extends AndroidApplication  implements Platform {
	
	public WifiManager wifimanage;
	public WifiConfiguration wificfg = null;
	public Context contect ;
	public Handler uiHandler;
	public Intent heartbeatService ;
	private static final String RECVTIMERACTION = "com.cn.lHClient.android.service.HeartbeatService";
	public BufferedOutputStream bufferedOutputStream;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contect = getApplicationContext();
		uiHandler = new Handler();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);    
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);//设置手机全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //防止手机休眠
		StageManager.registerPlatformInstance(this);
		
		wifimanage=(WifiManager)getSystemService(Context.WIFI_SERVICE);//获取WifiManager 
		
		/**开启服务和广播接收*/
		startHeartbeatService();
		registerBroadcastReceiver();
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new lHClient(), config);
	}

	@Override
	protected void onResume() {
		super.onResume();
		fullScreen();
	}
	
	/**开启心跳包服务*/
	public void startHeartbeatService(){
		heartbeatService = new Intent(contect, HeartbeatService.class);
		startService(heartbeatService);
	}
	
	/**注册广播 xml 注册*/
	public void registerBroadcastReceiver(){
		HeartTimerReceicer receiver = new HeartTimerReceicer();
		IntentFilter filter = new IntentFilter(RECVTIMERACTION);
		registerReceiver(receiver, filter);
	}
	
	
	/**去除导航栏*/
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("InlinedApi")
	public void fullScreen(){
		View view = getWindow().getDecorView();
		if(getSystemVersion() > 18){
			hideSystemUi(view);
		}else{
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
	        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
	        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // hide nav bar
	        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); // hide status bar
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		}
		
		if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
			  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}
	
	/**获取焦点*/
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus && getSystemVersion() >18){
			fullScreen();
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE){
			fullScreen();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**监听返回按钮*/
   @Override
    public void onBackPressed() {
       //super.onBackPressed();
    }

	/**
	 * Android 4.4系统的沉浸模式
	 * 隐藏虚拟按键和状态栏
	 * */
	@SuppressLint("InlinedApi")
	private void hideSystemUi(View view){
		view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE  
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  
                | View.SYSTEM_UI_FLAG_FULLSCREEN  
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); 
	}
	
	/**
	 * 退出沉浸模式
	 * 显示虚拟按键
	 * */
	@SuppressLint("InlinedApi")
	private void showSystemUi(View view){
		view.setSystemUiVisibility(
				 View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				 | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				 | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);  
	}
	
	/**
	 * 获取系统的版本
	 * */
	public static int getSystemVersion(){
		int ver = android.os.Build.VERSION.SDK_INT;
		return ver;
	}
	
	@Override
	public void callApp(String pageName,String activityName) {
		Intent intent=new Intent();  
		intent.setClassName(pageName, activityName);  
		startActivity(intent);  
	}
	
	@Override
	public void toast(final String msg) {
		uiHandler.post(new Runnable() {
			public void run() {
				Toast.makeText(contect, msg, Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	/**静默安装程序*/
	public void installApp(String path){
		
	}
	
	/**分享*/
	@Override
	public void share(final String activityTitle,final String msgTitle, final String msgText, final String imgPath) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(Intent.ACTION_SEND);
				if (imgPath == null || imgPath.equals("")) {
					intent.setType("text/plain"); // 纯文本
				} 
				else 
				{
					File f = new File(imgPath);
					if (f != null && f.exists() && f.isFile()) {
						intent.setType("image/png");
						Uri u = Uri.fromFile(f);
						intent.putExtra(Intent.EXTRA_STREAM, u);
					}
				}
				intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
				intent.putExtra(Intent.EXTRA_TEXT, msgText);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, activityTitle));
			}
		});
		
	}

	/**获取ip,我们只获取最后一位*/
	public int getIp(){
		return AndroidWifi.getInstance(contect).getIp();
	}
	
	/**更改Ip*/
	public void setIp(String ip){
		try {
			AndroidWifi.getInstance(contect).setIp(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}		
	}

	
	@Override
	public void getOutputStream(BufferedOutputStream bufferedOutputStream) {
		this.bufferedOutputStream = bufferedOutputStream;
	}
	
	public void sendHeartMsg() throws IOException{
		TimePack t = new TimePack();
		t.loginCmd =  CreateUtils.intTobyte(586688);
		final ByteArray tmsg = new ByteArray();
		tmsg.addAll(t.loginCmd);
		tmsg.addAll(t.connect);
		
		bufferedOutputStream.write(tmsg.toArray());
		bufferedOutputStream.flush();
	}
	
	private class HeartTimerReceicer extends BroadcastReceiver{
		public void onReceive(Context context, Intent intent) {
			if(intent != null){
				if(intent.getAction().equals(RECVTIMERACTION) && bufferedOutputStream != null){
					//Log.e("AndroidLauncher", "心跳包");
					try {
						sendHeartMsg();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	class TimePack{
		byte[] loginCmd = new byte[4];
		byte[] connect = new byte[4];
	}

	@Override
	public void exitGame() {
		
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}

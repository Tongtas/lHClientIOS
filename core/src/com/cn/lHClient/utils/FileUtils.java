package com.cn.lHClient.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class FileUtils {

	private static Preferences share;
	private static FileUtils instance;
	
	/**获取实例*/
	public static FileUtils shareFile(){
		if(instance == null){
			instance = new FileUtils();
			share = Gdx.app.getPreferences("Recording");
		}
		return instance;
	}
	
	/**获取值为字符串的值*/
	public String getPropertyString(String key){
		return share.getString(key);
	}
	
	/**获取值为整形的值*/
	public int getPropertyInteger(String key){
		return share.getInteger(key);
	}
	
	/**设置值为字符串的值*/
	public void setProperty(String key,String val){
		share.putString(key, val);
		share.flush();
	}
	
	/**设置值为整形的值*/
	public void setProperty(String key,int val){
		share.putInteger(key, val);
		share.flush();
	}
}

package com.cn.lHClient.utils;

import com.badlogic.gdx.Gdx;
import com.cn.lHClient.stage.StageAttribute;

public class Logger {
	
	private static boolean isOpen = false;
	private static String tag = StageAttribute.GameName;
	
	/**
	 * 使用之前要setLogger
	 * 打印debug消息 msg
	 * */
	public static void showDebug(String msg){
		if(isOpen){
			Gdx.app.debug(tag, msg);
		}
	}
	
	/**
	 * 使用之前要setLogger
	 * 打印debug消息 tag 和 msg
	 * */
	public static void showDebug(String tag,String msg){
		if(isOpen){
			Gdx.app.debug(tag, msg);
		}
	}
	
	
	/**
	 * 使用之前要setLogger
	 * 打印错误消息
	 * */
	public static void showError(String msg){
		if(isOpen){
			Gdx.app.error(tag, msg);
		}
	}
	
	/**
	 * 使用之前要setLogger
	 * 打印错误消息
	 * */
	public static void showError(String tag,String msg){
		if(isOpen){
			Gdx.app.error(tag, msg);
		}
	}

	/**
	 * 使用之前要setLogger
	 * 打印log消息
	 * */
	public static void showLog(String msg){
		if(isOpen){
			Gdx.app.log(tag, msg);
		}
	}
	
	/**
	 * 使用之前要setLogger
	 * 打印log消息
	 * */
	public static void showLog(String tag,String msg){
		if(isOpen){
			Gdx.app.log(tag, msg);
		}
	}
	
	
	/**打印的开关
	 * true  打开
	 * false 关闭
	 * */
	public static void setLogger(boolean on_off){
		isOpen = on_off;
	}
	
	/**设置打印的tag*/
	public static void setTag(String newtag){
		tag = newtag;
	}
}


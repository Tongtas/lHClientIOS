package com.cn.lHClient.utils;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * 多语言管理器,中文,英文,等等
 * */
public class Language {

	private static I18NBundle bundle;
	
	/**初始化语言*/
	private static void init(){
		if(bundle == null){
			I18NBundle.setSimpleFormatter(true);
			FileHandle handle = Gdx.files.internal("language/message");
			String country = Locale.getDefault().getCountry();
			if("CN".equals(country)){
				bundle = I18NBundle.createBundle(handle);
			}else if("TW".equals(country)){
				bundle = I18NBundle.createBundle(handle, new Locale("tw"));
			}else if("ja".equals(country)){
				bundle = I18NBundle.createBundle(handle, new Locale("jpa"));
			}else{
				bundle = I18NBundle.createBundle(handle, new Locale("en"));
			}
		}
	}
	
	
	/**获取文本*/
	public static String get(String key){
		init();
		return bundle.get(key);
	}
	
	/**文本替换,参考java.text.messageFormat
	 * 
	 * eg:format("Hello{0},you have new mission,please finsh it on {1,time}","niecheng","30s")
	 * 
	 * */
	public static String format(String key,Object... object){
		init();
		return bundle.format(key, object);
	}
	
	
}

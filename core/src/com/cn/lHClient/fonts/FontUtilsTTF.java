package com.cn.lHClient.fonts;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontUtilsTTF {

	private static BitmapFont myFont,myFont6,myFont15,myFont20,myFont36,myFont72,myFontChinese;
	private static FreeTypeFontGenerator generator;
	private static FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	
	/**
	 * 初始化默认字体
	 * */
	private static void init(){
		if(generator == null){
			if(Gdx.app.getType() == ApplicationType.Android){
				generator  = new FreeTypeFontGenerator(Gdx.files.absolute("/system/fonts/DroidSansFallback.ttf"));
			}else if(Gdx.app.getType() == ApplicationType.Desktop){
				generator  = new FreeTypeFontGenerator(Gdx.files.absolute("C:\\Windows\\Fonts\\DroidSansFallback.ttf"));
				//generator  = new FreeTypeFontGenerator(Gdx.files.absolute("/System/Library/Fonts/STHeiti Light.ttc"));
			}else if(Gdx.app.getType() == ApplicationType.iOS){
				generator  = new FreeTypeFontGenerator(Gdx.files.absolute("System/Library/Fonts/Cache/STHeiti-Light.ttc"));
			}else {
				generator  = new FreeTypeFontGenerator(Gdx.files.absolute("C:\\Windows\\Fonts\\DroidSansFallback.ttf"));
			}
		}
	}

	public static BitmapFont getFont(){
		if(myFont == null){
			init();
			myFont = generator.generateFont(40);
			myFont.setColor(Color.BLACK);
		}
		return myFont;
	}
	
	public static BitmapFont getFont(int size){
			init();
			if(size == 15){
				if(myFont15 == null){
					myFont15 = generator.generateFont(size);
				}
				return myFont15;
			}else if(size == 20){
				if(myFont20 == null){
					myFont20 = generator.generateFont(size);
				}
				return myFont20;
			}else if(size == 36){
				if(myFont36 == null){
					myFont36 = generator.generateFont(size);
				}
				return myFont36;
			}else if(size == 72){
				if(myFont72 == null){
					myFont72 = generator.generateFont(size);
				}
				return myFont72;
			}else{
				if(myFont6 == null)
					myFont6 = generator.generateFont(size);
				return myFont6;
			}
	}
	
	
	public static BitmapFont getFont(String string,int size,boolean isChiese){
		init();
		parameter.characters = replaceDuplicateCharacter(parameter.characters+string);
		parameter.size = size;
		myFontChinese = generator.generateFont(parameter);
		return myFontChinese;
	}
	
	/**
	 * 去除重复字符
	 * @param str
	 * @return 去除后的字符串
	 */
	public static String replaceDuplicateCharacter(String str)
	{
		return str.replaceAll("(?s)(.)(?=.*\\1)", ""); 
	}
	
	public static void dispose(){
		if(generator != null){
			generator.dispose();
		}
	}
}

package com.cn.lHClient.fonts;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * @author Administrator
 *
 */
public class FontUtils {
	
	private static FreeTypeFontGeneratorExt generator;
	
	private static BitmapFont font;
	
	private static void init()
	{
		if(generator == null)
		{
			if(Gdx.app.getType() == ApplicationType.Desktop)
			{
				generator = new FreeTypeFontGeneratorExt(Gdx.files.absolute("C:\\Windows\\Fonts\\DroidSansFallback.ttf"));
			}
			else if(Gdx.app.getType() == ApplicationType.Android)
			{
				generator = new FreeTypeFontGeneratorExt(Gdx.files.absolute("/system/fonts/DroidSansFallback.ttf"));
			}
			else if(Gdx.app.getType() == ApplicationType.iOS)
			{
				generator = new FreeTypeFontGeneratorExt(Gdx.files.absolute("System/Library/Fonts/Cache/STHeiti-Light.ttc"));
			}
			font = generator.init(32, false);
			font = generator.appendToFont(FreeTypeFontGeneratorExt.DEFAULT_CHARS, font);
			font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	public static BitmapFont getFont()
	{
		init();
		return font;
	}
	
	
	public static void appendFont(String fontText)
	{
		init();
		fontText = replaceDuplicateCharacter(fontText);
		font = generator.appendToFont(fontText, font);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	/*public static BitmapFont getFont(String fontText)
	{
		init();
		fontText = replaceDuplicateCharacter(fontText);
		sb.delete(0, sb.length()-1);
		sb.append(fontText);
		for(int i=0;i<sb.length();i++)
		{
			if(str.contains(String.valueOf(sb.charAt(i))))
			{
				sb.deleteCharAt(i);
			}
		}
		font = generator.appendToFont(sb.toString(), font);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return font;
	}*/
	
	/**
	 * 去除重复字符
	 * @param str
	 * @return 去除后的字符串
	 */
	public static String replaceDuplicateCharacter(String str)
	{
		return str.replaceAll("(?s)(.)(?=.*\\1)", ""); 
	}
	
	
	public static void dispose()
	{
		generator.dispose();
		//font.dispose();
	}
	
}

package com.cn.lHClient.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontUtilsFNT {
	private static BitmapFont font;
	public static BitmapFont getFont(){
		if(font == null){
			font = new BitmapFont(Gdx.files.internal("BacPic/fonts/default.fnt"),Gdx.files.internal("BacPic/fonts/default.png"), false);
		}
		return font;
	}
}

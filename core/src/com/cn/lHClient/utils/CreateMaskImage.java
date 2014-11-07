package com.cn.lHClient.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CreateMaskImage {

	public static Image init(){
		return new Image(getPointImage());
	}
	
	/**获取一个像素点的图片*/
	public static Texture getPointImage(){
		Pixmap pixmap = new Pixmap(1,1,Format.RGBA8888);
		pixmap.drawPixel(0, 0, Color.rgba8888(Color.WHITE));
		return new Texture(pixmap);
	}
	
	/**获取一个像素点为白色的图片*/
	public static Image getMaskImage(float width,float height){
		Image mask = init();
		mask.setSize(width, height);
		mask.setColor(0, 0, 0, 0.6f);
		return mask;
	}
}

package com.cn.lHClient.atcors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.cn.lHClient.fonts.FontUtilsTTF;

public class CreateLabel {
	public static Label getLabel(String text,int alignment,Color color){
		LabelStyle style = new LabelStyle(FontUtilsTTF.getFont(),color);
		Label label = new Label(text,style);
		label.setAlignment(alignment);
		return label;
	}
	
	public static Label getLabel(String text,int size,int alignment,Color color){
		LabelStyle style = new LabelStyle(FontUtilsTTF.getFont(size),color);
		Label label = new Label(text,style);
		label.setAlignment(alignment);
		return label;
	}
	
	public static  Label getLabel(String text,int size ,int alignment,Color color,boolean isChinese){
		LabelStyle style = new LabelStyle(FontUtilsTTF.getFont(text,size,isChinese),color);
		Label label = new Label(text,style);
		label.setAlignment(alignment);
		return label;
	}
}

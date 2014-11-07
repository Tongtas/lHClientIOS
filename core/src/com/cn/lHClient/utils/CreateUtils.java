package com.cn.lHClient.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cn.lHClient.assets.Assets;

public class CreateUtils {
	
	/**获取Drawable对象,可在image随时换*/
	public static TextureRegionDrawable getTextureRegionDrawable(String path)
	{
		return new TextureRegionDrawable(new TextureRegion(Assets.get(path, Texture.class)));
	}
	
	/**int 转换成byte数组*/
	public static byte[] intTobyte(int a){
		byte[] target = new byte[4];
		
		target[0] = (byte)(a & 0xff);
		target[1] = (byte)(a>>8 & 0xff);
		target[2] = (byte)(a>>16 & 0xff);
		target[3] = (byte)(a>>>24);
		return target;
	}
	
	/**String赋值给byte数组*/
	public static byte[] stringTobyte (byte[] rec ,String string){
		byte[] a = string.getBytes();
		rec[0] = (byte)(string.length()& 0xff);
		for(int i=1;i<=a.length;i++){
			rec[i] = a[i-1];
		}
		return rec;
	}
	
	
	/**byte数组的前四位转换成int*/
	public static int bytesToInt(byte[] bytes) {  
	      int addr=0;  
	      if(bytes.length==1){  
	    	  addr = bytes[0] & 0xFF;  
	      }else{ 
	          addr = bytes[0] & 0xFF;  
	          addr |= ((bytes[1] << 8) & 0xFF00);  
	          addr |= ((bytes[2] << 16) & 0xFF0000);  
	          addr |= ((bytes[3] << 24) & 0xFF000000);  
	      }  
	      return addr;  
	}
	
	
	/**
	 * 将int转为低字节在前，高字节在后的byte数组
	 */
	public static byte[] tolh(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}
	/**
	 * 将byte数组转化成String
	 */
	public static String toStr(byte[] valArr,int maxLen) {
		int index = 0;
		while(index < valArr.length && index < maxLen) {
			if(valArr[index] == 0) {
				break;
			}
			index++;
		}
		byte[] temp = new byte[index];
		System.arraycopy(valArr, 0, temp, 0, index);
		return new String(temp);
	}
	
	/**
	 * 将低字节在前转为int，高字节在后的byte数组
	 */
	public static int vtolh(byte[] bArr) {
		int n = 0;
		for(int i=0;i<bArr.length&&i<4;i++){
			int left = i*8;
			n+= (bArr[i] << left);
		}
		return n;
	}
	/**
	 * 将float转为低字节在前，高字节在后的byte数组
	 */
	public static byte[] tolh(float f) {
		return tolh(Float.floatToRawIntBits(f));
	}
	
	/**字节转换成boolean*/
	public static boolean btob(byte b){
		if(b == 0){
			return true;
		}
		return false;
	}
	
}

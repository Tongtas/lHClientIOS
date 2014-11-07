package com.cn.lHClient.plantform;

import java.io.BufferedOutputStream;

public interface Platform {
	/**
	 * @param android Toast
	 * @msg 要显示的文本内容
	 * @time 文本显示的时间,只有短和长两种选择,分别表示0,1
	 * */
	public void callApp(String pageName,String activityName);
	
	/**
	 * @param 分享
	 * @activityTitle 标题
	 * @msgText 分享内容
	 * @imgPath 图片内容 没有可以为null
	 * */
	public void share(String activityTitle,String msgTitle, String msgText, String imgPath);
	
	/**获取ip*/
	public int getIp();
	
	/**设置ip*/
	public void setIp(String ip);
	
	/**toast*/
	public void toast(String msg);
	
	/**退出*/
	public void exitGame();
	
	/**心跳包服务包*/
	public void getOutputStream(BufferedOutputStream bufferedOutputStream);
}


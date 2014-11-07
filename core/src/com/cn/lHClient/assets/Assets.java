package com.cn.lHClient.assets;

import com.badlogic.gdx.assets.AssetManager;

/**
 * 把资源分的很细,到时候可以做到分批次加载
 * 避免一次加载时间过长
 * 
 * */
public class Assets {

	private static AssetManager manager = new AssetManager();
	
	public static String[] loadScene ={
		"LhPic/loadBg.png",
		"LhPic/progressBar.png",
		"LhPic/progressBarBg.png"
	};
	
	/**场景1*/
	public static String[] scene1 = {
		"LhPic/mainbg.png",
		"LhPic/record.png",
		"LhPic/Wait.png",
		"LhPic/error.png",
		"LhPic/totalBetMoney.png"
	};
	
	/**场景2*/
	public static String[] scene2 = {
		"LhPic/BetItem/1.png",
		"LhPic/BetItem/2.png",
		"LhPic/BetItem/3.png",
		"LhPic/BetItem/4.png",
		"LhPic/BetItem/5.png",
		"LhPic/BetItem/6.png",
		"LhPic/BetItem/7.png",
		"LhPic/BetItem/8.png",
		"LhPic/BetItem/9.png",
		
		"LhPic/Card/0.png",
		"LhPic/Card/1.png",
		"LhPic/Card/2.png",
		"LhPic/Card/3.png",
		"LhPic/Card/4.png",
		"LhPic/Card/5.png",
		"LhPic/Card/6.png",
		"LhPic/Card/7.png",
		"LhPic/Card/8.png",
		"LhPic/Card/9.png",
		"LhPic/Card/10.png",
		"LhPic/Card/11.png",
		"LhPic/Card/12.png",
		"LhPic/Card/13.png",
		"LhPic/Card/14.png",
		"LhPic/Card/15.png",
		"LhPic/Card/16.png",
		"LhPic/Card/17.png",
		"LhPic/Card/18.png",
		"LhPic/Card/19.png",
		"LhPic/Card/20.png",
		"LhPic/Card/21.png",
		"LhPic/Card/22.png",
		"LhPic/Card/23.png",
		"LhPic/Card/24.png",
		"LhPic/Card/25.png",
		"LhPic/Card/26.png",
		"LhPic/Card/27.png",
		"LhPic/Card/28.png",
		"LhPic/Card/29.png",
		"LhPic/Card/30.png",
		"LhPic/Card/31.png",
		"LhPic/Card/32.png",
		"LhPic/Card/33.png",
		"LhPic/Card/34.png",
		"LhPic/Card/35.png",
		"LhPic/Card/36.png",
		"LhPic/Card/37.png",
		"LhPic/Card/38.png",
		"LhPic/Card/39.png",
		"LhPic/Card/40.png",
		"LhPic/Card/41.png",
		"LhPic/Card/42.png",
		"LhPic/Card/43.png",
		"LhPic/Card/44.png",
		"LhPic/Card/45.png",
		"LhPic/Card/46.png",
		"LhPic/Card/47.png",
		"LhPic/Card/48.png",
		"LhPic/Card/49.png",
		"LhPic/Card/50.png",
		"LhPic/Card/51.png",
		"LhPic/Card/52.png",
		
		"LhPic/Coin/1.png",
		"LhPic/Coin/2.png",
		"LhPic/Coin/3.png",
		"LhPic/Coin/4.png",
		"LhPic/Coin/5.png",
		"LhPic/Coin/6.png",
		"LhPic/Coin/7.png",
		"LhPic/Coin/8.png",
		
		"LhPic/Direction/4.png",
		"LhPic/Direction/5.png",
		"LhPic/Direction/6.png",
		"LhPic/Direction/7.png",
		
		"LhPic/hog.png",
		"LhPic/hog2.png",
		
		"LhPic/Num/000.png",
		"LhPic/Num/001.png",
		"LhPic/Num/002.png",
		"LhPic/Num/003.png",
		"LhPic/Num/004.png",
		"LhPic/Num/005.png",
		"LhPic/Num/006.png",
		"LhPic/Num/007.png",
		"LhPic/Num/008.png",
		"LhPic/Num/009.png",
		

		"LhPic/Button/1.png",
		"LhPic/Button/2.png",
		"LhPic/Button/3.png",
		"LhPic/Button/4.png",
		"LhPic/Button/5.png",
		"LhPic/Button/6.png",
		"LhPic/Button/7.png",
		"LhPic/Button/8.png",
		
		"LhPic/exit.png",
		"LhPic/exitPress.png",
		"LhPic/he.png",
		"LhPic/hu.png",
		"LhPic/long.png",
		"LhPic/green.png",
		"LhPic/blue.png",
		"LhPic/red.png",
		
		
		"LhPic/menu.png",
		"LhPic/menuClose.png",
		"LhPic/menuOpen.png",
		
		"LhPic/mm/1.png",
		"LhPic/mm/2.png",
		"LhPic/mm/3.png",
		"LhPic/mm/4.png",
		"LhPic/mm/5.png",
		"LhPic/mm/6.png",
		"LhPic/mm/7.png",
		"LhPic/mm/8.png",
		"LhPic/mm/9.png",
		"LhPic/mm/10.png",
		"LhPic/mm/11.png",
		"LhPic/mm/12.png",
		"LhPic/mm/13.png",
		"LhPic/mm/14.png",
		"LhPic/mm/15.png",
		"LhPic/mm/16.png",
		"LhPic/mm/17.png",
		"LhPic/mm/18.png",
		"LhPic/mm/19.png",
		"LhPic/mm/20.png",
		"LhPic/mm/21.png",
		"LhPic/mm/22.png",
		"LhPic/mm/23.png",
		"LhPic/mm/24.png",
		"LhPic/mm/25.png",
		"LhPic/mm/26.png",
		"LhPic/mm/27.png",
		"LhPic/mm/28.png",
		"LhPic/mm/29.png",
		"LhPic/mm/30.png",
		"LhPic/mm/31.png",
		"LhPic/mm/32.png",
		"LhPic/mm/33.png",
		"LhPic/mm/34.png",
		"LhPic/mm/35.png",
		"LhPic/mm/36.png",
		"LhPic/mm/37.png",
		"LhPic/mm/38.png",
		"LhPic/mm/39.png",
		"LhPic/mm/40.png",
		"LhPic/mm/41.png",
		"LhPic/mm/42.png",
		"LhPic/mm/43.png",
		"LhPic/mm/44.png",
		"LhPic/mm/45.png",
		"LhPic/mm/46.png",
		"LhPic/mm/47.png",
		"LhPic/mm/48.png",
		"LhPic/mm/49.png",
		"LhPic/mm/50.png",
		"LhPic/mm/51.png",
		"LhPic/mm/52.png",
		"LhPic/mm/53.png",
		"LhPic/mm/0.png"
	};
	
	/**场景3*/
	public static String[] scene3 = {
		"LhPic/changeIp/button1-1.png",
		"LhPic/changeIp/messageframe.png",
		"LhPic/changeIp/button1-2.png",
		"LhPic/changeIp/button7-1.png",
		"LhPic/changeIp/button7-2.png",
		"LhPic/changeIp/pswbase.png",
		"LhPic/changeIp/pswmessage1.png",
		"LhPic/changeIp/pswnum1-1.png",
		"LhPic/changeIp/pswnum1-2.png",
		"LhPic/changeIp/pswnum1-3.png",
		"LhPic/changeIp/pswnum1-4.png",
		"LhPic/changeIp/pswnum1-5.png",
		"LhPic/changeIp/pswnum1-6.png",
		"LhPic/changeIp/pswnum1-7.png",
		"LhPic/changeIp/pswnum1-8.png",
		"LhPic/changeIp/pswnum1-9.png",
		"LhPic/changeIp/pswnum1-10.png",
		"LhPic/changeIp/pswnum1-11.png",
		"LhPic/changeIp/pswnum2-1.png",
		"LhPic/changeIp/pswnum2-2.png",
		"LhPic/changeIp/pswnum2-3.png",
		"LhPic/changeIp/pswnum2-4.png",
		"LhPic/changeIp/pswnum2-5.png",
		"LhPic/changeIp/pswnum2-6.png",
		"LhPic/changeIp/pswnum2-7.png",
		"LhPic/changeIp/pswnum2-8.png",
		"LhPic/changeIp/pswnum2-9.png",
		"LhPic/changeIp/pswnum2-10.png",
		"LhPic/changeIp/pswnum2-11.png"
	};
	
	/**背景音乐*/
	public static String[] muisc = {
		
	};
	
	/**音效*/
	public static String[] sound = {
		
	};
	
	/**粒子特效*/
	public static String[] particle[] ={
		
	};
	
	/**3D模型*/
	public static String[] modle[] = {
		
	};
	
	/**加载字体*/
	public static String[] fonts[] = {
		
	};
	
	/**加载完成时,返回true,否则返回false*/
	public static boolean update(){
		return manager.update();
	}
	
	/**加载资源*/
	public static <T> void load(String[] path ,Class<T> type){
		for(int i=0;i<path.length;i++){
			load(path[i], type);
		}
	}
	
	/**加载资源*/
	public static <T> void load(String path,Class<T> type){
		manager.load(path, type);
	}
	
	/**获取资源*/
	public static <T> T get(String name , Class<T> type){
		return manager.get(name, type);
	}
	
	/**卸载指定资源*/
	public static void remove(String name){
		manager.unload(name);
	}
	
	/**获取当前加载进度*/
	public static float getProgress(){
		return manager.getProgress();
	}
	
	public static void finishLoading()
	{
		manager.finishLoading();
	}
	
	/**卸载所有资源*/
	public static void dispose(){
		manager.dispose();
	}
	
}

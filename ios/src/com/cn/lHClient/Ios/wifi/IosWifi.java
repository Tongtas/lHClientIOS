package com.cn.lHClient.Ios.wifi;

import org.robovm.apple.foundation.NSObject;

public class IosWifi extends NSObject {

	static IosWifi iosWifi = null;
	
	public  IosWifi getInstance(){
		if(iosWifi == null){
			iosWifi = new IosWifi();
		}
		return iosWifi;
	}
	
	public int getIp(){
		String ip = new WifiIpLib().localWiFiIPAddress();
		if(ip != null)
			return Integer.parseInt(ip.substring(ip.lastIndexOf(".")+1));
		else{
			return 88;
		}
	}
}

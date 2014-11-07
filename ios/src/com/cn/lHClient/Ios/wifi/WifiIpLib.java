package com.cn.lHClient.Ios.wifi;

import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.annotation.Method;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.rt.bro.annotation.Library;

@Library(Library.INTERNAL)  
@NativeClass 
public class WifiIpLib extends NSObject {

	static {
		ObjCRuntime.bind(WifiIpLib.class);
	}
	
	@Method(selector = "localWiFiIPAddress")
	public native String localWiFiIPAddress();
}

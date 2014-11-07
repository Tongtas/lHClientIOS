package com.cn.lHClient.android.wifi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class AndroidWifi {
	WifiManager wifimanager;
	WifiConfiguration wifiConf = null;
	WifiInfo wifiInfo;
	static AndroidWifi androidWifi;
	
	public AndroidWifi(Context context){
		wifimanager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifimanager.getConnectionInfo();
	}
	
	public static AndroidWifi getInstance(Context context){
		if(androidWifi == null){
			androidWifi = new AndroidWifi(context);
		}
		return androidWifi;
	}
	
	/**获取wifi IP*/
	public int getIp(){
		if(wifimanager.isWifiEnabled()){
			/**这里只获取ip的最后一段*/
			return ((wifiInfo.getIpAddress() >>24)& 0xff);
			/**返回全部*/
			//return ((wifiInfo.getIpAddress())&0xff);
		}
		return 0;
	}
	
	/**设置wifi IP,Dns ,gateway
	 * @throws UnknownHostException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException */
	public void setIp(String ip) throws UnknownHostException, SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException{
		if(wifimanager.isWifiEnabled()){
			List<WifiConfiguration> configuredNetWorks = wifimanager.getConfiguredNetworks();
			for(WifiConfiguration conf:configuredNetWorks){
				if(conf.networkId == wifiInfo.getNetworkId()){
					wifiConf = conf;
					break;
				}
			}
			
			setIpAssignment("STATIC",wifiConf);//或者设置动态"DHCP"
			setIpAddress(InetAddress.getByName(ip),24,wifiConf);
			setGateway(InetAddress.getByName("192.168.1.1"),wifiConf);
			setDNS(InetAddress.getByName("192.168.1.1"),wifiConf);
			wifimanager.updateNetwork(wifiConf);
			wifimanager.saveConfiguration();
			wifimanager.disconnect();
			wifimanager.reconnect();
		}
	}
	


	public void setIpAssignment(String assign , WifiConfiguration wifiConf)throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
		setEnumField(wifiConf, assign, "ipAssignment");     
	}

    public void setIpAddress(InetAddress addr, int prefixLength, WifiConfiguration wifiConf)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException,
    NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException{
        Object linkProperties = getField(wifiConf, "linkProperties");
        if(linkProperties == null)return;
        Class laClass = Class.forName("android.net.LinkAddress");
        Constructor laConstructor = laClass.getConstructor(new Class[]{InetAddress.class, int.class});
        Object linkAddress = laConstructor.newInstance(addr, prefixLength);

        ArrayList mLinkAddresses = (ArrayList)getDeclaredField(linkProperties, "mLinkAddresses");
        mLinkAddresses.clear();
        mLinkAddresses.add(linkAddress);        
    }

    public void setGateway(InetAddress gateway, WifiConfiguration wifiConf)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, 
    ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException{
        Object linkProperties = getField(wifiConf, "linkProperties");
        if(linkProperties == null)return;
        Class routeInfoClass = Class.forName("android.net.RouteInfo");
        Constructor routeInfoConstructor = routeInfoClass.getConstructor(new Class[]{InetAddress.class});
        Object routeInfo = routeInfoConstructor.newInstance(gateway);

        ArrayList mRoutes = (ArrayList)getDeclaredField(linkProperties, "mRoutes");
        mRoutes.clear();
        mRoutes.add(routeInfo);
    }

    public  void setDNS(InetAddress dns, WifiConfiguration wifiConf)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
        Object linkProperties = getField(wifiConf, "linkProperties");
        if(linkProperties == null)return;

        ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>)getDeclaredField(linkProperties, "mDnses");
        mDnses.clear(); //or add a new dns address , here I just want to replace DNS1
        mDnses.add(dns); 
    }

    public  Object getField(Object obj, String name)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        Field f = obj.getClass().getField(name);
        Object out = f.get(obj);
        return out;
    }

    public  Object getDeclaredField(Object obj, String name)
    throws SecurityException, NoSuchFieldException,
    IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(name);
        f.setAccessible(true);
        Object out = f.get(obj);
        return out;
    }  

    public  void setEnumField(Object obj, String value, String name)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        Field f = obj.getClass().getField(name);
        f.set(obj, Enum.valueOf((Class<Enum>) f.getType(), value));
    }
	
}

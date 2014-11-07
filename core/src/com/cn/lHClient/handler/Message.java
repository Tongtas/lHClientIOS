package com.cn.lHClient.handler;

public class Message {

	public int what;
	public int arg1,arg2;
	public boolean arg4,arg5;
	public int[] arg3;
	public boolean[] arg6,arg7;
	public Handler handler;
	
	public Message(){
		
	}
	public Message(Handler h){
		handler = h;
	}
	
	public void sendToTarget() {
		handler.sendMessage(this);
	}
}

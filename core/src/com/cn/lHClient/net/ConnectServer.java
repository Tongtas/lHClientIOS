package com.cn.lHClient.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.cn.lHClient.handler.Handler;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.property.GameProperty;
import com.cn.lHClient.utils.CreateUtils;
import com.cn.lHClient.utils.RecvTypes;

/**连接服务器和通信*/
public class ConnectServer extends Thread{
	/**是否是断开后的首次连接*/
	private final static int  PORT = 32765;
	private final static String HOST = "192.168.1.18";
	private final static int TIMEOUT = 50000;
	
	private Socket socket;
	private byte[] message = new byte[1024];
	
	private BufferedInputStream mBufferedInputStream = null;
	private BufferedOutputStream mBufferedOutputStream = null;
	
	/**模拟Android的消息处理机制*/
	private Handler mHandler;
	private RecvTypes recvTypes;
	
	private boolean isFirst = true;
	
	public ConnectServer(Handler mHandler){
		this.mHandler = mHandler;
	}
	
	@Override
	public void run() {
		super.run();
		GameProperty.flagNum = StageManager.getPlatFormInstance().getIp()-1;
		recvTypes = new RecvTypes(mHandler);
		connectServer();
		addHeartbeat();
		
		byte[] temp = new byte[4];
		int cmd = 0;
		while(true){
			try {
				/**接收消息并添加到队列中*/
				int length = mBufferedInputStream.read(message);
				if(length >= 8){
					System.out.println("---------------------------\n\n##接收到数据的总长度:"+length);
					System.out.println("##接收到的总数据:"+Arrays.toString(message));
					int offset = 0;
					
					byte[] msg = new byte[length];
					System.arraycopy(message, 0, msg, 0, length);
					/**从队列中处理消息*/
					cmd = CreateUtils.bytesToInt(msg);
					
					while(isUserful(cmd)){
						cmd = 0;
						System.arraycopy(msg, 4+offset, temp, 0, 4);
						int userfulDataLength = CreateUtils.bytesToInt(temp)+8;
						
						byte[] userfulMsg = new byte[userfulDataLength];
						System.arraycopy(msg, offset, userfulMsg, 0, userfulDataLength);
						disposeData(userfulMsg);
						
						System.arraycopy(userfulMsg, 0, temp, 0, 4);
						System.out.println("包头:"+CreateUtils.bytesToInt(temp) +"  length:"+userfulDataLength+"  offset:"+offset);
						
						offset += userfulDataLength;
						System.out.println((offset+8)+"  "+length);
						if(offset+8 < length){
							System.out.println("\n--------@@---------------------");
							System.arraycopy(msg, offset, temp, 0, 4);
							cmd = CreateUtils.bytesToInt(temp);
						}
					}
				}else if(length == -1){
					restartNet();
				}
			} catch (IOException e) {
				System.out.println("与服务器断开连接");
				restartNet();
			}
		}
	}
	
	/**接受到的数据处理*/
	private void disposeData(final byte[] message2) {
		int cmd = CreateUtils.bytesToInt(message2);
		System.out.println("接收到的数据类型："+cmd+"   数据长度为:"+message2.length+"      数据内容:"+Arrays.toString(message2));
		switch (cmd) {
		case 5001:
			recvTypes.new CMD_GAMEDATA(message2);
			break;
		case 5002:
			recvTypes.new CMD_OPTIONDATA(message2);
			break;
		case 5003:
			recvTypes.new CMD_PLAYERDATA(message2);
			break;
		case 5004:
			recvTypes.new CMD_GAMETIMEDATA(message2);
			break;
		case 5005:
			recvTypes.new CMD_GAMECARDDATA(message2);
			break;
		case 5006:
			recvTypes.new CMD_PLAYERBETDATE(message2);
			break;
		case 5007:
			recvTypes.new CMD_UPDOWNSCOREDATA(message2);
			break;
		case 5008:
			recvTypes.new CMD_PLAYERCANCELBETDATA(message2);
			break;
		case 5009:
			recvTypes.new CMD_GAMELDDATA(message2);
			break;
		case 5010:
			recvTypes.new CMD_INOUTCOINDATA();
			break;
/*		case 5011:
			System.out.println("##休息倒计时:"+Arrays.toString(message));
			recvTypes.new CMD_CLEARBOARD(message2);
			break;*/
		case 5012:
			recvTypes.new CMD_StopFlash();
			break;
		case 5013:
			recvTypes.new CMD_BANKERTIME(message2);
			break;
		case 5014:
			recvTypes.new CMD_BankerData(message2);
			break;
		case 5015:
			recvTypes.new CMD_UPDOWNENDDATA();
			break;
		case 5016:
			recvTypes.new CMD_Close();
			break;
		case 5017:
			recvTypes.new CMD_PLAYERTOLBETDATA(message2);
			break;
		case 5018:
			recvTypes.new CMD_BAOJIDATA();
			break;
		case 5019:
			recvTypes.new CMD_RoundTimeData(message2);
			break;
		case 5020:
			System.out.println("5020");
			break;
		case 5021:
			System.out.println("5021");
			break;
		case 5022:
			System.out.println("5022");
			break;
		case 5023:
			System.out.println("5023");
			break;
		case 5024:
			recvTypes.new CMD_CardIndex(message2);
			break;
		case 5025:
			recvTypes.new CMD_CUTINDEX(message2);
			break;
		case 5026:
			recvTypes.new CMD_PlayerAllBet(message2);
			break;
		default:
			break;
		}
	}

	/**判断数据是否有效*/
	int cmds[] = {5001,5002,5003,5004,5005,5006,5007,5008,5009,5010,5011,5012,5013,5014,5105,5016,5017,5018,5019,5020,5021,5022,5023,5024,5025,5026,50020,586686,586687};
	public boolean isUserful(int cmd){
		for(int a :cmds){
			if(a == cmd){
				return true;
			}
		}
		return false;
	}
	
	public void sendMsg(byte[] msg) throws IOException{
		if(mBufferedOutputStream != null){
			System.out.println("send:"+Arrays.toString(msg));
			mBufferedOutputStream.write(msg);
			mBufferedOutputStream.flush();
		}else{
			GameProperty.isConnect = false;
			System.out.println("已经断开连接");
			restartNet();
		}
	}
	
	public void connectServer() {
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(HOST, PORT),TIMEOUT);
			
			socket.setTcpNoDelay(true);//数据不作缓冲，立即发送
			socket.setSoLinger(true, 0);//socket关闭时，立即释放资源
			socket.setTrafficClass(0x04|0x10);//高可靠性和最小延迟传输
			
			mBufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
			mBufferedInputStream = new BufferedInputStream(socket.getInputStream());
			
			GameProperty.isConnect = true;
			GameProperty.flagNum = StageManager.getPlatFormInstance().getIp()-1;
			System.out.println("与服务器连接成功");
			/**发送登录包*/
			login();
			addHeartbeat();
			/**读板包*/
			readBoard();
			StageManager.getPlatFormInstance().toast("发送登录包");
		}  catch (IOException e) {
			System.out.println("与服务器断开连接");
			restartNet();
		}
	}
	
	/**断开服务器*/
	public void disConnnect(){
		GameProperty.isConnect = false;
		try {
			if(socket != null){
				socket.close();
			}
			
			if(mBufferedInputStream != null){
				mBufferedInputStream.close();
			}
			
			if(mBufferedOutputStream != null){
				mBufferedOutputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**重新连接服务器*/
	public void restartNet(){
		System.out.println("重启服务器");
		disConnnect();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connectServer();
	}

	/**登录包*/
	protected void login() {
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.Name = CreateUtils.stringTobyte(loginInfo.Name, "LHCLIENT"+(GameProperty.flagNum+1));
		loginInfo.Pwd = CreateUtils.stringTobyte(loginInfo.Pwd, "RBGAMES");
		loginInfo.loginCmd = CreateUtils.intTobyte(586686);
		loginInfo.length = CreateUtils.intTobyte(371);
		loginInfo.Playerid = CreateUtils.intTobyte(GameProperty.flagNum+1);
		loginInfo.LifeTimeCount = CreateUtils.intTobyte(10000);
		
		ByteArray msg = new ByteArray();
		msg.addAll(loginInfo.loginCmd);
		msg.addAll(loginInfo.length);
		msg.addAll(loginInfo.Name);
		msg.addAll(loginInfo.Pwd);
		msg.addAll(loginInfo.Level);
		msg.addAll(loginInfo.Ip);
		msg.addAll(loginInfo.Gameid);
		msg.addAll(loginInfo.Playerid);
		msg.addAll(loginInfo.LifeTimeCount);
		msg.addAll(loginInfo.LoginTime);
		msg.addAll(loginInfo.LoginSuccess);
		msg.addAll(loginInfo.LoginType);
		msg.addAll(loginInfo.other);
		System.out.println("登录包消息:"+Arrays.toString(msg.toArray()));
		try {
			sendMsg(msg.toArray());
			
			System.out.println("发送登录包成功:"+(msg.size-8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void readBoard() {
		ReadBoardData readBoard = new ReadBoardData();
		readBoard.loginCmd = CreateUtils.intTobyte(GameProperty.CMD_COIN);
		readBoard.length = CreateUtils.intTobyte(9);
		readBoard.PlayerId = (byte) GameProperty.flagNum;
		
		ByteArray msg = new ByteArray();
		msg.addAll(readBoard.loginCmd);
		msg.addAll(readBoard.length);
		msg.addAll(readBoard.PlayerId);
		msg.addAll(readBoard.InCoinCount);
		msg.addAll(readBoard.OutCoinCount);
		
		try {
			sendMsg(msg.toArray());
			System.out.println("msg.length:"+(msg.size-8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**心跳包*/
	private void addHeartbeat() {
		if(Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.iOS){
			TimePack t = new TimePack();
			t.loginCmd =  CreateUtils.intTobyte(586688);
			final ByteArray tmsg = new ByteArray();
			tmsg.addAll(t.loginCmd);
			tmsg.addAll(t.connect);
			
			new Timer().scheduleTask(new Task() {
				public void run () {
					try {
						if(GameProperty.isConnect && isFirst){
							sendMsg(tmsg.toArray());
							//System.out.println("已发送心跳包");
						}else{
							isFirst = false;
							restartNet();
						}
					} catch (IOException e) {
						isFirst = false;
						restartNet();
					}
				}
			}, 0, 5);
		}else if(Gdx.app.getType() == ApplicationType.Android){
			StageManager.getPlatFormInstance().getOutputStream(mBufferedOutputStream);
		}
		
	}
	
	class TimePack{
		byte[] loginCmd = new byte[4];
		byte[] connect = new byte[4];
	}
	
	class LoginInfo {
		byte[] loginCmd = new byte[4];
		byte[] length = new byte[4];
		
		byte[] Name = new byte[33];
		byte[] Pwd = new byte[33];
		byte[] Level = new byte[4];
		byte[] Ip = new byte[20];
		byte[] Gameid = new byte[4];
		byte[] Playerid = new byte[4];
		byte[] LifeTimeCount = new byte[4];
		byte[] LoginTime  = new byte[8];
		byte[] LoginSuccess = new byte[1];
		byte[] LoginType = new byte[4];
		byte[] other = new byte[256];
	}
	
	/**读版命令包*/
	class ReadBoardData{
		byte[] loginCmd = new byte[4];
		byte[] length = new byte[4];
		
		byte PlayerId = 0;
        byte[] InCoinCount = new byte[4];
        byte[] OutCoinCount = new byte[4];
	}
}

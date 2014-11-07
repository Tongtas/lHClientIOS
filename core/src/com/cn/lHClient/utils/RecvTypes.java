package com.cn.lHClient.utils;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.cn.lHClient.handler.Handler;
import com.cn.lHClient.handler.Message;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.manager.StageManager.Transition;
import com.cn.lHClient.property.GameProperty;
import com.cn.lHClient.stage.AbstractStage;
import com.cn.lHClient.stage.BaojiStage;

public class RecvTypes{
	
	byte[] temp = new byte[4];
	private Handler mHandler;
	public RecvTypes(Handler mHandler){
		this.mHandler = mHandler;
	}
	
	/**游戏数据5001*/
	public class CMD_GAMEDATA{
		int gameMatch;
		int gameRound;
		byte gameState;
		byte nowtry = 0;
		public CMD_GAMEDATA(byte[] message){
			 System.out.println("##游戏数据:"+Arrays.toString(message));
			 
			 System.arraycopy(message, 8, temp, 0, 4);
			 gameMatch = CreateUtils.bytesToInt(temp);
			 System.arraycopy(message, 12, temp, 0, 4);
			 gameRound = CreateUtils.bytesToInt(temp);
			 gameState = message[16];
			 nowtry =message[17];
			 
			 GameProperty.boutNum = gameRound;
			 GameProperty.matchNum = gameMatch;
			 GameProperty.state = GameProperty.State.getState(gameState);
		 				 
			 Message msg =new Message();
			 msg.what = 3;
			 msg.arg1 = gameState;
			 mHandler.sendMessage(msg);	 
			 System.out.println("游戏状态切换为:"+gameState+"   "+GameProperty.State.getState(gameState).toString());
		}
	}
	
	/**游戏设置5002*/
	public class CMD_OPTIONDATA{
		int gameMaxBet,gameMaxOne,gameMinBet,gameMinBetStep,gameMaxBetStep,gameCoinStyle,GameMinBankerScore,GameCoinStep,GameRoundTime;
		byte GameBankerTime,GameBankerCount,GameBankerPayOutRate,GamePlayerPayOutRate,GameCutCount; 
		boolean GameOutCoinType;
		public CMD_OPTIONDATA(byte[] message){
			 System.out.println("##设置:"+Arrays.toString(message));
			 System.out.println("message length:"+message.length);
			 
			 System.arraycopy(message, 8, temp, 0, 4);
			 gameMaxBet = CreateUtils.bytesToInt(temp); /**限紅*/
			 System.arraycopy(message, 12, temp, 0, 4);
			 gameMaxOne = CreateUtils.bytesToInt(temp);/**限注*/
			 System.arraycopy(message, 16, temp, 0, 4);
			 gameMinBet = CreateUtils.bytesToInt(temp);/**最小押分*/
			 System.arraycopy(message, 20, temp, 0, 4);
			 gameMinBetStep = CreateUtils.bytesToInt(temp);/**最小步长*/
			 System.arraycopy(message, 24, temp, 0, 4);
			 gameMaxBetStep = CreateUtils.bytesToInt(temp);/**最大步长*/
			 
			 GameBankerTime = message[28];
			 GameBankerCount = message[29];
			 GameBankerPayOutRate = message[30];
			 GamePlayerPayOutRate = message[31];
			 System.arraycopy(message, 32, temp, 0, 4);
			 GameMinBankerScore = CreateUtils.bytesToInt(temp);/**庄家最少分数*/
			 
			 GameOutCoinType = CreateUtils.btob(message[36]);
			 System.arraycopy(message, 37, temp, 0, 4);
			 GameCoinStep = CreateUtils.bytesToInt(temp);/**投退币*/
			 System.arraycopy(message, 41, temp, 0, 4);
			 GameRoundTime = CreateUtils.bytesToInt(temp);/**上下分*/
			 GameCutCount = message[45];
			 
			 GameProperty.allMaxBetScore = gameMaxBet;
			 GameProperty.allMinBetScore = gameMinBet;
			 GameProperty.minStep = gameMinBetStep;
			 GameProperty.maxStep = gameMaxBetStep;
			 GameProperty.heMaxBetScore = GameProperty.allMaxBetScore/10;
			 GameProperty.GameBankerTime = GameBankerTime;
			 GameProperty.GameBankerCount = GameBankerCount;
			 GameProperty.GameBankerPayOutRate = GameBankerPayOutRate;
			 GameProperty.GamePlayerPayOutRate = GamePlayerPayOutRate;
			 GameProperty.GameMinBankerScore = GameMinBankerScore;
			 GameProperty.GameCutCount = GameCutCount;
			 
			 Message msg = new Message();
			 msg.what = 4;
			 mHandler.sendMessage(msg);
		}
	} 
	
	/**玩家数据5003*/
	public class CMD_PLAYERDATA{
		int tolMoney,toWin,BoutWithDraw;
		int[] itemBet = new int[3];
		public CMD_PLAYERDATA(byte[] message){
			System.out.println("##玩家数据:"+Arrays.toString(message));
			System.arraycopy(message, 8, temp, 0, 4);
  		    tolMoney = CreateUtils.bytesToInt(temp);/**总分*/
  		    System.arraycopy(message, 12, temp, 0, 4);
		    itemBet[0] = CreateUtils.bytesToInt(temp);/***/
		    System.arraycopy(message, 16, temp, 0, 4);
		    itemBet[1] = CreateUtils.bytesToInt(temp);
		    System.arraycopy(message, 20, temp, 0, 4);
		    itemBet[2] = CreateUtils.bytesToInt(temp);
		    System.arraycopy(message, 24, temp, 0, 4);
		    toWin = CreateUtils.bytesToInt(temp);/**总赢分*/
		    System.arraycopy(message, 28, temp, 0, 4);
		    BoutWithDraw = CreateUtils.bytesToInt(temp);/**提留分*/
		   
		    System.out.println("tolMoner:"+tolMoney);
		    System.out.println("ItemBet:"+itemBet[0]+" "+itemBet[1]+" "+itemBet[2]);
		    System.out.println("toWin :"+toWin);
		    System.out.println("BoutWithDraw:"+BoutWithDraw);
		    
		    GameProperty.BoutWithDraw = BoutWithDraw;
		    
	    	Message msg = new Message();
	    	msg.what = 5;
	    	msg.arg1 = tolMoney;
	    	msg.arg2 = toWin;
	    	msg.arg3 = itemBet;
	    	mHandler.sendMessage(msg);
		    
		}
	}
	
	/**倒计时5004*/
   public class CMD_GAMETIMEDATA {
    	int gameTime;
    	boolean outMax[] = new boolean[3];
    	public CMD_GAMETIMEDATA(byte[] message){
    		System.out.println("##倒计时:"+gameTime);
    		System.arraycopy(message, 8, temp, 0, 4);
  		    gameTime = CreateUtils.bytesToInt(temp);
  		    outMax[0] = CreateUtils.btob(message[12]);
  		    outMax[1] = CreateUtils.btob(message[13]);
  		    outMax[2] = CreateUtils.btob(message[14]);
  		    GameProperty.countTime = gameTime;
  		    Message msg =new Message();
  		    msg.what = 2;
  		    msg.arg6 = outMax;
  		    mHandler.sendMessage(msg);
  	   }
    }
    
    /**卡牌数据包5005*/
    public class CMD_GAMECARDDATA{
    	public CMD_GAMECARDDATA(byte[] message){
    		System.out.println("##卡牌数据包"+Arrays.toString(message));
    		//更新数组中的路单
    		GameProperty.billItem[GameProperty.matchNum] = message[12];
    		Message msg = new Message();
    		msg.what = 1;
    		msg.arg3 = new int[]{(message[8]-1)*13+message[9],(message[10]-1)*13+message[11],message[12]};
    		mHandler.sendMessage(msg);
    	}
    }
    
    /**押分数据包5006*/
    public class CMD_PLAYERBETDATE{
    	int playerId, betItem,betMoney;
    	public CMD_PLAYERBETDATE(byte[] message){
    		System.out.println("##押分数据包:"+Arrays.toString(message));
    		playerId = message[8];
    		betItem = message[9];
    		System.arraycopy(message, 10, temp, 0, 4);
    		betMoney = CreateUtils.bytesToInt(temp);
    	}
    }
    
    /**上分数据包5007*/
    public class CMD_UPDOWNSCOREDATA{
    	int upScore = 0,totalScore = 0;
    	public CMD_UPDOWNSCOREDATA(byte[] message){
    		System.out.println("##上下分数据包:"+Arrays.toString(message));
    		System.arraycopy(message, 9, temp, 0, 4);
    		totalScore = CreateUtils.bytesToInt(temp);
    		System.arraycopy(message, 13, temp, 0, 4);
    		upScore = CreateUtils.bytesToInt(temp);
    		
    		Message msg =new Message();
    		msg.what = 0;
    		msg.arg1 = totalScore;
    		msg.arg2 = upScore;
    		mHandler.sendMessage(msg);
    	}
    }
    
    /**玩家取消押分包5008*/
    public class CMD_PLAYERCANCELBETDATA{
    	int playerId;
    	public CMD_PLAYERCANCELBETDATA(byte[] message){
    		System.out.println("##取消押分:"+Arrays.toString(message));
    		System.arraycopy(message, 8, temp, 0, 4);
    		playerId = CreateUtils.bytesToInt(temp);
    	}
    }
    
    /**路单数据 5009*/
    public class CMD_GAMELDDATA{
    	int Bout;
    	int[] Records = new int[67];

    	public CMD_GAMELDDATA(byte[] message){
    		System.out.println("路单数据:"+Arrays.toString(message));
    		System.out.println("messageLength:"+message.length);
    		
    		System.arraycopy(message, 8, temp, 0, 4);
    		Bout = CreateUtils.bytesToInt(temp);
    		
    		for(int i=1;i<Records.length;i++){
    			System.arraycopy(message, 12+(i-1)*4, temp, 0, 4);
        		Records[i] = CreateUtils.bytesToInt(temp);
    		}
    		
    		GameProperty.billItem = Records;

    		//System.out.println("billRcord:"+Arrays.toString(GameProperty.billDoubleZhuan));
    		//System.out.println("billRcord:"+Arrays.toString(GameProperty.billDoubleXian));
    		
    		Message msg = new Message();
    		msg.what = 6;
    		mHandler.sendMessage(msg);
    		
    	}
    }
    
    /**退币包5010*/
    public class CMD_INOUTCOINDATA{
    	
    }
    
    /**倒计时5011*//*
    public class CMD_ROUNDTIMEDATA{
    	int roundTime;
    	public CMD_ROUNDTIMEDATA(byte[] message){
    		Gdx.app.postRunnable(new Runnable() {
				public void run() {
					if(!StageManager.isHadStage(StageManager.getStage(WaitStage.class))){
		    			final AbstractStage stage = StageManager.getStage(WaitStage.class);
		    			StageManager.stageSwitchOver(stage,Transition.None,new Runnable() {
		    				public void run() {
		    					
		    				}
		    			});
		    		}
				}
			});
    		
    		System.arraycopy(message, 8, temp, 0, 4);
    		roundTime = CreateUtils.bytesToInt(temp);
    		Message msg =new Message();
    		msg.what = 8;
    		msg.arg1 = roundTime;
    		mHandler.sendMessage(msg);
    	}
    }*/
    /**5012停止闪烁*/
    public class CMD_StopFlash{
    	public CMD_StopFlash(){
        	Message msg =new Message();
    		msg.what = 12;
    		mHandler.sendMessage(msg);
    	}
    }
    
    /**抢庄时间5013*/
    public class CMD_BANKERTIME{
    	public CMD_BANKERTIME(byte[] message){
    		System.out.println("##抢庄时间:"+Arrays.toString(message));
    		GameProperty.bankerTime = message[8];
    		Message msg = new Message();
    		msg.what = 13;
    		mHandler.sendMessage(msg);
    	}
    }
    
    /**5014*/
    public class CMD_BankerData {
    	byte PlayerId,BankerID,BankerTimes;
    	public CMD_BankerData(byte[] message){
    		System.out.println("##庄的属性:"+Arrays.toString(message));
    		PlayerId = message[8];
    		BankerID = message[9];
    		BankerTimes = message[10];
    		
    		GameProperty.GameBankerCurrentCount = BankerTimes;
    		GameProperty.GameBankerCurrentID = BankerID;
    		Message msg = new Message();
    		msg.what = 7;
    		mHandler.sendMessage(msg);
    	}
    }
    
    /******5015*/
    public class CMD_UPDOWNENDDATA {
    	
    };
    
    /**5016程序关闭*/
    public class CMD_Close{
    	public CMD_Close(){
    		Gdx.app.exit();
    	}
    }
    
    /**爆机数据包5016*/
    public class CMD_BAOJIDATA {
    	public CMD_BAOJIDATA(){
    		System.out.println("##爆机:");
    		Gdx.app.postRunnable(new Runnable() {
				public void run() {
					if(StageManager.isHadStage(StageManager.getStage(BaojiStage.class))){
		    			final AbstractStage stage = StageManager.getStage(BaojiStage.class);
		    			StageManager.stageSwitchOver(stage,Transition.BounceScaleIn,new Runnable() {
		    				public void run() {
		    					
		    				}
		    			});
		    		}
				}
			});
    	}
    };
    
    /**洗牌时*/
    public class CMD_RoundTimeData{
    	public CMD_RoundTimeData(byte[] message){
    		System.out.println("##洗牌时:"+Arrays.toString(message));
    		System.arraycopy(message, 8, temp, 0, 4);
    		GameProperty.RoundTime = CreateUtils.bytesToInt(temp);
    	}
    }
    
   
    public class CMD_CardIndex{
    	byte cardIndex;
    	public CMD_CardIndex(byte[] message){
    		System.out.println("##洗牌时:"+Arrays.toString(message));
    		cardIndex = message[8];
    	}
    }
    
    public class CMD_CUTINDEX{
    	public CMD_CUTINDEX(byte[] message){
    		System.out.println("##切牌:"+Arrays.toString(message));
    		GameProperty.cutIndex = message[8];
    	}
    }
    
    /**所有玩家的押分*/
    public class CMD_PlayerAllBet{
    	int bet[] = {0,0,0};
    	public CMD_PlayerAllBet(byte[] message){
    		System.out.println("##所有押分项:"+Arrays.toString(message));
    		System.arraycopy(message, 8, temp, 0, 4);
    		bet[1] = CreateUtils.bytesToInt(temp);
    		System.arraycopy(message, 12, temp, 0, 4);
    		bet[0] = CreateUtils.bytesToInt(temp);
    		System.arraycopy(message, 16, temp, 0, 4);
    		bet[2] = CreateUtils.bytesToInt(temp);
    		Message msg =new Message();
    		msg.what = 10;
    		msg.arg3 = bet;
    		mHandler.sendMessage(msg);
    	}
    }
    
    public class CMD_CUTGAME{
    	public CMD_CUTGAME(){
    		StageManager.getPlatFormInstance().exitGame();
    	}
    }
    
    /**所有玩家押分统计*/
    public class CMD_PLAYERTOLBETDATA{
    	public CMD_PLAYERTOLBETDATA(byte[] message){
    		int[] totalItemBet = new int[5];
    			System.out.println("##统计玩家押分数据:"+Arrays.toString(message));
    			System.arraycopy(message, 8, temp, 0, 4);
      		    totalItemBet[0] = CreateUtils.bytesToInt(temp);/**总分*/
      		    System.arraycopy(message, 12, temp, 0, 4);
      		    totalItemBet[1] = CreateUtils.bytesToInt(temp);/***/
    		    System.arraycopy(message, 16, temp, 0, 4);
    		    totalItemBet[2] = CreateUtils.bytesToInt(temp);
    		    System.arraycopy(message, 20, temp, 0, 4);
    		    totalItemBet[3] = CreateUtils.bytesToInt(temp);
    		    System.arraycopy(message, 24, temp, 0, 4);
    		    totalItemBet[4] = CreateUtils.bytesToInt(temp);
    		    Message msg = new Message();
    		    msg.what = 10;
    		    msg.arg3 = totalItemBet;
    		    mHandler.sendMessage(msg);
    	}
    }
    
}

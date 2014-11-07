package com.cn.lHClient.property;

public class GameProperty {
	
	public static boolean isConnect = true;
	
	/**全台限紅*/
	public static int allMaxBetScore = 0;
	/**最低投注*/
	public static int allMinBetScore = 0;
	/**和限注*/
	public static int heMaxBetScore = 0;
	/**押分步长*/
	public static int minStep,maxStep; 
	
	/**庄家的倒计时时间*/
	public static byte GameBankerTime;
	/**坐庄次数*/
	public static byte GameBankerCount;
	/**切牌次数*/
	public static byte GameCutCount;
	public static byte GameBankerPayOutRate;
	public static byte GamePlayerPayOutRate;
	public static int GameMinBankerScore;
	/**当前庄次数*/
	public static byte GameBankerCurrentCount = 0;
	/**当前庄的id*/
	public static byte GameBankerCurrentID=0;
	/**抢庄时间*/
	public static byte bankerTime = 0;
	
	/**台号*/
	public static int flagNum = 88;
	/**局数*/
	public static int boutNum = 0;
	/**场数*/
	public static int matchNum = 0;
	/**试分状态0:不试分，1：随即试分，2：2秒试分，8：3秒试分*/
	public static byte nowTry = 0;
	
	/**当前局庄的个数*/
	public static int zhuanNum = 0;
	/**当前局闲的个数*/
	public static int xianNum = 0;
	/**当前局和的个数*/
	public static int heNum = 0;

	/**五项押分的分数 依次是  和 庄 闲 */
	public static int betItemScore[] = {0,0,0};
	
	
	/**珠路*/
	public static int billItem[] = new int[67];
	/**大路 [场数] [坐标]*/
	public static float billPosition[][] = new float[67][2]; 
	/**当前场的值，true 庄 ，false 闲*/
	public static boolean value[] =  new boolean[67];
	/**大眼仔*/
	public static float bigEyesBillPosition[][] = new float[67][2];
	public static boolean bigEyesValue[] = new boolean[67];
	/**小路*/
	public static float smallBillPosition[][] = new float[67][2];
	public static boolean smallValue[] = new boolean[67];
	/**曱甴路*/
	public static float cockroachBillPosition[][] = new float[67][2]; 
	public static boolean cockriachValue[] = new boolean[67];
	
	/**出牌的结果*/
	public static int[] cardValue = new int[2];
	/**总分*/
	public static int totalScore;
	/**总得分*/
	public static int getScore;
	/**上下分*/
	public static int upScore,downScore;
	/**提留分*/
	public static int BoutWithDraw = 0;
	
	/**倒计时*/
	public static int countTime = 0;
	/**一局倒计时*/
	public static int RoundTime = 0;
	/**游戏当前状态*/
	public static State state = State.gsNone; 

	/**当前正在切牌的张数*/
	public static int cutIndex=0;
	
	/**庄闲的左右分数(决定出什么结果的)
	 * leftResult 表示 闲
	 * rightResult 表示 庄
	 * */
	public static int leftResult,rightResult,result;
	
	/**玩家最多总分*/
	public static int playerMaxScore;
	
	/**包头*/
	public static int CMD_GAMEDATA = 5001;
	public static int CMD_OPTIONDATA = 5002;
	public static int CMD_PLAYERDATA = 5003;
	public static int CMD_GAMETIMEDATA = 5004;
	public static int CMD_GAMECARDDATA = 5005;
	public static int CMD_PLAYERBETDATE = 5006;
	public static int CMD_UPDOWNSCOREDATA = 5007;
	public static int CMD_PLAYERCANCELBETDATA = 5008;
	public static int CMD_GAMELDDATA = 5009;
	public static int CMD_INOUTCOINDATA = 5010;
	public static int CMD_CLEARBOARD = 5011;
	public static int CMD_STOPFLASH = 5012;
	public static int CMD_BANKERTIME = 5013;
	public static int CMD_BANKERDATA = 5014;
	public static int CMD_CLEARDATA = 5015;
	public static int CMD_CLOSE = 5016;
	public static int CMD_LUCKYCARDS = 5017;
	public static int CMD_BAOJI = 5018;
	public static int CMD_ROUNDTIME = 5019;
	public static int CMD_COIN = 5020;
	public static int CMD_UPDOWNOK = 5021;
	public static int CMD_DISCERNERROR = 5022;
	public static int CMD_DOGERROR = 5023;
	public static int CMD_CARDINDEX = 5024;
	public static int CMD_CUTINDEX = 5025;
	public static int CMD_ALLBET = 5026;
	
	/**当前步长*/
	public static int betStep;
	
	/**游戏状态*/
	public static enum State{
		gsNone(0),
        gsInit(1),
        gsReady(2),
        gsSendStart(3),
        gsWait(4),
        gsWaitBet(5),
        gsNewGame(6),
        gsStart(7),
        gsNewBout(8),
        gsPlay(9),
        gsPause(10),
        gsResume(11),
        gsBoutEnd(12),
        gsPlayWin(13),
        gsCheckWin(14),
        gsAddWin(15),
        gsStop(16),
        gsClose(17),
        gsWaitNextGame(18),
        gsReStart(19);
        
		private int index;
        private State(int index) {
            this.index = index;
        }
        
        /**获取当前状态*/
        public static State getState(int index){
        	for(State state:State.values()){
        		if(state.index == index){
        			return state;
        		}
        	}
        	return State.gsNone;
        }
        
	}
}

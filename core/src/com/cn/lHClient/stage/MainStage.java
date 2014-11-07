package com.cn.lHClient.stage;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.atcors.ActionMenuActor;
import com.cn.lHClient.atcors.CreateLabel;
import com.cn.lHClient.atcors.PrizeAnimation;
import com.cn.lHClient.atcors.ShowBetScore;
import com.cn.lHClient.atcors.SwitchButton;
import com.cn.lHClient.atcors.TextButton;
import com.cn.lHClient.fonts.FontUtilsTTF;
import com.cn.lHClient.handler.Handler;
import com.cn.lHClient.handler.Message;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.manager.StageManager.Transition;
import com.cn.lHClient.net.ConnectServer;
import com.cn.lHClient.property.GameProperty;
import com.cn.lHClient.sqlite.CreateSqliteDB;
import com.cn.lHClient.utils.CreateUtils;
import com.cn.lHClient.utils.FileUtils;
import com.cn.lHClient.utils.WayBillUtil;

/**
 * @author Tong
 *
 */
public class MainStage extends AbstractStage {

	/**整个场景的画笔*/
	private Pixmap labelPixmap = new Pixmap((int)Gdx.graphics.getWidth(),(int)Gdx.graphics.getHeight(),Format.RGBA8888);
	private SpriteBatch batch = new SpriteBatch();;
	private Texture billTure;
	/**背景*/
	private Image mainBg;
	/**查询,续押,取消,抢庄*/
	private TextButton selectButton,keepBetButton,cancelButton,hogButton;
	/**步长10,100*/
	private SwitchButton tenStep,hundredStep;
	/**闲 和 庄三个押分按钮*/
	private TextButton huButton,heButton,longButton;
	/**押分按钮下面对应的赔率*/
	private Label huLabel,heLabel,longLabel;
	
	/**总分,押分,得分,抢庄时间*/
	private Label TotalScore,betScore,getScore,bogTime;
	
	/**局数,场数,台号*/
	private Label boutLabel,matchLabel,numLabel;
	/**抢庄*/
	private Image hog;
	private TextureRegionDrawable[] hogDrawable = new TextureRegionDrawable[2];
	
	/**全台限紅,最低投注,和限注*/
	private Label allMaxBetScoreLabel,allMinBetScoreLabel,heMaxLabel;
	/**庄,做庄次数,庄最低分,庄提留,玩家提留*/
	private Label lordLabel,lordTimeLabel,minLordLabel,lordDividendLabel,playDividendLabel;
	
	/**显示押分*/
	private ShowBetScore huShow,heShow,longShow;
	/**倒计时*/
	private Label countDownTime;
	
	/**网络*/
	private ConnectServer connectServer;
	/**显示卡牌*/
	private Image[] image = new Image[2];
	private TextureRegionDrawable[] card = new TextureRegionDrawable[53];
	private TextureRegionDrawable[] mm = new TextureRegionDrawable[53];
	/**显示中奖*/
	private PrizeAnimation prizeAnimation;
	/**上一场押分记录*/
	private  int[] betRecord = {0,0,0};

	/**数据库*/
	private CreateSqliteDB database;
	/**路单*/
	private TextureRegion[] billRegion = new TextureRegion[3];
	private TextureRegion[] jumpBillRegion = new TextureRegion[3];
	//private TextureRegion[] otherRegion = new TextureRegion[6];
	/**统计客户押分情况*/
	private Image totalBetMoneyImage;
	private Label totalLong,totalHu,totalHe;
	/**押分长按定时器*/
	private Timer[] timer = new Timer[3];
	/**改台号按键次数*/
	private int numLabelTouchDownTimes = 0;
	/**限紅提示*/
	private Label betMaxLongLabel,betMaxHuLabel,betMaxHeLabel;
	/**服务按钮*/
	private ActionMenuActor helpMenuButton;
	private TextButton upScoreMenu,downScoreMenu,callMenu,waterMenu,switchoverMenu;
	/**是否可以改IP*/
	private boolean isChangeIp = true;
	
	public MainStage(Viewport viewport) {
		super(viewport);
		mainBg = new Image(Assets.get("LhPic/mainbg.png", Texture.class));
		
		selectButton = new TextButton(Assets.get("LhPic/Button/7.png", Texture.class), Assets.get("LhPic/Button/8.png", Texture.class));
		keepBetButton = new TextButton(Assets.get("LhPic/Button/5.png", Texture.class), Assets.get("LhPic/Button/6.png", Texture.class));
		cancelButton = new TextButton(Assets.get("LhPic/Button/3.png", Texture.class), Assets.get("LhPic/Button/4.png", Texture.class));
		hogButton = new TextButton(Assets.get("LhPic/Button/1.png", Texture.class), Assets.get("LhPic/Button/2.png", Texture.class));
		
		tenStep = new SwitchButton(Integer.toString(GameProperty.minStep),Assets.get("LhPic/Coin/6.png", Texture.class),Assets.get("LhPic/Coin/5.png", Texture.class),Color.RED,25);
		hundredStep = new SwitchButton(Integer.toString(GameProperty.maxStep),Assets.get("LhPic/Coin/8.png", Texture.class),Assets.get("LhPic/Coin/7.png", Texture.class),Color.BLACK,25);
		
		huButton = new TextButton(Assets.get("LhPic/BetItem/4.png", Texture.class), Assets.get("LhPic/BetItem/6.png", Texture.class));
		heButton = new TextButton(Assets.get("LhPic/BetItem/1.png", Texture.class), Assets.get("LhPic/BetItem/3.png", Texture.class));
		longButton = new TextButton(Assets.get("LhPic/BetItem/7.png", Texture.class), Assets.get("LhPic/BetItem/9.png", Texture.class));
		
		huLabel =  CreateLabel.getLabel("1x2",15, Align.center, Color.BLUE);
		heLabel = CreateLabel.getLabel("1x9",15, Align.center, Color.GREEN);
		longLabel = CreateLabel.getLabel("1x1.95",15, Align.center, Color.RED);
		
		
		bogTime= CreateLabel.getLabel("0", 20, Align.center, Color.WHITE);
		boutLabel = CreateLabel.getLabel(Integer.toString(GameProperty.boutNum), 20, Align.center, Color.GREEN);
		matchLabel = CreateLabel.getLabel(Integer.toString(GameProperty.matchNum), 20, Align.center, Color.GREEN);
		
		numLabel = CreateLabel.getLabel(Integer.toString(StageManager.getPlatFormInstance().getIp()), 20, Align.center, Color.RED);
		
		hogDrawable[0] = CreateUtils.getTextureRegionDrawable("LhPic/hog.png");
		hogDrawable[1] = CreateUtils.getTextureRegionDrawable("LhPic/hog2.png");
		hog = new Image(hogDrawable[0]);
		
		TotalScore = CreateLabel.getLabel("0",36 ,Align.center,Color.YELLOW);
		betScore = CreateLabel.getLabel("0", 36, Align.center, Color.WHITE);
		getScore = CreateLabel.getLabel("0", 36, Align.center,  Color.GREEN);
		
		allMaxBetScoreLabel = CreateLabel.getLabel(Integer.toString(GameProperty.allMaxBetScore), 15, Align.center, Color.GREEN);
		allMinBetScoreLabel = CreateLabel.getLabel(Integer.toString(GameProperty.allMinBetScore), 15, Align.center, Color.GREEN);
		heMaxLabel = CreateLabel.getLabel(Integer.toString(GameProperty.heMaxBetScore), 15,Align.center, Color.GREEN);
		
		lordLabel = CreateLabel.getLabel("电脑", 15, Align.center, Color.GREEN,true);
		lordTimeLabel = CreateLabel.getLabel("0", 15, Align.center, Color.GREEN);
		minLordLabel = CreateLabel.getLabel("0", 15, Align.center, Color.GREEN);
		lordDividendLabel = CreateLabel.getLabel("0", 15, Align.center, Color.GREEN);
		playDividendLabel = CreateLabel.getLabel("0", 15, Align.center, Color.GREEN);
		
		betMaxLongLabel = CreateLabel.getLabel("龙满注", 25, Align.center, Color.RED, true);
		betMaxLongLabel.setColor(betMaxLongLabel.getColor().r, betMaxLongLabel.getColor().g, betMaxLongLabel.getColor().b, 0.8f);
		betMaxLongLabel.setVisible(false);
		betMaxHuLabel = CreateLabel.getLabel("虎满注", 25, Align.center, Color.BLUE, true);
		betMaxHuLabel.setColor(betMaxHuLabel.getColor().r, betMaxHuLabel.getColor().g, betMaxHuLabel.getColor().b, 0.8f);
		betMaxHuLabel.setVisible(false);
		betMaxHeLabel = CreateLabel.getLabel("和满注", 25, Align.center, Color.GREEN, true);
		betMaxHeLabel.setColor(betMaxHeLabel.getColor().r, betMaxHeLabel.getColor().g, betMaxHeLabel.getColor().b, 0.8f);
		betMaxHeLabel.setVisible(false);
		
		huShow = new ShowBetScore(0);
		heShow = new ShowBetScore(1);
		longShow = new ShowBetScore(2);
		
		countDownTime = CreateLabel.getLabel(Integer.toString(GameProperty.countTime), 72, Align.center, Color.YELLOW);
		
		for(int i=0;i<card.length;i++){
			card[i] = CreateUtils.getTextureRegionDrawable("LhPic/Card/"+i+".png");
			mm[i] = CreateUtils.getTextureRegionDrawable("LhPic/mm/"+i+".png");
		}
		
		for(int i=0;i<image.length;i++){
			image[i] = new Image(card[0]);
			image[i].setDrawable(null);
		}
		
		
		billRegion[0] = new TextureRegion(Assets.get("LhPic/he.png", Texture.class));
		billRegion[1] = new TextureRegion(Assets.get("LhPic/long.png", Texture.class));
		billRegion[2] = new TextureRegion(Assets.get("LhPic/hu.png", Texture.class));
		
		jumpBillRegion[0] = new TextureRegion(Assets.get("LhPic/green.png", Texture.class));
		jumpBillRegion[1] = new TextureRegion(Assets.get("LhPic/red.png", Texture.class));
		jumpBillRegion[2] = new TextureRegion(Assets.get("LhPic/blue.png", Texture.class));
		
		totalBetMoneyImage = new Image(Assets.get("LhPic/totalBetMoney.png", Texture.class));
		totalLong = CreateLabel.getLabel("0", 20, Align.center, Color.YELLOW);
		totalHu = CreateLabel.getLabel("0", 20, Align.center, Color.YELLOW);
		totalHe = CreateLabel.getLabel("0", 20, Align.center, Color.YELLOW);
		
		billTure = new Texture(labelPixmap,false);
		
		prizeAnimation = new PrizeAnimation();
		
		database = new CreateSqliteDB();
		
		upScoreMenu = new TextButton("上分", 20, Assets.get("LhPic/menu.png", Texture.class), 0.6f);
		downScoreMenu = new TextButton("下分", 20, Assets.get("LhPic/menu.png", Texture.class), 0.6f);
		waterMenu = new TextButton("茶水", 20, Assets.get("LhPic/menu.png", Texture.class), 0.6f);
		callMenu = new TextButton("呼叫服务员", 20, Assets.get("LhPic/menu.png", Texture.class), 0.6f);
		switchoverMenu = new TextButton("切换游戏", 20, Assets.get("LhPic/menu.png", Texture.class), 0.6f);
		
		helpMenuButton = new ActionMenuActor(new Image(Assets.get("LhPic/menuClose.png", Texture.class)));
		upScoreMenu.setPosition(100, 100);
		helpMenuButton.addActor(upScoreMenu);
		helpMenuButton.addActor(downScoreMenu);
		helpMenuButton.addActor(waterMenu);
		helpMenuButton.addActor(switchoverMenu);
		helpMenuButton.addActor(callMenu);
	
		/**与服务器联接*/
		connectServer = new ConnectServer(mHandler);
		connectServer.start();
		
		/**设置坐标*/
		setPosition();
		/**添加到舞台*/
		addActor();
		/**事件监听*/
		addListen();
		/**游戏设置的初始化*/
		gameSet();
	}

	/**初始化界面设置*/
	private void gameSet() {
		tenStep.setIsTouchDown(true);
	}
	
	/**
	*模拟Android的Handler线程间通信
	 * */
	public Handler mHandler = new Handler(){
	  public void handleMessage(Message msg) {
		switch (msg.what) {
		case 0:
			if(msg.arg2 >0){
				GameProperty.upScore += msg.arg2;
			}else{
				GameProperty.downScore +=Math.abs(msg.arg2);
			}
			GameProperty.totalScore = msg.arg1;
			TotalScore.setText(Integer.toString(GameProperty.totalScore));
			break;
		case 1:
			/**显示牌*/
			image[0].setDrawable(card[msg.arg3[0]]);
			image[1].setDrawable(card[msg.arg3[1]]);
			/**记录出牌结果*/
			GameProperty.cardValue[0] = msg.arg3[0];
			GameProperty.cardValue[1] = msg.arg3[1];
			GameProperty.result =  msg.arg3[2]-1;
			
			/**庄闲和*/
			prizeAnimation.getAnimation(msg.arg3[2]-1);
			/**记录本场押分*/
			betRecord[0] = GameProperty.betItemScore[0]; 
			betRecord[1] = GameProperty.betItemScore[1]; 
			betRecord[2] = GameProperty.betItemScore[2]; 
			
			/**更新大路路单*/
			WayBillUtil.WayBillToBigWayBill();
			//WayBillUtil.bigWayBillToOtherWayBill(1);
			//WayBillUtil.bigWayBillToOtherWayBill(2);
			//WayBillUtil.bigWayBillToOtherWayBill(3);
			
			betMaxLongLabel.setVisible(false);
			betMaxHuLabel.setVisible(false);
			betMaxHeLabel.setVisible(false);
			break;
		case 2:
			/**倒计时*/
			countDownTime.setText(Integer.toString(GameProperty.countTime));
			
			if(msg.arg6[0]){
				betMaxHeLabel.setVisible(false);
			}else{
				betMaxHeLabel.setVisible(true);
			}
			if(msg.arg6[1]){
				betMaxLongLabel.setVisible(false);
			}else{
				betMaxLongLabel.setVisible(true);
			}
			if(msg.arg6[2]){
				betMaxHuLabel.setVisible(false);
			}else{
				betMaxHuLabel.setVisible(true);
			}
			break;
		case 3:
			/**台号,局数,场数*/
			numLabel.setText(Integer.toString(GameProperty.flagNum+1));
			boutLabel.setText(Integer.toString(GameProperty.boutNum));
			matchLabel.setText(Integer.toString(GameProperty.matchNum));
			
			/**游戏状态为none*/
			if(msg.arg1 == 0){
				image[0].setDrawable(card[0]);
				image[1].setDrawable(card[0]);
			}
			
			/**游戏状态为gsStart,一场的开始*/
			if(msg.arg1 == 7){
				bogTime.setText("");
				getScore.setText("");
				betScore.setText("");
				hog.setDrawable(hogDrawable[0]);
				
			}
			
			/**游戏状态为gsBountEnd,一局结束,进入洗牌时间*/
			if(msg.arg1 == 12){
				if(GameProperty.state == GameProperty.State.gsBoutEnd && GameProperty.isConnect  && !StageManager.isHadStage(StageManager.getStage(WaitStage.class))){
					final AbstractStage stage = StageManager.getStage(WaitStage.class);
					StageManager.stageSwitchOver(stage,Transition.None,new Runnable() {
						public void run() {
							
						}
					});
				}
			}
			
			/**新一局的开始清空一些数据*/
			if(msg.arg1 == 6){
				for(int i=0;i<67;i++){
					GameProperty.billItem[i] = 0;
					GameProperty.billPosition[i][0] = 0;
					GameProperty.billPosition[i][1] = 0;
				}
			}

			if(GameProperty.state == GameProperty.State.gsNone){
				StringBuffer sb = new StringBuffer();
				sb.append("'").append(GameProperty.boutNum).append("-").append(GameProperty.matchNum-1).append("'").append(",");
				sb.append("'").append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date())).append("'").append(",");
				sb.append(GameProperty.result).append(",");
				sb.append(GameProperty.cardValue[0]).append(",");
				sb.append(GameProperty.cardValue[1]).append(",");
				sb.append(GameProperty.flagNum+1).append(",");
				sb.append(GameProperty.GameBankerCurrentID).append(",");
				sb.append(GameProperty.totalScore).append(",");
				sb.append(GameProperty.getScore).append(",");
				sb.append(betRecord[0]).append(",");
				sb.append(betRecord[1]).append(",");
				sb.append(betRecord[2]).append(",");
				sb.append(GameProperty.BoutWithDraw).append(",");
				sb.append(GameProperty.upScore).append(",");
				sb.append(GameProperty.downScore);
				if(Gdx.app.getType() == ApplicationType.iOS){
					int RecordNum = FileUtils.shareFile().getPropertyInteger("RecordNum");
					FileUtils.shareFile().setProperty("RecordNum",RecordNum+1);
					if(RecordNum >= 10000){
						FileUtils.shareFile().setProperty("RecordNum",0);
					}
					System.out.println("Record"+RecordNum+sb.toString());
					FileUtils.shareFile().setProperty("Record"+RecordNum,sb.toString());
				}else{
					System.out.println("select sql:"+sb.toString());
					database.insert(sb.toString());
				}
				
				
				/**清楚临时记录*/
				for(int i=0;i<2;i++){
					GameProperty.cardValue[i] = 0;
				}
				GameProperty.result = 0;
				GameProperty.upScore = 0;
				GameProperty.downScore = 0;
			}
			break;
		case 4:
			/**设置*/
			tenStep.setText(Integer.toString(GameProperty.minStep));
			hundredStep.setText(Integer.toString(GameProperty.maxStep));
			GameProperty.betStep = GameProperty.minStep;
			
			allMaxBetScoreLabel.setText(Integer.toString(GameProperty.allMaxBetScore));
			allMinBetScoreLabel.setText(Integer.toString(GameProperty.allMinBetScore));
			heMaxLabel.setText(Integer.toString(GameProperty.heMaxBetScore));
			
			lordTimeLabel.setText(GameProperty.GameBankerCurrentCount+"/"+GameProperty.GameBankerCount);
			minLordLabel.setText(Integer.toString(GameProperty.GameMinBankerScore*5));
			lordDividendLabel.setText(GameProperty.GameBankerPayOutRate+"/%");
			playDividendLabel.setText(GameProperty.GamePlayerPayOutRate+"/%");
			
			/**显示所有的押分*/
			addActor(totalBetMoneyImage);
			addActor(totalLong);
			addActor(totalHu);
			addActor(totalHe);
			
			/**停止监听改台号*/
			isChangeIp = false;
			numLabel.clearListeners();
			break;
		case 5:
			/**玩家数据押分时触发*/
			GameProperty.betItemScore[0] = msg.arg3[0];
			GameProperty.betItemScore[1] = msg.arg3[1];
			GameProperty.betItemScore[2] = msg.arg3[2];
			
			/**统计当前场的总分,得分*/
			GameProperty.totalScore = msg.arg1;
			GameProperty.getScore =msg.arg2;
			
			betScore.setText(Integer.toString(msg.arg3[0]+msg.arg3[1]+msg.arg3[2]));
			getScore.setText(Integer.toString(msg.arg2));
			
			TotalScore.setText(Integer.toString(msg.arg1));
			break;
		case 6:
			WayBillUtil.WayBillToBigWayBill();
			WayBillUtil.bigWayBillToOtherWayBill(1);
			//WayBillUtil.bigWayBillToOtherWayBill(2);
			//WayBillUtil.bigWayBillToOtherWayBill(3);
			break;
		case 7:
			/**庄的相关信息*/
			if(GameProperty.GameBankerCurrentID == 0){
				lordLabel.setText("电脑");
			}else{
				lordLabel.setText(Integer.toString(GameProperty.GameBankerCurrentID));
				lordTimeLabel.setText(GameProperty.GameBankerCurrentCount+"/"+GameProperty.GameBankerCount);
			}
			break;
		case 8:
			GameProperty.RoundTime = msg.arg1;
			break;
		case 9:
			/**一局结束*/
			/*StageManager.removeStage(StageManager.getStage(WaitStage.class));
			cleanBill();
			WayBillUtil.g_x = 0;
			WayBillUtil.g_y = 0;
			WayBillUtil.BigWayBill.clear();
			
			bigWayLongImage.setDrawable(null);
			bigWayHeImage.setDrawable(null);
			bigWayHuImage.setDrawable(null);
			wayLongImage.setDrawable(null);
			wayHeImage.setDrawable(null);
			wayHuImage.setDrawable(null);*/
			break;
		case 10:
			/**统计所有押分*/
			totalLong.setText(Integer.toString(msg.arg3[0]));
			totalHu.setText(Integer.toString(msg.arg3[2]));
			totalHe.setText(Integer.toString(msg.arg3[1]));
			break;
		case 12:
			prizeAnimation.removeAllAnimation();
			image[0].setDrawable(mm[new Random().nextInt(53)]);
			image[1].setDrawable(mm[new Random().nextInt(53)]);
		case 13:
			bogTime.setText(Integer.toString(GameProperty.bankerTime));
			break;
		default:
			break;
		}
	}
	};
	
	/**清空路单*/
	public void cleanBill(){
		System.out.println("清空路单~~");
		for(int i=0;i<67;i++){
			GameProperty.value[i] = false;
			GameProperty.bigEyesValue[i] = false;
			GameProperty.smallValue[i] = false;
			GameProperty.cockriachValue[i] = false;
			GameProperty.billItem[i] = 0;
			GameProperty.billPosition[i][0] = 0;
			GameProperty.billPosition[i][1] = 0;
			GameProperty.bigEyesBillPosition[i][0] = 0;
			GameProperty.bigEyesBillPosition[i][1] = 0;
			GameProperty.smallBillPosition[i][0] = 0;
			GameProperty.smallBillPosition[i][1] = 0;
			GameProperty.cockroachBillPosition[i][0] = 0;
			GameProperty.cockroachBillPosition[i][0] = 0;
		}
	}
	
	/**刷新路单*/
	private void updateBill() {
		int totalZero = 0;
		boolean isChange = false;
		for(int i=1;i<67;i++){
			//batch.draw(otherRegion[GameProperty.bigEyesValue[i]==true?2:5],GameProperty.bigEyesBillPosition[i][0],GameProperty.bigEyesBillPosition[i][1],0,0,otherRegion[2].getRegionWidth()*StageAttribute.ratioX, otherRegion[2].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
			//batch.draw(otherRegion[GameProperty.smallValue[i]==true?0:3],GameProperty.smallBillPosition[i][0],GameProperty.smallBillPosition[i][1],0,0,otherRegion[0].getRegionWidth()*StageAttribute.ratioX, otherRegion[0].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
			//batch.draw(otherRegion[GameProperty.cockriachValue[i]==true?1:4],GameProperty.cockroachBillPosition[i][0],GameProperty.cockroachBillPosition[i][1],0,0,otherRegion[1].getRegionWidth()*StageAttribute.ratioX, otherRegion[1].getRegionHeight()*StageAttribute.ratioY,1, 1, 0);
			
			if(GameProperty.billItem[i] == 1){
				batch.draw(billRegion[0],(602+(i-1)/6*16.5f)*StageAttribute.ratioX, (StageAttribute.height-26-(i-1)%6*16.5f)*StageAttribute.ratioY, 0, 0, billRegion[0].getRegionWidth()*StageAttribute.ratioX, billRegion[0].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
				
				totalZero++;
				if(totalZero == 1 && isChange){
					batch.draw(jumpBillRegion[0],GameProperty.billPosition[i][0],GameProperty.billPosition[i][1], 0, 0, jumpBillRegion[0].getRegionWidth()*StageAttribute.ratioX, jumpBillRegion[0].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
				}else if(totalZero > 1){
					batch.draw(jumpBillRegion[0],GameProperty.billPosition[i][0],GameProperty.billPosition[i][1], 0, 0, jumpBillRegion[0].getRegionWidth()*StageAttribute.ratioX, jumpBillRegion[0].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
					FontUtilsTTF.getFont(6).draw(batch, Integer.toString(totalZero),GameProperty.billPosition[i][0]+2*StageAttribute.ratioX,GameProperty.billPosition[i][1]+9*StageAttribute.ratioY);
					totalZero = 0;
				}
				isChange = false;
			}else if(GameProperty.billItem[i] == 2){
				batch.draw(billRegion[1],(602+(i-1)/6*16.5f)*StageAttribute.ratioX, (StageAttribute.height-26-(i-1)%6*16.5f)*StageAttribute.ratioY, 0, 0, billRegion[1].getRegionWidth()*StageAttribute.ratioX, billRegion[1].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
				batch.draw(jumpBillRegion[1],GameProperty.billPosition[i][0],GameProperty.billPosition[i][1], 0, 0, jumpBillRegion[1].getRegionWidth()*StageAttribute.ratioX, jumpBillRegion[1].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
				isChange = true;
				totalZero = 0;
			}else if(GameProperty.billItem[i] == 3){
				batch.draw(billRegion[2],(602+(i-1)/6*16.5f)*StageAttribute.ratioX, (StageAttribute.height-26-(i-1)%6*16.5f)*StageAttribute.ratioY, 0, 0, billRegion[2].getRegionWidth()*StageAttribute.ratioX, billRegion[2].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
				batch.draw(jumpBillRegion[2],GameProperty.billPosition[i][0],GameProperty.billPosition[i][1], 0, 0, jumpBillRegion[2].getRegionWidth()*StageAttribute.ratioX, jumpBillRegion[2].getRegionHeight()*StageAttribute.ratioY, 1, 1, 0);
				isChange = true;
				totalZero = 0;
			}
		}
	}
	
	private void setPosition() {
		mainBg.setPosition(0, 0);
		selectButton.setPosition(490, StageAttribute.height-550);
		keepBetButton.setPosition(380, StageAttribute.height-560);
		cancelButton.setPosition(267, StageAttribute.height-550);
		hogButton.setPosition(732, StageAttribute.height-555);
		
		tenStep.setPosition(100, StageAttribute.height-505);
		hundredStep.setPosition(640, StageAttribute.height-505);
		
		huButton.setPosition(90,StageAttribute.height-440);
		heButton.setPosition(330,StageAttribute.height-480);
		longButton.setPosition(580,StageAttribute.height-430);
		
		huLabel.setPosition(169,StageAttribute.height-430);
		heLabel.setPosition(400,StageAttribute.height-469);
		longLabel.setPosition(600,StageAttribute.height-420);
		
		TotalScore.setPosition(205, StageAttribute.height-605);
		betScore.setPosition(405, StageAttribute.height-605);
		getScore.setPosition(604, StageAttribute.height-605);

		bogTime.setPosition(225, StageAttribute.height-29);
		boutLabel.setPosition(293, StageAttribute.height-29);
		matchLabel.setPosition(368, StageAttribute.height-29);
		numLabel.setPosition(69, StageAttribute.height-29);
		hog.setPosition(117, StageAttribute.height-25);
		
		allMaxBetScoreLabel.setPosition(131, StageAttribute.height - 170);
		allMinBetScoreLabel.setPosition(131, StageAttribute.height - 185);
		heMaxLabel.setPosition(131, StageAttribute.height - 205);
		
		lordLabel.setPosition(708, StageAttribute.height - 150);
		lordTimeLabel.setPosition(718, StageAttribute.height - 170+2);
		minLordLabel.setPosition(718, StageAttribute.height - 190+4);
		lordDividendLabel.setPosition(718, StageAttribute.height - 210+6);
		playDividendLabel.setPosition(718, StageAttribute.height - 230+8);
		
		huShow.setPosition(220, StageAttribute.height - 417);
		heShow.setPosition(726, StageAttribute.height - 348);
		longShow.setPosition(472, StageAttribute.height - 462);
		
		countDownTime.setPosition(385, StageAttribute.height-235);
		
		image[0].setPosition(209, StageAttribute.height - 270);
		image[1].setPosition(505, StageAttribute.height - 270);

		betMaxLongLabel.setPosition(536, StageAttribute.height- 333);
		betMaxHuLabel.setPosition(219, StageAttribute.height-337 );
		betMaxHeLabel.setPosition(372, StageAttribute.height-335 );
		
		totalBetMoneyImage.setPosition(StageAttribute.widgth-totalBetMoneyImage.getWidth(), StageAttribute.height - 460);
		totalLong.setPosition(751, StageAttribute.height - 498+20*4-7);
		totalHu.setPosition(751, StageAttribute.height - 498+20*3-7);
		totalHe.setPosition(751, StageAttribute.height - 498+20*2-7);
		
		helpMenuButton.setPosition(-helpMenuButton.getWidth()/3*2, StageAttribute.height-488);
	}

	private void addActor() {
		addActor(mainBg);
		addActor(selectButton);
		addActor(keepBetButton);
		addActor(cancelButton);
		addActor(hogButton);
		
		addActor(tenStep);
		addActor(hundredStep);
		
		addActor(huButton);
		addActor(heButton);
		addActor(longButton);
		
		addActor(huLabel);
		addActor(heLabel);
		addActor(longLabel);
		
		addActor(TotalScore);
		addActor(betScore);
		addActor(getScore);
		
		addActor(bogTime);
		addActor(boutLabel);
		addActor(matchLabel);
		addActor(numLabel);
		addActor(hog);
		
		addActor(allMaxBetScoreLabel);
		addActor(allMinBetScoreLabel);
		addActor(heMaxLabel);
		
		addActor(lordLabel);
		addActor(lordTimeLabel);
		addActor(minLordLabel);
		addActor(lordDividendLabel);
		addActor(playDividendLabel);
		
		addActor(longShow);
		addActor(huShow);
		addActor(heShow);
		

		
		addActor(countDownTime);
		
		addActor(image[0]);
		addActor(image[1]);

		
		addActor(prizeAnimation);
		
		addActor(helpMenuButton);
		
		addActor(betMaxHeLabel);
		addActor(betMaxHuLabel);
		addActor(betMaxLongLabel);
		
	}
	
	/**服务包*/
	class ServiceData{
		byte[] serviceCmd = new byte[4];
		byte[] length = new byte[4];
		byte[] playerID = new byte[4];
		byte[] serviceItem = new byte[4];
	}
	
	/**押分包*/
	class PlayerBetData{
		byte playerID = 0;
		byte betItem = 0;
		byte[] betMoney = new byte[4];
	}
	
	/**取消押分*/
	class PlayerCancelData{
		byte[] loginCmd = new byte[4];
		byte[] length = new byte[4];
		byte playerID = 0;
	}
	
	/**抢庄包*/
	class BankerData{
		byte[] loginCmd = new byte[4];
		byte[] length = new byte[4];
		byte playerID = 0;
		byte bankID = 0;
		byte BankerTimes = 0;
	}
	
	private void addListen() {
		
		tenStep.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				tenStep.setIsTouchDown(true);
				hundredStep.setIsTouchDown(false);
				GameProperty.betStep = GameProperty.minStep;
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		hundredStep.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				tenStep.setIsTouchDown(false);
				hundredStep.setIsTouchDown(true);
				GameProperty.betStep = GameProperty.maxStep;
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		hogButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				ByteArray msg = new ByteArray();
				BankerData bankerData = new BankerData();
				bankerData.bankID=0;
				bankerData.BankerTimes = 0;
				bankerData.playerID = (byte) GameProperty.flagNum;
				
				msg.addAll(CreateUtils.intTobyte(GameProperty.CMD_BANKERDATA));
				msg.addAll(CreateUtils.intTobyte(3));
				msg.addAll(bankerData.playerID);
				msg.addAll(bankerData.bankID);
				msg.addAll(bankerData.BankerTimes);
				try {
					connectServer.sendMsg(msg.toArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
				msg.clear();
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		selectButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				final AbstractStage stage = StageManager.getStage(SelectStage.class);
				StageManager.stageSwitchOver(stage,Transition.FadeIn,new Runnable() {
					public void run() {
						//StageManager.pushStage(stage);
					}
				});
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		keepBetButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				for(int i=0;i<betRecord.length;i++){
					sendBetScore(i,betRecord[i]);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		cancelButton.addListener(new InputListener(){
			ByteArray msg = new ByteArray();
			PlayerCancelData cancelData = new PlayerCancelData();
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				cancelData.loginCmd = CreateUtils.intTobyte(GameProperty.CMD_PLAYERCANCELBETDATA);
				cancelData.length = CreateUtils.intTobyte(1);
				cancelData.playerID = (byte) GameProperty.flagNum;
				msg.addAll(cancelData.loginCmd);
				msg.addAll(cancelData.length);
				msg.addAll(cancelData.playerID);
				try {
					connectServer.sendMsg(msg.toArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
				msg.clear();
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		huButton.addListener(new InputListener(){
			
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if(timer[0] == null){
					timer[0] = new Timer();
					timer[0].scheduleTask(new Task() {
						public void run() {
							sendBetScore(2);
						}
					},0.6f,0.1f);
				}
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(timer[0] != null){
					timer[0].stop();
					timer[0] = null;
				}
				sendBetScore(2);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		
		heButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if(timer[2] == null){
					timer[2] = new Timer();
					timer[2].scheduleTask(new Task() {
						public void run() {
							sendBetScore(0);
						}
					},0.6f,0.1f);
				}
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(timer[2] != null){
					timer[2].stop();
					timer[2] = null;
				}
				sendBetScore(0);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		
		longButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if(timer[1] == null){
					timer[1] = new Timer();
					timer[1].scheduleTask(new Task() {
						public void run() {
							sendBetScore(1);
						}
					},0.6f,0.1f);
				}
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(timer[1] != null){
					timer[1].stop();
					timer[1] = null;
				}
				sendBetScore(1);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		numLabel.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(Gdx.app.getType() == ApplicationType.Android)
					numLabelTouchDownTimes++;
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(numLabelTouchDownTimes>8){
					final AbstractStage stage = StageManager.getStage(ChangeIpStage.class);
					StageManager.stageSwitchOver(stage,Transition.FadeIn,new Runnable() {
						public void run() {
							
						}
					});
					numLabelTouchDownTimes = 0;
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		upScoreMenu.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				sendServiceMsg(0);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		downScoreMenu.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				sendServiceMsg(1);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		waterMenu.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				sendServiceMsg(2);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		callMenu.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				sendServiceMsg(3);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		switchoverMenu.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				sendServiceMsg(4);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	
	/**
	 * 呼叫
	 * */
	protected void sendServiceMsg(int serviceItem){
		ByteArray msg = new ByteArray();
		ServiceData serviceData = new ServiceData();
		serviceData.serviceCmd = CreateUtils.intTobyte(50020);
		serviceData.playerID = CreateUtils.intTobyte(GameProperty.flagNum);
		serviceData.serviceItem = CreateUtils.intTobyte(serviceItem);
		
		msg.addAll(serviceData.serviceCmd);
		msg.addAll(CreateUtils.intTobyte(8));
		msg.addAll(serviceData.playerID);
		msg.addAll(serviceData.serviceItem);
		try {
			connectServer.sendMsg(msg.toArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		msg.clear();
	}
	
	/**
	 * 发送押分,
	 * @param Item 那一项押分
	 * */
	protected void sendBetScore(int Item) {
		if(GameProperty.totalScore == 0 || GameProperty.GameBankerCurrentID == GameProperty.flagNum+1 || GameProperty.countTime <= 0){
			
		}else{
			ByteArray msg = new ByteArray();
			PlayerBetData betData = new PlayerBetData();
			betData.betItem = (byte) Item;
			if(GameProperty.betItemScore[Item] == 0 && GameProperty.betStep < GameProperty.allMinBetScore){
				betData.betMoney = CreateUtils.intTobyte(GameProperty.allMinBetScore);
			}else{
				betData.betMoney = CreateUtils.intTobyte(GameProperty.betStep);
			}
			betData.playerID = (byte) GameProperty.flagNum;
			
			msg.addAll(CreateUtils.intTobyte(GameProperty.CMD_PLAYERBETDATE));
			msg.addAll(CreateUtils.intTobyte(6));
			msg.addAll(betData.playerID);
			msg.addAll(betData.betItem);
			msg.addAll(betData.betMoney);
			try {
				connectServer.sendMsg(msg.toArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			msg.clear();
		}
	}
	
	/**重载  押分方法*/
	protected void sendBetScore(int Item,int score) {
		ByteArray msg = new ByteArray();
		PlayerBetData betData = new PlayerBetData();
		betData.betItem = (byte) Item;
		betData.betMoney = CreateUtils.intTobyte(score);
		betData.playerID = (byte) GameProperty.flagNum;
		
		msg.addAll(CreateUtils.intTobyte(GameProperty.CMD_PLAYERBETDATE));
		msg.addAll(CreateUtils.intTobyte(6));
		msg.addAll(betData.playerID);
		msg.addAll(betData.betItem);
		msg.addAll(betData.betMoney);
		try {
			connectServer.sendMsg(msg.toArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		msg.clear();
	}
	
	public ConnectServer getConnectServer(){
		return connectServer;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		/**是否掉线*/
		if(!GameProperty.isConnect && !StageManager.isHadStage(StageManager.getStage(DisconnectStage.class))){
			final AbstractStage stage = StageManager.getStage(DisconnectStage.class);
			StageManager.stageSwitchOver(stage,Transition.FadeIn,new Runnable() {
				public void run() {
					//cleanBill();
				}
			});
		}
	}
	
	@Override
	public void resume() {
		Assets.update();
		super.resume();
	}

	@Override
	public void draw() {
		super.draw();
		if(isChangeIp){
			numLabel.setText(Integer.toString(GameProperty.flagNum));
		}
		
		batch.begin();
		if(billTure != null){
			batch.draw(billTure, 0,0);
		}
		updateBill();
		batch.end();
	}

	public void dispose(){
		
	}
	
}

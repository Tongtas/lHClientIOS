package com.cn.bacGame.stage;


import java.io.IOException;
import java.text.SimpleDateFormat;

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
import com.cn.bacGame.assets.Assets;
import com.cn.bacGame.atcors.CreateLabel;
import com.cn.bacGame.atcors.PrizeAnimation;
import com.cn.bacGame.atcors.ShowBetScore;
import com.cn.bacGame.atcors.SwitchButton;
import com.cn.bacGame.atcors.TextButton;
import com.cn.bacGame.fonts.FontUtilsTTF;
import com.cn.bacGame.handler.Handler;
import com.cn.bacGame.handler.Message;
import com.cn.bacGame.manager.StageManager;
import com.cn.bacGame.manager.StageManager.Transition;
import com.cn.bacGame.net.ConnectServer;
import com.cn.bacGame.property.GameProperty;
import com.cn.bacGame.sqlite.CreateSqliteDB;
import com.cn.bacGame.utils.CreateUtils;

public class MainStage extends AbstractStage {
	
	/**整个场景的画笔*/
	private Pixmap labelPixmap;
	private SpriteBatch batch;
	private Texture texture;
	/**背景*/
	private Image mainBg;
	/**查询,续押,取消,说明*/
	private TextButton selectButton,keepBetButton,cancelButton,aboutButton;
	/**步长,1,5,10,100*/
	private SwitchButton oneStep,fiveStep,tenStep,hundredStep;
	/**闲  闲对 和 庄对 庄*/
	private TextButton xianButton,doubleXianButton,heButton,doubleZhuanButton,zhuanButton;
	/**对应的赔率*/
	private Label xianLabel,doubleXianLabel,heLabel,doubleZhuanLabel,zhuanLabel;
	/**总分,押分,得分*/
	private Label TotalScore,betScore,getScore;
	/**局数,场数,台号*/
	private Label boutLabel,matchLabel,numLabel;
	/**全台限紅,最低投注,和限注,对子限注*/
	private Label allMaxBetScoreLabel,allMinBetScoreLabel,heMaxLabel,doubleMaxLabel;
	/**显示押分*/
	private ShowBetScore xianShow,doubleXianShow,heShow,zhuanShow,doubleZhuanShow;
	/**坐标*/
	private float position[][] = {{613,StageAttribute.height - 55},{613,StageAttribute.height -80},{613,StageAttribute.height -106},{650,StageAttribute.height -55},{650,StageAttribute.height -80},{650,StageAttribute.height -106}};
	/**右上角当前场的比例等消息*/
	@SuppressWarnings("unused")
	private Image redZhuanImage,redXianImage,redHeImage,redDoubleZhuanImage,redDoubleXianImage,blueZhuanImage,blueXianImage,blueHeImage,blueDoubleZhuanImage,blueDoubleXianImage;
	private Label matchNumLabel,zhuanNumLabel,xianNumLabel,heNumLabel,doubleXianNumLabel,doubleZhuanNumLabel;
	/**倒计时*/
	private Label countDownTime;
	/**圆圈,线,实心圆等小图标*/
	private TextureRegionDrawable[] showIco;
	/**网络*/
	private ConnectServer connectServer;
	/**显示卡牌*/
	private Image[] image = new Image[6];
	private TextureRegionDrawable[] card = new TextureRegionDrawable[53];
	/**显示中奖*/
	private PrizeAnimation prizeAnimation;
	/**上一场押分记录*/
	private  int[] betRecord = {0,0,0,0,0};
	/**显示结果分数*/
	private Label leftLabel , rightLabel;
	/**数据库*/
	private CreateSqliteDB database;
	/**路单*/
	private Image[] wayBill = new Image[66];
	private TextureRegionDrawable[] wayBillDrawable = new TextureRegionDrawable[3];
	/**跳单*/
	private Image[] jumpBill = new Image[66];
	private TextureRegionDrawable[] jumpBillDrawable = new TextureRegionDrawable[13];
	private TextureRegion redRegion = new TextureRegion(Assets.get("BacPic/Bigld/4.png", Texture.class));
	private TextureRegion blueRegion = new TextureRegion(Assets.get("BacPic/Bigld/5.png", Texture.class));
	private int heNum = 0,lastResult = 4,x=0,y=0,T=0,maxY=6;

	
	public MainStage(Viewport viewport) {
		super(viewport);
		mainBg = new Image(Assets.get("BacPic/mainbg.png", Texture.class));
		
		selectButton = new TextButton(Assets.get("BacPic/Button/15.png", Texture.class), Assets.get("BacPic/Button/14.png", Texture.class));
		keepBetButton = new TextButton(Assets.get("BacPic/Button/12.png", Texture.class), Assets.get("BacPic/Button/11.png", Texture.class));
		cancelButton = new TextButton(Assets.get("BacPic/Button/6.png", Texture.class), Assets.get("BacPic/Button/5.png", Texture.class));
		aboutButton = new TextButton(Assets.get("BacPic/Button/21.png", Texture.class), Assets.get("BacPic/Button/20.png", Texture.class));
		
		oneStep = new SwitchButton(Integer.toString(GameProperty.minStep),Assets.get("BacPic/Coin/2.png", Texture.class),Assets.get("BacPic/Coin/1.png", Texture.class),Color.BLUE,25);
		fiveStep = new SwitchButton(Integer.toString(GameProperty.secondStep),Assets.get("BacPic/Coin/4.png", Texture.class),Assets.get("BacPic/Coin/3.png", Texture.class),Color.GREEN,25);
		tenStep = new SwitchButton(Integer.toString(GameProperty.threeStep),Assets.get("BacPic/Coin/6.png", Texture.class),Assets.get("BacPic/Coin/5.png", Texture.class),Color.RED,25);
		hundredStep = new SwitchButton(Integer.toString(GameProperty.maxStep),Assets.get("BacPic/Coin/8.png", Texture.class),Assets.get("BacPic/Coin/7.png", Texture.class),Color.BLACK,25);
		
		xianButton = new TextButton(Assets.get("BacPic/BetItem/7.png", Texture.class), Assets.get("BacPic/BetItem/9.png", Texture.class));
		doubleXianButton = new TextButton(Assets.get("BacPic/BetItem/13.png", Texture.class), Assets.get("BacPic/BetItem/15.png", Texture.class));
		heButton = new TextButton(Assets.get("BacPic/BetItem/1.png", Texture.class), Assets.get("BacPic/BetItem/3.png", Texture.class));
		doubleZhuanButton = new TextButton(Assets.get("BacPic/BetItem/10.png", Texture.class), Assets.get("BacPic/BetItem/12.png", Texture.class));
		zhuanButton = new TextButton(Assets.get("BacPic/BetItem/4.png", Texture.class), Assets.get("BacPic/BetItem/6.png", Texture.class));
		
		xianLabel =  CreateLabel.getLabel("1:1",15, Align.center, Color.BLUE);
		doubleXianLabel = CreateLabel.getLabel("1:11",15, Align.center, Color.BLUE);
		heLabel = CreateLabel.getLabel("1:8",15, Align.center, Color.GREEN);
		doubleZhuanLabel = CreateLabel.getLabel("1:11",15, Align.center, Color.RED);
		zhuanLabel = CreateLabel.getLabel("1:0.95",15, Align.center, Color.RED);
		
		TotalScore = CreateLabel.getLabel("",20 ,Align.center, new Color(7, 101, 101, 1));
		betScore = CreateLabel.getLabel("", 20, Align.center, new Color(0, 128, 0,1));
		getScore = CreateLabel.getLabel("", 20, Align.center,  Color.YELLOW);
		
		boutLabel = CreateLabel.getLabel(Integer.toString(GameProperty.boutNum), 15, Align.center, Color.YELLOW);
		matchLabel = CreateLabel.getLabel(Integer.toString(GameProperty.matchNum), 15, Align.center, Color.YELLOW);
		numLabel = CreateLabel.getLabel(Integer.toString(StageManager.getPlatFormInstance().getIp()), 15, Align.center, Color.YELLOW);
		
		allMaxBetScoreLabel = CreateLabel.getLabel(Integer.toString(GameProperty.allMaxBetScore), 18, Align.center, Color.WHITE);
		allMinBetScoreLabel = CreateLabel.getLabel(Integer.toString(GameProperty.allMinBetScore), 18, Align.center, Color.WHITE);
		heMaxLabel = CreateLabel.getLabel(Integer.toString(GameProperty.heMaxBetScore), 18,Align.center, Color.WHITE);
		doubleMaxLabel = CreateLabel.getLabel(Integer.toString(GameProperty.doubleMaxBetScore), 18, Align.center, Color.WHITE);
		
		xianShow = new ShowBetScore(0);
		doubleXianShow = new ShowBetScore(1);
		heShow = new ShowBetScore(2);
		doubleZhuanShow = new ShowBetScore(3);
		zhuanShow = new ShowBetScore(4);
		
		showIco = new TextureRegionDrawable[6];
		showIco[0] = CreateUtils.getTextureRegionDrawable("BacPic/blueCircle.png");
		showIco[1] = CreateUtils.getTextureRegionDrawable("BacPic/solidBlueCircle.png");
		showIco[2] = CreateUtils.getTextureRegionDrawable("BacPic/blueLine.png");
		showIco[3] = CreateUtils.getTextureRegionDrawable("BacPic/redCircle.png");
		showIco[4] = CreateUtils.getTextureRegionDrawable("BacPic/solidRedCircle.png");
		showIco[5] = CreateUtils.getTextureRegionDrawable("BacPic/redLine.png");
		
		redZhuanImage = new Image(showIco[0]);
		redXianImage = new Image(showIco[1]);
		redHeImage = new Image(showIco[2]);
		blueZhuanImage = new Image(showIco[3]);
		blueXianImage = new Image(showIco[4]);
		blueHeImage = new Image(showIco[5]);
		
		matchNumLabel = CreateLabel.getLabel(Integer.toString(GameProperty.matchNum), 20, Align.center, Color.YELLOW);
		zhuanNumLabel = CreateLabel.getLabel(Integer.toString(GameProperty.zhuanNum), 20, Align.center, Color.YELLOW);
		xianNumLabel = CreateLabel.getLabel(Integer.toString(GameProperty.xianNum), 20, Align.center, Color.YELLOW);
		heNumLabel = CreateLabel.getLabel(Integer.toString(GameProperty.heNum), 20, Align.center, Color.YELLOW);
		doubleZhuanNumLabel = CreateLabel.getLabel(Integer.toString(GameProperty.doubleZhuanNum), 20, Align.center, Color.YELLOW);
		doubleXianNumLabel = CreateLabel.getLabel(Integer.toString(GameProperty.doubleXianNum), 20, Align.center, Color.YELLOW);
		
		countDownTime = CreateLabel.getLabel(Integer.toString(GameProperty.countTime), 36, Align.center, Color.YELLOW);
		
		leftLabel = CreateLabel.getLabel("", 36,Align.center, Color.BLUE);
		rightLabel = CreateLabel.getLabel("", 36, Align.center, Color.RED);
		
		for(int i=0;i<card.length;i++){
			card[i] = CreateUtils.getTextureRegionDrawable("BacPic/Card/"+i+".png");
		}
		
		wayBillDrawable[0] = CreateUtils.getTextureRegionDrawable("BacPic/he.png");
		wayBillDrawable[1] = CreateUtils.getTextureRegionDrawable("BacPic/zhuan.png");
		wayBillDrawable[2] = CreateUtils.getTextureRegionDrawable("BacPic/xian.png");
		
		for(int i=0;i<10;i++){
			jumpBillDrawable[i] = CreateUtils.getTextureRegionDrawable("BacPic/Num/00"+i+".png");
		}
		for(int i=10;i<15;i++){
			jumpBillDrawable[i] = CreateUtils.getTextureRegionDrawable("BacPic/Bigld/"+(i-9)+".png");
		}
		
		for(int i=0;i<image.length;i++){
			image[i] = new Image(card[0]);
			image[i].setDrawable(null);
		}
		
		for(int i=0;i<wayBill.length;i++){
			wayBill[i] = new Image(wayBillDrawable[0]);
			wayBill[i].setDrawable(null);
		}
		
		for(int i=0;i<jumpBill.length;i++){
			jumpBill[i] = new Image(jumpBillDrawable[0]);
			jumpBill[i].setDrawable(null);
			jumpBill[i].setScaleY(0.5f);
		}
		
		
		prizeAnimation = new PrizeAnimation();
		
		database = new CreateSqliteDB();
		
		/**与服务器联接*/
		connectServer = new ConnectServer(mHandler);
		connectServer.start();
		
		/**设置坐标*/
		setPosition();
		/**添加到舞台*/
		addActor();
		/**事件监听*/
		addListen();
		/**labelPixmap自定义画笔*/
		labelPixmap();
		/**游戏设置的初始化*/
		gameSet();
	}

	/**
	*模式Android的Handler线程间通信
	 * */
	public Handler mHandler = new Handler(){
	  public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			/**出的结果*/
			/**庄闲的大小*/
			if(msg.arg1%2 == 1){
				rightLabel.setText(Integer.toString((GameProperty.rightResult+((msg.arg2%13)>10?0:msg.arg2%13))%10));
				GameProperty.rightResult = ((msg.arg2%13)>10?0:msg.arg2%13);
			}else{
				leftLabel.setText(Integer.toString((GameProperty.leftResult+((msg.arg2%13)>10?0:msg.arg2%13))%10));
				GameProperty.leftResult = ((msg.arg2%13)>10?0:msg.arg2%13);
			}
			
			/**显示牌*/
			image[msg.arg1].setDrawable(card[msg.arg2]);
			/**记录出牌结果*/
			GameProperty.cardValue[msg.arg1] = msg.arg2;
			/**记录本场押分*/
			betRecord[0] = GameProperty.betItemScore[0]; 
			betRecord[1] = GameProperty.betItemScore[1]; 
			betRecord[2] = GameProperty.betItemScore[2]; 
			betRecord[3] = GameProperty.betItemScore[3]; 
			betRecord[4] = GameProperty.betItemScore[4]; 
			break;
		case 2:
			/**倒计时*/
			countDownTime.setText(Integer.toString(GameProperty.countTime));
			break;
		case 3:
			/**游戏数据 发送结果*/
			
			/**台号,局数,场数*/
			numLabel.setText(Integer.toString(GameProperty.flagNum));
			boutLabel.setText(Integer.toString(GameProperty.boutNum));
			matchLabel.setText(Integer.toString(GameProperty.matchNum));
			
			/**当前局的结果统计*/
			matchNumLabel.setText(Integer.toString(GameProperty.matchNum -1));
			heNumLabel.setText(Integer.toString(msg.arg3[0]));
			zhuanNumLabel.setText(Integer.toString(msg.arg3[1]));
			xianNumLabel.setText(Integer.toString(msg.arg3[2]));
			doubleZhuanNumLabel.setText(Integer.toString(msg.arg3[3]));
			doubleXianNumLabel.setText(Integer.toString(msg.arg3[4]));
			
			/**是否庄对*/
			if(!msg.arg4){
				prizeAnimation.getAnimation(3);
			}
			/**是否闲对*/
			if(!msg.arg5){
				prizeAnimation.getAnimation(4);
			}
			/**庄闲和*/
			prizeAnimation.getAnimation(msg.arg1 -1);
			
			/**添加路单*/
			if(msg.arg1 == 1){
				wayBill[GameProperty.matchNum].setDrawable(wayBillDrawable[0]);
				addJumpBill(msg.arg1,GameProperty.matchNum);
			}else if(msg.arg1 == 2){
				wayBill[GameProperty.matchNum].setDrawable(wayBillDrawable[2]);
				addJumpBill(msg.arg1,GameProperty.matchNum);
			}else if(msg.arg1 == 3){
				wayBill[GameProperty.matchNum].setDrawable(wayBillDrawable[1]);
				addJumpBill(msg.arg1,GameProperty.matchNum);
			}
				
			/**新一局,从新开始路单*/
			if(GameProperty.state == GameProperty.State.gsNewGame){
				for(int i=0;i<wayBill.length;i++){
					wayBill[i].setDrawable(null);
				}
			}
			
			if(GameProperty.state == GameProperty.State.gsNewBout){
				for(int i=0;i<image.length;i++){
					image[i].setDrawable(null);
				}
				prizeAnimation.removeAllAnimation();
				
				leftLabel.setText("");
				rightLabel.setText("");
				
				StringBuffer sb = new StringBuffer();
				sb.append("'").append(GameProperty.boutNum).append("-").append(GameProperty.matchNum).append("'").append(",");
				sb.append("'").append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date())).append("'").append(",");
				sb.append(GameProperty.leftResult == GameProperty.rightResult?0:GameProperty.leftResult > GameProperty.rightResult?1:2).append(",");
				sb.append(GameProperty.cardValue[0]).append(",");
				sb.append(GameProperty.cardValue[1]).append(",");
				sb.append(GameProperty.cardValue[2]).append(",");
				sb.append(GameProperty.cardValue[3]).append(",");
				sb.append(GameProperty.cardValue[4]).append(",");
				sb.append(GameProperty.cardValue[5]).append(",");
				sb.append(GameProperty.flagNum).append(",");
				sb.append(GameProperty.totalScore).append(",");
				sb.append(GameProperty.getScore).append(",");
				sb.append(betRecord[0]).append(",");
				sb.append(betRecord[1]).append(",");
				sb.append(betRecord[2]).append(",");
				sb.append(betRecord[3]).append(",");
				sb.append(betRecord[4]);
				System.out.println("select sql:"+sb.toString());
				database.insert(sb.toString());
				/**清楚临时记录*/
				
				for(int i=0;i<StageAttribute.betItem;i++){
					betRecord[i] = 0;
					GameProperty.cardValue[i] = 0;
				}
			}
			break;
		case 4:
			/**设置*/
			oneStep.setText(Integer.toString(GameProperty.minStep));
			fiveStep.setText(Integer.toString(GameProperty.secondStep));
			tenStep.setText(Integer.toString(GameProperty.threeStep));
			hundredStep.setText(Integer.toString(GameProperty.maxStep));
			
			GameProperty.betStep = GameProperty.threeStep;
			
			allMaxBetScoreLabel.setText(Integer.toString(GameProperty.allMaxBetScore));
			allMinBetScoreLabel.setText(Integer.toString(GameProperty.allMinBetScore));
			heMaxLabel.setText(Integer.toString(GameProperty.heMaxBetScore));
			doubleMaxLabel.setText(Integer.toString(GameProperty.doubleMaxBetScore));
			break;
		case 5:
			/**玩家数据押分时触发*/
			GameProperty.betItemScore[0] = msg.arg3[0];
			GameProperty.betItemScore[1] = msg.arg3[1];
			GameProperty.betItemScore[2] = msg.arg3[2];
			GameProperty.betItemScore[3] = msg.arg3[3];
			GameProperty.betItemScore[4] = msg.arg3[4];
			
			/**统计当前场的总分,得分*/
			if(msg.arg4){
				GameProperty.totalScore = msg.arg1;
				GameProperty.getScore =msg.arg2;
			}
			
			betScore.setText(Integer.toString(msg.arg3[0]+msg.arg3[1]+msg.arg3[2]+msg.arg3[3]+msg.arg3[4]));
			getScore.setText(Integer.toString(msg.arg2));
			
			if(msg.arg2 == 0){
				TotalScore.setText(Integer.toString(msg.arg1));
			}
			
			if(GameProperty.state == GameProperty.State.gsNewBout && !msg.arg4){
				TotalScore.setText(Integer.toString(msg.arg1));
			}
			break;
		case 6:
			/**第一次路单数据*/
			for(int i=0;i<66;i++){
				/**路单*/
				if(msg.arg3[i] != 0){
					wayBill[i].setDrawable(wayBillDrawable[msg.arg3[i]-1]);
				}else {
					wayBill[i].setDrawable(null);
					break;
				}
				addJumpBill(msg.arg3[i], i);
			}
			break;
		case 7:
			TotalScore.setText(Integer.toString(msg.arg2));
			break;
		default:
			break;
		}
	}
	};
	 /**
	  *跳单位置
	  * @param num 结果
	  * 0 未出
	  * 1 和
	  * 2 庄
	  * 3 闲
	  * 4初始状态
	  * @param match 场数
	  * */
	public void addJumpBill(int num,int match){
		/**记录和连续出现的次数,和上一次的结果,x,y,T是横出来多少个*/
		if(num == 0){
			lastResult =4;
			heNum = 0;
		}else if(num == 1){
			heNum++;
			if(heNum == 0){
				jumpBill[match].setDrawable(jumpBillDrawable[10]);
			}else{
				jumpBill[match].setDrawable(jumpBillDrawable[heNum]);
			}
		}else if(num == 2){
			if(lastResult == 2){
				if(y>(maxY-T)){
					x++;
					T++;
				}else{
					y++;
				}
			}else{
				x++;
				y=0;
				T=0;
			}
			lastResult = 2;
			heNum = 0;
			jumpBill[match].setDrawable(jumpBillDrawable[11]);
		}else if(num == 3){
			if(lastResult == 3){
				if(y>6-T){
					x++;
					T++;
				}else{
					y++;
				}
			}else{
				x++;
				y=0;
				T=0;
			}
			lastResult = 3;
			heNum = 0;
			jumpBill[match].setDrawable(jumpBillDrawable[12]);
		}
		if(match == 0){
			x=0;
			y=0;
		}
		jumpBill[match].setPosition(9+12*x, StageAttribute.height -18-12*y );
	}
	
	/**初始化界面设置*/
	private void gameSet() {
		tenStep.setIsTouchDown(true);
	}

	/**画笔*/
	private void labelPixmap() {
		batch = new SpriteBatch();
		labelPixmap = new Pixmap((int)Gdx.graphics.getWidth(),(int)Gdx.graphics.getHeight(),Format.RGBA8888);
		texture = new Texture(labelPixmap);
	}

	private void updateBill() {
		for(int i=0;i<66;i++){
			batch.draw(region, i, i, originX, originY, width, height, scaleX, scaleY, rotation);
		}
	}
	
	private void setPosition() {
		mainBg.setPosition(0, 0);
		selectButton.setPosition(186, StageAttribute.height-533);
		keepBetButton.setPosition(303, StageAttribute.height-570);
		cancelButton.setPosition(436, StageAttribute.height-568);
		aboutButton.setPosition(548, StageAttribute.height-535);
		
		oneStep.setPosition(11, StageAttribute.height-548);
		fiveStep.setPosition(91, StageAttribute.height-551);
		tenStep.setPosition(642, StageAttribute.height-549);
		hundredStep.setPosition(721, StageAttribute.height-551);
		
		xianButton.setPosition(68,StageAttribute.height-346);
		doubleXianButton.setPosition(175,StageAttribute.height-460);
		heButton.setPosition(340,StageAttribute.height-500);
		doubleZhuanButton.setPosition(495,StageAttribute.height-457);
		zhuanButton.setPosition(610,StageAttribute.height-343);
		
		xianLabel.setPosition(120,StageAttribute.height-358);
		doubleXianLabel.setPosition(223,StageAttribute.height-459);
		heLabel.setPosition(394,StageAttribute.height-504);
		doubleZhuanLabel.setPosition(553,StageAttribute.height-458);
		zhuanLabel.setPosition(655,StageAttribute.height-355);
		
		TotalScore.setPosition(318, StageAttribute.height-590);
		betScore.setPosition(515, StageAttribute.height-590);
		getScore.setPosition(700, StageAttribute.height-590);

		boutLabel.setPosition(690, StageAttribute.height-117);
		matchLabel.setPosition(748, StageAttribute.height-117);
		numLabel.setPosition(760, StageAttribute.height-150);
		
		allMaxBetScoreLabel.setPosition(402, StageAttribute.height - 335);
		allMinBetScoreLabel.setPosition(402, StageAttribute.height - 352);
		heMaxLabel.setPosition(402, StageAttribute.height - 369);
		doubleMaxLabel.setPosition(402, StageAttribute.height - 384);
		
		xianShow.setPosition(39, StageAttribute.height - 259);
		doubleXianShow.setPosition(130, StageAttribute.height - 425);
		heShow.setPosition(443, StageAttribute.height - 492);
		doubleZhuanShow.setPosition(602, StageAttribute.height - 425);
		zhuanShow.setPosition(700, StageAttribute.height - 255);
		
		redZhuanImage.setPosition(position[0][0],position[0][1]);
		redXianImage.setPosition(position[1][0],position[1][1]);
		redHeImage.setPosition(position[2][0],position[2][1]);
		blueZhuanImage.setPosition(position[3][0],position[3][1]);
		blueXianImage.setPosition(position[4][0],position[4][1]);
		blueHeImage.setPosition(position[5][0],position[5][1]);
		
		matchNumLabel.setPosition(582, StageAttribute.height - 25-8);
		zhuanNumLabel.setPosition( 582, StageAttribute.height - 50-8);
		xianNumLabel.setPosition(582, StageAttribute.height - 75-8);
		heNumLabel.setPosition(582, StageAttribute.height - 100-8);
		doubleZhuanNumLabel.setPosition(582, StageAttribute.height - 125-8);
		doubleXianNumLabel.setPosition(582, StageAttribute.height - 150-8);
		
		countDownTime.setPosition(385, StageAttribute.height-203);
		
		leftLabel.setPosition(135, StageAttribute.height - 210);
		rightLabel.setPosition(658, StageAttribute.height - 210);
		
		image[0].setPosition(172, StageAttribute.height - 264);
		image[2].setPosition(248, StageAttribute.height - 264);
		image[1].setPosition(482, StageAttribute.height - 264);
		image[3].setPosition(557, StageAttribute.height - 264);
		image[4].setPosition(200, StageAttribute.height - 268);
		image[4].setRotation(-90);
		image[5].setPosition(516, StageAttribute.height - 268);
		image[5].setRotation(-90);
		
		for(int i=0;i<wayBill.length;i++){
			wayBill[i].setPosition(335+i/6*12, StageAttribute.height-214-i%6*12);
		}
	}

	private void addActor() {
		addActor(mainBg);
		addActor(selectButton);
		addActor(keepBetButton);
		addActor(cancelButton);
		addActor(aboutButton);
		
		addActor(oneStep);
		addActor(fiveStep);
		addActor(tenStep);
		addActor(hundredStep);
		
		addActor(xianButton);
		addActor(doubleXianButton);
		addActor(heButton);
		addActor(doubleZhuanButton);
		addActor(zhuanButton);
		
		addActor(xianLabel);
		addActor(doubleXianLabel);
		addActor(heLabel);
		addActor(doubleZhuanLabel);
		addActor(zhuanLabel);
		
		addActor(TotalScore);
		addActor(betScore);
		addActor(getScore);
		
		addActor(boutLabel);
		addActor(matchLabel);
		addActor(numLabel);
		
		addActor(allMaxBetScoreLabel);
		addActor(allMinBetScoreLabel);
		addActor(heMaxLabel);
		addActor(doubleMaxLabel);
		
		addActor(zhuanShow);
		addActor(doubleXianShow);
		addActor(doubleZhuanShow);
		addActor(xianShow);
		addActor(heShow);
		
		addActor(redHeImage);
		addActor(redXianImage);
		addActor(redZhuanImage);
		addActor(blueHeImage);
		addActor(blueXianImage);
		addActor(blueZhuanImage);
		
		addActor(xianNumLabel);
		addActor(doubleXianNumLabel);
		addActor(zhuanNumLabel);
		addActor(doubleZhuanNumLabel);
		addActor(heNumLabel);
		addActor(matchNumLabel);
		
		addActor(countDownTime);
		
		addActor(image[0]);
		addActor(image[1]);
		addActor(image[2]);
		addActor(image[3]);
		addActor(image[4]);
		addActor(image[5]);
		
		addActor(leftLabel);
		addActor(rightLabel);
		
		addActor(prizeAnimation);
		
		for(int i=0;i<wayBill.length;i++){
			addActor(wayBill[i]);
			addActor(jumpBill[i]);
		}
	}
	
	/**押分包*/
	class PlayerBetData{
		byte[] playerID = new byte[4];
		byte[] betItem = new byte[4];
		byte[] betMoney = new byte[4];
	}
	
	/**取消押分*/
	class PlayerCancelData{
		byte[] loginCmd = new byte[4];
		byte[] length = new byte[4];
		
		byte[] playerID = new byte[4];
	}
	
	private void addListen() {
		oneStep.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				oneStep.setIsTouchDown(true);
				fiveStep.setIsTouchDown(false);
				tenStep.setIsTouchDown(false);
				hundredStep.setIsTouchDown(false);
				GameProperty.betStep = GameProperty.minStep;
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		fiveStep.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				oneStep.setIsTouchDown(false);
				fiveStep.setIsTouchDown(true);
				tenStep.setIsTouchDown(false);
				hundredStep.setIsTouchDown(false);
				GameProperty.betStep = GameProperty.secondStep;
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		tenStep.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				oneStep.setIsTouchDown(false);
				fiveStep.setIsTouchDown(false);
				tenStep.setIsTouchDown(true);
				hundredStep.setIsTouchDown(false);
				GameProperty.betStep = GameProperty.threeStep;
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		hundredStep.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				oneStep.setIsTouchDown(false);
				fiveStep.setIsTouchDown(false);
				tenStep.setIsTouchDown(false);
				hundredStep.setIsTouchDown(true);
				GameProperty.betStep = GameProperty.maxStep;
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		aboutButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				final AbstractStage stage = StageManager.getStage(AboutStage.class);
				StageManager.stageSwitchOver(stage,Transition.FadeIn,new Runnable() {
					public void run() {
						//StageManager.pushStage(stage);
					}
				});
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
				cancelData.length = CreateUtils.intTobyte(4);
				cancelData.playerID = CreateUtils.intTobyte(GameProperty.flagNum);
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
		
		xianButton.addListener(new InputListener(){
			Timer timer;
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				timer = new Timer();
				timer.scheduleTask(new Task() {
					public void run() {
						sendBetScore(2);
					}
				},0.6f,0.1f);
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				timer.stop();
				sendBetScore(2);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		doubleXianButton.addListener(new InputListener(){
			Timer timer;
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				timer = new Timer();
				timer.scheduleTask(new Task() {
					public void run() {
						sendBetScore(4);
					}
				},0.6f,0.1f);
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				timer.stop();
				sendBetScore(4);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		heButton.addListener(new InputListener(){
			Timer timer;
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				timer = new Timer();
				timer.scheduleTask(new Task() {
					public void run() {
						sendBetScore(0);
					}
				},0.6f,0.1f);
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				timer.stop();
				sendBetScore(0);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		doubleZhuanButton.addListener(new InputListener(){
			Timer timer;
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				timer = new Timer();
				timer.scheduleTask(new Task() {
					public void run() {
						sendBetScore(3);
					}
				
				},0.6f,0.1f);
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				timer.stop();
				sendBetScore(3);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		zhuanButton.addListener(new InputListener(){
			Timer timer;
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				timer = new Timer();
				timer.scheduleTask(new Task() {
					public void run() {
						sendBetScore(1);
					}
				},0.6f,0.1f);
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				timer.stop();
				
				sendBetScore(1);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	/**发送押分,参数是那一项押分*/
	protected void sendBetScore(int Item) {
		ByteArray msg = new ByteArray();
		PlayerBetData betData = new PlayerBetData();
		betData.betItem = CreateUtils.intTobyte(Item);
		if(GameProperty.betItemScore[Item] == 0){
			betData.betMoney = CreateUtils.intTobyte(GameProperty.allMinBetScore);
		}else{
			betData.betMoney = CreateUtils.intTobyte(GameProperty.betStep);
		}
		betData.playerID = CreateUtils.intTobyte(GameProperty.flagNum);
		
		msg.addAll(CreateUtils.intTobyte(GameProperty.CMD_PLAYERBETDATE));
		msg.addAll(CreateUtils.intTobyte(12));
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
	
	/**重载押分方法*/
	protected void sendBetScore(int Item,int score) {
		ByteArray msg = new ByteArray();
		PlayerBetData betData = new PlayerBetData();
		betData.betItem = CreateUtils.intTobyte(Item);
		betData.betMoney = CreateUtils.intTobyte(score);
		betData.playerID = CreateUtils.intTobyte(GameProperty.flagNum);
		
		msg.addAll(CreateUtils.intTobyte(GameProperty.CMD_PLAYERBETDATE));
		msg.addAll(CreateUtils.intTobyte(12));
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
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(!GameProperty.isConnect && !StageManager.isHadStage(StageManager.getStage(DisconnectStage.class))){
			final AbstractStage stage = StageManager.getStage(DisconnectStage.class);
			StageManager.stageSwitchOver(stage,Transition.FadeIn,new Runnable() {
				public void run() {
					
				}
			});
		}
	}
	
	@Override
	public void draw() {
		super.draw();
		
		batch.begin();
		batch.draw(texture, 0,0);
		/**更新路单*/
		updateBill();
		//batch.draw(new TextureRegion(Assets.get("BacPic/zhuan.png", Texture.class)), 335*StageAttribute.ratioX,(StageAttribute.height -215)*StageAttribute.ratioY, 0, 0, 11*StageAttribute.ratioX, 11*StageAttribute.ratioX, 0,0,0);
		//FontUtilsTTF.getFont(20,Color.YELLOW).draw(batch, ("比例X:"+StageAttribute.ratioX+"比例Y:"+StageAttribute.ratioY+"X:"+335*StageAttribute.ratioX+"Y:"+(StageAttribute.height -215)*StageAttribute.ratioY), 582, StageAttribute.height - 25+10);
		batch.end();
	}



	public void dispose(){
		
	}
	
}

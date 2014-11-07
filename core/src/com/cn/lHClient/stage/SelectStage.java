package com.cn.lHClient.stage;


import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.atcors.CreateLabel;
import com.cn.lHClient.atcors.TextButton;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.manager.StageManager.Transition;
import com.cn.lHClient.sqlite.CreateSqliteDB;
import com.cn.lHClient.sqlite.SqliteDataType;
import com.cn.lHClient.utils.CreateUtils;
import com.cn.lHClient.utils.FileUtils;

public class SelectStage extends AbstractStage{
	private Image bg,long1,hu,result;
	private TextButton next ,previous,exit;
	private Label timeLabel,recordNumLabel,boutMatchLabel,bankerIDLabel;
	private Label playNun,totalScore,getScore,heScore,huScore,longScore,BoutWithDrawLabel,upScore,downScore;
	private TextureRegionDrawable[] card = new TextureRegionDrawable[53];
	private TextureRegionDrawable[] resultRegion = new TextureRegionDrawable[3];
	private int currentID = 0;
	/**数据库*/
	private CreateSqliteDB database;
	
	public SelectStage(Viewport viewport) {
		super(viewport);
		
		if(Gdx.app.getType() == ApplicationType.iOS){
			currentID = FileUtils.shareFile().getPropertyInteger("RecordNum");
		}else{
			database = new CreateSqliteDB();
		}
		
		
		for(int i=0;i<card.length;i++){
			card[i] = CreateUtils.getTextureRegionDrawable("LhPic/Card/"+i+".png");
		}
		
		resultRegion[0] = CreateUtils.getTextureRegionDrawable("LhPic/BetItem/1.png");
		resultRegion[1] = CreateUtils.getTextureRegionDrawable("LhPic/BetItem/4.png");
		resultRegion[2] = CreateUtils.getTextureRegionDrawable("LhPic/BetItem/7.png");
		
		
		bg = new Image(Assets.get("LhPic/record.png", Texture.class));
		long1 = new Image(card[0]);
		long1.setDrawable(null);
		long1.setScale(0.6f);
		
		
		hu = new Image(card[0]);
		hu.setDrawable(null);
		hu.setScale(0.6f);
		
		
		result = new Image(resultRegion[0]);
		result.setScale(0.4f);
		result.setOrigin(result.getWidth()/2, result.getHeight()/2);
		result.setDrawable(null);
		
		timeLabel = CreateLabel.getLabel("", 15,Align.center, Color.YELLOW);
		recordNumLabel = CreateLabel.getLabel("",20, Align.center, Color.WHITE);
		boutMatchLabel = CreateLabel.getLabel("", 20,Align.center, Color.WHITE);
		
		playNun = CreateLabel.getLabel("", 20,Align.center, Color.WHITE);	
		totalScore = CreateLabel.getLabel("", 20,Align.center, Color.WHITE);
		getScore = CreateLabel.getLabel("",20, Align.center, Color.WHITE);
		heScore = CreateLabel.getLabel("",20, Align.center, Color.WHITE);;
		huScore = CreateLabel.getLabel("",20, Align.center, Color.WHITE);
		longScore = CreateLabel.getLabel("", 20,Align.center, Color.WHITE);
		
		upScore = CreateLabel.getLabel("",20, Align.center, Color.WHITE);
		downScore = CreateLabel.getLabel("",20, Align.center, Color.WHITE);
		
		bankerIDLabel = CreateLabel.getLabel("", 20, Align.center, Color.WHITE, true);
		BoutWithDrawLabel = CreateLabel.getLabel("", 20, Align.center, Color.WHITE,true);
		
		next = new TextButton(Assets.get("LhPic/Direction/6.png", Texture.class), Assets.get("LhPic/Direction/7.png", Texture.class));
		previous = new TextButton(Assets.get("LhPic/Direction/4.png", Texture.class), Assets.get("LhPic/Direction/5.png", Texture.class));
		exit = new TextButton(Assets.get("LhPic/exit.png", Texture.class),Assets.get("LhPic/exitPress.png", Texture.class));
		System.out.println("init selectStage");		
		setposition();
		addActor();
		addListener();
		
		if(Gdx.app.getType() == ApplicationType.iOS){
			IosShowRecord(FileUtils.shareFile().getPropertyString("Record"+(currentID-1)));
		}else{
			showRecord(database.select());
		}
		
	}
	
	private void IosShowRecord(String  connect){
		System.out.println("connect:"+connect);
		if(connect != "0"){
			String[] temp = connect.split(","); 
			SqliteDataType dataType = new SqliteDataType();
			dataType.id = currentID;
			dataType.match = temp[0];
			dataType.time = temp[1];
			dataType.result = Integer.parseInt(temp[2]);
			dataType.result1 = Integer.parseInt(temp[3]);
			dataType.result2 = Integer.parseInt(temp[4]);
			dataType.playerID = Integer.parseInt(temp[5]);
			dataType.bankerID = Integer.parseInt(temp[6]);
			dataType.TotalScore = Integer.parseInt(temp[7]);
			dataType.getScore = Integer.parseInt(temp[8]);
			dataType.betHeScore = Integer.parseInt(temp[9]);
			dataType.betHuScore = Integer.parseInt(temp[10]);
			dataType.betLongScore = Integer.parseInt(temp[11]);
			dataType.BoutWithDraw = Integer.parseInt(temp[12]);
			dataType.upScore = Integer.parseInt(temp[13]);
			dataType.downScore = Integer.parseInt(temp[14]);
			showRecord(dataType);
		}
	}
	
	private void showRecord(SqliteDataType dataType) {
		currentID = dataType.id;
		recordNumLabel.setText(Integer.toString(dataType.id));
		timeLabel.setText(dataType.time);
		boutMatchLabel.setText(dataType.match);
		playNun.setText(Integer.toString(dataType.playerID));
		totalScore.setText(Integer.toString(dataType.TotalScore));
		getScore.setText(Integer.toString(dataType.getScore));
		heScore.setText(Integer.toString(dataType.betHeScore));
		huScore.setText(Integer.toString(dataType.betHuScore));
		longScore.setText(Integer.toString(dataType.betLongScore));
		upScore.setText(Integer.toString(dataType.upScore));
		downScore.setText(Integer.toString(dataType.downScore));
		bankerIDLabel.setText(Integer.toString(dataType.bankerID));
		BoutWithDrawLabel.setText(Integer.toString(dataType.BoutWithDraw));
		
		if(dataType.result == 0){
			result.setDrawable(resultRegion[0]);
			result.setRotation(0);
		}else if(dataType.result == 1){
			result.setDrawable(resultRegion[2]);
		}else if(dataType.result == 2){
			result.setDrawable(resultRegion[1]);
		}else{
			result.setDrawable(null);
		}
		
		if(dataType.bankerID == 0){
			bankerIDLabel.setText("电脑");
		}
		
		long1.setDrawable(card[dataType.result2]);
		hu.setDrawable(card[dataType.result1]);
	}

	private void setposition() {
		bg.setPosition((StageAttribute.widgth-bg.getWidth())/2,(StageAttribute.height-bg.getHeight())/2);
		exit.setPosition((StageAttribute.widgth-bg.getWidth())/2+bg.getWidth()-40,(StageAttribute.height-bg.getHeight())/2+bg.getHeight()-50);
		previous.setPosition(277, StageAttribute.height -465);
		next.setPosition(488, StageAttribute.height - 465);
		
		hu.setPosition(249, StageAttribute.height - 256);
		long1.setPosition(462, StageAttribute.height - 256);
		
		result.setPosition(321, StageAttribute.height - 332);
		
		recordNumLabel.setPosition(275, StageAttribute.height - 169);
		boutMatchLabel.setPosition(395, StageAttribute.height - 169);
		timeLabel.setPosition(540, StageAttribute.height - 169);
		
		playNun.setPosition(280, StageAttribute.height -315);
		totalScore.setPosition(415, StageAttribute.height -392);
		getScore.setPosition(560, StageAttribute.height -315);
		heScore.setPosition(280, StageAttribute.height -340);
		huScore.setPosition(415, StageAttribute.height -340);
		longScore.setPosition(560, StageAttribute.height -340);
		upScore.setPosition(280, StageAttribute.height -367);
		downScore.setPosition(415, StageAttribute.height -367);
		
		bankerIDLabel.setPosition(415, StageAttribute.height -315);
		BoutWithDrawLabel.setPosition(560, StageAttribute.height -392);
	}

	private void addActor() {
		addActor(bg);
		addActor(exit);
		addActor(next);
		addActor(previous);
		
		addActor(long1);
		addActor(hu);
		addActor(result);
		
		addActor(longScore);
		addActor(huScore);
		addActor(heScore);
		addActor(bankerIDLabel);
		addActor(timeLabel);
		addActor(playNun);
		addActor(getScore);
		addActor(totalScore);
		addActor(recordNumLabel);
		addActor(boutMatchLabel);
		
		addActor(upScore);
		addActor(downScore);
		addActor(BoutWithDrawLabel);
	}

	private void addListener() {
		exit.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				final AbstractStage stage = StageManager.getStage(SelectStage.class);
				StageManager.stageSwitchOver(stage,Transition.FadeOut,new Runnable() {
					public void run() {
						StageManager.removeStage(stage);
					}
				});
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		
		next.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(Gdx.app.getType() == ApplicationType.iOS){
					if(currentID >=(FileUtils.shareFile().getPropertyInteger("RecordNum") -1)|| currentID>=10000){
						currentID = 0;
					}else{
						currentID++;
					}
					String connect = FileUtils.shareFile().getPropertyString("Record"+currentID);
					IosShowRecord(connect);
				}else {
					showRecord(database.select(currentID+1));
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		
		previous.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(Gdx.app.getType() == ApplicationType.iOS){
					if(currentID<=0){
						currentID = FileUtils.shareFile().getPropertyInteger("RecordNum")-1;
					}else{
						currentID--;
					}
					String connect = FileUtils.shareFile().getPropertyString("Record"+currentID);
					IosShowRecord(connect);
				}else {
					showRecord(database.select(currentID-1));
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
}

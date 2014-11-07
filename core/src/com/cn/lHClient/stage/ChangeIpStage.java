package com.cn.lHClient.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.atcors.CreateLabel;
import com.cn.lHClient.manager.StageManager;
import com.cn.lHClient.manager.StageManager.Transition;
import com.cn.lHClient.property.GameProperty;


/**
 * 修改IP
 * @author(作者) 余锦福
 * @time(时间) 13-11-20
 * @version(版本) 1.0.0
 * @cooperator(合作者)余锦福 、聂成
 *
 */
public class ChangeIpStage extends AbstractStage{
	
	private Image background; //背景图片
	private Image inputHint; //输入提示
	private Image passwordImage; //密码输入图片
	private ImageButton[] numberButton = new ImageButton[11]; //数字图片数组 
	
	private ImageButton ensureButton; //确定按钮
	private ImageButton cancelButton; //取消按钮
	
	private String inputPassword = ""; //保存用户输入的密码
	private Label IpShowLabel;
	
	public ChangeIpStage(Viewport viewport)
	{
		super(viewport);
		
		//初始化背景图片
		background = new Image(Assets.get("LhPic/changeIp/messageframe.png", Texture.class));
		inputHint = new Image(Assets.get("LhPic/changeIp/pswmessage1.png", Texture.class));
		passwordImage = new Image(Assets.get("LhPic/changeIp/pswbase.png", Texture.class));
		
		IpShowLabel = CreateLabel.getLabel("", 25, Align.center, Color.YELLOW);
		//初始化10数字按钮和密码回退按钮
		for(int i = 0;i<numberButton.length;i++)
		{
			numberButton[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(Assets.get("LhPic/changeIp/pswnum1-"+(i+1)+".png", Texture.class))),
											  new TextureRegionDrawable(new TextureRegion(Assets.get("LhPic/changeIp/pswnum2-"+(i+1)+".png", Texture.class))));
		}
		//确定按钮
		ensureButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(Assets.get("LhPic/changeIp/button1-1.png", Texture.class))), 
									   new TextureRegionDrawable(new TextureRegion(Assets.get("LhPic/changeIp/button1-2.png", Texture.class))));
		
		//取消按钮
		cancelButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(Assets.get("LhPic/changeIp/button7-1.png", Texture.class))), 
				   					   new TextureRegionDrawable(new TextureRegion(Assets.get("LhPic/changeIp/button7-2.png", Texture.class))));
		setActorName();
		setActorPosition();
		addActorToStage();
		addListenerToActor();
		
	}
	
	
	/**
	 * 为舞台上的演员设置位置
	 */
	private void setActorPosition()
	{
		background.setPosition((StageAttribute.widgth-background.getWidth())/2, (StageAttribute.height-background.getHeight())/2);
		inputHint.setPosition(background.getX()+(background.getWidth()-inputHint.getWidth())/2, background.getY()+background.getHeight()/5*4);
		passwordImage.setPosition(background.getX()+(background.getWidth()-passwordImage.getWidth())/2, background.getY()+background.getHeight()/5*3);
		IpShowLabel.setPosition(background.getX()+(background.getWidth()-IpShowLabel.getWidth())/2,passwordImage.getY()+20);
		
		//设置数字按钮位置
		for(int i = 0;i<numberButton.length;i++)
		{
			numberButton[i].setPosition(background.getX()+(background.getWidth()-numberButton[0].getWidth()*11)/2+i*numberButton[i].getWidth(), background.getY()+background.getHeight()/5*2);
		}
		ensureButton.setPosition(background.getX()+background.getWidth()/2+10, background.getY()+background.getHeight()/5*1);
		cancelButton.setPosition(background.getX()+background.getWidth()/2+ensureButton.getWidth()+20, background.getY()+background.getHeight()/5*1);
	}
	/**
	 * 为舞台上演员设置名字
	 */
	private void setActorName()
	{
		
	}
	/**
	 * 把演员加入舞台
	 */
	private void addActorToStage()
	{
		this.addActor(background);
		this.addActor(inputHint);
		this.addActor(passwordImage);
		this.addActor(IpShowLabel);
		for(int i = 0;i<numberButton.length;i++)
		{
			this.addActor(numberButton[i]);
		}
		
		this.addActor(ensureButton);
		this.addActor(cancelButton);
	}
	
	/**
	 * 为舞台上的演员添加监听
	 */
	private void addListenerToActor()
	{
		for(int i = 0;i<numberButton.length;i++)
		{	
			final int fi = i;
			numberButton[fi].addListener(new InputListener()
			{
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}
				
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
						if(inputPassword.length() >0 && fi == 10){
							inputPassword = inputPassword.substring(0, inputPassword.length()-1);
						}else if(fi != 10){
							inputPassword+=fi;	
						}
						if(inputPassword.length()>0 && Integer.parseInt(inputPassword)>30){
							StageManager.getPlatFormInstance().toast("台号请不要超过30");
						}
						IpShowLabel.setText(inputPassword);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		
		
		
		//为确定按钮添加监听
		ensureButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
					StageManager.getPlatFormInstance().setIp("192.168.1."+inputPassword);
					final AbstractStage stage = StageManager.getStage(ChangeIpStage.class);
					StageManager.stageSwitchOver(stage,Transition.FadeOut,new Runnable() {
						public void run() {
							GameProperty.flagNum = StageManager.getPlatFormInstance().getIp()-1;
							StageManager.removeStage(stage);
						}
					});
			}
		});
		
		//为取消按钮添加监听
		cancelButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				final AbstractStage stage = StageManager.getStage(ChangeIpStage.class);
				StageManager.stageSwitchOver(stage,Transition.FadeOut,new Runnable() {
					public void run() {
						StageManager.removeStage(stage);
					}
				});
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	public void dispose() {
		background = null;
		inputHint = null;
		passwordImage = null;
		ensureButton = null;
		cancelButton = null;
		for(int i = 0;i<numberButton.length;i++)
		{
			numberButton[i] = null;
		}
	}
}

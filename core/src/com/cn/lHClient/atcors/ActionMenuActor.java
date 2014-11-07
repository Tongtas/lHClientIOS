package com.cn.lHClient.atcors;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cn.lHClient.utils.CreateUtils;

/**
 * 旋转菜单Actor对象
 * @author Administrator
 *
 */
public class ActionMenuActor extends Group{
	
	public static final int VerticalUp = 1; //垂直向上滑动
	public static final int VerticalDown = 2; //垂直向下
	public static final int HorizontalLeft = 3; //水平向左
	public static final int HorizontalRight = 4; //水平向右
	
	public static final int SlideShow = 1; //滑动出来
	public static final int OneByOne = 2; //一个接一个显示出来
	
	private int moveMode = 1; //表示垂直向上滑动
	private int showMode = 1; //表示显示模式为滑动显示
	
	private int interval = 10; //表示各个actor之间的间隔
	
	private boolean show = true;
	private boolean isDone = true;
	private TextureRegionDrawable menu[] = new TextureRegionDrawable[2]; 
	
	public ActionMenuActor(final Image image)
	{
		setSize(image.getWidth(), image.getHeight());
		addActor(image);
		image.setOrigin(image.getWidth()/2, image.getHeight()/2);
		
		menu[0] = CreateUtils.getTextureRegionDrawable("LhPic/menuClose.png");
		menu[1] = CreateUtils.getTextureRegionDrawable("LhPic/menuOpen.png");
		image.setDrawable(menu[0]);
		
		image.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(show && isDone)
				{
					show = false;
					isDone = false;
					image.setDrawable(menu[0]);
					float temp = 0;
					if(showMode == SlideShow)
					{
						
						image.addAction(Actions.moveBy(image.getImageWidth()/3*2, 0,0.3f, Interpolation.pow2In));
						switch(moveMode)
						{
						case VerticalUp:
							temp = image.getHeight()-(image.getHeight()-getChildren().get(0).getHeight())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								Actor actor = getChildren().get(i);
								actor.addAction(Actions.parallel(Actions.moveBy(0, temp,0.3f),Actions.moveBy(image.getImageWidth()/3*2, 0)));
								temp += actor.getHeight()+interval;
							}
							break;
						case VerticalDown:
							temp = image.getHeight()-(image.getHeight()-getChildren().get(0).getHeight())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								Actor actor = getChildren().get(i);
								actor.addAction(Actions.parallel(Actions.moveBy(0, -temp,0.3f),Actions.fadeIn(0.3f)));
								temp += actor.getHeight()+interval;
							}
							break;
						case HorizontalLeft:
							temp = image.getWidth()-(image.getWidth()-getChildren().get(0).getWidth())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								Actor actor = getChildren().get(i);
								actor.addAction(Actions.moveBy(-temp, 0,0.3f));
								temp += actor.getWidth()+interval;
							}
							break;
						case HorizontalRight:
							temp = image.getWidth()-(image.getWidth()-getChildren().get(0).getWidth())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								Actor actor = getChildren().get(i);
								actor.addAction(Actions.moveBy(temp, 0,0.3f));
								temp += actor.getWidth()+interval;
							}
							break;
						}
					}
					else if(showMode == OneByOne)
					{
						switch(moveMode)
						{
						case VerticalUp:
							temp = image.getHeight()-(image.getHeight()-getChildren().get(0).getHeight())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								final float fTemp = temp;
								final Actor actor = getChildren().get(i);
								actor.addAction(Actions.delay(0.1f*(i+1), Actions.run(new Runnable(){
									public void run() {
										actor.setColor(actor.getColor().r, actor.getColor().g, actor.getColor().b, 0);
										actor.moveBy(0, fTemp);
										actor.addAction(Actions.fadeIn(0.3f));
									}
								})));
								temp += actor.getHeight()+interval;
							}
							break;
						case VerticalDown:
							temp = image.getHeight()-(image.getHeight()-getChildren().get(0).getHeight())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								final float fTemp = temp;
								final Actor actor = getChildren().get(i);
								actor.addAction(Actions.delay(0.1f*(i+1), Actions.run(new Runnable(){
									public void run() {
										actor.setColor(actor.getColor().r, actor.getColor().g, actor.getColor().b, 0);
										actor.moveBy(0, -fTemp);
										actor.addAction(Actions.fadeIn(0.2f));
									}
								})));
								temp += actor.getHeight()+interval;
							}
							break;
						case HorizontalLeft:
							temp = image.getWidth()-(image.getWidth()-getChildren().get(0).getWidth())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								final float fTemp = temp;
								final Actor actor = getChildren().get(i);
								actor.addAction(Actions.delay(0.1f*(i+1), Actions.run(new Runnable(){
									public void run() {
										actor.setColor(actor.getColor().r, actor.getColor().g, actor.getColor().b, 0);
										actor.moveBy(-fTemp, 0);
										actor.addAction(Actions.fadeIn(0.2f));
									}
								})));
								temp += actor.getHeight()+interval;
							}
							break;
						case HorizontalRight:
							temp = image.getWidth()-(image.getWidth()-getChildren().get(0).getWidth())/2+interval;
							for(int i=0;i<getChildren().size-1;i++)
							{
								final float fTemp = temp;
								final Actor actor = getChildren().get(i);
								actor.addAction(Actions.delay(0.1f*(i+1), Actions.run(new Runnable(){
									public void run() {
										actor.setColor(actor.getColor().r, actor.getColor().g, actor.getColor().b, 0);
										actor.moveBy(fTemp, 0);
										actor.addAction(Actions.fadeIn(0.2f));
									}
								})));
								temp += actor.getHeight()+interval;
							}
							break;
						}
					}
					isDone = true;
					image.setDrawable(menu[1]);
				}else if(isDone){
					isDone = false;
					show = true;
					for(int i=0;i<getChildren().size;i++)
					{
						Actor actor = getChildren().get(i);
						actor.addAction(Actions.sequence(Actions.moveTo(image.getX()+(image.getWidth()-actor.getWidth())/2, image.getY()+(image.getHeight()-actor.getHeight())/2,0.3f),Actions.moveBy(-image.getImageWidth()/3*2, 0,0.3f)));
					}
					isDone = true;
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					image.setDrawable(menu[0]);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	public void setMoveMode(int mode)
	{
		if(mode >= VerticalUp && mode <= HorizontalRight)
		{
			this.moveMode = mode;
		}
	}
	
	@Override
	public void addActor(Actor actor) {
		super.addActor(actor);
		actor.toBack();
		actor.setPosition((getWidth()-actor.getWidth())/2, (getHeight()-actor.getHeight())/2);
	}
	
	public void setShowMode(int mode)
	{
		if(mode >= SlideShow && mode <= OneByOne)
		{
			this.showMode = mode;
		}
	}
	
	public void setActorInterval(int value)
	{
		this.interval = value;
	}
	
}

package com.cn.lHClient.manager;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cn.lHClient.assets.Assets;
import com.cn.lHClient.fonts.FontUtilsTTF;
import com.cn.lHClient.plantform.Platform;
import com.cn.lHClient.stage.AbstractStage;
import com.cn.lHClient.stage.StageAttribute;
import com.cn.lHClient.utils.Logger;

public class StageManager {
	/**渲染舞台数组*/
	private static Array<AbstractStage> renderArray = new Array<AbstractStage>();
	/**已经new过的舞台对象*/
	@SuppressWarnings("rawtypes")
	private static ObjectMap<Class,AbstractStage> stageMap = new ObjectMap<Class,AbstractStage>(); 
	/**各自平台特性*/
	private static Platform platFormInstance;
	
	private static float transitionTime = 0.6f;
	
	private StageManager(){
		
	}
	
	/**实例化平台特性*/
	public static void registerPlatformInstance(Platform platform){
		platFormInstance = platform;
	}
	
	/**获取平台特新的实例*/
	public static Platform getPlatFormInstance(){
		
		return platFormInstance;
	}
	
	/**舞台跳转*/
	public static void stageSwitchOver(final AbstractStage stage,Transition type,final Runnable runnable){
		stage.getRoot().setScale(1f);
		stage.getRoot().setBounds(0, 0, StageAttribute.widgth, StageAttribute.height);
		stage.getRoot().setColor(stage.getRoot().getColor().r, stage.getRoot().getColor().g, stage.getRoot().getColor().b, stage.getRoot().getColor().a);
		stage.getRoot().setOrigin(0, 0);
		stage.getRoot().setRotation(0);
		stage.getRoot().setVisible(true);
		
		switch(type){
		case None:
			pushStage(stage);
			break;
		case MoveUpToDownIn:
			stage.getRoot().setPosition(0, StageAttribute.height);
			pushStage(stage);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime,Interpolation.pow2), Actions.run(new Runnable() {
				public void run() {
					if(runnable != null){
						runnable.run();
					}
				}
			})));
			break;
		case MoveDownToUpIn: //从下到上进入
			pushStage(stage);
			stage.getRoot().setPosition(0, -StageAttribute.height);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveLeftToRightIn: //从左到右进入
			stage.getRoot().setPosition(-StageAttribute.widgth, 0);
			pushStage(stage);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveRightToLeftIn: //从右到左进入
			stage.getRoot().setPosition(StageAttribute.widgth, 0);
			pushStage(stage);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromUpLeftIn: //从左上进入
			stage.getRoot().setPosition(-StageAttribute.widgth, StageAttribute.height);
			pushStage(stage);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromDownLeftIn: //从左下进入
			stage.getRoot().setPosition(-StageAttribute.widgth, -StageAttribute.height);
			pushStage(stage);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromUpRightIn: //从右上进入
			stage.getRoot().setPosition(StageAttribute.widgth, StageAttribute.height);
			pushStage(stage);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromDownRightIn: // 从右下进入
			stage.getRoot().setPosition(StageAttribute.widgth, -StageAttribute.height);
			pushStage(stage);
			stage.addAction(Actions.sequence(Actions.moveTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case FadeIn: //淡入
			pushStage(stage);
			stage.getRoot().setColor(stage.getRoot().getColor().r, stage.getRoot().getColor().g, stage.getRoot().getColor().b, 0);
			stage.addAction(Actions.sequence(Actions.fadeIn(transitionTime,Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case BounceScaleIn: //爆炸式放大进入
			pushStage(stage);
			stage.getRoot().setOrigin(stage.getWidth()/2, stage.getHeight()/2);
			stage.getRoot().setScale(0);
			stage.addAction(Actions.sequence(Actions.scaleTo(1, 1, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromLeftUpIn: //从左上旋转进入
			pushStage(stage);
			stage.getRoot().setRotation(90);
			stage.addAction(Actions.sequence(Actions.rotateTo(0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromLeftDownIn: //从左下旋转进入
			pushStage(stage);
			stage.getRoot().setRotation(-90);
			stage.addAction(Actions.sequence(Actions.rotateTo(0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromRightUpIn: //从右上旋转进入
			pushStage(stage);
			stage.getRoot().setOrigin(stage.getWidth(), 0);
			stage.getRoot().setRotation(90);
			stage.addAction(Actions.sequence(Actions.rotateTo(0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromRightDownIn: //从右下旋转进入
			pushStage(stage);
			stage.getRoot().setOrigin(stage.getWidth(), 0);
			stage.getRoot().setRotation(-90);
			stage.addAction(Actions.sequence(Actions.rotateTo(0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
			
		case MoveUpToDownOut: //从上到下退出
			stage.addAction(Actions.sequence(Actions.moveTo(0, -StageAttribute.height, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveDownToUpOut: //从下到上退出
			stage.addAction(Actions.sequence(Actions.moveTo(0, StageAttribute.height, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveLeftToRightOut: //从左到右退出
			stage.addAction(Actions.sequence(Actions.moveTo(StageAttribute.widgth, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveRightToLeftOut: //从右到左退出
			stage.addAction(Actions.sequence(Actions.moveTo(StageAttribute.widgth, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromUpLeftOut: //从左上退出
			stage.addAction(Actions.sequence(Actions.moveTo(-StageAttribute.widgth, StageAttribute.height, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromDownLeftOut: //从左下退出
			stage.addAction(Actions.sequence(Actions.moveTo(-StageAttribute.widgth, -StageAttribute.height, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromUpRightOut: //从右上退出
			stage.addAction(Actions.sequence(Actions.moveTo(StageAttribute.widgth, StageAttribute.height, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case MoveFromDownRightOut: //从右下退出
			stage.addAction(Actions.sequence(Actions.moveTo(StageAttribute.widgth, -StageAttribute.height, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case FadeOut: //淡出
			stage.addAction(Actions.sequence(Actions.fadeOut(transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case BounceScaleOut: //爆炸式缩小退出
			stage.getRoot().setOrigin(stage.getWidth()/2, stage.getHeight()/2);
			stage.addAction(Actions.sequence(Actions.scaleTo(0, 0, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromLeftUpOut://从左上旋转退出
			stage.addAction(Actions.sequence(Actions.rotateTo(90, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromLeftDownOut: //从左下旋转退出
			stage.addAction(Actions.sequence(Actions.rotateTo(-90, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromRightUpOut: //从右上旋转退出
			stage.getRoot().setOrigin(stage.getWidth(), 0);
			stage.addAction(Actions.sequence(Actions.rotateTo(-90, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		case RotateFromRightDownOut: //从右下旋转退出
			stage.getRoot().setOrigin(stage.getWidth(), 0);
			stage.addAction(Actions.sequence(Actions.rotateTo(90, transitionTime, Interpolation.pow2),Actions.run(new Runnable(){
				public void run() {
					if(runnable != null)
					{
						runnable.run();
					}
				}
			})));
			break;
		default:
			break;
		}
	}
	
	/**添加场景到渲染舞台*/
	public static void pushStage(AbstractStage stage) {
		renderArray.add(stage);
	}

	/**舞台中时候包含此舞台*/
	public static boolean isHadStage(AbstractStage stage){
		for(int i=0;i<renderArray.size;i++){
			if(renderArray.get(i) == stage){
				return true;
			}
		}
		return false;
	}
	
	/**清空渲染舞台,再添加*/
	public static void pushAndCleanStage(AbstractStage stage){
		renderArray.clear();
		renderArray.add(stage);
	}

	/**移除某个舞台*/
	public static void removeStage(AbstractStage stage){
		renderArray.removeValue(stage, false);
	}
	
	/**暂停舞台*/
	public static void puaseStage(){
		for(int i=0;i<renderArray.size;i++){
			renderArray.get(i).pause();
		}
	}
	
	/**恢复舞台*/
	public static void resumeStage(){
		for(int i=0;i<renderArray.size;i++){
			renderArray.get(i).resume();
		}
	}	
	
	/**
	 * 用反射
	 * 根据类名获取舞台对象*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static AbstractStage getStage(Class class1){
		AbstractStage stage = stageMap.get(class1);
		if(stage == null)
		{
			try {
				stage = (AbstractStage) class1.getConstructor(Viewport.class).newInstance(new StretchViewport(StageAttribute.widgth,StageAttribute.height));
			} catch (Exception e) {
				Logger.showError("stage had exception");
				e.getMessage();
				e.printStackTrace();
			} 
			stageMap.put(class1, stage);
		}
		return stage;
	}
	
	@SuppressWarnings("rawtypes")
	public static void  disposeStage(Class type){
		AbstractStage stage = stageMap.get(type);
		if(stage != null){
			stage.dispose();
			stageMap.remove(type);
		}
	}
	
	/**跳转动画*/
	public static enum Transition{
		None, //默认无过度效果
		MoveUpToDownIn, //从上到下进入
		MoveUpToDownOut, //从上到下退出
		MoveDownToUpIn, //从下到上进入
		MoveDownToUpOut, //从下到上退出
		
		MoveLeftToRightIn, //从左到右进入
		MoveLeftToRightOut, //从左到右退出
		MoveRightToLeftIn, //从右到左进入
		MoveRightToLeftOut, //从右到左退出
		
		MoveFromUpLeftIn, //从左上进入
		MoveFromUpLeftOut, //从左上退出
		MoveFromDownLeftIn, //从左下进入
		MoveFromDownLeftOut, //从左下退出
		MoveFromUpRightIn, //从右上进入
		MoveFromUpRightOut, //从右上退出
		MoveFromDownRightIn, // 从右下进入
		MoveFromDownRightOut, //从右下退出
		
		FadeIn, //淡入
		FadeOut, //淡出
		
		BounceScaleIn, //爆炸式放大进入
		BounceScaleOut, //爆炸式缩小退出
		
		RotateFromLeftUpIn, //从左上旋转进入
		RotateFromLeftUpOut,//从左上旋转退出
		RotateFromLeftDownIn, //从左下旋转进入
		RotateFromLeftDownOut, //从左下旋转退出
		
		RotateFromRightUpIn, //从右上旋转进入
		RotateFromRightUpOut, //从右上旋转退出
		RotateFromRightDownIn, //从右下旋转进入
		RotateFromRightDownOut, //从右下旋转退出
		
	}
	
	/**渲染*/
	public static void render(){
		for(int i=0;i<renderArray.size;i++){
			AbstractStage stage = renderArray.get(i);
			Gdx.input.setInputProcessor(stage);
			if(!stage.getPause()){
				stage.act();
			}
			stage.draw();
		}
	}
	
	public static void dispose(){
		FontUtilsTTF.dispose();
		Assets.dispose();
		//CreateUtils.dispose();
		
		Values<AbstractStage> values = stageMap.values();
		while(values.hasNext){
			values.next().dispose();
		}
	}
	
}

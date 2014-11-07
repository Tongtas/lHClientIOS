package com.cn.lHClient.utils;

import java.util.Vector;

import com.cn.lHClient.property.GameProperty;
import com.cn.lHClient.stage.StageAttribute;


public class WayBillUtil {
	/**大路路单*/
	/**服务器传过来的路单转换成大路路单类型，存储多少列，每一列的个数*/
	public static Vector<Integer> BigWayBill = null;
	/**最后一次的位置x,y*/
	public static int g_x,g_y;
	public static boolean lastResultValue = false;
	public static void WayBillToBigWayBill(){
		int billItem[] = GameProperty.billItem;
		BigWayBill = new Vector<Integer>();
		boolean isUse[][] = new boolean[44][6];
		/**x,y坐标，lastResult上一场的值，t y值到了最大向x的方向移动次数*/
		int x =0,y=0,lastResult = 4,t=0;
		
		for(int i=1;i<billItem.length;i++){
			if(billItem[i] == 2){
				if(lastResult == 2){
					if(y<isUse[0].length-1 && !isUse[x][y+1]){
						y++;
					}else{
						t++;
					}
				}else if(lastResult == 3){
					x++;
					y=0;
					t=0;
				}
				if(BigWayBill.size() <= x){
					BigWayBill.add(x, y);
				}else{
					BigWayBill.set(x, y);
				}
				lastResult = 2;
				GameProperty.billPosition[i][0] = (27+12*(x+t))*StageAttribute.ratioX;
				GameProperty.billPosition[i][1] = (StageAttribute.height -49-11.5f*y)*StageAttribute.ratioY;
				GameProperty.value[i] = true;
				lastResultValue = true;
				g_x = x;
				g_y = y;
			}else if(billItem[i] == 3){
				if(lastResult == 3){
					if(y<isUse[0].length-1 && !isUse[x][y+1]){
						y++;
					}else{
						t++;
					}
				}else if(lastResult == 2){
					x++;
					y=0;
					t=0;
				}
				
				if(BigWayBill.size() <= x){
					BigWayBill.add(x, y);
				}else{
					BigWayBill.set(x, y);
				}
				lastResult = 3;
				GameProperty.billPosition[i][0] = (27+12*(x+t))*StageAttribute.ratioX;
				GameProperty.billPosition[i][1] = (StageAttribute.height -49-11.5f*y)*StageAttribute.ratioY;
				GameProperty.value[i] = false;
				lastResultValue = false;
				g_x = x;
				g_y = y;
			}else if(billItem[i] == 1){
				GameProperty.billPosition[i][0] = (27+12*(x+t))*StageAttribute.ratioX;
				GameProperty.billPosition[i][1] = (StageAttribute.height -49-11.5f*y)*StageAttribute.ratioY;
				GameProperty.value[i] = false;
			}else if(billItem[i] == 0){
				break;
			}
		}
	}

	
	/**根据大路路单生成下三路
	 * @param type 1 = 大眼仔，2 = 小路 ，3 = 曱甴路
	 * */
	public static void bigWayBillToOtherWayBill(int type){
		boolean isUse[][] = new boolean[44][6];
		int begian = 0,yBegian = 1,x=0,y=0,t = 0;
		boolean lastResult = true,firstTime = true;
		/**计数*/
		int num=0;
		
		if(BigWayBill.size() > type){
			/**从哪里开始*/
			if(BigWayBill.get(type)>=1){
				begian = type;
			}else{
				begian = type+1;
				yBegian = 0;
			}
			System.out.println(type+"BigWayBill"+BigWayBill.toString());
			for(int i=begian;i<BigWayBill.size();i++){
				for(int j=yBegian;j<=BigWayBill.get(i);j++){
					if(j==0){
						if(lastResult == newColumn(type, i-1)){
							if(y<isUse[0].length-1 && !isUse[x][y+1]){
								y++;
							}else{
								t++;
							}
						}else{
							y=0;
							x++;
							t=0;
						}
						lastResult = newColumn(type, i-1);
						//System.out.println("对齐："+i+"  "+BigWayBill.get(i)+"  "+j+"  "+newColumn(type, i-1));
					}else if(j==1 || YON(type,i,j-1)){
						if(lastResult == YON(type, i, j)){
							if(y<isUse[0].length-1 && !isUse[x][y+1]){
								y++;
							}else{
								t++;
							}
						}else{
							y=0;
							x++;
							t=0;
						}
						lastResult = YON(type, i, j);
						//System.out.println("有无："+i+"  "+BigWayBill.get(i)+"  "+j+"  "+YON(type,i,j));
					}else{
						if(lastResult){
							if(y<isUse[0].length-1 && !isUse[x][y+1]){
								y++;
							}else{
								t++;
							}
						}else{
							y=0;
							x++;
							t=0;
						}
						lastResult = true;
						//System.out.println("直落："+i+"  "+BigWayBill.get(i)+"  "+j+"  "+true);
					}
					
					if(firstTime){
						x=0;y=0;
						firstTime = false;
					}
					num++;
					if(type == 1){
						GameProperty.bigEyesBillPosition[num][0] = (9+(x+t)*6f)*StageAttribute.ratioX;
						GameProperty.bigEyesBillPosition[num][1] = (StageAttribute.height-90-6f*y)*StageAttribute.ratioY;
						GameProperty.bigEyesValue[num] = lastResult;
					}else if(type == 2){
						GameProperty.smallBillPosition[num][0] = (9+(x+t)*6f)*StageAttribute.ratioX;
						GameProperty.smallBillPosition[num][1] = (StageAttribute.height-126-6f*y)*StageAttribute.ratioY;
						GameProperty.smallValue[num] = lastResult;
					}else{
						GameProperty.cockroachBillPosition[num][0] = (274+(x+t)*6f)*StageAttribute.ratioX;
						GameProperty.cockroachBillPosition[num][1] = (StageAttribute.height-128-6f*y)*StageAttribute.ratioY;
						GameProperty.cockriachValue[num] = lastResult;
					}
				}
				yBegian = 0;
			}
	  }
	}
	
	/**判断新一行的*/
	public static boolean newColumn(int type,int column){
		if(BigWayBill.get(column)+1==1 || YON(type,column,BigWayBill.get(column))){
			return !YON(type, column, BigWayBill.get(column)+1);
		}else {
			return false;
		}
	}

	/**有无
	 * 左边一列的同排是否有数，无论是庄还是闲
	 * 从第二排开始判断，直到出现无
	 * @param type 1 = 大眼仔 路，2 = 小路   3 = 曱甴路（小强路）
	 * @param column 第几列
	 * @param row 第几排
	 * @return  true = 庄（红） false = 庄（蓝）
	 * */
	public static boolean YON(int type,int column,int row){
		if(BigWayBill.get(column-type)>=row){
			return true;
		}
		return false;
	}
	
	
	/**下一场预测*/
	public static boolean nextMatch(int type,boolean result){
		if(lastResultValue == result){
			return YON(type, g_x, g_y+1);
		}else {
			return newColumn(type, g_x);
		}
	}
	
}

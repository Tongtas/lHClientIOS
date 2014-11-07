package com.cn.lHClient.sqlite;


public class CreateSqliteDB {
	
	/**数据库句柄*/
	Database dbHandler;
	
	public static final String TABLENAME = "Record";
	private static final String DATABASENAME = "lHClient.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table if not exists " + TABLENAME + 
			" (ID integer primary key autoincrement, " + 
			"match text, " +
			"time text, " +
			"result integer, " +
			"result1 integer, " +
			"result2 integer, " +
			"playerID integer, " +
			"bankerID integer, " +
			"totalScore integer, "+
			"getScore integer, " +
			"betHeScore integer, " +
			"betHuScore integer, " +
			"betLongScore integer, " +
			"BoutWithDraw integer," +
			"upScore integer," +
			"downScore integer)";
	
	/***创建数据库,创建表,并打开*/
	public void openDateBase(){
		dbHandler = DatabaseFactory.getNewDatabase(DATABASENAME, DATABASE_VERSION, DATABASE_CREATE, null);
		dbHandler.setupDatabase();
		
		try {
			dbHandler.openOrCreateDatabase();
			dbHandler.execSQL(DATABASE_CREATE);
		} catch (SQLiteGdxException e) {
			e.printStackTrace();
		}
	}
	
	/**获取表中的行数*/
	public int getRow(){
		if(dbHandler == null){
			openDateBase();
		}
		try {
			return dbHandler.rawQuery("select ID from Record").getCount();
		} catch (SQLiteGdxException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**插入数据*/
	public void insert(String sql){
		if(dbHandler == null){
			openDateBase();
		}
		
		try {
			dbHandler.execSQL("insert into Record (match,time,result,result1,result2,playerID,bankerID," +
					"totalScore,getScore,betHeScore,betHuScore,betLongScore,BoutWithDraw,upScore,downScore) " +
					"values ("+sql+")");
		} catch (SQLiteGdxException e) {
			e.printStackTrace();
		}
	}
	
	/**查询 by id*/
	public SqliteDataType select (int id){
		SqliteDataType dataType = new SqliteDataType();
		DatabaseCursor cursor = null;
		if(dbHandler == null){
			openDateBase();
		}
		
		try {
			cursor = dbHandler.rawQuery("select * from Record where ID = "+id);
		} catch (SQLiteGdxException e) {
			e.printStackTrace();
		}
		if(cursor.next()){
			dataType.id = cursor.getInt(0);
			dataType.match = cursor.getString(1);
			dataType.time = cursor.getString(2);
			dataType.result = cursor.getInt(3);
			dataType.result1 = cursor.getInt(4);
			dataType.result2 = cursor.getInt(5);
			dataType.playerID = cursor.getInt(6);
			dataType.bankerID = cursor.getInt(7);
			dataType.TotalScore = cursor.getInt(8);
			dataType.getScore = cursor.getInt(9);
			dataType.betHeScore = cursor.getInt(10);
			dataType.betHuScore = cursor.getInt(11);
			dataType.betLongScore = cursor.getInt(12);
			dataType.BoutWithDraw = cursor.getInt(13);
			dataType.upScore = cursor.getInt(14);
			dataType.downScore = cursor.getInt(15);
		}
		return dataType;
	}
	
	
	/**查询 ID最大*/
	public SqliteDataType select (){
		SqliteDataType dataType = new SqliteDataType();
		DatabaseCursor cursor = null;
		if(dbHandler == null){
			openDateBase();
		}
		
		try {
			cursor = dbHandler.rawQuery("select * from Record order by ID  desc");
		} catch (SQLiteGdxException e) {
			e.printStackTrace();
		}
		
		if(cursor.next()){
			dataType.id = cursor.getInt(0);
			dataType.match = cursor.getString(1);
			dataType.time = cursor.getString(2);
			dataType.result = cursor.getInt(3);
			dataType.result1 = cursor.getInt(4);
			dataType.result2 = cursor.getInt(5);
			dataType.playerID = cursor.getInt(6);
			dataType.bankerID = cursor.getInt(7);
			dataType.TotalScore = cursor.getInt(8);
			dataType.getScore = cursor.getInt(9);
			dataType.betHeScore = cursor.getInt(10);
			dataType.betHuScore = cursor.getInt(11);
			dataType.betLongScore = cursor.getInt(12);
			dataType.BoutWithDraw = cursor.getInt(13);
			dataType.upScore = cursor.getInt(14);
			dataType.downScore = cursor.getInt(15);
		}
		return dataType;
	}
	
	/**
	 * @param num 前num行
	 * 删除部分数据
	 * */
	public void delete(int num){
		if(dbHandler == null){
			openDateBase();
		}
		
		try {
			dbHandler.execSQL("delete from Record where ID in (select ID from Record order by ID limit "+num+")");
		} catch (SQLiteGdxException e) {
			e.printStackTrace();
		}
	}
	
	
	/**关闭数据库*/
	public void dispose(){
		if(dbHandler != null){
			try {
				dbHandler.closeDatabase();
				dbHandler = null;
			} catch (SQLiteGdxException e) {
				e.printStackTrace();
			}
		}
	}
}

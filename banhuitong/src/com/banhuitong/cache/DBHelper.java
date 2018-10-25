package com.banhuitong.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";

	private final static String DB_NAME = "banhuitong.db";// 数据库名
	private final static int VERSION = 1;// 版本号

	// 自带的构造方法
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 为了每次构造时不用传入dbName和版本号，自己得新定义一个构造方法
	public DBHelper(Context cxt) {
		this(cxt, DB_NAME, null, VERSION);// 调用上面的构造方法
	}

	// 版本变更时
	public DBHelper(Context cxt, int version) {
		this(cxt, DB_NAME, null, version);
	}

	// 当数据库创建的时候调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table tb_json (key varchar(100) primary key, value text, datepoint timestamp);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}
}

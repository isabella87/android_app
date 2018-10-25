package com.banhuitong.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CacheService {

	 private static CacheService instance;
	 DBHelper helper = null;

	 private CacheService(Context cxt) {
		 helper = new DBHelper(cxt);
	 }
	 
	 public static CacheService getInstance(Context cxt) {
		if (instance == null) { 
			instance=new CacheService(cxt);
		}
		return instance;
	 }

	 public void insert(String key, String json) {
		  String sql = "insert into tb_json (key,value,datepoint)values(?,?,?)";
		  SQLiteDatabase db = helper.getWritableDatabase();
		  db.execSQL(sql, new Object[] {key,json,new Date().getTime()});
	 }
	 
	 public void update(String key, String json) {
		  String sql = "update tb_json set value = ?, datepoint = ? where key = '" + key + "'";
		  SQLiteDatabase db = helper.getWritableDatabase();
		  db.execSQL(sql, new Object[] {json, new Date().getTime()});
	 }
	 
	 public Map<String,String> query(String key) {
		  Map<String,String> rst = new HashMap<String,String>();
		  
		  String sql = "select key,value,datepoint from tb_json where key = ?";
		  SQLiteDatabase db = helper.getReadableDatabase();
		  Cursor cursor =db.rawQuery(sql, new String[] {key});  
		  
	      while(cursor.moveToNext()){  
	    	  rst.put("value", cursor.getString(cursor.getColumnIndex("value")));
	    	  rst.put("datepoint", cursor.getString(cursor.getColumnIndex("datepoint")));
	    	  break;
	      }
	      return rst;  
	 }
}

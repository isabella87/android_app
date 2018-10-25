package com.mengcheng.async;

import com.mengcheng.activity.MyApplication;
import com.mengcheng.util.Constants;
/**
 * 基础Task
 * @author Administrator
 *
 */
public abstract class BaseTask implements Runnable {
	protected String username = "", password = "";
	
	protected BaseTask(){
		this.username = MyApplication.sp.getString(Constants.USER_NAME, "");
		this.password = MyApplication.sp.getString(Constants.USER_PASS, "");
	}
}

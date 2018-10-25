package com.banhuitong.async;

import com.banhuitong.activity.MyApplication;
import com.banhuitong.util.Constants;

public abstract class BaseTask implements Runnable {
	protected String username = "", password = "";
	
	protected BaseTask(){
		this.username = MyApplication.sp.getString(Constants.USER_NAME, "");
		this.password = MyApplication.sp.getString(Constants.USER_PASS, "");
	}
}

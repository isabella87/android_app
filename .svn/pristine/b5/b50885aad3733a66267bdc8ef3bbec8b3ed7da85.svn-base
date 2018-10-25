package com.banhuitong.cache;

import org.json.JSONObject;

import android.graphics.Bitmap;

/**
 * 缓存类
 * @author Administrator
 *
 */
public class CacheObject {
	
	private static CacheObject instance;
	
	private CacheObject(){
	}

	public static CacheObject getInstance() {
		if (instance == null) { 
			instance=new CacheObject();
		}
		return instance;
	}
	
	private JSONObject survey;
	private JSONObject personInfo;
	private String rcode;
	private Bitmap rcodeImage;

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public Bitmap getRcodeImage() {
		return rcodeImage;
	}

	public void setRcodeImage(Bitmap rcodeImage) {
		this.rcodeImage = rcodeImage;
	}

	public JSONObject getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(JSONObject personInfo) {
		this.personInfo = personInfo;
	}

	public JSONObject getSurvey() {
		return survey;
	}

	public void setSurvey(JSONObject survey) {
		this.survey = survey;
	}
	
}

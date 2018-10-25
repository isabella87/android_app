package com.banhuitong.activity.cp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.banhuitong.activity.BaseActivity;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetCPDataTask;
import com.banhuitong.util.Constants;
import com.banhuitong.util.cp.HexUtil;
import com.banhuitong.util.cp.HttpClientHelper;
import com.banhuitong.util.cp.RSAUtils;
import com.chinapay.authplugin.activity.Initialize;
import com.chinapay.authplugin.util.CPGlobaInfo;
import com.chinapay.authplugin.util.Utils;

public class CPActivity extends BaseActivity {

	private static final String MY_PKG = "com.banhuitong.activity";

	private final String ENV = "TEST";
	private final String GET_SIGN_URL = "https://test.credit2go.cn/fes/p2p/authSDK";
	private final String BANK_CODE = "30050000";
	private final String COINST_CODE = "000017";

	// private static final String ENV = "PRODUCT";

	private Map<String, Object> mapCPData = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Utils.getPayResult() != null && !Utils.getPayResult().equals("")) {

			// 根据返回码做出相应处理
			if (Utils.getPayResult().indexOf("0000") > -1) {
				// 认证成功，返回卡信息及用户信息
				Log.i("AuthSDK",
						"姓名：" + Utils.getCerName() + "\n身份证类型："
								+ Utils.getCerType() + "\n身份证号："
								+ Utils.getCerNo() + "\n卡号："
								+ Utils.getCardNo() + "\n手机号："
								+ Utils.getMobile());
			} else {
				// 认证失败
			}

		}
		CPGlobaInfo.init(); // 清空返回结果
		CPActivity.this.finish();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!isRestart) {
			DefaultThreadPool.getInstance().execute(new GetCPDataTask(handler, this.mApp));
//			startCheck();
			isRestart = true;
		}
	}

	private void startCheck() {
		Log.v("tag:", "message");
		Map map = getData();
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append('\n');

		String signJson = getSign();
		JSONObject json;
		try {
			if (signJson != null) {
				json = new JSONObject(signJson);
				if (json.get("sign") != null) {
					sb.append("<CpPay application=\"LunchPay.Req\">").append(
							'\n');
					sb.append("<env>").append(map.get("env")).append("</env>")
							.append('\n');
					sb.append("<appSysId>")
							.append((String) json.get("appSysId"))
							.append("</appSysId>").append('\n');
					sb.append("<cardNo>").append(map.get("cardNo"))
							.append("</cardNo>").append('\n');
					sb.append("<cerType>").append(map.get("cerType"))
							.append("</cerType>").append('\n');
					sb.append("<cerNo>").append(map.get("cerNo"))
							.append("</cerNo>").append('\n');
					sb.append("<cerName>").append(map.get("cerName"))
							.append("</cerName>").append('\n');
					sb.append("<cardMobile>").append(map.get("cardMobile"))
							.append("</cardMobile>").append('\n');
					sb.append("<sign>").append((String) json.get("sign"))
							.append("</sign>").append('\n');
					sb.append("</CpPay>");

					// 初始化手机POS环境
					Utils.setPackageName(MY_PKG);// MY_PKG是你项目的包名
					// 设置Intent指向Initialize.class
					Intent intent = new Intent(this, Initialize.class); // this为你当前的activity.this
					// 传入移动认证类型

					intent.putExtra(CPGlobaInfo.XML_TAG, sb.toString()); // xml
																			// 为返回的订单信息
																			// 拼接而成的xml
																			// String对象。
					this.startActivity(intent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startCheck2() {
		Log.v("tag:", "message");
		Map map = getData();
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append('\n');

		try {
				if (map.get("sign") != null) {
					sb.append("<CpPay application=\"LunchPay.Req\">").append(
							'\n');
					sb.append("<env>").append(map.get("env")).append("</env>")
							.append('\n');
					sb.append("<appSysId>")
							.append((String) map.get("appSysId"))
							.append("</appSysId>").append('\n');
					sb.append("<cardNo>").append(map.get("cardNo"))
							.append("</cardNo>").append('\n');
					sb.append("<cerType>").append(map.get("cerType"))
							.append("</cerType>").append('\n');
					sb.append("<cerNo>").append(map.get("cerNo"))
							.append("</cerNo>").append('\n');
					sb.append("<cerName>").append(map.get("cerName"))
							.append("</cerName>").append('\n');
					sb.append("<cardMobile>").append(map.get("cardMobile"))
							.append("</cardMobile>").append('\n');
					sb.append("<sign>").append((String) map.get("sign"))
							.append("</sign>").append('\n');
					sb.append("</CpPay>");

					// 初始化手机POS环境
					Utils.setPackageName(MY_PKG);// MY_PKG是你项目的包名
					// 设置Intent指向Initialize.class
					Intent intent = new Intent(this, Initialize.class); // this为你当前的activity.this
					// 传入移动认证类型

					intent.putExtra(CPGlobaInfo.XML_TAG, sb.toString()); // xml
																			// 为返回的订单信息
																			// 拼接而成的xml
																			// String对象。
					this.startActivity(intent);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 示例数据
	private Map getData() {
//		mapCPData.put("env", ENV);
//		mapCPData.put("cardNo", "6226620607792207");
//		mapCPData.put("cerType", "01");
//		mapCPData.put("cerNo", "231002198903302089");
//		mapCPData.put("cerName", "刘敏");
//		mapCPData.put("cardMobile", "18817331234");
		return mapCPData;
	}

	// 获取sign和appSysId时加密方法
	private String encrypt(String data) throws Exception {
		Map map = getData();
		String pubKey = "";
		if ("PRODUCT".equals((String) map.get("env"))) {
			// 生产
			pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqjckmcli52Z6Pog35cxeP2GYnosWxB6YjJiLr9Vp3VAfZSd9C3dgGuaMr6s1yvyeB8eQpkk87Wt1Mx4bUxUc72gYUy+t1hlDbb1YmkvPZIKGdT/cSYzMRKkGxRFoXCHXZDuKe1gyFLGBT+XCriO+VEBk2pIYgbZ+nF9wyDO+AShG4KSfRYfA6m8g3C7XwywEpnG8VW6H3jlToRDdmTdXWL3M1+TTZWMPJtaQfTBmoYbjE45XZIrNyFkHHvWjzFme4m+np05RxqxL8h9Qa24+uB/R/N/vYXTcGzZgHmv68kFcxz6rkKUs5GL4G/Mzroxi3N51uuDKKP+V2ILojDyedwIDAQAB";

		} else {
			// 測試
			pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDy3EnpwmiQXt5I3dx8E3QXTFbQwAc3HvD5E3HTfZv+Xw6hiQZWC8nA+J/qSGIzxLjEf9aJOkaHRzxSWoGEmy2y31RWAmfqfS2awynehOeSSYzs0vFGvLTqkn+EgbrqhJb1XUhQ1jZ2qZWi2VkvLIxcQvRaOwhEI+zQX9nPsYfjGQIDAQAB";
		}
		byte[] encryptByte = RSAUtils.encryptByPublicKey(data.getBytes(),
				pubKey);
		String reqdata = HexUtil.byte2HexStr(encryptByte);
		return reqdata;
	}

	// 获取sign和appSysId
	private String getSign() {
		// 測試地址
		String url = GET_SIGN_URL;
		Map map = getData();
		StringBuilder sb = new StringBuilder();
		sb.append("cardNo=").append(map.get("cardNo")).append('&');
		sb.append("cerType=").append(map.get("cerType")).append('&');
		sb.append("cerNo=").append(map.get("cerNo")).append('&');
		sb.append("cerName=").append(map.get("cerName")).append('&');
		sb.append("cardMobile=").append(map.get("cardMobile"));
		String reqdata = sb.toString();
		Log.v("reqdata before encryt", reqdata);
		try {
			reqdata = encrypt(reqdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sb2 = new StringBuilder();
		sb2.append(url).append("?");
		sb2.append("BANKCODE=").append(BANK_CODE).append("&");
		sb2.append("COINSTCODE=").append(COINST_CODE).append("&");
		sb2.append("reqdata=").append(reqdata);

		url = sb2.toString();
		String content = "";
		try {
			HttpClient httpClient = new HttpClientHelper().getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			content = inStream2String(response.getEntity().getContent());
			Log.v("request url:", url);
			Log.v("rspContent:", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	// inputStream转String
	private String inStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case Constants.GET_CP_DATA_SUCCESS:
				Map<String, String> cpData = (Map<String, String>) msg.obj;
				mapCPData.put("env", cpData.get("env").toString());
				mapCPData.put("cardNo", cpData.get("cardNo").toString());
				mapCPData.put("cerType", cpData.get("cerType").toString());
				mapCPData.put("cerNo", cpData.get("cerNo").toString());
				mapCPData.put("cerName", cpData.get("cerName").toString());
				mapCPData.put("cardMobile", cpData.get("cardMobile").toString());
				mapCPData.put("appSysId", cpData.get("appSysId").toString());
				mapCPData.put("sign", cpData.get("sign").toString());
				
				startCheck2();
			default:
			}
		}
	};
}

package net.lqm.main;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.CodeConvertUtils;
import net.lqm.util.HttpUtils;
import net.lqm.util.NameUtils;
import net.lqm.util.RandomCharUtil;

public class RegistMain {
	public static final String userId = "33084";
	public static void main(String[] args) {
//		String user = registerWeApp("14575788479", "0417");
//		System.out.println(user);
		
		String token = getToken();
		if (!"".equals(token)) {
			for (int i = 0; i < 5; i++) {
				new Thread() {
					public void run() {
						String phone = getPhoneNum(token);
						System.out.println("phone: " + phone);

						if (!"".equals(phone)) {
							try {
								Thread.sleep(55555);
								double d = Math.random();
								int time = (int) (d * 10);
								Thread.sleep(time * 888);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							String code = getCode(token, phone);
							if (!"".equals(code)) {
								String user = registerWeApp(phone, code);
								System.out.println(user);
							} else {
								System.out.println(phone + " :code is null!");
							}
						}
					}

				}.start();
			}
		} else {
			System.out.println("token is null!");
		}

	}

	private static String getToken() {
		try {
			String url = "http://api.jima123.com/api.php";
			String param = "act=login&username=qiqicarter&password=abc123&developer=67v8x8Huq00Oa59TCs4eDI3A2DeeBkAy";
			String res = HttpUtils.sendGet(url, param);
			Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);
			System.out.println(res);
			if("0".equals(map.get("errno"))){
				JSONArray datas = (JSONArray)JSON.parse(map.get("data")+"");
				JSONObject data = datas.getJSONObject(0);
				return data.getString("token");
			}
		} catch (Exception e) {
			System.out.println("获取token失败");
			return "";
		}
		return "";
	}

	private static String getPhoneNum(String token) {
		String url = "http://api.jima123.com/api.php";
		String param = "act=getmobile&userid="+userId+"&token="+token+"&taskid=6130&count=1";
		String res = HttpUtils.sendGet(url, param);
		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);
		if("0".equals(map.get("errno"))){
			JSONArray datas = (JSONArray)JSON.parse(map.get("data")+"");
			JSONObject data = datas.getJSONObject(0);
			return data.getString("mobile");
		}
		return "";
	}

	private static String getCode(String token, String phone) {
		String url = "http://api.jima123.com/api.php";
		String param = "act=getsms&userid="+userId+"&token="+token+"&taskid=6130&mobile=" + phone;
		String res = HttpUtils.sendGet(url, param);
		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);
		String sms = map.get("sms")+"";
		if (!"".equals(sms)) {
			String chineseSms = CodeConvertUtils.unicode2String(sms);
			int startIndex = chineseSms.indexOf("验证码：");
			String realCode = chineseSms.substring(startIndex + 4, startIndex + 8);
			return realCode;
		}
		return "";
	}

	private static String registerWeApp(String phone, String code) {
		String mac = RandomCharUtil.getRandomNumberChar(2) + ":" + RandomCharUtil.getRandomNumberChar(2) + ":"
				+ RandomCharUtil.getRandomNumberChar(2) + ":" + RandomCharUtil.getRandomNumberChar(2) + ":"
				+ RandomCharUtil.getRandomNumberChar(2) + ":" + RandomCharUtil.getRandomNumberChar(2);
//		String user = RandomCharUtil.getRandomLowerLetterChar(4) + RandomCharUtil.getRandomNumberChar(4);
		String user = NameUtils.getName().toLowerCase() + "_cici" ;

		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/register";
		String param = "{\"parameters\":{\"verification_code\":\"" + code + "\",\"mobile_phone\":\"" + phone
				+ "\",\"username\":\"" + user
				+ "\",\"device_token\":\"\",\"email\":\"\",\"device_type\":\"1\",\"imei\":\"86"
				+ System.currentTimeMillis()
				+ "\",\"mac\":\""+mac+"\",\"device_id\":\"WIFIMAC:"+mac+"\",\"password\":\"abc123\"}}";
		String res = HttpUtils.sendPost(url, param);

		 System.out.println("register return :" + res);
		if (res.indexOf("200") > 0) {
			return user;
		}
		return "";
	}

//	public static void main(String[] args) {
//		String res = "{\"errno\":\"0\",\"errmsg\":\"OK\",\"state\":\"\u5df2\u5b8c\u7ed3\",\"sms\":\"\u3010\u7533\u901a\u5fb7\u9ad8\u3011\u9a8c\u8bc1\u7801\uff1a0510 (10\u5206\u949f\u5185\u6709\u6548)\u3002\u5ba2\u670d\u5fae\u4fe1\uff1a2721670359\u3002\u5b98\u65b9\u5fae\u4fe1\u516c\u4f17\u53f7\uff1aSTDECAUX\u3002\"}";
//		int startIndex = res.indexOf("\u9a8c\u8bc1\u7801\uff1a");
//		String realCode = res.substring(startIndex + 4, startIndex + 8);
//		System.out.println(realCode);
//	}
}

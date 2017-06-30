package net.lqm.main;

import net.lqm.util.HttpUtils;

public class JieMaHK {

	public static String getToken() {
		String url = "http://api.jiema.hk/http.aspx";
		String param = "action=loginIn&uid=qiqicarter&pwd=abc123";
		String res = HttpUtils.sendGet(url, param);
		if (res.indexOf("qiqicarter") >= 0) {
			return res.split("\\|")[1];
		}
		return "";
	}

	public static String getPhoneNum(String token) {
		String url = "http://api.jiema.hk/http.aspx";
		String param = "action=getMobilenum&pid=18881&uid=qiqicarter&token=" + token + "&size=1";
		String res = HttpUtils.sendGet(url, param);
		if (res.indexOf(token) >= 0) {
			return res.split("\\|")[0];
		}
		return "";
	}

	public static String getCode(String token, String phone) {
		String url = "http://api.jiema.hk/http.aspx";
		String param = "action=getVcodeAndReleaseMobile&uid=qiqicarter&token=" + token + "&pid=18881&mobile=" + phone;
		String res = HttpUtils.sendGet(url, param);
		if (res.indexOf(phone) >= 0) {
			String code = res.split("\\|")[1];
			int startIndex = code.indexOf("验证码：");
			try {
				String realCode = code.substring(startIndex + 4, startIndex + 8);
				return realCode;
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}
}

package net.lqm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;
import net.lqm.util.ReadFileUtil;

public class DelMain {

	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		names = ReadFileUtil.readTxtFile("D:\\Documents\\we\\del\\del_michlie.txt",true);
		
		int times = 1;
		
		for (String s : names) {
			try {
				if(times%25==0) {
					System.out.println("防止被封，休息65分钟");
					Thread.sleep(65*60*1000);
				}
				
				String token = loginWeApp(s,s);
				Thread.sleep(3000);
				if (!"".equals(token)) {
					System.out.println(s);
					votePhoto(token);
					Thread.sleep(2200);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			times++;
		}

	}
	
	private static String loginWeApp(String user,String pass) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.3.0\",\"username\":\"" + user + "\",\"password\":\""+pass+"\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			return map.get("content").get("token").toString();
		} catch (Exception e) {
			System.out.println("获取token出错："+user);
		}
		return "";
	}
	
	private static String loginWeApp(String userAndpass) {
		String[] ss = userAndpass.split("-");
		String user = ss[0];
		String pass = ss[1];
		
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.3.0\",\"username\":\"" + user + "\",\"password\":\""+pass+"\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			return map.get("content").get("token").toString();
		} catch (Exception e) {
			System.out.println("获取token出错："+user);
		}
		return "";
	}
	
	private static void votePhoto(String token) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/favoritePicture?token=" + token;
		String param = "{\"parameters\":\"+iAfbVqXJqwfrJ4CL38ouYmRtwF1c99TWgmOIAMlQf5wdfrzI\\/lvxtB43yxw ffuG\"}";
		
//		String res1 = HttpUtils.sendPost(url, param_m);
//		System.out.println("m:"+res1);
		
		String res2 = HttpUtils.sendPost(url, param);
		if(res2.indexOf("200")>0) {
			System.out.println("success");
		}else
			System.out.println("michlie"+res2);
	}

}

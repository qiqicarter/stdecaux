package net.lqm.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;
import net.lqm.util.ReadFileUtil;

public class DelThreeMain {

	public static void main(String[] args) {
		List<String> m1 = new ArrayList<String>();
		m1 = ReadFileUtil.readTxtFile("/home/std/m1.txt",true);
//		List<String> m2 = new ArrayList<String>();
//		m2 = ReadFileUtil.readTxtFile("/home/std/m2.txt",true);
		List<String> m3 = new ArrayList<String>();
		m3 = ReadFileUtil.readTxtFile("/home/std/m3.txt",true);
		
		for (int i=1;i<771;i++) {
			try {
				if(i%28==0) {
					System.out.println("防止被封，休息64分钟");
					System.out.println(new Date());
					Thread.sleep(64*60*1000);
				}
				loginAndVote(m1.get(i-1),1);
//				loginAndVote(m2.get(i-1),2);
				try {
					loginAndVote(m3.get(i-1),3);
				} catch (Exception e) {
					System.out.println("m3 is finished");
				}
				
				Thread.sleep(2345);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loginAndVote(String s,int i) {
		try {
			String token = loginWeApp(s,s);
			
			if (!"".equals(token)) {
				System.out.println(s);
				votePhoto(token,i);
				Thread.sleep(1234);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	
	private static void votePhoto(String token,int i) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/favoritePicture?token=" + token;
		String param = null;
		switch (i) {
		case 1:
			param = "{\"parameters\":\"mWdTQXW4Ku+sUHsknIa3ltJB7tbTL\\/G354ISdPHhtSdXEndaxrNafRcxr4sF G0pH\"}";
			break;
		case 2:
			param = "{\"parameters\":\"mWdTQXW4Ku+sUHsknIa3loHHSfmWWz9uNwZY\\/6\\/nTCBXEndaxrNafRcxr4sF G0pH\"}";
			break;
		case 3:
			param = "{\"parameters\":\"mWdTQXW4Ku+sUHsknIa3lvGST+m3oXBef5MOlpK7f3lXEndaxrNafRcxr4sF G0pH\"}";
		default:
			break;
		}
		
		
		String res2 = HttpUtils.sendPost(url, param);
		if(res2.indexOf("200")>0) {
			System.out.println("m"+i+":"+"success");
		}else
			System.out.println("m"+i+":"+res2);
	}

}

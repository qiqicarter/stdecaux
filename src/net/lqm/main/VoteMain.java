package net.lqm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;
import net.lqm.util.ReadFileUtil;

public class VoteMain {

	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		// names.add("vpkfyo026");
		names = ReadFileUtil.readTxtFile("D:\\Documents\\we\\first\\luo.txt", true);

		String nowUsing = null;
		for (String s : names) {
			try {
				nowUsing = s;
//				String token = loginWeApp(s,s);
				String token = loginWeApp(s);
				Thread.sleep(1234);
				if (!"".equals(token)) {
					System.out.println(s);
					votePhoto(token, s);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				ReadFileUtil.writeTxtFile("D:\\Documents\\we\\error.txt", nowUsing);
				e.printStackTrace();
			}
		}

		// moreVote(ReadFileUtil.readTxtFile("D:\\Documents\\we\\xu.txt",true));
	}

	private static void moreVote(List<String> names) {
		String nowUsing = null;
		for (String s : names) {
			try {
				nowUsing = s;
				String token = loginWeApp(s, "abc123");
				Thread.sleep(3000);
				if (!"".equals(token)) {
					System.out.println(s);
					votePhoto(token, s);
					Thread.sleep(8800);
				}
			} catch (Exception e) {
				ReadFileUtil.writeTxtFile("D:\\Documents\\we\\error.txt", nowUsing);
				e.printStackTrace();
			}
		}
	}

	private static String loginWeApp(String user, String pass) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.4.0\",\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			return map.get("content").get("token").toString();
		} catch (Exception e) {
			System.out.println("获取token出错：" + user);
		}
		return "";
	}

	private static String loginWeApp(String userAndpass) {
		String[] ss = userAndpass.split("-");
		String user = ss[0];
		String pass = ss[1];

		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.4.0\",\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			return map.get("content").get("token").toString();
		} catch (Exception e) {
			ReadFileUtil.writeTxtFile("D:\\Documents\\we\\error.txt", user);
			System.out.println("获取token出错：" + user);
		}
		return "";
	}

	private static void votePhoto(String token, String user) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/favoritePicture?token=" + token;
		String param_luo = "{\"parameters\":\"+9RYRteOWgzyO6yWNobDHzY3oZN1zzV7btIBoMR6omGw+rx7okwKSxESXsqT cJmS\"}";// 罗

		String res_luo = HttpUtils.sendPost(url, param_luo);
		System.out.println("luo:" + res_luo);
		if (res_luo.indexOf("200") <= 0) {
			ReadFileUtil.writeTxtFile("D:\\Documents\\we\\error.txt", user);
		}
	}

}

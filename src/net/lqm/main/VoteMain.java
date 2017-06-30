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
//		names.add("vpkfyo026");
		names = ReadFileUtil.readTxtFile("D:\\Documents\\we\\sea_other.txt");

		for (String s : names) {
			try {
				String token = loginWeApp(s);
				Thread.sleep(3000);
				if (!"".equals(token)) {
					System.out.println(s);
					votePhoto(token);
					Thread.sleep(9000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static String loginWeApp(String user) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.3.0\",\"username\":\"" + user + "\",\"password\":\"abc123\"}}";

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
//		String param = "{\"parameters\":\"IOiDtsSnOE1qsW3wHOwDxZXY8Ulfbo20LNvP1urM65SvAdb0KarjU2wMPrDg +NZp\"}";//吴
		
		String param = "{\"parameters\":\"IOiDtsSnOE1qsW3wHOwDxfuxbuh1Abe+eoD797MN4Q6vAdb0KarjU2wMPrDg +NZp\"}";//罗
		String res = HttpUtils.sendPost(url, param);
		System.out.println(res);
	}
	
}

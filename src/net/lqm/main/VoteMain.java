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
		names = ReadFileUtil.readTxtFile("D:\\Documents\\we\\wu.txt",true);

		for (String s : names) {
			try {
				String token = loginWeApp(s);
				Thread.sleep(3000);
				if (!"".equals(token)) {
					System.out.println(s);
					votePhoto(token);
					Thread.sleep(100);
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
		String param_wu = "{\"parameters\":\"+iAfbVqXJqwfrJ4CL38oudsr1FyzeQpyCktD6isfS7lwdfrzI\\/lvxtB43yxw ffuG\"}";//吴
		String param_xu = "{\"parameters\":\"+iAfbVqXJqwfrJ4CL38ouRrvoG4khlVkiaba7GQIrzlwdfrzI\\/lvxtB43yxw ffuG\"}";  //许
//		String param_m = "{\"parameters\":\"3XnsRG6wzl+TQUTOaYECDvpVjSe6svvb96q+RJfuF1V1QLihBTm1lvm4DFxL +a0q\"}";  //michlie
//		String param_m2 = "{\"parameters\":\"3XnsRG6wzl+TQUTOaYECDppHkugomKI7KAATNmO1pxZ1QLihBTm1lvm4DFxL +a0q\"}";  //michlie2
		String param_luo = "{\"parameters\":\"+iAfbVqXJqwfrJ4CL38ouQqfnyfD31hkoxgiXtiz65JwdfrzI\\/lvxtB43yxw ffuG\"}";//罗
		
//		String res_luo = HttpUtils.sendPost(url, param_luo);
//		System.out.println("luo:"+res_luo);
		
//		String res_xu = HttpUtils.sendPost(url, param_xu);
//		System.out.println("xu:"+res_xu);
		
		String res_wu = HttpUtils.sendPost(url, param_wu);
		System.out.println("wu:"+res_wu);
	}
	
}

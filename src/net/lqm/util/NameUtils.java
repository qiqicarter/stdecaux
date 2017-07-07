package net.lqm.util;

import java.util.ArrayList;
import java.util.List;

public class NameUtils {
	public static String getName(){
		try {
			List<String> tmp = new ArrayList<String>();
			tmp = ReadFileUtil.readTxtFile("source\\names.txt",false);
			double d = Math.random();
			int time = (int) (d * 505);
			
			return tmp.get(time);
		} catch (Exception e) {
			return RandomCharUtil.getRandomLowerLetterChar(4);
		}
	}
	
	public static String getYear(){
		double d = Math.random();
		int time = (int) (d * 10);
		
		int year = 1+time;
				
		return year+"";
	}
}

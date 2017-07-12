package net.lqm.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.lqm.util.ReadFileUtil;

public class Test {

	public static void main(String[] args) {
		List<String> l1 = ReadFileUtil.readTxtFile("D:\\Documents\\we\\luo.txt", false);
		List<String> l2 = ReadFileUtil.readTxtFile("D:\\Documents\\we\\wu.txt", false);
		
		Set<String> set=new HashSet<String>();         
        set.addAll(l1);
        set.addAll(l2);
        
        System.out.println(l1.contains("bob2011"));
        System.out.println(set.size());
        
	}

}

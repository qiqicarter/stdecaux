package net.lqm.main;

import net.lqm.util.Base64Utils;

public class Test {

	public static void main(String[] args) {
		String s = "kkNLPz2wW9lkWvOyni3kg30teLV46PoABlZvJ7S3y6Fl00ofaxSjAzOvVTGwrZrq6C+MHSttAbs/2axZvR63Sw==";
		try {
			System.out.println(new String(Base64Utils.decodeBase64(s),"UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

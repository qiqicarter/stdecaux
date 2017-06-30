package net.lqm.util;

public class CodeConvertUtils {
	public static String unicode2String(String ori) {
		char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
 
        }
        return outBuffer.toString();
	}
	
	public static void main(String[] args) {
		String res = "{\"errno\":\"0\",\"errmsg\":\"OK\",\"state\":\"\u5df2\u5b8c\u7ed3\",\"sms\":\"\u3010\u7533\u901a\u5fb7\u9ad8\u3011\u9a8c\u8bc1\u7801\uff1a6161 (10\u5206\u949f\u5185\u6709\u6548)\u3002\u5ba2\u670d\u5fae\u4fe1\uff1a2721670359\u3002\u5b98\u65b9\u5fae\u4fe1\u516c\u4f17\u53f7\uff1aSTDECAUX\u3002\"}";
		System.out.println(unicode2String(res));
	}
}

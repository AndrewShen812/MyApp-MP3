package com.na517.lf.utils;

import android.content.Context;


/**
 * 字符串处理
 * 
 * @author genie
 * 
 */
public class StringUtils {

	/**
	 * @Description: 判断String是否为空,true代表空，false代表非空
	 * @param @param string
	 * @return boolean
	 * @throws
	 */
	public static boolean isEmpty(String string) {
		if (string == null) {
			return true;
		}
		else if (string.trim().equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	/**
	 * 转换字符串方法
	 * 
	 * @param url
	 * @return
	 */
	public static String toTransverseLine(String url) {
		String name1 = url.replaceAll("/", "_");
		String name2 = name1.replaceAll("\\.", "_");
		String name3 = name2.replaceAll("\\:", "_");
		return name3;
	}

	/**
	 * 返回两个字符串中间的内容
	 * 
	 * @param all
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getMiddleString(String all, String start, String end) {
		int beginIdx = all.indexOf(start) + start.length();
		int endIdx = all.indexOf(end);
		return all.substring(beginIdx, endIdx);
	}

	/**
	 * 判定输入汉字
	 * @param c
	 * @return
	 */
	public boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判定输入数字
	 * @param c
	 * @return
	 */
	public boolean isNumber(char c) {
		if ( c >= '0' && c <= '9' ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判定输入字母
	 * @param c
	 * @return
	 */
	public boolean isWorld(char c) {
		if (  ( c >= 'a' && c <= 'z') || ( c >= 'A'  &&  c <= 'Z') ) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * @Description: 验证姓名
	 * @param name 姓名名称
	 * @return int
	 * @throws
	 */
	public static int nameVerificationCode(String name) {
		String str;
		if ((StringUtils.isEmpty(name)) || (name.trim().length() == 0)) {
			return 1;
		}
		else if(name.trim().length() < 2) {
			return 2;
		}
		else if( name.trim().length() > 60 ) {
			return 3;
		}
		str = name.trim().replaceAll("／", "/");
		if (!str.matches("^[一-龥A-Za-z/\\s]+$")) {
			return 4;
		}
		else if (str.matches("^[一-龥]+\\s+[一-龥\\s]+$"))
			return 5;
		else if ((str.matches("^/.+")) || (str.matches("^.+/$")))
			return 6;
		else if (str.matches("[一-龥]+/+[一-龥]+[/一-龥]*"))
			return 7;
		else if ((str.matches("^[A-Za-z/]+$")) && (!str.matches("^[A-Za-z]+/[A-Za-z]+$")))
			return 8;
		else if (str.matches("^.*[A-Za-z/]+[一-龥\\s]+.*$"))
			return 9;
		return 0;
	}
	
	/**
	 * @Description: 验证姓名
	 * @param name 姓名名称
	 * @return int
	 * @throws
	 */
	public static int nameVerification(Context context, String name) {
		int code = nameVerificationCode(name);
//		int nRst = 0;
//		if( code == 0 ) {
//			return code;
//		}
//		else if(code == 1) {
//			nRst = R.string.book_add_passengers_name_error1;
//		}
//		else if(code == 2) {
//			nRst = R.string.book_add_passengers_name_error2;
//		}
//		else if(code == 3) {
//			nRst = R.string.book_add_passengers_name_error3;
//		}
//		else if(code == 4) {
//			nRst = R.string.book_add_passengers_name_error4;
//		}
//		else if(code == 5) {
//			nRst = R.string.book_add_passengers_name_error5;
//		}
//		else if(code == 6) {
//			nRst = R.string.book_add_passengers_name_error6;
//		}
//		else if(code == 7) {
//			nRst = R.string.book_add_passengers_name_error7;
//		}
//		else if(code == 8) {
//			nRst = R.string.book_add_passengers_name_error8;
//		}
//		else if(code == 9) {
//			nRst = R.string.book_add_passengers_name_error9;
//		}
//		ToastUtils.showMessage(context, nRst);
		return code;
	}
	
	/**
     * Capitalize the first letter
     * 
     * @param s
     *            model,manufacturer
     * @return Capitalize the first letter
     */
    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        }
        else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
    
	/**
	 * @Description: 验证码
	 * @param @param passworld 密码
	 * @return boolean true密码验证通过 false密码验证不通过
	 * @throws
	 */
	public static boolean pwdVerification(String passworld) {
		if (StringUtils.isEmpty(passworld) || StringUtils.isEmpty(passworld.trim())) {
			return false;
		}
		passworld = passworld.trim();
		if (passworld.length() < 6 || passworld.length() > 20) {
			return false;
		}
		String regex = "[0-9a-zA-Z]+$";
		return passworld.matches(regex);
	}

}

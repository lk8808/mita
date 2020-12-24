package com.tr.mita.utils;

import java.security.MessageDigest;

public class MD5Util {
	
	public static String md5Encode(String inStr) {
        MessageDigest md5 = null;
        StringBuffer hexValue = new StringBuffer();
        
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
        return hexValue.toString();
	}
	
	public static void main(String[] args) {
		//670b14728ad9902aecba32e22fa4f6bd
		System.out.println(md5Encode("000000"));
		
	}
	
	
}

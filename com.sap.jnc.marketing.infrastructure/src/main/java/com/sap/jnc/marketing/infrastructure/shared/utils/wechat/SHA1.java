package com.sap.jnc.marketing.infrastructure.shared.utils.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ying.dong on 16-1-7.
 */
public class SHA1 {

	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		return new String(tempArr);
	}

	public static String SHA1Encode(String source) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digest = md.digest(source.toString().getBytes());
		return byteToStr(digest);
	}
}

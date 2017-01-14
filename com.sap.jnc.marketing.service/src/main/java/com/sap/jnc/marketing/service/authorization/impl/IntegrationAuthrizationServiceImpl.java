package com.sap.jnc.marketing.service.authorization.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.service.authorization.IntegrationAuthorizationService;

@Service
public class IntegrationAuthrizationServiceImpl implements IntegrationAuthorizationService {
	@Override
	public boolean authorize(String authorizationCode) {

		List<String> dateList = getCurrentEncodeData();
		for (String date : dateList) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			}
			catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			byte[] byteList = md.digest((date + getToken()).getBytes());
			String resultString = byteToString(byteList).toUpperCase();
			if (resultString.equals(authorizationCode)) {
				return true;
			}
		}
		return false;
	}

	private List<String> getCurrentEncodeData() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date currentDate = calendar.getTime();

		calendar.add(Calendar.MINUTE, -1);
		Date lastMinuteDate = calendar.getTime();
		calendar.add(Calendar.MINUTE, 2);
		Date futureMinuteDate = calendar.getTime();

		List<String> arrray = new ArrayList<String>(3);
		arrray.add(sdf.format(currentDate));
		arrray.add(sdf.format(lastMinuteDate));
		arrray.add(sdf.format(futureMinuteDate));

		return arrray;
	}

	private String getToken() {
		return "45BD4C243D2611E6AC619E71128CAE77";
	}

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}
}

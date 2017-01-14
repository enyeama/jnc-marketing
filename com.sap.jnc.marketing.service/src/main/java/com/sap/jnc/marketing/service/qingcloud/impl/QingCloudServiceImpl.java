/*
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.qingcloud.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.service.qingcloud.QingCloudService;

@Service
public class QingCloudServiceImpl implements QingCloudService {

	public byte[] downloadFile(String path, String fileType) throws UnsupportedEncodingException, IOException, Exception {
		String gmt = getCurrentGMT();
		URL url = new URL(getUrl() + path);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("Authorization", getAuthValue("GET", path, fileType, gmt));
		httpConn.setRequestProperty("Content-Type", fileType);
		httpConn.setRequestProperty("Date", gmt);
		try {
			int responseCode = httpConn.getResponseCode();
			if (HttpURLConnection.HTTP_OK == responseCode) {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				BufferedInputStream in = null;
				in = new BufferedInputStream(httpConn.getInputStream());
				int buf_size = 1024;
				byte[] buffer = new byte[buf_size];
				int len = 0;
				while (-1 != (len = in.read(buffer, 0, buf_size))) {
					bos.write(buffer, 0, len);
				}

				requestStatusLog("PUT", httpConn.getHeaderFields());
				return bos.toByteArray();
			}
			else {
				// TODO throw exception
				requestStatusLog("PUT", httpConn.getHeaderFields());
				return null;
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			httpConn.disconnect();
		}

	}

	public boolean uploadFile(String path, String fileType, byte[] fileBytes) throws UnsupportedEncodingException, IOException, Exception {
		String gmt = getCurrentGMT();
		URL url = new URL(getUrl() + path);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setDoOutput(true);

		httpConn.setRequestMethod("PUT");
		httpConn.setRequestProperty("Authorization", getAuthValue("PUT", path, fileType, gmt));
		httpConn.setRequestProperty("Content-Type", fileType);
		httpConn.setRequestProperty("Date", gmt);
		// httpConn.setRequestProperty("Content-Length", Integer.toString(fileBytes.length));

		try {
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(fileBytes);
			outputStream.close();

			int responseCode = httpConn.getResponseCode();
			if (responseCode == 201) {
				requestStatusLog("PUT", httpConn.getHeaderFields());
				return true;
			}
			else {
				requestStatusLog("PUT", httpConn.getHeaderFields());
				return false;
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			httpConn.disconnect();
		}

	}

	public boolean deleteFile(String path) throws UnsupportedEncodingException, IOException, Exception {
		String gmt = getCurrentGMT();
		URL url = new URL(getUrl() + path);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("DELETE");
		httpConn.setRequestProperty("Authorization", getAuthValue("DELETE", path, "", gmt));
		httpConn.setRequestProperty("Date", gmt);
		try {
			int responseCode = httpConn.getResponseCode();

			if (responseCode == 204) {
				requestStatusLog("DELETE", httpConn.getHeaderFields());
				return true;
			}
			else {
				requestStatusLog("DELETE", httpConn.getHeaderFields());
				return false;
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			httpConn.disconnect();
		}

	}

	public boolean createFolder(String path) throws UnsupportedEncodingException, IOException, Exception {
		String gmt = getCurrentGMT();

		if (path.charAt(path.length() - 1) != '/') {
			path += "/";
		}

		URL url = new URL(getUrl() + path);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setDoOutput(true);
		httpConn.setRequestMethod("PUT");
		httpConn.setRequestProperty("Authorization", getAuthValue("PUT", path, "", gmt));
		httpConn.setRequestProperty("Date", gmt);
		httpConn.setRequestProperty("Content-Length", "0");

		try {
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write("".getBytes());
			outputStream.close();

			int responseCode = httpConn.getResponseCode();
			if (responseCode == 201) {
				requestStatusLog("PUT", httpConn.getHeaderFields());
				return true;
			}
			else {
				requestStatusLog("PUT", httpConn.getHeaderFields());
				return false;
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			httpConn.disconnect();
		}
	}

	private void requestStatusLog(String method, Map<String, List<String>> headers) {
		for (String key : headers.keySet()) {
			List<String> value = headers.get(key);
			// TODO System.out.print(key + ":");
			for (String v : value) {
				// TODO System.out.print(" " + v);
			}
			// TODO System.out.println("");
		}
	}

	private String generateSignature(String method, String path, String fileType, String gmt) throws UnsupportedEncodingException, Exception {
		String stringToSign = method + "\n\n" + fileType + "\n" + gmt + "\n" + path;
		byte[] encodeData = encodeHmacSHA256(stringToSign.getBytes("UTF-8"), getSecretAccessKey());
		String base64Code = encryptBASE64(encodeData);
		return base64Code;
	}

	private byte[] encodeHmacSHA256(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		byte[] digest = mac.doFinal(data);
		return digest;
	}

	private static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decodeBase64(key);
	}

	private String encryptBASE64(byte[] key) throws Exception {
		return Base64.encodeBase64String(key);
	}

	private String getCurrentGMT() {
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(cd.getTime());
	}

	private byte[] getSecretAccessKey() throws UnsupportedEncodingException {
		// TODO change to property file
		return "SZuYBrSVeNlOYBmk7P7mmLIhlbd2ycT20gd9Yay2".getBytes("utf-8");
	}

	private String getUrl() {
		// TODO change to property file
		return "http://pek3a.qingstor.com";
	}

	private String getScretId() {
		// TODO change to property file
		return "MBVAVBBHZQJZLLPLPISM";
	}

	private String getAuthValue(String method, String path, String fileType, String gmt) throws UnsupportedEncodingException, Exception {
		return "QS-HMAC-SHA256 " + getScretId() + ":" + generateSignature(method, path, fileType, gmt);
	}

}

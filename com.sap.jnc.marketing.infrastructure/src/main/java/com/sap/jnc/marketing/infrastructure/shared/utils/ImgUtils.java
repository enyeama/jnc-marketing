package com.sap.jnc.marketing.infrastructure.shared.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImgUtils {

	public static byte[] getImgData(String imgName, SystemConfig config) throws Exception {

		FileInputStream imgStream = null;
		byte data[] = null;
		String fileUploadPath = (new URI(config.getImgServer())).getPath();
		File file = new File(fileUploadPath + imgName);
		/*
		 * if (!file.exists()) { //TODO 默认显示图片、此处暂时占位 file = new File(new URL(fileUploadPath + Constants.PATH_IMG_NOT_FOUND).toURI()); }
		 */
		imgStream = new FileInputStream(file);
		int i = imgStream.available();
		data = new byte[i];
		imgStream.read(data);
		imgStream.close();
		return data;
	}

	/**
	 * 上传图片
	 * 
	 * @param file
	 * @return
	 */
	public static String imgUpload(MultipartFile file, SystemConfig config, String folder) {
		if (config != null && file != null && !file.isEmpty()) {

			String suffix = "." + file.getOriginalFilename().split("\\.")[1];
			String fileFormat = suffix.toUpperCase();
			// 校验图片格式是否正确
			if (".JPG.JPEG.TIFF.RAW.BMP.GIF.PNG".indexOf(fileFormat) < 0) {
				return null;
			}
			try {
				folder = folder == null ? "" : folder;
				URI driURI = new URI(config.getImgServer() + folder);
				String fileName = DigestUtils.md5Hex(file.getBytes()) + suffix;
				File path = new File(driURI.getPath());
				if (!path.isDirectory()) {
					path.mkdirs();
				}
				File saveFile = new File(driURI.getPath(), fileName);
				file.transferTo(saveFile);
				return ("/imgs/" + folder + "/" + fileName);
			}
			catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
		return null;
	}
}

/*
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.qingcloud;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface QingCloudService {
	/*
	 * Returns the byte stream when get the correct file from Qingyun server. You need to catch the exception by yourself.
	 * @param path the path format should be "/<bucket-name>/<object-name>". Please confirm that the it starts with '/', and not ends with '/'.
	 * @param fileType The file type is the http content type. For example, if you download the file is text, the file type shoule be "text/plain". if
	 * the file is jpeg, the file type should be image/jpeg
	 * @see file type list http://tool.oschina.net/commons
	 */
	public byte[] downloadFile(String path, String fileType) throws UnsupportedEncodingException, IOException, Exception;

	/*
	 * Returns true or false when upload the correct file to Qingyun server. You need to catch the exception by yourself.
	 * @param path the path format should be "/<bucket-name>/<object-name>".Please confirm that the it starts with '/', and not ends with '/'.
	 * @param fileType the file type is the http content type. For example, if you download the file is text, the file type shoule be "text/plain". if
	 * the file is jpeg, the file type should be image/jpeg
	 * @param fileBytes the file should be convert to byte array first.
	 * @see file type list http://tool.oschina.net/commons
	 */
	public boolean uploadFile(String path, String fileType, byte[] fileBytes) throws UnsupportedEncodingException, IOException, Exception;

	/*
	 * Returns true or false when delete the correct file from Qingyun server. You need to catch the exception by yourself.
	 * @param path the path format should be "/<bucket-name>/<object-name>".Please confirm that the it starts with '/', and not ends with '/'.
	 * @see file type list http://tool.oschina.net/commons
	 */
	public boolean deleteFile(String path) throws UnsupportedEncodingException, IOException, Exception;
}

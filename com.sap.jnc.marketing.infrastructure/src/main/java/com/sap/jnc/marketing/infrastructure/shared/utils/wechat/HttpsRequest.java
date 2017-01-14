package com.sap.jnc.marketing.infrastructure.shared.utils.wechat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Component
public class HttpsRequest {

	@Autowired
	private SystemConfig systemConfig;

	private static Logger LOGGER = LoggerFactory.getLogger(HttpsRequest.class);

	private int socketTimeout = 10000;

	private int connectTimeout = 30000;

	private RequestConfig requestConfig;

	private CloseableHttpClient httpClient;

	@PostConstruct
	public void init() {
		try (FileInputStream instream = new FileInputStream(new File(systemConfig.getCertLocalPath()))) {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(instream, systemConfig.getShopId().toCharArray()); // password
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, systemConfig.getShopId().toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory
				.getDefaultHostnameVerifier());
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		}
		catch (FileNotFoundException e) {
			LOGGER.debug("can not find https certification file");
		}
		catch (IOException e) {
		}
		catch (CertificateException e) {
		}
		catch (NoSuchAlgorithmException e) {
		}
		catch (UnrecoverableKeyException e) {
		}
		catch (KeyStoreException e) {
		}
		catch (KeyManagementException e) {
		}

		if (httpClient == null) { // normal http
			httpClient = HttpClients.custom().build();
			LOGGER.debug("use normal http client");
		}
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	}

	/**
	 * @param url
	 * @param xmlObj
	 * @return
	 */
	public String sendPost(String url, Object xmlObj) {
		String result = null;
		HttpPost httpPost = new HttpPost(url);

		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData.processAnnotations(xmlObj.getClass());
		String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
		LOGGER.debug("API，POST Query Params：");
		LOGGER.debug(postDataXML);
		StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		httpPost.setConfig(requestConfig);
		LOGGER.debug("executing request" + httpPost.getRequestLine());
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		}
		catch (ConnectionPoolTimeoutException e) {
			LOGGER.debug("http get throw ConnectionPoolTimeoutException(wait time out)");
		}
		catch (ConnectTimeoutException e) {
			LOGGER.debug("http get throw ConnectTimeoutException");
		}
		catch (SocketTimeoutException e) {
			LOGGER.debug("http get throw SocketTimeoutException");
		}
		catch (Exception e) {
			LOGGER.debug("http get throw Exception");
		}
		finally {
			httpPost.abort();
		}
		return result;
	}

	/**
	 * @param socketTimeout
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		resetRequestConfig();
	}

	/**
	 * @param connectTimeout
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		resetRequestConfig();
	}

	private void resetRequestConfig() {
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	}

	/**
	 * @param requestConfig
	 */
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	/**
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public String sendSimpleGet(URI uri) throws IOException {
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setConfig(requestConfig);
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
	}
}

/**
 * 
 */
package com.sap.jnc.marketing.service.qqmap;

/**
 * 腾讯地图服务
 * 
 * @author I323560
 */
public interface QQMapService {

	/**
	 * 调用腾讯地图 逆地址解析(坐标位置描述)<br>
	 * 返回JSON格式的字符串, 返回内容可参考：{@link}http://lbs.qq.com/webservice_v1/guide-gcoder.html{@link}<br>
	 * 可用下方式解析：<br>
	 * <br>
	 * {@literal}@Autowired<br>
	 * private QQMapService qqMapService;<br>
	 * <br>
	 * String locationStr = qqMapService.getLocation(latitudeStr, longitudeStr);<br>
	 * JSONObject jsonObj = JSON.parseObject(locationStr);<br>
	 * JSONObject result = (JSONObject) jsonObj.get(QQMapConstants.RESULT);<br>
	 * JSONObject formattedAddresses = (JSONObject) result.get(QQMapConstants.FORMATTED_ADDRESSES);<br>
	 * String recommend = formattedAddresses.getString(QQMapConstants.FORMATTED_ADDRESSES_RECOMMEND);<br>
	 * JSONObject addressComponent = (JSONObject) result.get(QQMapConstants.ADDRESS_COMPONENT);<br>
	 * String nation = addressComponent.getString(QQMapConstants.ADDRESS_COMPONENT_NATION);<br>
	 * String province = addressComponent.getString(QQMapConstants.ADDRESS_COMPONENT_DISTRICT);<br>
	 * String city = addressComponent.getString(QQMapConstants.ADDRESS_COMPONENT_CITY);<br>
	 * {@literal}
	 * <br>
	 * @param lat 纬度
	 * @param lng 经度
	 * @return 腾讯地图服务返回的地址JSON字符串
	 */
	public String getLocation(String lat, String lng);

}

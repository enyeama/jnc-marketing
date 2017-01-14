package com.sap.jnc.marketing.infrastructure.shared.utils.wechat;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class XMLParser {

	public static Map<String, Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = WechatUtils.getStringStream(xmlString);
		Document document = builder.parse(is);

		NodeList allNodes = document.getFirstChild().getChildNodes();
		Node node;
		Map<String, Object> map = new HashMap<>();
		int i = 0;
		while (i < allNodes.getLength()) {
			node = allNodes.item(i);
			if (node instanceof Element) {
				map.put(node.getNodeName(), node.getTextContent());
			}
			i++;
		}
		return map;

	}

	/**
	 * @param xmlString
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static <T> T getObjectFromXML(String xmlString, Class<T> clazz) throws IOException, SAXException, ParserConfigurationException,
		IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		T obj = clazz.newInstance();
		Map<String, Object> map = getMapFromXML(xmlString);
		Map<String, Object> fieldValueMap = new HashMap<String, Object>();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			XStreamAlias xStreamAliasAnnotation = field.getDeclaredAnnotation(XStreamAlias.class);
			if (null != xStreamAliasAnnotation && null != xStreamAliasAnnotation.value()) {
				String annotationValue = xStreamAliasAnnotation.value();
				if (map.containsKey(annotationValue)) {
					fieldValueMap.put(field.getName(), map.get(annotationValue));
				}
			}
		}

		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (fieldValueMap.containsKey(key)) {
				Object value = fieldValueMap.get(key);
				Method setter = property.getWriteMethod();
				setter.invoke(obj, value);
			}
		}

		return obj;
	}

}

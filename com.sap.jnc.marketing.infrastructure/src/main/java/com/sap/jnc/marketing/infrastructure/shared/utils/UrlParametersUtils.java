package com.sap.jnc.marketing.infrastructure.shared.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dy on 16/6/23.
 */
public class UrlParametersUtils {


    /**
     *
     * @param queryString
     * @return
     */
    public static Map<String, String> parseToMap(String queryString) {
        if (StringUtils.isBlank(queryString)) {
            return null;
        }
        List<NameValuePair> paramsList = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
        return paramsList.stream().collect(Collectors.toMap(NameValuePair :: getName, NameValuePair :: getValue));
    }

    /**
     *
     * @param queryString
     * @return
     */
    public static String findParamterByName(String queryString, String paramName) {
        return parseToMap(queryString).get(paramName);
    }

}

package com.sap.jnc.marketing.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class JsonResponseUtils {

	public static void writeJson(HttpServletResponse response, Object object) {

		ObjectMapper objectMapper = null;

		objectMapper = new ObjectMapper();
		String result = "";

		try {
			result = objectMapper.writeValueAsString(object);
		}
		catch (Exception e1) {

		}

		response.setContentType("text/text;charset=UTF-8");
		response.setHeader("Charset", "UTF-8");

		try {
			response.getWriter().write(result);
		}
		catch (IOException e) {

		}
	}
}

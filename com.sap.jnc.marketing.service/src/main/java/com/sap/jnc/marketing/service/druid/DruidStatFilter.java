/**
 * 
 */
package com.sap.jnc.marketing.service.druid;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @author Quansheng Liu I075496
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = {
	@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
})
public class DruidStatFilter extends WebStatFilter {

}

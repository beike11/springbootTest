package com.stevenw.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 获取配置文件的数据
 */
@Component
@PropertySource({"classpath:openfire.properties"})
public class ParamUtil {


	//服务器域名或IP
	public static String SERVERHOST;


	@Value("${openfire.server}")
	public  void setServerHost(String serverHost) {
		SERVERHOST = serverHost;
	}


}

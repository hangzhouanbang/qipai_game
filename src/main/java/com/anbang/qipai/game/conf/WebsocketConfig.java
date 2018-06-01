package com.anbang.qipai.game.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "websocket")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class WebsocketConfig {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

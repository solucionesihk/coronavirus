package com.coronavirus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "common")
public class ConfigProperties {

	private String jksDir;

	public String getJksDir() {
		return jksDir;
	}

	public void setJksDir(String jksDir) {
		this.jksDir = jksDir;
	}

}

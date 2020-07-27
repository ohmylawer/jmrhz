package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="maintenanceplan")
public class YMLConfig {
	
	public int getEncryption() {
		return encryption;
	}

	public void setEncryption(int encryption) {
		this.encryption = encryption;
	}

	private int encryption;
}

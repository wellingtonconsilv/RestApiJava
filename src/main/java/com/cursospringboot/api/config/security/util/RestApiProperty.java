package com.cursospringboot.api.config.security.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("restapi")
public class RestApiProperty {
	
	private String originPermitida = "http://localhost:4200";
	
	public String getOriginPermitida() {
		return originPermitida;
	}


	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}


	private final Seguranca seguranca = new Seguranca();
		
	public Seguranca getSeguranca() {
		return seguranca;
	}


	public static class Seguranca{
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}
	
}

package com.coronavirus.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Logger;

@Component
public class ConfigRestClient {

	protected static final int TIMEOUT = 30000;
	protected static final int READ_TIMEOUT = 60000;
	private final String filenameJKS;

	private RestTemplate restTemplate;

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		return getClientHttpRequestFactory(TIMEOUT, READ_TIMEOUT);
	}

	@SuppressWarnings("deprecation")
	protected ClientHttpRequestFactory getClientHttpRequestFactory(int timeout, int readtimeout) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		clientHttpRequestFactory.setReadTimeout(readtimeout);

		char[] keyFile = "Apis123".toCharArray();

		try {
			SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(keyStore(keyFile), keyFile)
					.loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
			clientHttpRequestFactory.setHttpClient(httpClient);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger("NoSuchAlgorithmException. Detalle: " + ex.getMessage());
		} catch (KeyStoreException ex) {
			Logger.getLogger("KeyStoreException. Detalle: " + ex.getMessage());
		} catch (KeyManagementException ex) {
			Logger.getLogger("KeyManagementException. Detalle: " + ex.getMessage());
		} catch (UnrecoverableKeyException e) {
			Logger.getLogger("UnrecoverableKeyException. Detalle: " + e.getMessage());
		} catch (Exception e) {
			Logger.getLogger("Exception. Detalle: " + e.getMessage());
		}

		return clientHttpRequestFactory;
	}

	private KeyStore keyStore(char[] password) throws KeyStoreException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		try (FileInputStream in = new FileInputStream(ClassLoader.getSystemResource(filenameJKS).getFile())) {
			keyStore.load(in, password);
		} catch (Exception ex) {
			Logger.getLogger(ex.getMessage());
		}
		return keyStore;
	}

	public ConfigRestClient(ConfigProperties configProp) {
		filenameJKS = configProp.getJksDir();
		restTemplate = new RestTemplate(getClientHttpRequestFactory());
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

}

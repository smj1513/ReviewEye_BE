package spring.changyong.search.config;

import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

@Component
public class HttpClientConfigImpl implements HttpClientConfigCallback {

	@Value("${spring.data.elasticsearch.username}")
	private String username;

	@Value("${spring.data.elasticsearch.password}")
	private String password;

	@Value("${spring.data.elasticsearch.ssl.trust-store}")
	private String trustStore;

	@Value("${spring.data.elasticsearch.ssl.trust-store-password}")
	private String trustStorePassword;

	@Override
	public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
		try {
			Resource resource = new ClassPathResource("cert_key.p12");
			KeyStore trustStore = KeyStore.getInstance("pkcs12");
			try (InputStream inputStream = resource.getInputStream()) {
				trustStore.load(inputStream, trustStorePassword.toCharArray());
			}

			SSLContextBuilder sslContextBuilder = SSLContexts.custom()
					.loadTrustMaterial(trustStore, null);
			SSLContext sslContext = sslContextBuilder.build();
			httpAsyncClientBuilder.setSSLContext(sslContext);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		return httpAsyncClientBuilder;
	}
}
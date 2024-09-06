package spring.changyong.search.config;

import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

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
			File trustLocationFile = new File(trustStore);
			SSLContextBuilder sslContextBuilder = SSLContexts.custom()
					//	.loadTrustMaterial((chain, authType) -> true); // 모든 인증서를 신뢰합니다.
					.loadTrustMaterial(trustLocationFile, trustStorePassword.toCharArray());
			SSLContext sslContext = sslContextBuilder.build();
			httpAsyncClientBuilder.setSSLContext(sslContext);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		return httpAsyncClientBuilder;
	}
}
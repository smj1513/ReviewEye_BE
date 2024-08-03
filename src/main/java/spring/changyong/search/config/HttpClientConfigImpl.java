package spring.changyong.search.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
public class HttpClientConfigImpl implements HttpClientConfigCallback {

	@Value("${spring.data.elasticsearch.username}")
	private String username;

	@Value("${spring.data.elasticsearch.ssl.trust-store-password}")
	private String password;

	@Value("${spring.data.elasticsearch.ssl.trust-store}")
	private String trustStore;

	@Override
	public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
		try {

			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(username, password);
			credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);
			httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

			File trustLocationFile = new File(trustStore);

			SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustLocationFile,
					password.toCharArray());
			SSLContext sslContext = sslContextBuilder.build();
			httpAsyncClientBuilder.setSSLContext(sslContext);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return httpAsyncClientBuilder;
	}
}

package spring.changyong.config;

import co.elastic.clients.elasticsearch.inference.ElasticsearchInferenceAsyncClient;
import co.elastic.clients.elasticsearch.inference.ElasticsearchInferenceClient;
import co.elastic.clients.elasticsearch.ingest.InferenceConfig;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@EnableElasticsearchRepositories(basePackages = "spring.changyong.search.domain.repository")
@Configuration
@RequiredArgsConstructor
public class ElasticSearchConfig extends ElasticsearchConfiguration {

//	private final HttpClientConfigImpl httpClientConfigCallback;

	@Value("${spring.data.elasticsearch.url}")
	private String url;
	@Value("${spring.data.elasticsearch.username}")
	private String username;
	@Value("${spring.data.elasticsearch.password}")
	private String password;

	@Bean
	public RestClient restClient() {
		ClientConfiguration clientConfiguration = clientConfiguration();
		return ElasticsearchClients.getRestClient(clientConfiguration);
	}

	@Bean
	public ElasticsearchTransport elasticsearchTransport(){
		return new RestClientTransport(restClient(), new JacksonJsonpMapper());
	}

	@Bean
	public ElasticsearchInferenceClient inferenceClient(){
		return new ElasticsearchInferenceClient(elasticsearchTransport());
	}

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
				.connectedTo(url)
				.usingSsl(disableSslVerification(), allHostsValid())
				.withBasicAuth(username, password)
				.withConnectTimeout(10000)
				.withSocketTimeout(10000)
				.build();
		/*
		* 	.withClientConfigurer(ElasticsearchClients
						.ElasticsearchRestClientConfigurationCallback
						.from(restClientBuilder ->
								restClientBuilder.setHttpClientConfigCallback(httpClientConfigCallback))
				)*/
	}
	public static SSLContext disableSslVerification() {
		try {
			// ============================================
			// trust manager 생성(인증서 체크 전부 안함)
			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}};

			// trust manager 설치
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			return sc;
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HostnameVerifier allHostsValid() {

		// ============================================
		// host name verifier 생성(호스트 네임 체크안함)
		HostnameVerifier allHostsValid = (hostname, session) -> true;

		// host name verifier 설치
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		return allHostsValid;

	}
}
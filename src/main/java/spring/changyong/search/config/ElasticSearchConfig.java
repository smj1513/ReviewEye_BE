package spring.changyong.search.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;

@EnableElasticsearchRepositories(basePackages = "spring.changyong.search.domain.repository")
@Configuration
@RequiredArgsConstructor
public class ElasticSearchConfig extends ElasticsearchConfiguration {

	@Value("${spring.data.elasticsearch.url}")
	private String url;
	@Value("${spring.data.elasticsearch.api-key}")
	private String apiKey;

	@Value("${spring.data.elasticsearch.username}")
	private String username;

	@Value("${spring.data.elasticsearch.password}")
	private String password;

	private final HttpClientConfigImpl httpClientConfigCallback;
	@Override
	public ClientConfiguration clientConfiguration() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "ApiKey " + apiKey);
		return ClientConfiguration.builder()
				.connectedTo(url)
				.usingSsl()
				.withHeaders(() -> headers)
				.withBasicAuth(username,password)
				.withClientConfigurer(ElasticsearchClients
						.ElasticsearchRestClientConfigurationCallback
						.from(restClientBuilder ->
								restClientBuilder.setHttpClientConfigCallback(httpClientConfigCallback))
				).build();

	}


//	@Value("${spring.data.elasticsearch.username}")
//	private String username;
//	@Value("${spring.data.elasticsearch.password}")
//	private String password;

//	@Bean
//	public RestClient restClient() {
//		return RestClient.builder(HttpHost.create(url))
//				.setDefaultHeaders(new Header[]{
//						new BasicHeader("Authorization", "ApiKey " + apiKey)
//				})
//				.build();
//	}

//
//	@Bean
//	public ElasticsearchTransport elasticsearchTransport() {
//		return new RestClientTransport(restClient(), new JacksonJsonpMapper());
//	}
//
//	@Bean
//	public ElasticsearchClient elasticsearchClient() {
//		return new ElasticsearchClient(elasticsearchTransport());
//	}
//
//	@Bean
//	public ElasticsearchOperations elasticsearchOperations() {
//		return new ElasticsearchTemplate(elasticsearchClient());
//	}

}
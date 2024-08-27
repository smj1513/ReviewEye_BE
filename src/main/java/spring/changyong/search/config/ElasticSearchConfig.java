package spring.changyong.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

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
	private final HttpClientConfigCallback httpClientConfigCallback;
	@Bean
	public RestClient restClient() {
		return RestClient.builder(HttpHost.create(url))
				.setHttpClientConfigCallback(httpClientConfigCallback)
				.setDefaultHeaders(new Header[]{
						new BasicHeader("Authorization", "ApiKey " + apiKey)
				})
				.build();
	}
	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
				.connectedTo(url)
				.usingSsl()
				.withBasicAuth(username, password)
				.withClientConfigurer(ElasticsearchClients
						.ElasticsearchRestClientConfigurationCallback
						.from(restClientBuilder ->
						restClientBuilder.setHttpClientConfigCallback(httpClientConfigCallback)
				))
				.build();
	}


	@Bean
	public ElasticsearchTransport elasticsearchTransport() {
		return new RestClientTransport(restClient(), new JacksonJsonpMapper());
	}

	@Bean
	public ElasticsearchClient elasticsearchClient() {
		return new ElasticsearchClient(elasticsearchTransport());
	}

	@Bean
	public ElasticsearchOperations elasticsearchOperations() {
		return new ElasticsearchTemplate(elasticsearchClient());
	}

}
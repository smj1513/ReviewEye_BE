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
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;

@EnableElasticsearchRepositories(basePackages = "spring.changyong.search.domain.repository")
@Configuration
@RequiredArgsConstructor
public class ElasticSearchConfig {
	@Value("${spring.data.elasticsearch.url}")
	private String url;
	@Value("${spring.data.elasticsearch.api-key}")
	private String apiKey;

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

	@Bean
	public ElasticsearchTransport elasticsearchTransport(){
		return new RestClientTransport(restClient(), new JacksonJsonpMapper());
	}

	@Bean
	public ElasticsearchClient elasticsearchClient() {
		return new ElasticsearchClient(elasticsearchTransport());
	}
//
//	@Bean
//	public ElasticsearchOperations elasticsearchOperations() {
//		return new ElasticsearchTemplate(elasticsearchClient());
//	}
}

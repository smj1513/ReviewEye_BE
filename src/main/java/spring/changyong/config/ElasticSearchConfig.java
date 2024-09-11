package spring.changyong.config;

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

	private final HttpClientConfigImpl httpClientConfigCallback;
	@Value("${spring.data.elasticsearch.url}")
	private String url;
	@Value("${spring.data.elasticsearch.username}")
	private String username;
	@Value("${spring.data.elasticsearch.password}")
	private String password;

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
				.connectedTo(url)
				.usingSsl()
				.withBasicAuth(username, password)
				.withClientConfigurer(ElasticsearchClients
						.ElasticsearchRestClientConfigurationCallback
						.from(restClientBuilder ->
								restClientBuilder.setHttpClientConfigCallback(httpClientConfigCallback))
				).build();

	}
}
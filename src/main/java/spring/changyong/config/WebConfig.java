package spring.changyong.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class WebConfig {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}

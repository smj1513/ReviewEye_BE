package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

	private final RestTemplate restTemplate;
	private final ProductSearchRepository productSearchRepository;

	@Value("${crawling.base-url}")
	private String crawlingUrl;

	@Scheduled(cron = "0 0 3 * * ?")
	public void updatePrice() {
		Iterable<ProductDocument> all = productSearchRepository.findAll();

		all.forEach(doc->{

		});
	}
}

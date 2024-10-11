package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

	private final ProductSearchRepository productSearchRepository;

	private final CrawlingService crawlingService;


	@Scheduled(cron = "0 0 3 * * ?")
	public void updatePrice() throws Exception{
		Iterable<ProductDocument> all = productSearchRepository.findAll();
		all.forEach(doc->{
			crawlingService.updateProduct(doc);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		productSearchRepository.updateDocuments(all);
	}
}

package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import spring.changyong.search.domain.repository.ProductSearchRepository;

@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

	private final RestTemplate restTemplate;
	private final ProductSearchRepository productSearchRepository;

	@Scheduled(cron = "0 0 3 * * ?")
	public void updatePrice() {

	}
}

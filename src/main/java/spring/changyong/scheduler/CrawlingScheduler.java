package spring.changyong.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.changyong.scheduler.dto.PriceResponse;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Component
@Log4j2
@RequiredArgsConstructor
public class CrawlingScheduler {

	private final ProductSearchRepository productSearchRepository;

	private final CrawlingService crawlingService;


	@Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Seoul")
	public void updatePrice() throws Exception {
		int pageSize = 100;
		int pageNumber = 0;
		Page<ProductDocument> page = null;
		Map<String, Document> update = new HashMap<>();
		do {
			PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
			page = productSearchRepository.searchWithSource(new String[]{"productId"}, pageRequest);

			log.info("=========== Page {} Document Load Complete ==========", pageNumber + 1);
			log.info("========= Page {} Document sample : {}", pageNumber + 1, page.getContent().get(0).toString());
			for (ProductDocument doc : page.getContent()) {
				PriceResponse priceResponse = crawlingService.getPrice(doc.getProductId());
				Document document = Document.create();
				document.put("price", priceResponse.getPrice() == null ? 0 : priceResponse.getPrice());
				document.put("discount_price", priceResponse.getDiscountPrice() == null ? 0 : priceResponse.getDiscountPrice());
				update.put(doc.getId().toString(), document);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					log.error(e.getMessage());
				}
			}
			productSearchRepository.updateBulkDocuments(update);
			pageNumber++;

		} while (page.hasNext());

		log.info("=========== All Pages Processing Complete ==========");
	}

	@PostConstruct
	public void init() {
		log.info("===============================");
		log.info("CrawlingScheduler initialized");
		log.info("===============================");
	}
}

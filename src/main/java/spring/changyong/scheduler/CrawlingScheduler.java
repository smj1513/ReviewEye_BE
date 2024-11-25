package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Log4j2
@RequiredArgsConstructor
public class CrawlingScheduler {

	private final ProductSearchRepository productSearchRepository;

	private final CrawlingService crawlingService;
	@Scheduled(cron = "0 0 3 * * ?")
	public void updatePrice() throws Exception{
		int pageSize = 10;
		int pageNumber = 0;
		Page<ProductDocument> page;

		do {
			PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
			page = productSearchRepository.findAll(pageRequest);

			log.info("=========== Page {} Document Load Complete ==========", pageNumber + 1);

			for (ProductDocument doc : page.getContent()) {
				crawlingService.updateProduct(doc);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}

			productSearchRepository.updateDocuments(page.getContent());
			pageNumber++;

		} while (page.hasNext());

		log.info("=========== All Pages Processing Complete ==========");
	}
}

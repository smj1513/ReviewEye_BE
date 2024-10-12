package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
		Iterable<ProductDocument> all = productSearchRepository.findAll();

		for (ProductDocument doc : all) {
			crawlingService.updateProduct(doc);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				throw new BusinessLogicException(ErrorCode.INTENAL_SERVER_ERROR,e.getMessage());
			}
		}
		productSearchRepository.updateDocuments(all);
	}
}

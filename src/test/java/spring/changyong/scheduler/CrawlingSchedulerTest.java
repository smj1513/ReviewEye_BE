package spring.changyong.scheduler;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class CrawlingSchedulerTest {
	@Autowired
	ProductSearchRepository productSearchRepository;

	@Autowired
	CrawlingService crawlingService;

	@Test
	public void updatePrice() throws Exception{


		int pageSize = 100;
		int pageNumber = 0;
		Page<ProductDocument> page;
		do {
			PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
			page = productSearchRepository.findAll(pageRequest);

			System.out.println("=========== Page " + (pageNumber + 1) + " Document Load Complete ==========");

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
	}

}
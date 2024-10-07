package spring.changyong;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.model.ReviewDocument;

@SpringBootTest
@Disabled
public class ReIndexServiceTest {
	@Autowired
	ElasticsearchOperations operations;

	@Test
	public void reIndex() {
		System.out.println(" ============ Product ReIndexing 시작 ============ ");
		operations.indexOps(ProductDocument.class).delete();
		operations.indexOps(ProductDocument.class).create();
		operations.indexOps(ProductDocument.class).putMapping();
		operations.indexOps(ProductDocument.class).refresh();
		System.out.println(" ============ Product ReIndexing 완료 ============ ");

		System.out.println(" ============ Review ReIndexing 시작 ============ ");
		operations.indexOps(ReviewDocument.class).delete();
		operations.indexOps(ReviewDocument.class).create();
		operations.indexOps(ReviewDocument.class).putMapping();
		operations.indexOps(ReviewDocument.class).refresh();
		System.out.println(" ============ Review ReIndexing 완료 ============ ");

	}
}

package spring.changyong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.changyong.product.domain.model.Product;
import spring.changyong.product.domain.repository.ProductRepository;
import spring.changyong.review.domain.repository.ReviewRepository;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

import java.util.List;

@SpringBootTest
public class DataDump {

	@Autowired
	ProductSearchRepository productSearchRepository;

	@Autowired
	ReviewSearchRepository reviewSearchRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ReviewRepository reviewRepository;


	@Test
	@Transactional
	void dump() {
		List<Product> all = productRepository.findAll();

		for (Product product : all) {
			productSearchRepository.save(ProductDocument.from(product));
		}
	}
}

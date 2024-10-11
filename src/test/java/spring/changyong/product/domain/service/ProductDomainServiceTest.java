package spring.changyong.product.domain.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
@Profile("dev")
class ProductDomainServiceTest {

	@Autowired
	private ReviewSearchRepository reviewSearchRepository;

	@Autowired
	private ProductDomainService productDomainService;

	@Test
	void getProductEvaluation() {
		List<ReviewDocument> allByProductId = reviewSearchRepository.findAllByProductId("A000000199562");
		Map<String, Map<String, Integer>> productEvaluation = productDomainService.getProductEvaluation(allByProductId);

		productEvaluation.forEach((t,v)->{
			System.out.println(t);
			v.forEach((t1,v1)->{
				System.out.println(t1 + " : " + v1);
			});
		});
	}

}
package spring.changyong.review.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.changyong.review.api.response.ReviewResponse;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReviewAppService {
	private final ReviewSearchRepository reviewSearchRepository;
	private final ProductSearchRepository productSearchRepository;

	@Transactional(readOnly = true)
	public Page<ReviewResponse.Result> getProductReview(String id, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("date")));
		Page<ReviewDocument> result = reviewSearchRepository.findAllByProductId(id, pageRequest);
		result.forEach(reviewDocument -> log.info("reviewDocument: {}", reviewDocument));
		return result.map(ReviewResponse.Result::from);
	}
}

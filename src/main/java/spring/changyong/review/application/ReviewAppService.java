package spring.changyong.review.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.changyong.review.api.response.ReviewResponse;
import spring.changyong.review.domain.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewAppService {
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public Slice<ReviewResponse.Result> getProductReview(Integer id, int page, int size) {
		return reviewRepository.findByProductId(id, PageRequest.of(page,size))
				.map(ReviewResponse.Result::from);
	}
}

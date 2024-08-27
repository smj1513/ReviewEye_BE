package spring.changyong.review.application;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spring.changyong.review.api.response.ReviewResponse;

@Service
public class ReviewAppService {
	public Page<ReviewResponse.Result> getProductReview(Integer id, int page, int size) {
		return null;
	}
}

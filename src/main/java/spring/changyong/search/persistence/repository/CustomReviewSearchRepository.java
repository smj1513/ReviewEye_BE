package spring.changyong.search.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.SearchHits;
import spring.changyong.review.domain.model.Review;
import spring.changyong.search.domain.model.ReviewDocument;

public interface CustomReviewSearchRepository {
	SearchHits<ReviewDocument> searchByProductId(String id, String keyword, Pageable pageable);
}

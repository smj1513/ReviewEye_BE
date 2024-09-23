package spring.changyong.search.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;
import spring.changyong.review.domain.model.Review;

@Repository
@RequiredArgsConstructor
public class ReviewSearchRepositoryImpl implements CustomReviewSearchRepository {
	private final ElasticsearchOperations elasticsearchOperations;


}

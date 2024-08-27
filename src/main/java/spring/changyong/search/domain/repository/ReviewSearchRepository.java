package spring.changyong.search.domain.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.changyong.review.domain.model.Review;
import spring.changyong.search.domain.model.ReviewDocument;

public interface ReviewSearchRepository extends ElasticsearchRepository<ReviewDocument, Long> {

}

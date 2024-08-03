package spring.changyong.search.domain.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.changyong.review.domain.Review;

public interface ReviewSearchRepository extends ElasticsearchRepository<Review, Long> {

}

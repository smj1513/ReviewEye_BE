package spring.changyong.search.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.persistence.repository.CustomReviewSearchRepository;

import java.util.List;

public interface ReviewSearchRepository extends ElasticsearchRepository<ReviewDocument, Long>, CustomReviewSearchRepository {
	Slice<ReviewDocument> findAllByProductId(String productId, Pageable pageable);
	List<ReviewDocument> findAllByProductId(String productId);
}

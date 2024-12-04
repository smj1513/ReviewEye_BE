package spring.changyong.search.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.enums.OrderBy;
import spring.changyong.search.enums.ResultFilter;
import spring.changyong.search.enums.SortOption;

public interface CustomReviewSearchRepository {
	SearchHits<ReviewDocument> searchByProductId(String id, String keyword, Pageable pageable, ResultFilter filter, SortOption sortOption, OrderBy orderBy);
	Page<ReviewDocument> findAllByProductIdWithFilter(String id, Pageable pageable, ResultFilter filter, SortOption sortOption, OrderBy orderBy);
}

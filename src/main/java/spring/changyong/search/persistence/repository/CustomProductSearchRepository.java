package spring.changyong.search.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import spring.changyong.search.domain.model.ProductDocument;

public interface CustomProductSearchRepository {
	SearchHits<ProductDocument> searchByName(String name, Pageable pageable);
	SearchHits<ProductDocument> searchByTag(String keyword, Pageable pageable);
}

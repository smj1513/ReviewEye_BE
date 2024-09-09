package spring.changyong.search.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import spring.changyong.search.domain.model.ProductDocument;

public interface CustomProductSearchRepository {
	Page<ProductDocument> searchByName(String name, Pageable pageable);
}

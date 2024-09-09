package spring.changyong.search.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.persistence.repository.CustomProductSearchRepository;

public interface ProductSearchRepository extends CustomProductSearchRepository, ElasticsearchRepository<ProductDocument, Long> {
	Page<ProductDocument> searchByName(String name, Pageable pageable);
}

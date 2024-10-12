package spring.changyong.search.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.persistence.repository.CustomProductSearchRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSearchRepository extends CustomProductSearchRepository, ElasticsearchRepository<ProductDocument, Long> {
	Optional<ProductDocument> findByProductId(String productId);
	Page<ProductDocument> findByNameStartingWith(String prefix, Pageable pageable);
}

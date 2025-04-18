package spring.changyong.search.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.annotations.SourceFilters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.persistence.repository.CustomProductSearchRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductSearchRepository extends CustomProductSearchRepository, ElasticsearchRepository<ProductDocument, Long> {
	Optional<ProductDocument> findByProductId(String productId);
	Page<ProductDocument> findByTitleStartingWith(String prefix, Pageable pageable);
}

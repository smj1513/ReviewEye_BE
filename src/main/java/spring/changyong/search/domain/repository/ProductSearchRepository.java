package spring.changyong.search.domain.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.changyong.product.domain.model.Product;
import spring.changyong.search.domain.model.ProductDocument;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {
}

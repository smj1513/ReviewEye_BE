package spring.changyong.search.domain.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import spring.changyong.product.domain.Product;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
}

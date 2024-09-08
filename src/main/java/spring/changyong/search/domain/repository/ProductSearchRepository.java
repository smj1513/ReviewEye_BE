package spring.changyong.search.domain.repository;

import co.elastic.clients.elasticsearch._types.SlicedScroll;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import spring.changyong.product.domain.model.Product;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.persistence.repository.CustomProductSearchRepository;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long>, CustomProductSearchRepository {


}

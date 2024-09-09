package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Repository;
import spring.changyong.search.domain.model.ProductDocument;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements CustomProductSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public Page<ProductDocument> searchByName(String name, Pageable pageable) {
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
		log.info("keyword : {}", name);
		log.info("============================================== query build ===========================================");
		NativeQuery query = nativeQueryBuilder.withQuery(
				QueryBuilders
						.match()
						.fuzziness("AUTO")
						.field("name")
						.query(name)
						.build()
						._toQuery()
		).withPageable(pageable).build();
		log.info("query: {}", query.getQuery());

		SearchHits<ProductDocument> result = elasticsearchOperations
				.search(query, ProductDocument.class);
		SearchPage<ProductDocument> searchHits = SearchHitSupport.searchPageFor(result, pageable);
		return (Page<ProductDocument>) SearchHitSupport.unwrapSearchHits(searchHits);
	}
}

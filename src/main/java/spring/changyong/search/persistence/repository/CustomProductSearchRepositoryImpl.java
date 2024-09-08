package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQuery;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQueryBuilder;
import org.springframework.stereotype.Repository;
import spring.changyong.search.domain.model.ProductDocument;

import java.lang.annotation.Native;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomProductSearchRepositoryImpl implements CustomProductSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public SearchPage<ProductDocument> findByName(String name, Pageable pageable) {
		SearchTemplateQueryBuilder builder = new SearchTemplateQueryBuilder();
		builder.withParams(Map.of("name", name));
		SearchTemplateQuery query = builder.build();

		SearchHits<ProductDocument> result = elasticsearchOperations.search(query, ProductDocument.class);

		return SearchHitSupport.searchPageFor(result, pageable);
	}
}

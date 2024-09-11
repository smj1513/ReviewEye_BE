package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.search.SuggestFuzziness;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.stereotype.Repository;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.domain.model.ProductDocument;

import java.util.List;
import java.util.stream.Collectors;

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
		nativeQueryBuilder.withQuery(
				QueryBuilders
						.match()
						.field("name")
						.query(name)
						.analyzer("nori_analyzer") //노리 형태소 분석기 사용
						.fuzziness("2")
						.boost(2F)
						.autoGenerateSynonymsPhraseQuery(true)
						.build()
						._toQuery()
		).withSort(Sort.by(Sort.Order.desc("_score")));
		nativeQueryBuilder.withPageable(pageable);
		NativeQuery query = nativeQueryBuilder.build();
		log.info("query: {}", query.getQuery());

		SearchHits<ProductDocument> result = elasticsearchOperations
				.search(query, ProductDocument.class);


		SearchPage<ProductDocument> searchHits = SearchHitSupport.searchPageFor(result, pageable);

		List<SearchResponse.Product> list = searchHits.getSearchHits().stream().map(SearchResponse.Product::from).toList();

		searchHits.getSearchHits()
				.getSearchHits()
				.forEach(searchHit -> log.info("searchHit: {} \n, score:{}", searchHit.getContent().getName(), searchHit.getScore()));


		return (Page<ProductDocument>) SearchHitSupport.unwrapSearchHits(searchHits);
	}
}

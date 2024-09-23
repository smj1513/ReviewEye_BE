package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;
import spring.changyong.search.SearchProperties;
import spring.changyong.search.domain.model.ProductDocument;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements CustomProductSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public SearchHits<ProductDocument> searchByName(String nameKeyword, Pageable pageable) {
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();

		// nameKeyword를 공백으로 나누어 키워드 배열 생성
		String[] keywords = nameKeyword.split(" "); // 공백 기준으로 분리

		BoolQuery.Builder boolQuery = QueryBuilders.bool();

		// 각 키워드를 포함하는 should 쿼리 추가
		for (String keyword : keywords) {
			boolQuery.should(QueryBuilders
					.match()
					.field("name")
					.query(keyword) // 각 키워드로 검색
					.analyzer(SearchProperties.NORI_ANALYZER)
					.fuzziness(SearchProperties.FUZZINESS)
					.build()
					._toQuery())
					.should(QueryBuilders
							.wildcard()
							.field("name") // 'name' 필드에 대해 매칭
							.value("*" + keyword + "*") // nameKeyword로 매칭
							.build()
							._toQuery())
			;
		}

		Query query = boolQuery
				.should(QueryBuilders
						.wildcard()
						.field("name") // 'name' 필드에 대해 매칭
						.value("*" + nameKeyword + "*") // nameKeyword로 매칭
						.boost(2.0F)
						.build()
						._toQuery())
				.build()
				._toQuery();
		nativeQueryBuilder.withQuery(query)
				.withSort(Sort.by(Sort.Order.desc(SearchProperties.SCORE)));
		nativeQueryBuilder.withPageable(pageable);
		NativeQuery nativeQuery = nativeQueryBuilder.build();

		SearchHits<ProductDocument> result = elasticsearchOperations.search(nativeQuery, ProductDocument.class);

		result.forEach(searchHit -> log.info("searchHit: {} \n, score:{}", searchHit.getContent().getName(), searchHit.getScore()));
		return result;
	}
}

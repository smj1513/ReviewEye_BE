package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.utils.SearchUtils;
import spring.changyong.search.utils.builder.ProductSearchQueryBuilder;
import spring.changyong.search.utils.builder.TagQueryBuilder;
import spring.changyong.timer.ExecutionTimeHolder;
import spring.changyong.timer.aspect.ExeTimer;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements CustomProductSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;
	private final SearchUtils searchUtils;

	@Override
	public SearchHits<ProductDocument> searchByName(String nameKeyword, Pageable pageable) {
		ProductSearchQueryBuilder builder = new ProductSearchQueryBuilder(nameKeyword);
		NativeQuery query = builder.addMatchPhraseQuery()
				.addMatchQuery()
				.addMultiMatchQuery()
				.addTermQuery()
				.build(pageable);

		SearchHits<ProductDocument> result = searchUtils.searchWithTimer(query, ProductDocument.class);

		result.forEach(searchHit -> log.info("searchHit: {} \n, score:{}", searchHit.getContent().getName(), searchHit.getScore()));
		return result;
	}

	@Override
	public SearchHits<ProductDocument> searchByTag(String keyword, Pageable pageable) {
		TagQueryBuilder builder = new TagQueryBuilder(keyword);
		NativeQuery nativeQuery = builder.addTagNestedQuery()
				.addTagMatchQuery()
				.build(pageable);

		SearchHits<ProductDocument> result = searchUtils.searchWithTimer(nativeQuery, ProductDocument.class);

		result.forEach(searchHit -> log.info("searchHit: {} \n, score:{}, keyword:{}", searchHit.getContent().getName(), searchHit.getScore(), searchHit.getContent().getPositiveTags().toString()));
		return result;
	}

	@Override
	public void updateDocument(ProductDocument productDocument) {
		elasticsearchOperations.update(productDocument);
	}


}

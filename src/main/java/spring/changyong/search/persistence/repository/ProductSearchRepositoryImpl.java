package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.search.Suggester;
import co.elastic.clients.elasticsearch.inference.*;
import co.elastic.clients.elasticsearch.xpack.usage.MlInferenceIngestProcessor;
import co.elastic.clients.elasticsearch.xpack.usage.MlInferenceTrainedModels;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;
import spring.changyong.config.ElasticSearchConfig;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.service.InferenceService;
import spring.changyong.search.utils.SearchUtils;
import spring.changyong.search.utils.builder.ProductSearchQueryBuilder;
import spring.changyong.search.utils.builder.TagQueryBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements CustomProductSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;
	private final ElasticsearchClient client;
	private final SearchUtils searchUtils;
	private final InferenceService inferenceService;

	@Override
	public SearchHits<ProductDocument> searchByName(String nameKeyword, Pageable pageable) {
		ProductSearchQueryBuilder builder = new ProductSearchQueryBuilder(nameKeyword);
		NativeQuery query = builder.addMatchPhraseQuery()
				.addMatchQuery()
				.addMultiMatchQuery()
				.addTermQuery()
				.build(pageable);

		SearchHits<ProductDocument> result = searchUtils.searchWithTimer(query, ProductDocument.class);
		return result;
	}

	@Override
	public SearchHits<ProductDocument> searchByTag(String keyword, Pageable pageable) {
		TagQueryBuilder builder = new TagQueryBuilder(keyword);
		NativeQuery nativeQuery = builder.addTagNestedQuery()
				.addTagMatchQuery()
				.build(pageable);

		SearchHits<ProductDocument> result = searchUtils.searchWithTimer(nativeQuery, ProductDocument.class);

		return result;
	}

	@Override
	public void updateDocuments(Iterable<ProductDocument> productDocument) {
		List<UpdateQuery> updateQueries = new ArrayList<>();

		productDocument.forEach(doc->{
			Map<String, Object> priceUpdateMap = new HashMap<>();
			priceUpdateMap.put("price", doc.getPrice());
			priceUpdateMap.put("discount_price", doc.getDiscountPrice());

			// partial update를 위한 Document 생성
			Document document = Document.create();
			document.put("doc", priceUpdateMap);

			UpdateQuery updateQuery = UpdateQuery.builder(doc.getId().toString())
					.withDocument(document)
					.withDocAsUpsert(false)  // document가 없을 경우 새로 생성
					.build();

			updateQueries.add(updateQuery);
			log.info("updateQuery: {}", updateQuery.getDocument());
		});

		elasticsearchOperations.bulkUpdate(updateQueries, ProductDocument.class);
	}

	@Override
	public SearchHits<ProductDocument> searchAutoComplete(String keyword, Pageable pageable) {
		ProductSearchQueryBuilder builder = new ProductSearchQueryBuilder(keyword);
		NativeQuery query = builder.addMatchPhraseQuery()
				.addMatchQuery()
				.addMultiMatchQuery()
				.addTermQuery()
				.build(pageable);

		SearchHits<ProductDocument> result = searchUtils.searchWithTimer(query, ProductDocument.class);

		return result;
	}

	@Override
	public SearchHits<ProductDocument> searchSimilarityKeyword(String keyword, Pageable pageable) {
		TagQueryBuilder builder = new TagQueryBuilder(keyword);
		NativeQuery query = builder.addTagMatchQuery().addNestedKnnQuery(inferenceService).buildKnnQuery(pageable);

		SearchHits<ProductDocument> searchHits = searchUtils.searchWithTimer(query, ProductDocument.class);
		return searchHits;
	}


}

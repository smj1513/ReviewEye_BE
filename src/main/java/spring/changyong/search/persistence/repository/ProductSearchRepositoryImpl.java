package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.utils.builder.ProductSearchQueryBuilder;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements CustomProductSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;
	private final ElasticsearchClient elasticsearchClient;

	@Override
	public SearchHits<ProductDocument> searchByName(String nameKeyword, Pageable pageable) {
		ProductSearchQueryBuilder builder = new ProductSearchQueryBuilder(nameKeyword);
		NativeQuery query = builder.addMatchPhraseQueryStrategy()
				.addMatchQuery()
				.addMultiMatchQuery()
				.addTermQueryStrategy()
				.build(pageable);


		SearchHits<ProductDocument> result = elasticsearchOperations.search(query, ProductDocument.class);


		result.forEach(searchHit -> log.info("searchHit: {} \n, score:{}", searchHit.getContent().getName(), searchHit.getScore()));
		return result;
	}

	/*
	* 	NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();

		HighlightField highlightField = new HighlightField("name",
				HighlightFieldParameters.builder()
						.withPreTags("<strong>")
						.withPostTags("</strong>")
						.build());

		Highlight highlight = new Highlight(List.of(
				highlightField
		));
		HighlightQuery highlightQuery = new HighlightQuery(highlight, Highlight.class);

		NativeQuery query = nativeQueryBuilder.withQuery(
						QueryBuilders
								.bool()
								.should(
										QueryBuilders.
												multiMatch()
												.query(nameKeyword)
												.fields(List.of("name", "brand"))
												.type(TextQueryType.BestFields)
												.autoGenerateSynonymsPhraseQuery(true)
												.operator(Operator.And)
												.tieBreaker(0.3)
												.fuzziness("1")
												.prefixLength(2)
												.boost(2.0F)
												.build()._toQuery()
								)
								.should(
										QueryBuilders.
												match()
												.field("name")
												.query(nameKeyword)
												.analyzer("standard")
												.operator(Operator.And)
												.fuzziness("1")
												.boost(3F)
												.build()
												._toQuery()
								)
								.should(
										QueryBuilders.
												matchPhrase()
												.field("name")
												.query(nameKeyword)
												.analyzer("standard")
												.boost(3F)
												.build()
												._toQuery()
								)
								.should(
										QueryBuilders
												.term()
												.field("name")
												.value(nameKeyword)
												.boost(5F)
												.build()._toQuery()
								)
								.minimumShouldMatch("1")
								.build()._toQuery()
				)
				.withPageable(pageable)
				.withHighlightQuery(highlightQuery)
				.build();
	* */
}

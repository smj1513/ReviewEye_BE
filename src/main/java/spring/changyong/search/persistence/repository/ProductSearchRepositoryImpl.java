package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SuggestMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.search.Suggester;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.stereotype.Repository;
import spring.changyong.search.SearchProperties;
import spring.changyong.search.domain.model.ProductDocument;

import java.util.List;

@Repository
@Log4j2
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements CustomProductSearchRepository {

	private final ElasticsearchOperations elasticsearchOperations;
	private final ElasticsearchClient elasticsearchClient;

	@Override
	public SearchHits<ProductDocument> searchByName(String nameKeyword, Pageable pageable) {
		//NativeQuery
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();

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

		SearchHits<ProductDocument> result = elasticsearchOperations.search(query, ProductDocument.class);


		result.forEach(searchHit -> log.info("searchHit: {} \n, score:{}", searchHit.getContent().getName(), searchHit.getScore()));
		return result;
	}
}

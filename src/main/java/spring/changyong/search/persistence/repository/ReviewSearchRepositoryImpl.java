package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.utils.SearchUtils;
import spring.changyong.search.utils.builder.ReviewSearchQueryBuilder;
import spring.changyong.timer.ExecutionTimeHolder;
import spring.changyong.timer.aspect.ExeTimer;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewSearchRepositoryImpl implements CustomReviewSearchRepository {
	private final ElasticsearchOperations elasticsearchOperations;
	private final SearchUtils searchUtils;


	@Override
	public SearchHits<ReviewDocument> searchByProductId(String id, String keyword, Pageable pageable) {
		ReviewSearchQueryBuilder queryBuilder = new ReviewSearchQueryBuilder(id, keyword)
				.addProductIdFilter()
				.addTermQuery()
				.addMatchQuery()
				.addMatchPhraseQuery();

		NativeQuery nativeQuery = queryBuilder.build(pageable);
		return searchUtils.searchWithTimer(nativeQuery, ReviewDocument.class);
	}
	/*
	* NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
		HighlightField highlightField = new HighlightField("review",
				HighlightFieldParameters.builder()
						.withPreTags("<strong>")
						.withPostTags("</strong>")
						.build());

		Highlight highlight = new Highlight(List.of(
				highlightField
		));
		HighlightQuery highlightQuery = new HighlightQuery(highlight, Highlight.class);

		NativeQuery nativeQuery = nativeQueryBuilder
				.withQuery(QueryBuilders
						.bool()
						.must(
								QueryBuilders
										.term()
										.field("product_id")
										.value(id)
										.build()
										._toQuery()
						)
						.must(
								QueryBuilders
										.bool()
										.should(
												QueryBuilders.term()
														.field("review")
														.value(keyword)
														.boost(3F)
														.build()
														._toQuery()
										)
										.should(
												QueryBuilders.match()
														.field("review")
														.analyzer("nori_analyzer")
														.query(keyword)
														.fuzziness("1")
														.boost(2F)
														.build()
														._toQuery()
										)
										.should(
												QueryBuilders
														.matchPhrase()
														.field("review")
														.query(keyword)
														.slop(1)
														.boost(1F)
														.analyzer("nori_analyzer")
														.build()._toQuery()
										)
										.minimumShouldMatch("1")
										.build()._toQuery()
						)
						.build()
						._toQuery()
				)
				.withHighlightQuery(highlightQuery)
				.withPageable(pageable)
				.build();
	* */
}

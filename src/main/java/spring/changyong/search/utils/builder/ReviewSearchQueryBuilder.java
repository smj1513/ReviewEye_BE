package spring.changyong.search.utils.builder;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import spring.changyong.search.utils.strategy.QueryStrategy;
import spring.changyong.search.utils.strategy.review.ReviewMatchPhraseQueryStrategy;
import spring.changyong.search.utils.strategy.review.ReviewMatchQueryStrategy;
import spring.changyong.search.utils.strategy.review.ReviewTermQueryStrategy;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ReviewSearchQueryBuilder {
	private final String productId;
	private final String keyword;
	private final List<QueryStrategy> queryStrategies = new ArrayList<>();
	private boolean hasProductIdFilter;

	public ReviewSearchQueryBuilder addProductIdFilter() {
		hasProductIdFilter = true;
		return this;
	}

	public ReviewSearchQueryBuilder addTermQuery() {
		queryStrategies.add(new ReviewTermQueryStrategy());
		return this;
	}

	public ReviewSearchQueryBuilder addMatchQuery() {
		queryStrategies.add(new ReviewMatchQueryStrategy());
		return this;
	}

	public ReviewSearchQueryBuilder addMatchPhraseQuery() {
		queryStrategies.add(new ReviewMatchPhraseQueryStrategy());
		return this;
	}

	public NativeQuery build(Pageable pageable) {
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();

		BoolQuery.Builder mainBoolQuery = QueryBuilders.bool();

		if (hasProductIdFilter) {
			mainBoolQuery.must(QueryBuilders.term()
					.field("product_id")
					.value(productId)
					.build()._toQuery());
		}

		BoolQuery.Builder keywordBoolQuery = QueryBuilders.bool();
		for (QueryStrategy strategy : queryStrategies) {
			keywordBoolQuery.should(strategy.buildQuery(keyword));
		}
		keywordBoolQuery.minimumShouldMatch("1");

		mainBoolQuery.must(keywordBoolQuery.build()._toQuery());

		return nativeQueryBuilder
				.withQuery(mainBoolQuery.build()._toQuery())
				.withHighlightQuery(createHighlightQuery())
				.withPageable(pageable)
				.build();
	}

	private HighlightQuery createHighlightQuery() {
		HighlightField highlightField = new HighlightField("review",
				HighlightFieldParameters.builder()
						.withNumberOfFragments(0)
						.withPreTags("<strong backgroundColor='yellow'>")
						.withPostTags("</strong>")
						.build());

		Highlight highlight = new Highlight(List.of(highlightField));
		return new HighlightQuery(highlight, Highlight.class);
	}
}

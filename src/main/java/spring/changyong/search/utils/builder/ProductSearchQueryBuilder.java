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
import spring.changyong.search.utils.strategy.product.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductSearchQueryBuilder {
	private final String nameKeyword;
	private final List<QueryStrategy> queryStrategies = new ArrayList<>();

	public ProductSearchQueryBuilder addMultiMatchQuery(){
		queryStrategies.add(new ProductMultiMatchQueryStrategy());
		return this;
	}

	public ProductSearchQueryBuilder addMatchQuery(){
		queryStrategies.add(new ProductMatchQueryStrategy());
		return this;
	}

	public ProductSearchQueryBuilder addTermQuery(){
		queryStrategies.add(new ProductTermQueryStrategy());
		return this;
	}

	public ProductSearchQueryBuilder addMatchPhraseQuery(){
		queryStrategies.add(new ProductMatchPhraseQueryStrategy());
		return this;
	}


	private HighlightQuery createHighlightQuery() {
		HighlightField highlightField = new HighlightField("name",
				HighlightFieldParameters.builder()
						.withPreTags("<strong>")
						.withPostTags("</strong>")
						.build());

		Highlight highlight = new Highlight(List.of(highlightField));
		return new HighlightQuery(highlight, Highlight.class);
	}



	public NativeQuery build(Pageable pageable){

		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();

		BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
		for (QueryStrategy strategy : queryStrategies) {
			boolQueryBuilder.should(strategy.buildQuery(nameKeyword));
		}
		boolQueryBuilder.minimumShouldMatch("1");


		return nativeQueryBuilder
				.withQuery(boolQueryBuilder.build()._toQuery())
				.withPageable(pageable)
				.withHighlightQuery(createHighlightQuery())
				.build();
	}
}

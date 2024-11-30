package spring.changyong.search.utils.builder;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import spring.changyong.search.service.InferenceService;
import spring.changyong.search.utils.TextEmbeddingUtil;
import spring.changyong.search.utils.strategy.product.AbstractQueryStrategy;
import spring.changyong.search.utils.strategy.product.tag.NestedKnnQueryStrategy;
import spring.changyong.search.utils.strategy.product.tag.TagMatchQueryStrategy;
import spring.changyong.search.utils.strategy.product.tag.TagNestedQueryStrategy;

import java.util.ArrayList;
import java.util.List;

public class TagQueryBuilder {
	private String tag;
	private String name;

	private List<AbstractQueryStrategy> queryStrategies = new ArrayList<>();

	public TagQueryBuilder(String query) {
		queryParse(query);
	}

	public void setQuery(String query) {
		queryParse(query);
	}

	private void queryParse(String query) {
		int lastIndex = query.lastIndexOf(' ');
		if (lastIndex == -1) {
			tag = query;
			name = query;
		} else {
			tag = query.substring(0, lastIndex);
			name = query.substring(lastIndex + 1);
		}
	}

	public TagQueryBuilder addTagNestedQuery() {
		queryStrategies.add(new TagNestedQueryStrategy(tag));
		return this;
	}

	public TagQueryBuilder addTagMatchQuery() {
		queryStrategies.add(new TagMatchQueryStrategy(name));
		return this;
	}

	public TagQueryBuilder addNestedKnnQuery() {
		queryStrategies.add(new NestedKnnQueryStrategy(tag));
		return this;
	}

	public NativeQuery buildKnnQuery(Pageable pageable) {
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
		BoolQuery.Builder bool = QueryBuilders.bool();
		for (AbstractQueryStrategy queryStrategy : queryStrategies) {
			bool.must(queryStrategy.buildQuery());
		}

		return nativeQueryBuilder
				.withQuery(bool.build()._toQuery())
				.withSort(s -> s
						.field(field -> field
							.field("positiveTags.count")
							.order(SortOrder.Desc)
							.nested(nested -> nested
									.path("positiveTags")
									.filter(QueryBuilders.knn()
											.field("positiveTags.embedding")
											.queryVector(TextEmbeddingUtil.embedding(tag))
											.similarity(0.75f)
											.numCandidates(5333L)
											.build()._toQuery()
									)
							)
						)
				)
				.withTrackScores(true)
				.withSourceFilter(FetchSourceFilter.of(s -> s.withExcludes("*embedding*")))
				.withPageable(pageable).build();

	}

	public NativeQuery build(Pageable pageable) {
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
		BoolQuery.Builder bool = QueryBuilders.bool();

		for (AbstractQueryStrategy queryStrategy : queryStrategies) {
			bool.must(queryStrategy.buildQuery());
		}


		return nativeQueryBuilder
				.withQuery(bool.build()._toQuery())
				.withSort(s -> s.field(field -> field
						.field("positiveTags.count")
						.order(SortOrder.Desc)
						.nested(nested -> nested
								.path("positiveTags")
								.filter(QueryBuilders.match()
										.field("positiveTags.keyword")
										.query(tag)
										.analyzer("nori_analyzer")
										.fuzziness("2")
										.prefixLength(2).build()._toQuery()
								)
						)))
				.withTrackScores(true)
				.withPageable(pageable)
				.withSourceFilter(FetchSourceFilter.of(s -> s.withExcludes("*embedding*")))
				.build();
	}


}

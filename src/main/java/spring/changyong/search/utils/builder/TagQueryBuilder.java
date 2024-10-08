package spring.changyong.search.utils.builder;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import spring.changyong.search.utils.strategy.product.AbstractQueryStrategy;
import spring.changyong.search.utils.strategy.product.TagMatchQueryStrategy;
import spring.changyong.search.utils.strategy.product.TagNestedQueryStrategy;

import java.util.ArrayList;
import java.util.List;

public class TagQueryBuilder {

	private String tag;
	private String name;
	private List<AbstractQueryStrategy> queryStrategies = new ArrayList<>();

	public TagQueryBuilder(String query) {
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
				.withPageable(pageable)
				.build();
	}


}

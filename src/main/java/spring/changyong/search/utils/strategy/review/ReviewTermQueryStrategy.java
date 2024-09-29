package spring.changyong.search.utils.strategy.review;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.QueryStrategy;

public class ReviewTermQueryStrategy implements QueryStrategy {
	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.term()
				.field("review")
				.value(keyword)
				.boost(3F)
				.build()._toQuery();
	}
}

package spring.changyong.search.utils.strategy.review;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.QueryStrategy;

public class ReviewMatchQueryStrategy implements QueryStrategy {
	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.match()
				.field("review")
				.analyzer("nori_analyzer")
				.query(keyword)
				.fuzziness("1")
				.boost(2F)
				.build()._toQuery();
	}
}

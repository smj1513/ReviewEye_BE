package spring.changyong.search.utils.strategy.review;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.QueryStrategy;

public class ReviewMatchPhraseQueryStrategy implements QueryStrategy {
	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.matchPhrase()
				.field("review")
				.query(keyword)
				.slop(0)
				.boost(1F)
				.analyzer("standard")
				.build()._toQuery();
	}
}

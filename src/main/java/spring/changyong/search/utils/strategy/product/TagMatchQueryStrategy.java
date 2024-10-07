package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.QueryStrategy;

public class TagMatchQueryStrategy extends AbstractQueryStrategy {
	public TagMatchQueryStrategy(String query) {
		super(query);
	}

	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders
				.match()
				.field("name")
				.query(keyword)
				.fuzziness("AUTO")
				.build()
				._toQuery();
	}
}

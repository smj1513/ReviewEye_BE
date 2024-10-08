package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

public class TagMatchQueryStrategy extends AbstractQueryStrategy {
	public TagMatchQueryStrategy(String query) {
		super(query);
	}

	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders
				.bool()
				.should(QueryBuilders.term()
						.field("name")
						.value(keyword)
						.boost(3.0F)
						.build()
						._toQuery()
				)
				.should(QueryBuilders
						.match()
						.field("name")
						.operator(Operator.And)
						.query(keyword)
						.analyzer("n_gram_analyzer")
						.build()
						._toQuery()).build()._toQuery();
	}
}

package spring.changyong.search.utils.strategy.product.tag;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.product.AbstractQueryStrategy;

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
				.should(keyword.equals("화장품") ? QueryBuilders.matchAll().build()._toQuery() : QueryBuilders
						.match()
						.field("name")
						.operator(Operator.And)
						.query(keyword)
						.analyzer("n_gram_analyzer")
						.build()
						._toQuery()).build()._toQuery();
	}
}

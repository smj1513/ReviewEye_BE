package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.QueryStrategy;

public class ProductMatchQueryStrategy implements QueryStrategy {
	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.bool().should(QueryBuilders.match()
						.field("name")
						.query(keyword)
						.analyzer("n_gram_analyzer")
						.operator(Operator.And)
						.prefixLength(2)
						.fuzziness("AUTO")
						.build()._toQuery()
				)
				.should(QueryBuilders
						.match()
						.field("title")
						.analyzer("n_gram_analyzer")
						.fuzziness("AUTO")
						.prefixLength(2)
						.query(keyword)
						.build()._toQuery()
				).minimumShouldMatch("1").boost(2.0F).build()._toQuery();
	}
}

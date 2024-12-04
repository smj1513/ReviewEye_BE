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
						.fuzziness("AUTO")
						.boost(1.0F)
						.build()._toQuery())
				.should(QueryBuilders.match()
						.field("title")
						.query(keyword)
						.operator(Operator.And)
						.analyzer("nori_analyzer")
						.fuzziness("1").boost(0.5F).build()._toQuery()
				)
				.minimumShouldMatch("1")
				.build()
				._toQuery();
	}
}

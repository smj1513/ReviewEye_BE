package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import spring.changyong.search.utils.strategy.QueryStrategy;

import java.util.List;

public class ProductMultiMatchQueryStrategy implements QueryStrategy {
	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.multiMatch()
				.query(keyword)
				.fields(List.of("name", "title"))
				.analyzer("n_gram_analyzer")
				.operator(Operator.And)
				.tieBreaker(0.3)
				.fuzziness("1")
				.boost(2.0F)
				.build()._toQuery();
	}
}

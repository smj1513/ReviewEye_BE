package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.QueryStrategy;

public class ProductTermQueryStrategy implements QueryStrategy {
	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.term()
				.field("title")
				.value(keyword)
				.boost(3.0F)
				.build()._toQuery();
	}
}

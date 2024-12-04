package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import spring.changyong.search.utils.strategy.QueryStrategy;

public class ProductMatchPhraseQueryStrategy implements QueryStrategy {
	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.matchPhrase()
				.field("name")
				.query(keyword)
				.analyzer("n_gram_analyzer")
				.build()
				._toQuery();
	}
}

package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

public class AutoCompleteQueryStrategy extends AbstractQueryStrategy{
	public AutoCompleteQueryStrategy(String query) {
		super(query);
	}

	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.prefix()
				.field("title")
				.value(keyword)
				.caseInsensitive(false)
				.build()
				._toQuery();
	}
}

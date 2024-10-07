package spring.changyong.search.utils.strategy.product;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.Getter;
import lombok.Setter;
import spring.changyong.search.utils.strategy.QueryStrategy;


@Getter
@Setter
public abstract class AbstractQueryStrategy implements QueryStrategy {

	private String query;


	public AbstractQueryStrategy(String query) {
		this.query = query;
	}

	public Query buildQuery(){
		return buildQuery(query);
	}
}

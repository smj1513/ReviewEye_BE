package spring.changyong.search.utils.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

public interface QueryStrategy {
	Query buildQuery(String keyword);
}

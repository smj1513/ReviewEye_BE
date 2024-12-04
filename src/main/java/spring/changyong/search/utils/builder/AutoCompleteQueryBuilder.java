package spring.changyong.search.utils.builder;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.search.Suggester;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import spring.changyong.search.utils.strategy.QueryStrategy;
import spring.changyong.search.utils.strategy.product.AutoCompleteQueryStrategy;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AutoCompleteQueryBuilder {
	private final String keyword;
	private final List<QueryStrategy> queryStrategies = new ArrayList<>();

	public AutoCompleteQueryBuilder addPrefix(){
		queryStrategies.add(new AutoCompleteQueryStrategy(keyword));
		return this;
	}


}

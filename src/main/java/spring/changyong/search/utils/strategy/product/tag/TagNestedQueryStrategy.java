package spring.changyong.search.utils.strategy.product.tag;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import spring.changyong.search.utils.strategy.product.AbstractQueryStrategy;

public class TagNestedQueryStrategy extends AbstractQueryStrategy {
	public TagNestedQueryStrategy(String query) {
		super(query);
	}

	@Override
	public Query buildQuery(String keyword) {
		return QueryBuilders.nested()
				.path("positiveTags")
				.query(query -> query
						.match(match -> match
								.field("positiveTags.keyword")
								.query(keyword)
								.analyzer("nori_analyzer")
								.fuzziness("2")
								.prefixLength(2)
						)
				)
				.innerHits(in->in.source(s->s.filter(f->f.excludes("*embedding*"))))
				.build()
				._toQuery();
	}
}


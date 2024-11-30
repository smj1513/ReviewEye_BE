package spring.changyong.metric.rankRequest.requests;

import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import spring.changyong.search.utils.builder.TagQueryBuilder;

public class NestedQueryStrategy implements RankEvalQueryStrategy{
	@Override
	public RankEvalQuery rankEvalQuery(String query) {
		PageRequest pageRequest = PageRequest.of(0, 10);
		TagQueryBuilder tagQueryBuilder = new TagQueryBuilder(query);
		NativeQuery nQuery = tagQueryBuilder.addTagMatchQuery().addTagNestedQuery().build(pageRequest);
		return RankEvalQuery.of(rank->rank.query(nQuery.getQuery()));
	}
}

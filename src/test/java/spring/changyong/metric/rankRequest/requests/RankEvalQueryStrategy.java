package spring.changyong.metric.rankRequest.requests;

import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalQuery;

public interface RankEvalQueryStrategy {
	RankEvalQuery rankEvalQuery(String query);
}

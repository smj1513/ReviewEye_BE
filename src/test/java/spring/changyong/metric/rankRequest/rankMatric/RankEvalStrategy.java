package spring.changyong.metric.rankRequest.rankMatric;

import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalMetric;

public interface RankEvalStrategy {
	RankEvalMetric metric();
}

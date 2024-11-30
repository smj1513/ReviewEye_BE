package spring.changyong.metric.rankRequest.rankMatric.err;

import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalMetric;
import spring.changyong.metric.rankRequest.rankMatric.RankEvalStrategy;

public class ErrStrategy implements RankEvalStrategy {

	@Override
	public RankEvalMetric metric() {
		return RankEvalMetric.of(metric -> metric.expectedReciprocalRank(err -> err.k(10).maximumRelevance(10)));
	}
}

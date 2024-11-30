package spring.changyong.metric.rankRequest.rankMatric.precision;

import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalMetric;
import spring.changyong.metric.rankRequest.rankMatric.RankEvalStrategy;

public class PrecisionStrategy implements RankEvalStrategy {
	@Override
	public RankEvalMetric metric() {
		return RankEvalMetric.of(metric->metric.precision(pre->pre.k(10).relevantRatingThreshold(3).ignoreUnlabeled(false)));
	}
}

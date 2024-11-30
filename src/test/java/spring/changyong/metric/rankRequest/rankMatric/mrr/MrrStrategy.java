package spring.changyong.metric.rankRequest.rankMatric.mrr;

import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalMetric;
import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalQuery;
import spring.changyong.metric.rankRequest.rankMatric.RankEvalStrategy;
import spring.changyong.metric.rankRequest.requests.RankEvalQueryStrategy;

public class MrrStrategy implements RankEvalStrategy {

	@Override
	public RankEvalMetric metric() {
		return RankEvalMetric.of(me->me.meanReciprocalRank(mrr->mrr.k(10).relevantRatingThreshold(2)));
	}
}

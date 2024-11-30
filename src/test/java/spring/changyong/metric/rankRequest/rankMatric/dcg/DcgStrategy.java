package spring.changyong.metric.rankRequest.rankMatric.dcg;

import co.elastic.clients.elasticsearch.core.rank_eval.RankEvalMetric;
import spring.changyong.metric.rankRequest.rankMatric.RankEvalStrategy;

public class DcgStrategy implements RankEvalStrategy {
	@Override
	public RankEvalMetric metric() {
		return RankEvalMetric.of(metric->metric.dcg(dcg->dcg.k(10).normalize(true)));
	}
}

package spring.changyong.metric.rankRequest.requests;

import co.elastic.clients.elasticsearch.core.RankEvalRequest;
import spring.changyong.metric.rankRequest.rankMatric.RankEvalStrategy;
import spring.changyong.metric.rankRequest.rankMatric.dcg.DcgStrategy;

public class RankEvalRequestProvider {

	public static RankEvalRequest nestedSearchRequest(String query, RankEvalStrategy rankEvalStrategy) {
		RankEvalRequestBuilder rankEvalRequestBuilder = new RankEvalRequestBuilder();

		return rankEvalRequestBuilder
				.addRankEvalStrategy(rankEvalStrategy)
				.addRankEvalQueryStrategy(new NestedQueryStrategy()).build(query);
	}

	public static RankEvalRequest knnSearchRequest(String query, RankEvalStrategy rankEvalStrategy) {
		RankEvalRequestBuilder rankEvalRequestBuilder = new RankEvalRequestBuilder();
		return rankEvalRequestBuilder
				.addRankEvalStrategy(rankEvalStrategy)
				.addRankEvalQueryStrategy(new KnnQueryStrategy())
				.build(query);
	}

}

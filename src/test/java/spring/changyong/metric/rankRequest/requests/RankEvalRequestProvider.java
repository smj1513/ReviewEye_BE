package spring.changyong.metric.rankRequest.requests;

import co.elastic.clients.elasticsearch._types.Rank;
import co.elastic.clients.elasticsearch.core.RankEvalRequest;
import spring.changyong.metric.rankRequest.rankMatric.dcg.DcgStrategy;

public class RankEvalRequestProvider {

	public static RankEvalRequest nestedSearchRequest(String query){
		RankEvalRequestBuilder rankEvalRequestBuilder = new RankEvalRequestBuilder();
		rankEvalRequestBuilder
				.addRankEvalStrategy(new DcgStrategy())
				.addRankEvalQueryStrategy(new NestedQueryStrategy());
		RankEvalRequest tagRequest = rankEvalRequestBuilder.build(query);
		return tagRequest;
	}
	public static RankEvalRequest knnSearchRequest(String query){
		RankEvalRequestBuilder rankEvalRequestBuilder = new RankEvalRequestBuilder();
		return rankEvalRequestBuilder
				.addRankEvalStrategy(new DcgStrategy())
				.addRankEvalQueryStrategy(new KnnQueryStrategy())
				.build(query);
	}
}

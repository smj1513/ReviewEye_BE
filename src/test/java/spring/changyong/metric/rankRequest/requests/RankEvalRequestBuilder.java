package spring.changyong.metric.rankRequest.requests;

import co.elastic.clients.elasticsearch.core.RankEvalRequest;
import lombok.Setter;
import spring.changyong.metric.rankRequest.GoldStandardDataSetLoader;
import spring.changyong.metric.rankRequest.rankMatric.RankEvalStrategy;

@Setter
public class RankEvalRequestBuilder {
	private RankEvalQueryStrategy rankEvalQueryStrategy;
	private RankEvalStrategy rankEvalStrategy;

	public RankEvalRequestBuilder addRankEvalQueryStrategy(RankEvalQueryStrategy strategy) {
		rankEvalQueryStrategy = strategy;
		return this;
	}

	public RankEvalRequestBuilder addRankEvalStrategy(RankEvalStrategy strategy) {
		rankEvalStrategy = strategy;
		return this;
	}

	public RankEvalRequest build(String query) {
		return RankEvalRequest.of(request -> request.requests(requests -> requests
						.id(query)
						.request(rankEvalQueryStrategy.rankEvalQuery(query))
						.ratings(GoldStandardDataSetLoader.getDocumentRatings(query)))
				.metric(rankEvalStrategy.metric())
		);
	}

	public RankEvalRequest buildVectors(String query) {
		return RankEvalRequest.of(request -> request.requests(requests -> requests
						.id(query)
						.request(rankEvalQueryStrategy.rankEvalQuery(query))
						.ratings(GoldStandardDataSetLoader.getVectorDocumentRating(query)))
				.metric(rankEvalStrategy.metric())
		);
	}
}

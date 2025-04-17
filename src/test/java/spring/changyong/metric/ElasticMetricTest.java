package spring.changyong.metric;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.RankEvalRequest;
import co.elastic.clients.elasticsearch.core.RankEvalResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.changyong.metric.rankRequest.rankMatric.dcg.DcgStrategy;
import spring.changyong.metric.rankRequest.rankMatric.mrr.MrrStrategy;
import spring.changyong.metric.rankRequest.requests.RankEvalRequestProvider;

import java.io.IOException;

@SpringBootTest
public class ElasticMetricTest {

	String query1 = "겨울철 토너";
	String query2 = "에센스 넉넉한 토너 패드";
	String query3 = "건성 피부 로션";

	@Autowired
	ElasticsearchClient client;

	@Test
		void normalMetric() throws IOException {
		RankEvalRequest rankEvalRequest = RankEvalRequestProvider.nestedSearchRequest(query1, new DcgStrategy());
		RankEvalRequest rankEvalRequest1 = RankEvalRequestProvider.nestedSearchRequest(query2, new DcgStrategy());
		RankEvalRequest rankEvalRequest2 = RankEvalRequestProvider.nestedSearchRequest(query3, new DcgStrategy());


		RankEvalResponse rankEvalResponse = client.rankEval(rankEvalRequest);
		RankEvalResponse rankEvalResponse1 = client.rankEval(rankEvalRequest1);
		RankEvalResponse rankEvalResponse2 = client.rankEval(rankEvalRequest2);

		System.out.println("=== Search NDCG Metric Scores ===");
		System.out.println("겨울철 토너 Search NDCG: " + rankEvalResponse.metricScore());
		System.out.println("에센스 넉넉한 토너 패드 Search NDCG: " + rankEvalResponse1.metricScore());
		System.out.println("건성 피부 로션 Search NDCG: " + rankEvalResponse2.metricScore());

	}

	@Test
	void vectorMetric() throws IOException {
		RankEvalRequest rankEvalRequest = RankEvalRequestProvider.knnSearchRequest(query1, new DcgStrategy());

		RankEvalResponse rankEvalResponse = client.rankEval(rankEvalRequest);

		System.out.println("=== Vector Search NDCG Metric Scores ===");
		System.out.println("겨울철 토너 Search NDCG: " + rankEvalResponse.metricScore());
	}

	@Test
	void normalMRRMetric() throws IOException {
		RankEvalRequest rankEvalRequest = RankEvalRequestProvider.nestedSearchRequest(query1, new MrrStrategy());
		RankEvalRequest rankEvalRequest1 = RankEvalRequestProvider.nestedSearchRequest(query2, new MrrStrategy());
		RankEvalRequest rankEvalRequest2 = RankEvalRequestProvider.nestedSearchRequest(query3, new MrrStrategy());

		RankEvalResponse rankEvalResponse = client.rankEval(rankEvalRequest);
		RankEvalResponse rankEvalResponse1 = client.rankEval(rankEvalRequest1);
		RankEvalResponse rankEvalResponse2 = client.rankEval(rankEvalRequest2);

		System.out.println("=== Tag Search MRR Metric Scores ===");
		System.out.println("겨울철 토너 Search MRR: " + rankEvalResponse.metricScore());
		System.out.println("에센스 넉넉한 토너 패드 Search MRR: " + rankEvalResponse1.metricScore());
		System.out.println("건성 피부 로션 Search MRR: " + rankEvalResponse2.metricScore());
	}


	@Test
	void vectorMRRMetric() throws IOException {
		RankEvalRequest rankEvalRequest = RankEvalRequestProvider.knnSearchRequest(query1, new MrrStrategy());

		RankEvalResponse rankEvalResponse = client.rankEval(rankEvalRequest);

		System.out.println("=== Vector Search MRR Metric Scores ===");
		System.out.println("겨울철 토너 Search MRR: " + rankEvalResponse.metricScore());
	}
}

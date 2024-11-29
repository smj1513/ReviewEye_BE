package spring.changyong.search.utils.strategy.product.tag;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.knn_search.KnnSearchQuery;
import spring.changyong.search.service.InferenceService;
import spring.changyong.search.utils.strategy.product.AbstractQueryStrategy;

import java.util.List;

public class NestedKnnQueryStrategy extends AbstractQueryStrategy {

	private final InferenceService inferenceService;

	public NestedKnnQueryStrategy(String keyword, InferenceService inferenceService) {
		super(keyword);
		this.inferenceService = inferenceService;
	}


	@Override
	public Query buildQuery(String keyword) {
		List<Float> embeddingVector = inferenceService.textEmbedding(keyword);

		return QueryBuilders.nested(
				n -> n.path("positiveTags")
						.query(q -> q.knn(
										knn -> knn.field("positiveTags.embedding")
												.queryVector(embeddingVector)
												.similarity(0.75f)
												.numCandidates(5333L)
								)
						)
						.scoreMode(ChildScoreMode.Max)
						.innerHits(in->in.sort(s->s.score(sc->sc.order(SortOrder.Desc))).source(s->s.filter(f->f.excludes("*embedding*"))))

		);
	}
}

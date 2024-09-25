package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.stereotype.Repository;
import spring.changyong.search.SearchProperties;
import spring.changyong.search.domain.model.ReviewDocument;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewSearchRepositoryImpl implements CustomReviewSearchRepository {
	private final ElasticsearchOperations elasticsearchOperations;


	@Override
	public SearchHits<ReviewDocument> searchByProductId(String id, String keyword, Pageable pageable) {
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
		HighlightField highlightField = new HighlightField("review",
				HighlightFieldParameters.builder()
						.withPreTags("<strong>")
						.withPostTags("</strong>")
						.build());

		Highlight highlight = new Highlight(List.of(
			highlightField
		));
		HighlightQuery highlightQuery = new HighlightQuery(highlight, Highlight.class);

		NativeQuery nativeQuery = nativeQueryBuilder
				.withQuery(QueryBuilders
						.bool()
						.must(
								QueryBuilders
										.term()
										.field("product_id")
										.value(id)
										.build()
										._toQuery()
						)
						.must(
								QueryBuilders
										.match()
										.field("review")
										.query(keyword)
										.fuzziness("AUTO")
										.analyzer(SearchProperties.NORI_ANALYZER)
										.minimumShouldMatch("90%")
										.build()
										._toQuery()
						)
						.build()
						._toQuery()
				)
				.withHighlightQuery(highlightQuery)
				.withPageable(pageable)
				.build();
		return elasticsearchOperations.search(nativeQuery, ReviewDocument.class);
	}
}

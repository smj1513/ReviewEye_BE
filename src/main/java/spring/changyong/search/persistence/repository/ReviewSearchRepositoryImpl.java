package spring.changyong.search.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.enums.OrderBy;
import spring.changyong.search.enums.ResultFilter;
import spring.changyong.search.enums.SortOption;
import spring.changyong.search.utils.SearchUtils;
import spring.changyong.search.utils.builder.ReviewSearchQueryBuilder;

@Repository
@RequiredArgsConstructor
public class ReviewSearchRepositoryImpl implements CustomReviewSearchRepository {
	private final ElasticsearchOperations elasticsearchOperations;
	private final SearchUtils searchUtils;


	@Override
	public SearchHits<ReviewDocument> searchByProductId(String id, String keyword, Pageable pageable, ResultFilter filter, SortOption sortOption, OrderBy orderBy) {
		ReviewSearchQueryBuilder queryBuilder = new ReviewSearchQueryBuilder(id, keyword)
				.addProductIdFilter()
				.addTermQuery()
				.addMatchQuery()
				.addResultFilter(filter)
				.addSortOption(sortOption)
				.addOrderBy(orderBy);

		NativeQuery nativeQuery = queryBuilder.build(pageable);
		return searchUtils.searchWithTimer(nativeQuery, ReviewDocument.class);
	}
}

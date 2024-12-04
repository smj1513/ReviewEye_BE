package spring.changyong.search.persistence.repository;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.rank_eval.DocumentRating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Repository;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.enums.OrderBy;
import spring.changyong.search.enums.ResultFilter;
import spring.changyong.search.enums.SortOption;
import spring.changyong.search.utils.SearchUtils;
import spring.changyong.search.utils.builder.ReviewSearchQueryBuilder;

import java.util.stream.Collectors;

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

	@Override
	public Page<ReviewDocument> findAllByProductIdWithFilter(String id, Pageable pageable, ResultFilter filter, SortOption sortOption, OrderBy orderBy) {
		NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();

		BoolQuery.Builder mainBoolQuery = new BoolQuery.Builder();
		if (!filter.equals(ResultFilter.BOTH)) {
			mainBoolQuery.must(QueryBuilders.term()
					.field("sentiment")
					.value(filter.equals(ResultFilter.POSITIVE))
					.build()._toQuery()
			);
		}
		BoolQuery boolQuery = mainBoolQuery.must(m -> m.term(t -> t.field("product_id").value(id)))
				.must(m -> m.matchAll(ma -> ma)).build();


		NativeQuery query = nativeQueryBuilder.withQuery(boolQuery._toQuery())
				.withSort(s -> s.field(
						f -> f.field(sortOption.getValue())
								.order(orderBy.equals(OrderBy.DESC) ? SortOrder.Desc : SortOrder.Asc)))
				.withPageable(pageable)
				.withSourceFilter(FetchSourceFilter.of(fs->fs.withExcludes("*embedding*")))
				.build();
		SearchHits<ReviewDocument> result = elasticsearchOperations.search(query, ReviewDocument.class);

		return new PageImpl<>(
				result.getSearchHits().stream().map(SearchHit::getContent).toList(),
				pageable,
				result.getTotalHits());
	}
}

package spring.changyong.search.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class SearchAppService {
	private final ProductSearchRepository productSearchRepository;
	private final ReviewSearchRepository reviewSearchRepository;

	public Slice<SearchResponse.ProductResult> searchProductByKeyword(String keyword, int page, int size) {
		return null;
	}

	public Slice<SearchResponse.ProductResult> searchProductByName(String name, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		SearchHits<ProductDocument> result = productSearchRepository.searchByName(name, pageRequest);
		List<SearchResponse.ProductResult> productResultList = result
				.stream()
				.map(SearchResponse.ProductResult::from) // 변환
				.collect(Collectors.toList());

		boolean hasNext = result.getTotalHits() > (long) (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize();

		return new SliceImpl<>(productResultList, pageRequest, hasNext);
	}

	public Slice<SearchResponse.ReviewResult> searchReviewByProductId(String id, String keyword, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		SearchHits<ReviewDocument> searchHits = reviewSearchRepository.searchByProductId(id, keyword, pageRequest);
		searchHits.forEach(reviewDocument -> log.info("reviewDocument: {}", reviewDocument.getContent()));
		List<SearchResponse.ReviewResult> reviewResultList = searchHits
				.stream()
				.map(SearchResponse.ReviewResult::from)
				.toList();

		boolean hasNext = searchHits.getTotalHits() > (long) (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize();

		return new SliceImpl<>(reviewResultList, pageRequest, hasNext);
	}
}

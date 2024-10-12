package spring.changyong.search.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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
import spring.changyong.search.utils.ExecutionTimeHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class SearchAppService {
	private final ProductSearchRepository productSearchRepository;
	private final ReviewSearchRepository reviewSearchRepository;

	public SearchResponse.Result<SearchResponse.ProductResult> searchProductByKeyword(String keyword, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		SearchHits<ProductDocument> result = productSearchRepository.searchByTag(keyword, pageRequest);
		List<SearchResponse.ProductResult> productResultList = result
				.stream()
				.map(SearchResponse.ProductResult::from)
				.toList();

		boolean hasNext = result.getTotalHits() > (long) (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize();
		Long searchTime = ExecutionTimeHolder.get();
		ExecutionTimeHolder.clear();
		Slice<SearchResponse.ProductResult> productResults = new SliceImpl<>(productResultList, pageRequest, hasNext);

		return new SearchResponse.Result<>(searchTime, productResults);
	}

	public SearchResponse.Result<SearchResponse.ProductResult> searchProductByName(String name, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		SearchHits<ProductDocument> result = productSearchRepository.searchByName(name, pageRequest);

		List<SearchResponse.ProductResult> productResultList = result
				.stream()
				.map(SearchResponse.ProductResult::from) // 변환
				.collect(Collectors.toList());

		boolean hasNext = result.getTotalHits() > (long) (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize();
		Slice<SearchResponse.ProductResult> productResults = new SliceImpl<>(productResultList, pageRequest, hasNext);
		Long searchTime = ExecutionTimeHolder.get();
		ExecutionTimeHolder.clear();
		return new SearchResponse.Result<>(searchTime, productResults);
	}

	public SearchResponse.Result<SearchResponse.ReviewResult> searchReviewByProductId(String id, String keyword, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		SearchHits<ReviewDocument> searchHits = reviewSearchRepository.searchByProductId(id, keyword, pageRequest);
		List<SearchResponse.ReviewResult> reviewResultList = searchHits
				.stream()
				.map(SearchResponse.ReviewResult::from)
				.toList();

		boolean hasNext = searchHits.getTotalHits() > (long) (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize();
		Slice<SearchResponse.ReviewResult> reviewResults = new SliceImpl<>(reviewResultList, pageRequest, hasNext);
		Long searchTime = ExecutionTimeHolder.get();
		ExecutionTimeHolder.clear();
		return new SearchResponse.Result<>(searchTime, reviewResults);
	}

	public Page<SearchResponse.AutoComplete> autoCompleteQuery(String prefix, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		Page<ProductDocument> autoCompleteDocument = productSearchRepository.findByNameStartingWith(prefix.replace(" ",""), pageRequest);


		return autoCompleteDocument.map(doc-> SearchResponse.AutoComplete.builder()
				.productName(doc.getName())
				.build());
	}
}

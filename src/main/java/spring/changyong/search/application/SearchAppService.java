package spring.changyong.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.domain.repository.ProductSearchRepository;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

@Service
@RequiredArgsConstructor
public class SearchAppService {
	private final ProductSearchRepository productSearchRepository;
	private final ReviewSearchRepository reviewSearchRepository;

	public Page<SearchResponse.Product> productSearch(String keyword, int page, int size) {
		return null;
	}
}

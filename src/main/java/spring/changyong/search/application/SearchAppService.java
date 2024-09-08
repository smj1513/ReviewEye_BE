package spring.changyong.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

@Service
@RequiredArgsConstructor
public class SearchAppService {
	private final ProductSearchRepository productSearchRepository;
	private final ReviewSearchRepository reviewSearchRepository;

	public Slice<SearchResponse.Product> productSearch(String keyword, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Slice<ProductDocument> result = productSearchRepository.findByName(keyword, pageRequest);
		return result.map(productDocument -> SearchResponse.Product.builder()
				.id(productDocument.getId())
				.name(productDocument.getName())
				.price(productDocument.getPrice())
				.discountPrice(productDocument.getDiscountPrice())
				.imageUrl(productDocument.getThumbnail())
				.build());
	}

	public Slice<SearchResponse.Product> productSearchByName(String name, int page, int size) {
		return null;
	}
}

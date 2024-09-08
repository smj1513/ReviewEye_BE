package spring.changyong.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.SearchPage;
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

	public Slice<SearchResponse.Product> productSearchByKeyword(String keyword, int page, int size) {
		return null;
	}

	public Slice<SearchResponse.Product> productSearchByName(String name, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		SearchPage<ProductDocument> result = productSearchRepository.findByName(name, pageRequest);
		return result.map(productDocument -> SearchResponse.Product.builder()
				.id(productDocument.getContent().getId())
				.name(productDocument.getContent().getName())
				.price(productDocument.getContent().getPrice())
				.discountPrice(productDocument.getContent().getDiscountPrice())
				.imageUrl(productDocument.getContent().getThumbnail())
				.build());
	}
}

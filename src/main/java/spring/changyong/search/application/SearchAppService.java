package spring.changyong.search.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class SearchAppService {
	private final ProductSearchRepository productSearchRepository;
	private final ReviewSearchRepository reviewSearchRepository;

	public Slice<SearchResponse.Product> productSearchByKeyword(String keyword, int page, int size) {
		return null;
	}

	public Slice<SearchResponse.Product> productSearchByName(String name, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		log.info("==============================================app service ===========================================");
		Page<ProductDocument> result = productSearchRepository.searchByName(name, pageRequest);
		return result.map(productDocument -> SearchResponse.Product.builder()
				.id(productDocument.getId())
				.name(productDocument.getName())
				.price(productDocument.getPrice())
				.discountPrice(productDocument.getDiscountPrice())
				.imageUrl(productDocument.getThumbnail())
				.build());
	}
}

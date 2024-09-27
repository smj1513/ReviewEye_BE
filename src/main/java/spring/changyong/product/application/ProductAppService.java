package spring.changyong.product.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.product.api.response.ProductResponse;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.repository.ProductSearchRepository;

@Service
@RequiredArgsConstructor
public class ProductAppService {

	private final ProductSearchRepository productRepository;

	public ProductResponse.Detail getProductDetail(String id) {
		ProductDocument product = productRepository.findByProductId(id)
				.orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "상품을 찾을 수 없습니다."));

		return ProductResponse.Detail.builder()
				.id(product.getProductId())
				.title(product.getName())
				.brand(product.getBrand())
				.productId(product.getProductId())
				.thumbnail(product.getThumbnail())
				.keywords(null)
				.build();
	}

	public ProductResponse.PositiveKeyword getPositiveKeyword(String id) {
		return null;
	}

	public ProductResponse.NegativeKeyword getNegativeKeyword(String id) {
		return null;
	}
}

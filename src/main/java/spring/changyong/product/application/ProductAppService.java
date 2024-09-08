package spring.changyong.product.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.product.api.response.ProductResponse;
import spring.changyong.product.domain.model.Product;
import spring.changyong.product.domain.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductAppService {

	private final ProductRepository productRepository;

	public ProductResponse.Detail getProductDetail(Integer id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "상품을 찾을 수 없습니다."));

		return ProductResponse.Detail.builder()
				.id(product.getId().toString())
				.title(product.getName())
				.brand(product.getBrand())
				.productId(product.getProductId())
				.thumbnail(product.getThumbnail())
				.keywords(null)
				.build();
	}

	public ProductResponse.PositiveKeyword getPositiveKeyword(Integer id) {
		return null;
	}

	public ProductResponse.NegativeKeyword getNegativeKeyword(Integer id) {
		return null;
	}
}

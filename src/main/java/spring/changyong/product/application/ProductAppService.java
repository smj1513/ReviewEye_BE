package spring.changyong.product.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.product.api.response.ProductResponse;
import spring.changyong.product.domain.service.ProductDomainService;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.model.Tag;
import spring.changyong.search.domain.repository.ProductSearchRepository;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductAppService {

	private final ProductSearchRepository productRepository;
	private final ReviewSearchRepository reviewSearchRepository;
	private final ProductDomainService productDomainService;

	public ProductResponse.Detail getProductDetail(String id) {
		ProductDocument product = productRepository.findByProductId(id)
				.orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "상품을 찾을 수 없습니다."));
		List<String> keywords = product.getPositiveTags().stream().map(Tag::getKeyword).toList();
		return ProductResponse.Detail.builder()
				.id(product.getProductId())
				.title(product.getName())
				.brand(product.getBrand())
				.price(product.getPrice())
				.discountPrice(product.getDiscountPrice())
				.thumbnail(product.getThumbnail())
				.keywords(keywords)
				.build();
	}

	public ProductResponse.PositiveKeyword getPositiveKeyword(String id) {
		ProductDocument productDocument = productRepository.findByProductId(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "상품을 찾을 수 없습니다."));
		List<Tag> positiveTags = productDocument.getPositiveTags();
		return ProductResponse.PositiveKeyword.builder()
				.keywords(productDomainService.changeCountToPercentage(positiveTags))
				.build();
	}

	public ProductResponse.NegativeKeyword getNegativeKeyword(String id) {
		ProductDocument productDocument = productRepository.findByProductId(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "상품을 찾을 수 없습니다."));
		List<Tag> negativeTags = productDocument.getNegativeTags();
		return ProductResponse.NegativeKeyword.builder()
				.keywords(productDomainService.changeCountToPercentage(negativeTags))
				.build();
	}

	public List<ProductResponse.Evaluation> getProductEvaluation(String id) {
		List<ReviewDocument> reviews = reviewSearchRepository.findAllByProductId(id, PageRequest.of(0,10000)).getContent();
		Map<String, Map<String, Integer>> productEvaluation = productDomainService.getProductEvaluation(reviews);
		List<ProductResponse.Evaluation> response = new ArrayList<>();

		productEvaluation.forEach((key, value) -> {
			List<ProductResponse.EvaluationDetails> details = new ArrayList<>();
			value.forEach((skinType, count) -> {
				details.add(ProductResponse.EvaluationDetails.builder()
						.count(count)
						.content(skinType)
						.build());
			});
			details.sort((o1, o2) -> o2.getCount() - o1.getCount());
			response.add(ProductResponse.Evaluation.builder()
					.title(key)
					.evaluationDetails(details)
					.build());
		});

		return response;
	}
}

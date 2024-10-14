package spring.changyong.product.domain.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.product.api.response.ProductResponse;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.model.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductDomainService {

	private static final Logger log = LoggerFactory.getLogger(ProductDomainService.class);

	public Map<String, Map<String, Integer>> getProductEvaluation(List<ReviewDocument> reviewDocuments) {
		Map<String, Map<String, Integer>> evaluationMap = new HashMap<>();
		Map<String, Integer> skinTypeCount;

		for (ReviewDocument review : reviewDocuments) {
			if (review.getEvaluation() != null) {
				String[] evaluations = review.getEvaluation().split(",");
				for (String evDetails : evaluations) {
					String[] evDetail = evDetails.split(":");
					String evType = evDetail[0];
					String evValue = evDetail[1];
					skinTypeCount = evaluationMap.getOrDefault(evType, new HashMap<>());
					skinTypeCount.put(evValue, skinTypeCount.getOrDefault(evValue, 0) + 1);
					evaluationMap.put(evType, skinTypeCount);
				}
			}
		}
		return evaluationMap;
	}

	public List<ProductResponse.Keyword> changeCountPercentage(List<Tag> tags) {
		int total = tags.subList(0, 9).stream().mapToInt(Tag::getCount).sum();
		return tags.stream().map(tag -> {
			return ProductResponse.Keyword.builder()
					.keyword(tag.getKeyword())
					.percentage((double) tag.getCount() / total * 100)
					.build();
		}).toList();
	}
}

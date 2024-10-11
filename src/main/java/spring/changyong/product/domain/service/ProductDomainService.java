package spring.changyong.product.domain.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.repository.ReviewSearchRepository;
import spring.changyong.search.persistence.repository.ReviewSearchRepositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductDomainService {

	public Map<String, Map<String, Integer>> getProductEvaluation(List<ReviewDocument> reviewDocuments) {
		Map<String, Map<String, Integer>> evaluationMap = new HashMap<>();
		Map<String, Integer> skinTypeCount = new HashMap<>();
		reviewDocuments.forEach(review->{
			String[] split = review.getEvaluation().split(",");
			if (split.length < 1) {
				throw new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "평가를 찾을 수 없습니다.");
			}
			for (String evDetails : split) {
				String[] split1 = evDetails.split(":");
				if (split1.length < 1) {
					throw new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "평가를 찾을 수 없습니다.");
				}
				String evType = split1[0];
				String evValue = split1[1];
				skinTypeCount.put(evValue, skinTypeCount.getOrDefault(evValue, 0) + 1);
				evaluationMap.put(evType, skinTypeCount);
			}
		});
		return evaluationMap;
	}
}

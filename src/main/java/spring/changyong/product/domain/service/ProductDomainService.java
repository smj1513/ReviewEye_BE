package spring.changyong.product.domain.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spring.changyong.product.api.response.ProductResponse;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.model.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

	public List<ProductResponse.Keyword> changeCountToPercentage(List<Tag> tags) {
		List<Tag> newTags = tags.size() < 10 ? tags : tags.subList(0, 10);
		int max = newTags.stream().mapToInt(Tag::getCount).max().orElseGet(() -> 1);
		return newTags.stream().map(newTag -> {
			return ProductResponse.Keyword.builder()
					.keyword(newTag.getKeyword())
					.percentage((double) newTag.getCount() / max * 100)
					.count(newTag.getCount())
					.build();
		}).toList();
	}

	/**
	 * @return pivotTags에 대한 Percentage를 return함
	 * */
	public List<ProductResponse.Keyword> changeCountToPercentage(List<Tag> pivotTags, List<Tag> otherTags){
		List<Tag> newTags1 = pivotTags.size() < 10 ? pivotTags : pivotTags.subList(0, 10);
		List<Tag> newTags2 = otherTags.size() < 10 ? otherTags : otherTags.subList(0,10);
		int max = Stream.concat(newTags1.stream(), newTags2.stream()).toList().stream().mapToInt(Tag::getCount).max().orElseGet(()->1);
		return newTags1.stream().map(newTag ->{
			return ProductResponse.Keyword.builder()
					.keyword(newTag.getKeyword())
					.percentage(changeLogScale(max, newTag.getCount()))
					.count(newTag.getCount())
					.build();
		}
		).toList();
	}

	private double changeLogScale(int max, int value){
		double logValue = Math.log(value+1);
		double logMax = Math.log(max+1);

		return (logValue/logMax) * 100;
	}
}

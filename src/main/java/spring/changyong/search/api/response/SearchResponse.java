package spring.changyong.search.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import spring.changyong.search.domain.model.ProductDocument;
import spring.changyong.search.domain.model.ReviewDocument;
import spring.changyong.search.domain.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

	private static final Logger log = LoggerFactory.getLogger(SearchResponse.class);

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Result<T> {
		private Long searchTimeMs;
		private Slice<T> result;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(title = "상품 검색 결과 조회 응답")
	public static class ProductResult {
		@Schema(description = "상품 ID", example = "A00000000123")
		private String id;
		@Schema(description = "상품명", example = "독도 크림")
		private String name;
		@Schema(description = "가격", example = "20000")
		private Integer price;

		@Schema(description = "할인가격", example = "16000")
		private Integer discountPrice;

		@Schema(description = "썸네일 이미지", example = "http://image.com")
		private String imageUrl;

		@Schema(description = "매칭된 태그", example = "촉촉하다")
		private String matchedTag;

		@Schema(description = "태그", example = "['촉촉하다', '진정']")
		private List<String> tags;

		@Schema(description = "검색결과의 관련성 점수 NaN일수도 있음", example = "4.8")
		private Float score;

		public static ProductResult from(SearchHit<ProductDocument> searchHit) {
			SearchHits<?> positiveTags = searchHit.getInnerHits("positiveTags");
			List<String> keywordList = new ArrayList<>(searchHit.getContent().getPositiveTags().stream().map(Tag::getKeyword).toList());
			List<String> matchTagList = new ArrayList<>();

			if (positiveTags != null) {
				positiveTags.forEach(hits -> {
					Tag content = (Tag) hits.getContent();
					matchTagList.add(content.getKeyword());
				});
				keywordList.removeAll(matchTagList);
				matchTagList.forEach(keywordList::addFirst);
			}
			return ProductResult
					.builder()
					.name(searchHit.getContent().getName())
					.imageUrl(searchHit.getContent().getThumbnail())
					.price(searchHit.getContent().getPrice())
					.discountPrice(searchHit.getContent().getDiscountPrice())
					.id(searchHit.getContent().getProductId())
					.score(searchHit.getScore())
					.tags(keywordList.subList(0, 3))
					.matchedTag(matchTagList.isEmpty() ? null : matchTagList.getFirst())
					.build();
		}
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ReviewResult {
		@Schema(description = "리뷰 ID", example = "1")
		private Long id;

		private String productId;
		@Schema(description = "리뷰 내용", example = "좋아요")
		private String content;
		@Schema(description = "별점", example = "5")
		private Long star;
		@Schema(description = "추천수", example = "10")
		private Long recommend;
		@Schema(description = "긍부정 정보", example = "true")
		private boolean sentiment;
		@Schema(description = "닉네임", example = "테스터")
		private String nickname;
		@Schema(description = "피부 정보", example = "건성")
		private String userSkinInfo;
		@Schema(description = "평가", example = "좋아요")
		private String evaluation;

		public static ReviewResult from(SearchHit<ReviewDocument> searchHit) {
			return ReviewResult
					.builder()
					.id(searchHit.getContent().getId())
					.productId(searchHit.getContent().getProductId())
					.content(searchHit.getHighlightField("review").getFirst())
					.star(searchHit.getContent().getStar())
					.recommend(searchHit.getContent().getRecommend())
					.sentiment(false)
					.nickname(searchHit.getContent().getNickname())
					.userSkinInfo(searchHit.getContent().getUserSkinInfo())
					.evaluation(searchHit.getContent().getEvaluation())
					.build();
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AutoComplete {
		private List<String> productNames;
	}
}

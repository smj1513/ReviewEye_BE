package spring.changyong.review.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.changyong.search.domain.model.ReviewDocument;

public class ReviewResponse {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "리뷰 조회 응답")
	public static class Result {
		private Long id;
		private String content;
		private String nickname;
		private String userSkinInfo;
		private String evaluation;
		private Long star;
		private Long recommend;
		private boolean sentiment;

		public static Result from(ReviewDocument review) {
			return Result.builder()
					.id(review.getId())
					.content(review.getReview())
					.nickname(review.getNickname())
					.userSkinInfo(review.getUserSkinInfo())
					.evaluation(review.getEvaluation())
					.star(review.getStar())
					.recommend(review.getRecommend())
					.sentiment(review.getSentiment() == 1) // 1이 긍정 0이 부정
					.build();
		}
	}
}

package spring.changyong.review.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.changyong.search.domain.model.ReviewDocument;

import java.time.LocalDate;

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
		private LocalDate createdAt;
	//	private boolean sentiment;

		public static Result from(ReviewDocument review) {
			return Result.builder()
					.id(review.getId())
					.content(review.getReview())
					.nickname(review.getNickname())
					.userSkinInfo(review.getUserSkinInfo())
					.evaluation(review.getEvaluation())
					.star(review.getStar())
					.recommend(review.getRecommend())
					.createdAt(review.getDate())
					.build();
		}
	}
}

package spring.changyong.review.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.changyong.review.domain.model.Review;

public class ReviewResponse {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "리뷰 조회 응답")
	public static class Result {
		private String nickname;
		private Long id;
		private String content;
		private Double useFulPoint;

		public static Result from(Review review){
			return Result.builder()
					.id(review.getId().longValue())
					.nickname(review.getNickname())
					.content(review.getReview())
					.useFulPoint(review.getUsefulPoint())
					.build();
		}
	}
}

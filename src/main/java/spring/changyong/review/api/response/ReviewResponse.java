package spring.changyong.review.api.response;

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

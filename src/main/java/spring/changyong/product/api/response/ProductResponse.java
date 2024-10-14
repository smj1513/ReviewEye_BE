package spring.changyong.product.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.changyong.search.domain.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class ProductResponse {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(title = "상품 상세 정보 조회 응답")
	public static class Detail {

		@Schema(description = "상품 ID", example = "1")
		private String id;

		@Schema(description = "상품명", example = "아이폰 12")
		private String title;

		@Schema(description = "브랜드", example = "Apple")
		private String brand;


		@Schema(description = "가격", example = "900,000")
		private Integer price;

		@Schema(description = "할인 가격", example = "900,000")
		private Integer discountPrice;


		@Schema(description = "상품 이미지 URL", example = "http://image.com")
		private String thumbnail;

		@Schema(description = "키워드", example = "['keyword1', 'keyword2']")
		@Builder.Default
		private List<String> keywords = new ArrayList<>();

	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "상품 긍정 키워드 조회 응답")
	public static class PositiveKeyword {

		@Builder.Default
		@Schema(description = "긍정 키워드", example = "['keyword1', 'keyword2']")
		private List<Keyword> keywords = new ArrayList<>();
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "상품 부정 키워드 조회 응답")
	public static class NegativeKeyword {

		@Builder.Default
		@Schema(description = "부정 키워드", example = "['keyword1', 'keyword2']")
		private List<Keyword> keywords = new ArrayList<>();
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Keyword{
		private String keyword;
		private double percentage;
		private int count;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "상품 평가 조회 응답")
	public static class Evaluation {
		@Schema(description = "평가 항목", example = "피부 타입")
		private String title;
		@Builder.Default
		@Schema(description = "평가 상세", example = "[{'content':'건성', 'count':10}, {'content':'지성', 'count':20}]")
		private List<EvaluationDetails> evaluationDetails = new ArrayList<>();
	}


	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "상품 평가 상세")
	public static class EvaluationDetails{
		@Schema(description = "평가 내용", example = "건성에 좋아요")
		private String content;
		@Schema(description = "평가 수", example = "823")
		private Integer count;
	}
}

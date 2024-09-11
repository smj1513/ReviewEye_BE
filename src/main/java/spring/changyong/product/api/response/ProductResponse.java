package spring.changyong.product.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

		@Schema(description = "가격", example = "1,000,000")
		private String productId;


		@Schema(description = "상품 이미지 URL", example = "http://image.com")
		private String thumbnail;

		@Schema(description = "키워드", example = "['keyword1', 'keyword2']")
		private List<String> keywords;

	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "상품 긍정 키워드 조회 응답")
	public static class PositiveKeyword {

		@Schema(description = "긍정 키워드", example = "['keyword1', 'keyword2']")
		private List<String> keywords;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(title = "상품 부정 키워드 조회 응답")
	public static class NegativeKeyword {

		@Schema(description = "부정 키워드", example = "['keyword1', 'keyword2']")
		private List<String> keywords;
	}
}

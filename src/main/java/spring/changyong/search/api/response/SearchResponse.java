package spring.changyong.search.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SearchResponse {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Product {
		@Schema(description = "상품 ID", example = "1")
		private Long id;
		@Schema(description = "상품명", example = "독도 크림")
		private String name;
		@Schema(description = "가격", example = "20000")
		private Integer price;

		@Schema(description = "할인가격", example = "16000")
		private Integer discountPrice;

		@Schema(description = "썸네일 이미지", example = "http://image.com")
		private String imageUrl;
		private List<String> keywords;
	}
}

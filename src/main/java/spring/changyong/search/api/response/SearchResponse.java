package spring.changyong.search.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHit;
import spring.changyong.product.domain.model.Product;
import spring.changyong.search.domain.model.ProductDocument;

import java.util.List;

public class SearchResponse {

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
		private List<String> keywords;

		@Schema(description = "검색결과의 관련성 점수", example = "4.8")
		private Float score;

		public static ProductResult from(SearchHit<ProductDocument> searchHit){
			return ProductResult
					.builder()
					.name(searchHit.getContent().getName())
					.imageUrl(searchHit.getContent().getThumbnail())
					.price(searchHit.getContent().getPrice())
					.discountPrice(searchHit.getContent().getDiscountPrice())
					.id(searchHit.getContent().getProductId())
					.score(searchHit.getScore())
					.build();
		}
	}
}

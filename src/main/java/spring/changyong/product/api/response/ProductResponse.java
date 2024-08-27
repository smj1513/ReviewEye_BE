package spring.changyong.product.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductResponse {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Detail {

		private String id;
		private String title;
		private String brand;
		private String productId;
		private String thumbnail;
		private String keyword1;
		private String keyword2;


	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PositiveKeyword {
		private String keyword1;
		private String keyword2;
		private String keyword3;
		private String keyword4;
		private String keyword5;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NegativeKeyword {
		private String keyword1;
		private String keyword2;
		private String keyword3;
		private String keyword4;
		private String keyword5;
	}
}

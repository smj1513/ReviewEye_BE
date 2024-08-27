package spring.changyong.search.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SearchResponse {

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Product {
		private Long id;
		private String name;
		private String description;
		private String imageUrl;
		private String keyword1;
		private String keyword2;
	}
}

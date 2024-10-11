package spring.changyong.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {

	private Integer price;
	@JsonProperty("discount_price")
	private Integer discountPrice;
	@JsonProperty("item_id")
	private String itemId;
}

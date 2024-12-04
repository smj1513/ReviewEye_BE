package spring.changyong.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceResponse {

	private Integer price;
	@JsonProperty("discount_price")
	private Integer discountPrice;
	@JsonProperty("item_id")
	private String itemId;
}

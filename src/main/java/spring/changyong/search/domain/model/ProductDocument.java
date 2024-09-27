package spring.changyong.search.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import spring.changyong.search.SearchProperties;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "product")
@Setting(settingPath = "elasticsearch/product-settings.json")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocument {

	@Id
	@Field(name = "id", type = FieldType.Long)
	private Long id;

	@Field(name = "product_id", type = FieldType.Keyword)
	private String productId;

	@Field(type = FieldType.Text, analyzer = "n_gram_analyzer")
	private String name;

	@Field(type = FieldType.Integer, name = "price")
	private Integer price;

	@Field(type = FieldType.Integer, name = "discount_price")
	private Integer discountPrice;

	@Field(type = FieldType.Keyword)
	private String brand;

	@Field(type = FieldType.Keyword)
	private String category;

	@Field(type = FieldType.Double)
	private Double star;

	@Field(type = FieldType.Text)
	private String imageList;

	@Field(type = FieldType.Text)
	private String thumbnail;

	@Field(type = FieldType.Nested, includeInParent = true)
	@Builder.Default
	private List<ReviewDocument> reviews = new ArrayList<>();

}

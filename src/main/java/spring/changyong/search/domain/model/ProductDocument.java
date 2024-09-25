package spring.changyong.search.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

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

	@Field(type = FieldType.Text)
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

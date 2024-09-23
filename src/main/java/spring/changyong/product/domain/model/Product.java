package spring.changyong.product.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import spring.changyong.review.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "row_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@Id
	private Integer id;

	@Column(name = "product_id")
	private String productId;

	private String name;

	private Integer price;

	private Integer discountPrice;

	private String brand;

	private String category;

	private Double star;

	private String imageList;

	private String thumbnail;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
	@Builder.Default
	private List<Review> reviews = new ArrayList<>();

}

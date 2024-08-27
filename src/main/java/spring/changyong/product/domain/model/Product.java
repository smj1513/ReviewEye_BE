package spring.changyong.product.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import spring.changyong.review.domain.model.Review;

import java.util.List;

@Entity
@Table(name = "row_product")
@Getter
@Setter
public class Product {
	@Id
	private Integer id;

	@Column(name = "product_id")
	private String productId;

	private String name;

	private Integer price;

	private String brand;

	private String category;

	private Double star;

	private String imageList;

	private String thumbnail;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
	private List<Review> reviews;

}

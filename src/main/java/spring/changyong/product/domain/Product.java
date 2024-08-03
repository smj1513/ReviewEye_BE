package spring.changyong.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import spring.changyong.review.domain.Review;

import java.math.BigInteger;
import java.util.List;

@Entity
@Document(indexName = "product")
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

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<Review> reviews;

}

package spring.changyong.search.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import spring.changyong.product.domain.model.Product;
import spring.changyong.review.domain.model.Review;

import java.util.List;
import java.util.Optional;

@Document(indexName = "product")
@Getter
@Setter
public class ProductDocument {

	@Id
	private Long id;

	@Field(name = "product_id")
	private String productId;

	private String name;

	private Integer price;

	private String brand;

	private String category;

	private Double star;

	private String imageList;

	private String thumbnail;

	@Field(name = "discount_price")
	private Integer discountPrice;

	@Field(type = FieldType.Nested)
	private List<ReviewDocument> reviews;

	public static ProductDocument from(Product product){
		ProductDocument productDocument = new ProductDocument();
		productDocument.setId(product.getId().longValue());
		productDocument.setProductId(product.getProductId());
		productDocument.setName(product.getName());
		productDocument.setPrice(product.getPrice());
		productDocument.setBrand(product.getBrand());
		productDocument.setCategory(product.getCategory());
		productDocument.setStar(product.getStar());
		productDocument.setImageList(product.getImageList());
		productDocument.setThumbnail(product.getThumbnail());
		Optional.ofNullable(product.getReviews())
				.ifPresent(reviews ->
						reviews.stream()
								.map(ReviewDocument::from)
								.forEach(productDocument.getReviews()::add)
				);
		return productDocument;
	}
}

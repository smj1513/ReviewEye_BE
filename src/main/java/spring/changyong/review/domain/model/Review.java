package spring.changyong.review.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import spring.changyong.product.domain.model.Product;

@Entity
@Table(name = "labeled_review_2")
@Getter
@Setter
public class Review {
	@Id
	private Long id;

	@Column(name = "review")
	@Field(type = FieldType.Keyword)
	private String review;

	@Column(name = "star")
	private Long star;

	@Column(name = "recommend")
	private Long recommend;

	@Column(name = "sentiment")
	private Long sentiment;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "user_skin_info")
	private String userSkinInfo;

	@Column(name = "evaluation")
	private String evaluation;

	@Field(type = FieldType.Nested)
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private Product product;
}

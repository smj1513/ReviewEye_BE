package spring.changyong.review.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import spring.changyong.product.domain.model.Product;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {
	@Id
	private Long id;

	private String nickname;

	private String review;

	private Long star;

	private String date;

	private String userSkinInfo;

	private String evaluation;

	private String photo_list;

	@Column(name = "recommend")
	private Long recommend;

	private Double usefulPoint;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private Product product;
}

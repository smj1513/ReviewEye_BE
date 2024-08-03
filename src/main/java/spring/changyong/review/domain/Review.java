package spring.changyong.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import spring.changyong.product.domain.Product;

@Entity
@Table(name = "row_review")
@Document(indexName = "review")
@Getter
@Setter
public class Review {
	@Id
	private Integer id;

	private String nickname;

	private String review;

	private String star;

	private String date;

	private String userSkinInfo;

	private String evaluation;

	private String photo_list;

	@Column(name = "recommend")
	private String recommend;

	private String usefulPoint;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private Product product;
}

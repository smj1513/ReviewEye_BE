package spring.changyong.search.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import spring.changyong.review.domain.model.Review;

@Getter
@Setter
@Document(indexName = "review")
public class ReviewDocument {

	@Id
	private Long id;

	@Field(type = FieldType.Text)
	private String nickname;

	private String review;

	private Long star;

	private String date;

	private String userSkinInfo;

	private String evaluation;

	private String photo_list;

	private Long recommend;

	private Double usefulPoint;

	public static ReviewDocument from(Review review){
		ReviewDocument reviewDocument = new ReviewDocument();
		reviewDocument.setId(review.getId().longValue());
		reviewDocument.setNickname(review.getNickname());
		reviewDocument.setReview(review.getReview());
		reviewDocument.setStar(review.getStar());
		reviewDocument.setDate(review.getDate());
		reviewDocument.setUserSkinInfo(review.getUserSkinInfo());
		reviewDocument.setEvaluation(review.getEvaluation());
		reviewDocument.setPhoto_list(review.getPhoto_list());
		reviewDocument.setRecommend(review.getRecommend());
		reviewDocument.setUsefulPoint(review.getUsefulPoint());
		return reviewDocument;
	}
}

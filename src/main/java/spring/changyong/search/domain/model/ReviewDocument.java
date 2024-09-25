package spring.changyong.search.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "review")
@Setting(settingPath = "elasticsearch/review-settings.json")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDocument {
	@Id
	@Field(name = "id", type = FieldType.Long)
	private Long id;

	@Field(name = "product_id", type = FieldType.Keyword)
	private String productId;

	@Field(name = "review", type = FieldType.Text)
	private String review;

	@Field(name = "star", type = FieldType.Long)
	private Long star;

	@Field(name = "recommend", type = FieldType.Long)
	private Long recommend;

	@Field(name = "sentiment", type = FieldType.Long)
	private Long sentiment;

	@Field(name = "nickname", type = FieldType.Keyword)
	private String nickname;

	@Field(name = "user_skin_info", type = FieldType.Text)
	private String userSkinInfo;

	@Field(name = "evaluation", type = FieldType.Text)
	private String evaluation;
}

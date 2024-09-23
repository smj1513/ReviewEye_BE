package spring.changyong.search.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Setting(settingPath = "elasticsearch/elastic-settings.json")
@Builder
public class ReviewDocument {
	@Id
	@Field
	private Long id;

	@Field(name = "product_id")
	private String productId;

	@Field(analyzer = "nori_tokenizer")
	private String review;

	@Field
	private Long star;

	@Field
	private Long recommend;

	@Field
	private Long sentiment;

	@Field
	private String nickname;

	@Field(name = "user_skin_info", type = FieldType.Text)
	private String userSkinInfo;

	@Field(name = "evaluation", type = FieldType.Text)
	private String evaluation;
}

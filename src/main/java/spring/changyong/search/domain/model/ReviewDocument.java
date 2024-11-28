package spring.changyong.search.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.search.SearchProperties;

import java.time.LocalDate;
import java.util.Map;

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

	@Field(name = "review", type = FieldType.Text, analyzer = SearchProperties.NORI_ANALYZER)
	private String review;

	@Field(name = "star", type = FieldType.Long)
	private Long star;

	@Field(name = "recommend", type = FieldType.Long)
	private Long recommend;

	@Field(name = "nickname", type = FieldType.Keyword)
	private String nickname;

	@Field(name = "user_skin_info", type = FieldType.Text)
	private String userSkinInfo;

	@Field(name = "evaluation", type = FieldType.Text)
	private String evaluation;

	@Field(name = "sentiment", type = FieldType.Boolean)
	private boolean sentiment;

	@Field(name = "date", type = FieldType.Date, pattern = "yyyy.MM.dd")
	private LocalDate date;
}

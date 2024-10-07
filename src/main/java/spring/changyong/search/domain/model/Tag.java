package spring.changyong.search.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.TermVector;

@Document(indexName = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
	@Field(type = FieldType.Keyword, name = "keyword")
	private String keyword;

	@Field(type = FieldType.Integer, name = "count")
	private int count;


}

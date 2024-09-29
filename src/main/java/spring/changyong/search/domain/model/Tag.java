package spring.changyong.search.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
	@Field(type = FieldType.Keyword)
	private String keyword;

	@Field(type = FieldType.Integer)
	private int count;

	@Field(type = FieldType.Dense_Vector, similarity = "cosine", dims = 128)
	private float[] termVector;

}

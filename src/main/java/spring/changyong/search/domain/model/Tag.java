package spring.changyong.search.domain.model;

import co.elastic.clients.elasticsearch._types.mapping.IndexOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

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

	@Field(type = FieldType.Dense_Vector, name = "embedding", dims = 768, similarity = "cosine")
	private float[] embedding;

}

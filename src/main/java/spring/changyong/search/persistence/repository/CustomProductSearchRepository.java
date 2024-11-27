package spring.changyong.search.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import spring.changyong.search.domain.model.ProductDocument;

import java.util.Map;

public interface CustomProductSearchRepository {
	SearchHits<ProductDocument> searchByName(String name, Pageable pageable);
	SearchHits<ProductDocument> searchByTag(String keyword, Pageable pageable);
	void updateBulkDocuments(Map<String, Document> update);
	SearchHits<ProductDocument> searchAutoComplete(String keyword, Pageable pageable);
	SearchHits<ProductDocument> searchSimilarityKeyword(String keyword, Pageable pageable);
	Page<ProductDocument> searchWithSource(String[] source, Pageable pageable);

}

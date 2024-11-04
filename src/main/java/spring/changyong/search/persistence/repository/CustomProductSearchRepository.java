package spring.changyong.search.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import spring.changyong.search.domain.model.ProductDocument;

import java.util.List;

public interface CustomProductSearchRepository {
	SearchHits<ProductDocument> searchByName(String name, Pageable pageable);
	SearchHits<ProductDocument> searchByTag(String keyword, Pageable pageable);
	void updateDocuments(Iterable<ProductDocument> productDocument);
	SearchHits<ProductDocument> searchAutoComplete(String keyword, Pageable pageable);
}

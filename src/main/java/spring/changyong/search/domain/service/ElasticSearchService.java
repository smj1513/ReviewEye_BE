package spring.changyong.search.domain.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

	private final ElasticsearchClient esClient;

	private final ElasticsearchOperations elasticsearchOperations;


}

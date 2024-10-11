package spring.changyong.search.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import spring.changyong.timer.ExecutionTimeHolder;

@Component
@RequiredArgsConstructor
public class SearchUtils {
	private final ElasticsearchOperations elasticsearchOperations;

	public <T> SearchHits<T> searchWithTimer(Query query, Class<T> cls) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SearchHits<T> search = elasticsearchOperations.search(query, cls);
		stopWatch.stop();
		ExecutionTimeHolder.set(stopWatch.getTotalTimeMillis());
		return search;
	}

}

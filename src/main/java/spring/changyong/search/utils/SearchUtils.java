package spring.changyong.search.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;

@Component
@RequiredArgsConstructor
public class SearchUtils {
	private final ElasticsearchOperations elasticsearchOperations;
	private final ObjectMapper om;

	public <T> SearchHits<T> searchWithTimer(Query query, Class<T> cls) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SearchHits<T> search = elasticsearchOperations.search(query, cls);
		stopWatch.stop();
		ExecutionTimeHolder.set(stopWatch.getTotalTimeMillis());
		return search;
	}

	public <T> String convertObjectToJson(T obj) {
		try {
			return om.writeValueAsString(obj);
		} catch (Exception e) {
			throw new BusinessLogicException(ErrorCode.INTENAL_SERVER_ERROR,e.getMessage());
		}
	}



}

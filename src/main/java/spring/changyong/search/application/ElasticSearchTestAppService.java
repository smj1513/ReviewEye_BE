package spring.changyong.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.changyong.search.domain.repository.ReviewSearchRepository;

@Service
@RequiredArgsConstructor
public class ElasticSearchTestAppService {
	private final ReviewSearchRepository reviewSearchRepository;


}

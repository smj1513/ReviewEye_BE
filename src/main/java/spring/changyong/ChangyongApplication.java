package spring.changyong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = {spring.changyong.search.domain.repository.ProductSearchRepository.class,
				spring.changyong.search.domain.repository.ReviewSearchRepository.class}
))
@SpringBootApplication
@EnableScheduling
public class ChangyongApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChangyongApplication.class, args);
	}

}

package spring.changyong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = {spring.changyong.search.domain.repository.ProductSearchRepository.class,
				spring.changyong.search.domain.repository.ReviewSearchRepository.class}
))
@SpringBootApplication
public class ChangyongApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChangyongApplication.class, args);
	}

}

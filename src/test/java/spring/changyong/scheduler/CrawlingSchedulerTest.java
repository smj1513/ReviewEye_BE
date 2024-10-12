package spring.changyong.scheduler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
@Profile("dev")
class CrawlingSchedulerTest {

	@Autowired
	private CrawlingScheduler crawlingScheduler;

	@Test
	void updatePrice() throws Exception {
		crawlingScheduler.updatePrice();
	}
}
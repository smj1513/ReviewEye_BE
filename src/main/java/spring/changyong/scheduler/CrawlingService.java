package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.changyong.scheduler.dto.PriceResponse;

import java.net.URI;

@Service
@Log4j2
@RequiredArgsConstructor
public class CrawlingService {
	private final RestTemplate restTemplate;

	@Value("${crawling.base-url}")
	private String baseUrl;

	public PriceResponse getPrice(String productId){
		URI uri = URI.create(baseUrl+"/get_price/" + productId);
		ResponseEntity<PriceResponse> exchange = restTemplate.exchange(uri, HttpMethod.GET, null, PriceResponse.class);
		log.info(exchange.getBody());
		return exchange.getBody();
	}
}


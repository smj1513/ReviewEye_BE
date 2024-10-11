package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import spring.changyong.scheduler.dto.PriceResponse;
import spring.changyong.search.domain.model.ProductDocument;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CrawlingService {
	private final RestTemplate restTemplate;

	@Value("${crawling.base-url}")
	private String baseUrl;

	public void updateProduct(ProductDocument productDocument){
		URI uri = URI.create(baseUrl+"/get_price/" + productDocument.getProductId());
		ResponseEntity<PriceResponse> exchange = restTemplate.exchange(uri, HttpMethod.GET, null, PriceResponse.class);
		Optional.ofNullable(exchange.getBody()).ifPresent(priceResponse -> {
			productDocument.setPrice(priceResponse.getPrice());
			productDocument.setDiscountPrice(priceResponse.getDiscountPrice());
			log.info("productDocument: {}", productDocument);
		});
	}
}


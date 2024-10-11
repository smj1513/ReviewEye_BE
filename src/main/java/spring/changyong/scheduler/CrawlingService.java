package spring.changyong.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.changyong.search.domain.model.ProductDocument;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrawlingService {
	private final RestTemplate restTemplate;

	@Value("${crawling.base-url}")
	private String baseUrl;

	public ProductDocument updateProduct(ProductDocument productDocument){
		URI uri = URI.create(baseUrl+"/get_price/" + productDocument.getProductId());

		return null;
	}
}


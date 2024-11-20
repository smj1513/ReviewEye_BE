package spring.changyong.search.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.changyong.common.api.code.SuccessCode;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.docs.controller.SearchDocsController;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.application.SearchAppService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class SearchController implements SearchDocsController {

	private final SearchAppService searchService;

	@GetMapping("/products/keyword/search")
	public CommonResponse<SearchResponse.Result<SearchResponse.ProductResult>> searchProductByKeyword(@RequestParam String keyword,
	                                                                                  @RequestParam(defaultValue = "0") int page,
	                                                                                  @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.searchProductByKeyword(URLDecoder.decode(keyword, StandardCharsets.UTF_8), page, size));
	}

	@GetMapping("/products/name/search")
	public CommonResponse<SearchResponse.Result<SearchResponse.ProductResult>> searchProductByName(@RequestParam String name,
	                                                                               @RequestParam(defaultValue = "0") int page,
	                                                                               @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.searchProductByName(URLDecoder.decode(name, StandardCharsets.UTF_8), page, size));
	}

	@GetMapping("/products/{id}/reviews/search")
	public CommonResponse<SearchResponse.Result<SearchResponse.ReviewResult>> searchReview(@PathVariable String id,
	                                                                       @RequestParam String keyword,
	                                                                       @RequestParam(defaultValue = "0") int page,
	                                                                       @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.searchReviewByProductId(id, URLDecoder.decode(keyword, StandardCharsets.UTF_8), page, size));
	}

	@GetMapping("/products/name/auto-complete")
	public CommonResponse<Page<SearchResponse.AutoComplete>> autoCompleteQuery(@RequestParam String prefix,
	                                                                           @RequestParam(defaultValue = "0") int page,
	                                                                           @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.autoCompleteQuery(URLDecoder.decode(prefix, StandardCharsets.UTF_8), page, size));
	}

	@GetMapping("/products/similarity/search")
	public CommonResponse<SearchResponse.Result<SearchResponse.ProductResult>> searchSimilarity(
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.searchSimilarityQuery(URLDecoder.decode(keyword, StandardCharsets.UTF_8), page,size));
	}
}

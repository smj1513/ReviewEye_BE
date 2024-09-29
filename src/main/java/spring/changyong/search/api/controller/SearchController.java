package spring.changyong.search.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.changyong.common.api.code.SuccessCode;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.docs.controller.SearchDocsController;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.application.SearchAppService;

@RestController
@RequiredArgsConstructor
public class SearchController implements SearchDocsController {

	private final SearchAppService searchService;

	@GetMapping("/products/keyword/search")
	public CommonResponse<Slice<SearchResponse.ProductResult>> searchProductByKeyword(@RequestParam String keyword,
	                                                                                  @RequestParam(defaultValue = "0") int page,
	                                                                                  @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.searchProductByKeyword(keyword, page, size));
	}

	@GetMapping("/products/name/search")
	public CommonResponse<Slice<SearchResponse.ProductResult>> searchProductByName(@RequestParam String name,
	                                                                               @RequestParam(defaultValue = "0") int page,
	                                                                               @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.searchProductByName(name, page, size));
	}

	@GetMapping("/products/{id}/reviews/search")
	public CommonResponse<Slice<SearchResponse.ReviewResult>> searchReview(@PathVariable String id,
	                                                                       @RequestParam String keyword,
	                                                                       @RequestParam(defaultValue = "0") int page,
	                                                                       @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.searchReviewByProductId(id, keyword, page, size));
	}
}

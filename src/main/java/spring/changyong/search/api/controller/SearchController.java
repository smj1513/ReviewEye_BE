package spring.changyong.search.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.changyong.common.api.code.SuccessCode;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.search.api.response.SearchResponse;
import spring.changyong.search.application.SearchAppService;

@RestController
@RequiredArgsConstructor
public class SearchController {

	private final SearchAppService searchService;

	@GetMapping("/product/search")
	public CommonResponse<Page<SearchResponse.Product>> productSearch(@RequestParam String keyword,
	                                                                  @RequestParam(defaultValue = "0") int page,
	                                                                  @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.productSearch(keyword, page, size));
	}
}

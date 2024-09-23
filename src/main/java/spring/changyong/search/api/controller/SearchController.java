package spring.changyong.search.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
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
	public CommonResponse<Slice<SearchResponse.ProductResult>> productSearchByKeyword(@RequestParam String keyword,
	                                                                                  @RequestParam(defaultValue = "0") int page,
	                                                                                  @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.productSearchByKeyword(keyword, page, size));
	}

	@GetMapping("/products/name/search")
	public CommonResponse<Slice<SearchResponse.ProductResult>> productSearchByName(@RequestParam String name,
	                                                                               @RequestParam(defaultValue = "0") int page,
	                                                                               @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, searchService.productSearchByName(name, page, size));
	}
}

package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestParam;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.search.api.response.SearchResponse;

@Tag(name = "검색 API", description = "검색 API")
public interface SearchDocsController {


	@Operation(summary = "상품 검색", description = "상품 검색")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<Slice<SearchResponse.Product>> productSearch(
			@Parameter(description = "검색 키워드")
			@RequestParam String keyword,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size);
}
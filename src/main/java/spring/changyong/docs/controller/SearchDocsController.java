package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import spring.changyong.common.api.code.SuccessCode;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.search.api.response.SearchResponse;

@Tag(name = "검색 API", description = "검색 API")
public interface SearchDocsController {

	@Operation(summary = "리뷰 검색", description = "리뷰 검색")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<SearchResponse.Result<SearchResponse.ReviewResult>> searchReview(
			@Parameter(description = "상품 ID")
			@PathVariable String id,
			@Parameter(description = "검색 키워드")
			@RequestParam String keyword,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size);

	@Operation(summary = "키워드 기반 상품 검색", description = "키워드 기반 상품 검색")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<SearchResponse.Result<SearchResponse.ProductResult>> searchProductByKeyword(
			@Parameter(description = "검색 키워드 (키워드 + 카테고리)")
			@RequestParam String keyword,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size);

	@Operation(summary = "상품명 기반 상품 검색", description = "상품명 기반 상품 검색")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<SearchResponse.Result<SearchResponse.ProductResult>> searchProductByName(
			@Parameter(description = "상품명")
			@RequestParam String name,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size);

	@Operation(summary = "상품명 자동완성", description = "상품명 자동완성")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<Page<SearchResponse.AutoComplete>> autoCompleteQuery(
			@Parameter(description = "검색어")
			@RequestParam String prefix,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size);

	@Operation(summary = "벡터 검색", description = "태그 벡터 기반 검색")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<SearchResponse.Result<SearchResponse.ProductResult>> searchSimilarity(
			@Parameter(description = "검색 키워드 (키워드 + 카테고리)")
			@RequestParam String keyword,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size);
}
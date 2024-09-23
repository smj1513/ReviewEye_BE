package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.PathVariable;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.product.api.response.ProductResponse;

@Tag(name = "상품 API", description = "상품 API")
public interface ProductDocsController {

	@Operation(summary = "상품 상세 조회", description = "상품 상세 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<ProductResponse.Detail> getProductDetail(
			@Parameter(description = "상품 ID")
			@PathVariable String id);

	@Operation(summary = "상품 긍정 키워드 조회", description = "상품 긍정 키워드 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<ProductResponse.PositiveKeyword> getPositiveKeyword(
			@Parameter(description = "상품 ID")
			@PathVariable String id);

	@Operation(summary = "상품 부정 키워드 조회", description = "상품 부정 키워드 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<ProductResponse.NegativeKeyword> getNegativeKeyword(
			@Parameter(description = "상품 ID")
			@PathVariable String id);
}

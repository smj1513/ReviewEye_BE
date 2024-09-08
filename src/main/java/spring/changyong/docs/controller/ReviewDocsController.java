package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.review.api.response.ReviewResponse;

@Tag(name = "리뷰 API", description = "리뷰 API")
public interface ReviewDocsController {

	@Operation(summary = "상품 리뷰 조회", description = "상품 리뷰 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<Slice<ReviewResponse.Result>> getProductReview(
			@Parameter(description = "상품 ID")
			@PathVariable Integer id,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size);
}

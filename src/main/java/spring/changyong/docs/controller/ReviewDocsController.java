package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.review.api.response.ReviewResponse;
import spring.changyong.search.enums.OrderBy;
import spring.changyong.search.enums.ResultFilter;
import spring.changyong.search.enums.SortOption;

@Tag(name = "리뷰 API", description = "리뷰 API")
public interface ReviewDocsController {

	@Operation(summary = "상품 리뷰 조회", description = "상품 리뷰 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<Page<ReviewResponse.Result>> getProductReview(
			@Parameter(description = "상품 ID")
			@PathVariable String id,
			@Parameter(description = "페이지 번호 - 0번부터 시작")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "페이지 크기")
			@RequestParam(defaultValue = "10") int size,
			@Parameter(description = "긍부정 필터 - (POSITIVE, NEGATIVE, BOTH) - 디폴트 BOTH")
			@RequestParam(defaultValue = "both") ResultFilter filter,
			@Parameter(description = "정렬 옵션 - (STAR, DATE,SCORE) - 디폴트 SCORE")
			@RequestParam(defaultValue = "score") SortOption sortOption,
			@Parameter(description = "정렬 순서 - (DESC, ASC) - 디폴트 DESC")
			@RequestParam(defaultValue = "desc") OrderBy orderBy);
}

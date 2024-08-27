package spring.changyong.review.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.changyong.common.api.code.SuccessCode;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.review.api.response.ReviewResponse;
import spring.changyong.review.application.ReviewAppService;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewAppService reviewAppService;

	@GetMapping("/product/{id}/review")
	public CommonResponse<Page<ReviewResponse.Result>> getProductReview(@PathVariable Integer id,
	                                                                    @RequestParam(defaultValue = "0") int page,
	                                                                    @RequestParam(defaultValue = "10") int size)
	{
		return CommonResponse.success(SuccessCode.OK, reviewAppService.getProductReview(id, page, size));
	}
}

package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.review.api.response.ReviewResponse;

@Tag(name = "ReviewDocs", description = "리뷰 API")
public interface ReviewDocsController {
	CommonResponse<Slice<ReviewResponse.Result>> getProductReview(@PathVariable Integer id,
	                                                              @RequestParam(defaultValue = "0") int page,
	                                                              @RequestParam(defaultValue = "10") int size);
}

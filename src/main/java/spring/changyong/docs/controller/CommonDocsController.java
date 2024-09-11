package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import spring.changyong.common.api.response.CommonResponse;

@Tag(name = "공통 응답 API", description = "공통 응답 API")
public interface CommonDocsController {

	@Operation(summary = "공통 응답", description = "공통 응답")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<Void> commonResponse();
}

package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import spring.changyong.common.api.response.CommonResponse;

@Tag(name = "Test", description = "Swagger Test용 API")
public interface TestDocsController {

	@Operation(summary = "Swagger Test용 API", description = "Swagger Test용 API")
	@ApiResponse (responseCode = "200", description = "성공")
	public CommonResponse<String> test(Long id);
}


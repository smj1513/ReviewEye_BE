package spring.changyong.docs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.changyong.common.api.response.CommonResponse;

@RestController
public class CommonDocsControllerImpl implements CommonDocsController{

	@Override
	@GetMapping("/common")
	public CommonResponse<Void> commonResponse() {
		return null;
	}
}

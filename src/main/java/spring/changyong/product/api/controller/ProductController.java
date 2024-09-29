package spring.changyong.product.api.controller;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.changyong.common.api.code.SuccessCode;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.docs.controller.ProductDocsController;
import spring.changyong.product.api.response.ProductResponse;
import spring.changyong.product.application.ProductAppService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductDocsController {
	private final ProductAppService productAppService;

	@GetMapping("/{id}/detail")
	public CommonResponse<ProductResponse.Detail> getProductDetail(@PathVariable String id) {
		return CommonResponse.success(SuccessCode.OK, productAppService.getProductDetail(id));
	}

	@GetMapping("/{id}/positive-keywords")
	public CommonResponse<ProductResponse.PositiveKeyword> getPositiveKeyword(@PathVariable String id) {
		return CommonResponse.success(SuccessCode.OK, productAppService.getPositiveKeyword(id));
	}
	@GetMapping("/{id}/negative-keywords")
	public CommonResponse<ProductResponse.NegativeKeyword> getNegativeKeyword(@PathVariable String id) {
		return CommonResponse.success(SuccessCode.OK, productAppService.getNegativeKeyword(id));
	}
}

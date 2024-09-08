package spring.changyong.docs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.PathVariable;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.product.api.response.ProductResponse;

@Tag(name = "Product", description = "상품 API")
public interface ProductDocsController {
	CommonResponse<ProductResponse.Detail> getProductDetail(@PathVariable Integer id);
	CommonResponse<ProductResponse.PositiveKeyword> getPositiveKeyword(@PathVariable Integer id);
	CommonResponse<ProductResponse.NegativeKeyword> getNegativeKeyword(@PathVariable Integer id);
}

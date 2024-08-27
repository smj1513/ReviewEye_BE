package spring.changyong.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.changyong.common.api.response.CommonResponse;
import spring.changyong.common.exception.BusinessLogicException;
import spring.changyong.common.exception.CustomAuthorizationException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessLogicException.class)
	public ResponseEntity<CommonResponse<Void>> handleBusinessLogicException(BusinessLogicException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonResponse.error(e.getErrorCode()));
	}

	@ExceptionHandler(CustomAuthorizationException.class)
	public ResponseEntity<CommonResponse<Void>> handleCustomLoginException(CustomAuthorizationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.error(e.getErrorCode()));
	}

}

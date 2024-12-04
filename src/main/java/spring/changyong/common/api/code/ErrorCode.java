package spring.changyong.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


	//위에 추가
	INFERENCE_FAILURE(HttpStatus.FAILED_DEPENDENCY, EntityCode.PRODUCT, ExceptionCode.CONFLICT, "Inference Failure"),
	NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, EntityCode.COMMON, ExceptionCode.NOT_FOUND, "Entity Not Found"),
	INTENAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, EntityCode.COMMON, ExceptionCode.INTERNAL_SERVER_ERROR, "Internal Server Error"),
	;

	private final HttpStatus httpStatus;
	private final EntityCode entityCode;
	private final ExceptionCode exceptionCode;

	@Setter
	private String message;

	public Integer getCode(){
		return httpStatus.value() * 10000 + entityCode.getValue() * 100 + exceptionCode.getValue();
	}
}

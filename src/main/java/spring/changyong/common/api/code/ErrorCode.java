package spring.changyong.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


	//위에 추가
	NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, EntityCode.COMMON, ExceptionCode.NOT_FOUND, "Entity Not Found"),;

	private final HttpStatus httpStatus;
	private final EntityCode entityCode;
	private final ExceptionCode exceptionCode;

	@Setter
	private String message;

	public Integer getCode(){
		return httpStatus.value() * 10000 + entityCode.getValue() * 100 + exceptionCode.getValue();
	}
}

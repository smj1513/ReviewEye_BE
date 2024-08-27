package spring.changyong.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SuccessCode {

	OK(HttpStatus.OK, "OK"),
	Created(HttpStatus.CREATED, "Created"),
	Deleted(HttpStatus.OK, "Deleted"),
	Modified(HttpStatus.OK, "Modified");


	private HttpStatus status;

	@Getter
	private String message;

	public Integer getCode() {
		return status.value();
	}

}

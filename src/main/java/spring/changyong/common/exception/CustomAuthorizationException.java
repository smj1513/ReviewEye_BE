package spring.changyong.common.exception;


import spring.changyong.common.api.code.ErrorCode;

public class CustomAuthorizationException extends AbstractErrorException {
	public CustomAuthorizationException(ErrorCode errorCode) {
		super(errorCode);
	}
	public CustomAuthorizationException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

}

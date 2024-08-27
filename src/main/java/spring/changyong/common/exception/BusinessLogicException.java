package spring.changyong.common.exception;


import spring.changyong.common.api.code.ErrorCode;

public class BusinessLogicException extends AbstractErrorException {

	public BusinessLogicException(ErrorCode errorCode) {
		super(errorCode);
	}

	public BusinessLogicException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}

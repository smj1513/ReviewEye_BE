package spring.changyong.common.exception;

import lombok.Getter;
import spring.changyong.common.api.code.ErrorCode;

@Getter
public abstract class AbstractErrorException extends RuntimeException{
	protected final ErrorCode errorCode;

	public AbstractErrorException(ErrorCode errorCode){
		this.errorCode = errorCode;
	}

	public AbstractErrorException(ErrorCode errorCode, String message){
		this.errorCode = errorCode;
		this.errorCode.setMessage(message);
	}
}

package spring.changyong.common.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.api.code.SuccessCode;

@Data
@Builder
public class CommonResponse<T> {
	@JsonProperty("isSuccess")
	private boolean isSuccess;
	private Integer code;
	private String message;

	private T data;

	public static <T> CommonResponse<T> success(SuccessCode successCode, T data) {
		return CommonResponse.<T>builder()
				.isSuccess(true)
				.code(successCode.getCode())
				.message(successCode.getMessage())
				.data(data)
				.build();
	}

	public static <T> CommonResponse<T> error(ErrorCode errorCode) {
		return CommonResponse.<T>builder()
				.isSuccess(false)
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build();
	}

}

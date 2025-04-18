package spring.changyong.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
	COMMON(0),
	NOT_FOUND(1),
	INTERNAL_SERVER_ERROR(2),
	CONFLICT(3),
	BAD_REQUEST(4),
	UNAUTHORIZED(5),
	TOO_MANY_REQUESTS(6),
	PERMANENT_REDIRECT(7);

	private final Integer value;
}

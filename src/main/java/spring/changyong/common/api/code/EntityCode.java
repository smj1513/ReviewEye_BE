package spring.changyong.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityCode {
	COMMON(0),
	REVIEW(1),
	PRODUCT(2),
	USER(3);

	private final Integer value;
}

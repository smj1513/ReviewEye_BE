package spring.changyong.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ResultFilter {
	POSITIVE("positive"),
	NEGATIVE("negative"),
	BOTH("both");

	final String value;
	ResultFilter(String value){
		this.value = value;
	}
}

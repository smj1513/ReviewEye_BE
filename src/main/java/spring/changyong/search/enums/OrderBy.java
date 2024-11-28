package spring.changyong.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderBy {
	ASC("ASC"),
	DESC("DESC");

	final String value;

	OrderBy(String value){
		this.value = value;
	}
}

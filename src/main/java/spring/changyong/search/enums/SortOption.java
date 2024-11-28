package spring.changyong.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SortOption {
	STAR("star"),
	DATE("date"),
	SCORE("_score");

	private final String value;

	SortOption(String value){
		this.value = value;
	}

}

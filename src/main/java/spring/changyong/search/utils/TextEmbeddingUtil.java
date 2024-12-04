package spring.changyong.search.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.changyong.search.service.InferenceService;

import java.util.List;

@Component
public class TextEmbeddingUtil {
	private static InferenceService inferenceService;

	@Autowired
	public void setInferenceService(InferenceService inferenceService){
		TextEmbeddingUtil.inferenceService = inferenceService;
	}

	public static List<Float> embedding(String text){
		return inferenceService.textEmbedding(text);
	}
}

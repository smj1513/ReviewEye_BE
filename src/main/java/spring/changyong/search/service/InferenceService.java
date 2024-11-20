package spring.changyong.search.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch.inference.*;
import co.elastic.clients.elasticsearch.ml.InferTrainedModelRequest;
import co.elastic.clients.elasticsearch.ml.InferTrainedModelResponse;
import co.elastic.clients.elasticsearch.xpack.usage.MlInferenceTrainedModels;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.rest_client.RestClientHttpClient;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.changyong.common.api.code.ErrorCode;
import spring.changyong.common.exception.BusinessLogicException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InferenceService {

	@Value("${model.id}")
	private String modelId;

	private final ElasticsearchClient client;
	public List<Float> textEmbedding(String text) {
		Map<String, JsonData> docs = new HashMap<>();
		docs.put("text_field", JsonData.of(text));
		InferTrainedModelRequest inferTrainedModelRequest = InferTrainedModelRequest.of(itm -> itm.modelId(modelId)
				.docs(docs));
		InferTrainedModelResponse inference = null;
		try {
			inference = client.ml().inferTrainedModel(inferTrainedModelRequest);
		} catch (IOException e) {
			throw new BusinessLogicException(ErrorCode.INFERENCE_FAILURE);
		}
		List<FieldValue> textEmbeddingResults = inference.inferenceResults().getFirst().predictedValue();
		return textEmbeddingResults.stream().map(f->(float)f.doubleValue()).toList();
	}
}

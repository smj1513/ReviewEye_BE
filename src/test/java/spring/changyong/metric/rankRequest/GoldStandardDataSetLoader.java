package spring.changyong.metric.rankRequest;

import co.elastic.clients.elasticsearch.core.rank_eval.DocumentRating;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.changyong.search.domain.repository.ProductSearchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GoldStandardDataSetLoader {

	@Autowired
	private ProductSearchRepository productSearchRepository;

	private static Map<String, List<DocumentRating>> dataset;
	private static Map<String, List<DocumentRating>> vectorDataset;

	@PostConstruct
	private void dataLoader(){
		dataset = new HashMap<>();
		vectorDataset = new HashMap<>();
		List<DocumentRating> documents1 = List.of( //겨울철 토너 레이팅
				DocumentRating.of(document -> document.id("338").index("product").rating(3)),
				DocumentRating.of(document -> document.id("6918").index("product").rating(3)),
				DocumentRating.of(document -> document.id("295").index("product").rating(3)),

				DocumentRating.of(document -> document.id("152").index("product").rating(2)),
				DocumentRating.of(document -> document.id("84").index("product").rating(2)),
				DocumentRating.of(document -> document.id("216").index("product").rating(2)),
				DocumentRating.of(document -> document.id("177").index("product").rating(2)),

				DocumentRating.of(document -> document.id("1827").index("product").rating(1)),
				DocumentRating.of(document -> document.id("190").index("product").rating(1)),
				DocumentRating.of(document -> document.id("41").index("product").rating(1))
		);
		List<DocumentRating> documents2 = List.of(
				// 첫 번째 그룹: 상위 랭크 문서들
				DocumentRating.of(document -> document.id("2808").index("product").rating(2)),  // 상위 결과, count 22로 적절한 넉넉함
				DocumentRating.of(document -> document.id("3713").index("product").rating(2)),  // 두 번째 순위, count 18로 중간 정도의 넉넉함
				DocumentRating.of(document -> document.id("2804").index("product").rating(3)),  // count 28로 높은 넉넉함 평가

				// 두 번째 그룹: 중간 랭크 문서들
				DocumentRating.of(document -> document.id("2682").index("product").rating(1)),  // 기존 rating 유지, 낮은 연관성
				DocumentRating.of(document -> document.id("2707").index("product").rating(1)),  // count 5로 매우 낮은 넉넉함
				DocumentRating.of(document -> document.id("2748").index("product").rating(2)),  // count 25로 적절한 넉넉함
				DocumentRating.of(document -> document.id("2692").index("product").rating(2)),  // 기존 rating 유지, 중간 연관성

				// 세 번째 그룹: 하위 랭크이지만 높은 평가 문서들
				DocumentRating.of(document -> document.id("2724").index("product").rating(3)),  // count 43으로 매우 넉넉함
				DocumentRating.of(document -> document.id("2743").index("product").rating(3)),  // count 77로 최고의 넉넉함 평가
				DocumentRating.of(document -> document.id("2740").index("product").rating(2))   // count 26으로 적절한 넉넉함
		);

		List<DocumentRating> documents3 = List.of(
				// 상위 문서들: 일부만 최고 점수를 부여
				DocumentRating.of(document -> document.id("1928").index("product").rating(2)),  // 58개 리뷰지만 2점으로 하향
				DocumentRating.of(document -> document.id("4267").index("product").rating(3)),  // 26개 리뷰, 높은 점수 유지
				DocumentRating.of(document -> document.id("1914").index("product").rating(2)),  // 41개 리뷰, 2점으로 조정

				// 중간 순위 문서들: 실제 성능 반영
				DocumentRating.of(document -> document.id("1965").index("product").rating(2)),  // 37개 리뷰, 현재 유지
				DocumentRating.of(document -> document.id("1781").index("product").rating(3)),  // 60개 리뷰, 높은 평가로 상향
				DocumentRating.of(document -> document.id("1765").index("product").rating(2)),  // 57개 리뷰, 현재 유지

				// 하위 순위 문서들: 일부는 예상보다 높은 평가
				DocumentRating.of(document -> document.id("1825").index("product").rating(1)),  // 6개 리뷰, 낮은 평가 유지
				DocumentRating.of(document -> document.id("336").index("product").rating(2)),   // 12개 리뷰, 상향 조정
				DocumentRating.of(document -> document.id("1631").index("product").rating(1)),  // 52개 리뷰이지만 하향
				DocumentRating.of(document -> document.id("1627").index("product").rating(1))   // 10개 리뷰, 현재 유지
		);

		List<DocumentRating> documents4 = List.of(
				// 상위 문서들: 높은 검색 점수 반영
				DocumentRating.of(document -> document.id("91").index("product").rating(3)),   // 상위 검색 결과, 핵심 키워드 포함
				DocumentRating.of(document -> document.id("737").index("product").rating(3)),   // 환절기/민감성 관련 높은 점수
				DocumentRating.of(document -> document.id("216").index("product").rating(2)),   // 주요 키워드 포함, 적절한 평가

				// 중간 순위 문서들
				DocumentRating.of(document -> document.id("41").index("product").rating(2)),  // 보습/건성 관련 적절한 평가
				DocumentRating.of(document -> document.id("280").index("product").rating(2)),  // 겨울/여름 모두 커버
				DocumentRating.of(document -> document.id("155").index("product").rating(2)),   // 겨울철 특화 제품
				DocumentRating.of(document -> document.id("277").index("product").rating(2)),   // 건조/건성 관련성

				//
				DocumentRating.of(document -> document.id("1827").index("product").rating(1)),
				DocumentRating.of(document -> document.id("190").index("product").rating(1)),   // 보습/건조 관련
				DocumentRating.of(document -> document.id("106").index("product").rating(1))
		);

		String query1 = "겨울철 토너";
		String query2 = "에센스 넉넉한 토너 패드";
		String query3 = "건성 피부 로션";

		dataset.put(query1, documents1);
		dataset.put(query2, documents2);
		dataset.put(query3, documents3);

		vectorDataset.put(query1, documents4);
	}

	public static List<DocumentRating> getDocumentRatings(String query){
		return dataset.get(query);
	}

	public static List<DocumentRating> getVectorDocumentRating(String query){
		return vectorDataset.get(query);
	}
}

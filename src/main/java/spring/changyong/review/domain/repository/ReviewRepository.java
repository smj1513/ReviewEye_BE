package spring.changyong.review.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.changyong.review.domain.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("""
	SELECT R
	FROM Review R
	WHERE R.product.id = :productId
	ORDER BY R.usefulPoint DESC
	""")
	Slice<Review> findByProductId(@Param("productId") Integer productId, Pageable pageable);


}

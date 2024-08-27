package spring.changyong.review.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.changyong.review.domain.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("""
	SELECT R
	FROM Review R
	""")
	Page<Review> findReviews(Pageable pageable);
}

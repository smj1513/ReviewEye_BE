package spring.changyong.review.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.changyong.review.domain.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}

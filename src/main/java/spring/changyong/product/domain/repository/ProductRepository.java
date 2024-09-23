package spring.changyong.product.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.changyong.product.domain.model.Product;
import spring.changyong.search.domain.model.ProductDocument;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

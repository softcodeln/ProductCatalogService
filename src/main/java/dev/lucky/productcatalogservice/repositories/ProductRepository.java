package dev.lucky.productcatalogservice.repositories;

import dev.lucky.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByPriceBetween(Double priceAfter, Double priceBefore);

    List<Product> findAllByOrderByPrice();

    /**
     * Custom @Query method - read-only operation
     * Transactional with readOnly = true improves performance by:
     * - Telling Hibernate this won't modify data (no flush needed)
     * - Allowing database to optimize the query
     */
    @Transactional(readOnly = true)
    @Query("Select p.description From Product p Where p.id = :id")
    String getDescriptionWhereIdIs(@Param("id") long id);

    Page<Product> findByName(String query, Pageable pageable);

    Optional<Product> getProductsById(Long id);
}

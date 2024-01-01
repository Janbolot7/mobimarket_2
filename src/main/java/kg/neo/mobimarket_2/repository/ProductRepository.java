package kg.neo.mobimarket_2.repository;


import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("SELECT p FROM ProductDto p WHERE p.user.user_id = :userId")
//    List<ProductDto> findAllByUserId(int userId);
//@Query("SELECT p FROM ProductDto p WHERE p.user = :user")@Param("user")
List<Product> findAllByUser(User user);
}

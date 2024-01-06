package kg.neo.mobimarket_2.repository;


import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByUser_userId(Integer user_id);

}

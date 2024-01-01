package kg.neo.mobimarket_2.repository;

import kg.neo.mobimarket_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
    User findByPhoneNumber(String phoneNumber);

}

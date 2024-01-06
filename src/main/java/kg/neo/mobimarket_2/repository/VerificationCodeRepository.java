package kg.neo.mobimarket_2.repository;


import kg.neo.mobimarket_2.model.ActivationCode;
import kg.neo.mobimarket_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<ActivationCode, Integer> {
    ActivationCode findByEmail(String email);
    ActivationCode findByEmailAndUser(String email, User user);
    void deleteEmail(String email);

}

package kg.neo.mobimarket_2.repository;


import kg.neo.mobimarket_2.model.User;
import kg.neo.mobimarket_2.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    VerificationCode findByPhoneNumber(String phoneNumber);
    VerificationCode findByPhoneNumberAndUser(String phoneNumber, User user);
    void deleteByPhoneNumber(String phoneNumber);

}

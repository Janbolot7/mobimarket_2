//package kg.neo.mobimarket_2.sms;
//
//import kg.neo.mobimarket_2.model.ActivationCode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//public class ActivationCodeService {
//    private final ActivationCodeRepository activationCodeRepository;
//
////    public ActivationCode generateActivationToken() {
////        ActivationCode activationToken = new ActivationCode(user);
////        return activationTokenRepository.save(activationToken);
////    }
//        public String generateActivationToken() {
//        Random random = new Random();
//        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit code
//        return String.valueOf(code);
//    }
//
//    public Optional<ActivationCode> getActivationTokenByToken(String code) {
//        return activationCodeRepository.findByCode(code);
//    }
//
//    public void deleteActivationToken(ActivationCode activationCode) {
//        activationCodeRepository.delete(activationCode);
//    }
//}

//package kg.neo.mobimarket_2.sms;
//
//import kg.neo.mobimarket_2.model.ActivationCode;
//import kg.neo.mobimarket_2.model.User;
//import kg.neo.mobimarket_2.repository.UserRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.Optional;
//
//public class EmService {
//    public UserRepository userRepository;
//    public ActivationCodeService activationCodeService;
//    public EmailService emailService;
//    public ResponseEntity<?> createNewUser(@RequestBody String email) {
//        ActivationCode activationCode = activationCodeService.generateActivationToken();
//    sendActivationEmail(email, activationCode.getCode());
//        return ResponseEntity.ok().body("Activation code sent to email: " + email);
//    }
//
//
//    public void activateUserByToken(String token) {
//        Optional<ActivationCode> tokenOptional = activationCodeService.getActivationTokenByToken(token);
//        if (tokenOptional.isPresent()) {
//            ActivationCode activationToken = tokenOptional.get();
//            User user = activationToken.getUser();
//            user.setEnabled(true);
//            userRepository.save(user);
//            activationCodeService.deleteActivationToken(activationToken);
//        }else throw new IllegalArgumentException("Activation code not found");
//    }
//
//    public void sendActivationEmail(String email, String code) {
//        String activationLink = "Activation code sent to email: " + code;
//        emailService.sendEmail(email, activationLink);
//    }
//        }

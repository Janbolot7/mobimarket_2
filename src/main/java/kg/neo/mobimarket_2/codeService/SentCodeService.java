package kg.neo.mobimarket_2.codeService;

//@Service
//@RequiredArgsConstructor
//public class SentCodeService {
//    public void activateUserByToken(String token) {
//        Optional<ActivationToken> tokenOptional = activationTokenService.getActivationTokenByToken(token);
//        if (tokenOptional.isPresent()) {
//            ActivationToken activationToken = tokenOptional.get();
//            User user = activationToken.getUser();
//            user.setEnabled(true);
//            userRepository.save(user);
//            activationTokenService.deleteActivationToken(activationToken);
//        }else throw new IllegalArgumentException("Activation token not found");
//    }
////
////    public void sendActivationEmail(String email, String token) {
////        String activationLink = "http://68.183.64.48:8081/auth/activate?token=" + token;
////        emailService.sendEmail(email, activationLink);
////    }
////}
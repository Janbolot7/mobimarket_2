package kg.neo.mobimarket_2.sms.smsSender;


import kg.neo.mobimarket_2.model.ActivationCode;
import kg.neo.mobimarket_2.repository.UserRepository;
import kg.neo.mobimarket_2.repository.VerificationCodeRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsService {
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final ActivationCode activationCode;

    public SmsService(VerificationCodeRepository verificationCodeRepository, UserRepository userRepository, JavaMailSender javaMailSender, ActivationCode activationCode) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.activationCode = activationCode;
    }

//    public void sendVerificationCode(String phoneNumber) {
//        String verificationCode = generateVerificationCode();
//        LocalDateTime currentTime = LocalDateTime.now();
//        LocalDateTime expirationTime = currentTime.plusMinutes(5);
//
//
//        User user = userRepository.findByPhoneNumber(phoneNumber);
//        if (user != null) {
//            VerificationCode Verificationcode = new VerificationCode();
//            Verificationcode.setCode(verificationCode);
//            Verificationcode.setPhoneNumber(phoneNumber);
//            Verificationcode.setUser(user);
//            verificationCodeRepository.save(Verificationcode);
//        }
//
//
//        String message = "Your verification code is: " + verificationCode;
//        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);
//        smsSender.sendSms(smsRequest);
//    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit code
        return String.valueOf(code);
    }

    public void sendActivationEmail(String email, String generateVerificationCode) {
        String activationCode = "Copy your code and enter to confirm" + generateVerificationCode();
        sendEmail(email, activationCode);
    }

    public void sendEmail(String toEmail, String activationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Активируйте свой аккаунт");
        message.setText("Для активации Вашего аккаунта, пройдите по ссылке: \n\n" + activationLink);
        javaMailSender.send(message);
    }
}

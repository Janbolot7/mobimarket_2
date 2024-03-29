package kg.neo.mobimarket_2.service.Impl;

import kg.neo.mobimarket_2.dto.UserFullDto;
import kg.neo.mobimarket_2.exceptions.RecordNotFoundException;
import kg.neo.mobimarket_2.model.ActivationCode;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import kg.neo.mobimarket_2.repository.ProductRepository;
import kg.neo.mobimarket_2.repository.UserRepository;
import kg.neo.mobimarket_2.repository.VerificationCodeRepository;
import kg.neo.mobimarket_2.request.RegisterRequest;
import kg.neo.mobimarket_2.service.CloudinaryService;
import kg.neo.mobimarket_2.service.ProductService;
import kg.neo.mobimarket_2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final CloudinaryService cloudinaryService;

//    @Value(value = "${gmail}")
//    private String gmail;

//    private final JavaMailSender javaMailSender;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<User> updateFullDateOfUser(UserFullDto user, MultipartFile file) {
        User userInDB;
        try {
            userInDB = userRepository.findById(user.getUser_id()).orElseThrow(()
                    -> new IllegalStateException("User with id " + user.getUser_id() + " not found!"));

            userInDB.setFirstName(user.getFirstName());
            userInDB.setLastName(user.getLastName());
            userInDB.setPhoneNumber(user.getPhoneNumber());
            userInDB.setBirthDate(user.getBirthDate());

            if (file != null && !file.isEmpty()) {
                String profileImageUrl = cloudinaryService.uploadImage(file);
                userInDB.setAvatar(profileImageUrl);
            }


            userRepository.save(userInDB);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(userInDB);
    }

    @Override
    public void deleteUser(int id) {

        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateEmail(int userId, String newEmail) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (findByEmailAndVerified(newEmail)) {
                return ResponseEntity.badRequest().body("Email is already in use by another verified user.");
            }

            user.setEmail(newEmail);
            user.setVerified(false);
            userRepository.save(user);

            return ResponseEntity.ok("Email number was updated.");
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }
        return null;
    }

    @Override
    public User addOrRemoveFavoriteProduct(int userId, int productId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        Product product = productService.getProductByIdd(productId);

        if (product == null)
            return null;


        if (user.getFavoriteProducts().contains(product)) {
            user.getFavoriteProducts().remove(product);
            product.decrementLikeCount();

        } else {
            user.getFavoriteProducts().add(product);
            product.incrementLikeCount();
        }
        return userRepository.save(user);
    }

    @Override
    public boolean verifyEmail(String email, String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            ActivationCode verificationCode = verificationCodeRepository.findByEmailAndUser(email, user);

            if (verificationCode == null || !verificationCode.getCode().equals(code)) {
                System.out.println("Code is not valid !");
                return false;
            }

            user.setVerified(true);

            userRepository.save(user);

            verificationCodeRepository.delete(verificationCode);

            return true;
        } else {
            System.out.println("User is null");
            return false;
        }
    }

    @Override
    public boolean doesUserExistByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public boolean doesUserExistByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.isPresent();
    }

    //    @Override
//    public List<Product> findAllUserProducts(User user_id) {
//        return productRepository.findAllByUserId(user_id);
//    }
    /////////////////////78347848473487784378738478
    @Override
    public List<Product> findAllUserProducts(User user) {
        return productRepository.findAllByUser(user);
    }

    @Override
    public boolean findByEmailAndVerified(String newEmail) {
        Optional<User> userOptional = userRepository.findByEmail(newEmail);

        return userOptional.map(User::getVerified).orElse(false);
    }

//    public void sendEmail(String mail, Integer code) {
////        Integer code;
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        Random random = new Random();
//        code = random.nextInt(9000) + 1000;
//        simpleMailMessage.setFrom(gmail);
//        simpleMailMessage.setTo(mail);
//        simpleMailMessage.setText("Ваш код был отправлен" + code);
//        javaMailSender.send(simpleMailMessage);
//    }
//    public String sendTokenToEmail(RegisterRequest registerRequest, Integer code) {
//
//        User user = userRepository.findByUsername(registerRequest.getUsername())
//                .orElseThrow(() -> new RecordNotFoundException("Такой пользователь не существует!"));
//        sendEmail(code, user.getEmail());
//        user.setCode(code);
//        userRepository.save(user);
//        return "Письмо успешно отправлено на почту: " + user.getEmail();
//    }

}


//    @Override
//    public List<ProductListDto> getUserProductList(int userId) {
//        User user = getUserById(userId);
//
//        if (user != null) {
//            List<Product> userProducts = findAllUserProducts(user);
//
//            // Map Product entities to ProductListDto
//            return userProducts.stream()
//                    .map(product -> new ProductListDto(
//                            product.getProduct_id(),
//                            product.getImage(),
//                            product.getProductName(),
//                            product.getPrice(),
//                            product.getNumberOfLikes()
//                    ))
//                    .collect(Collectors.toList());
//        } else {
//            throw new EntityNotFoundException("User not found with id: " + userId);
//        }
//    }

//    @Override
//    public List<ProductListDto> getFavoriteProductList(int userId) {
//        User user = getUserById(userId);
//
//        if (user != null) {
//            Set<Product> favoriteProducts = user.getFavoriteProducts();
//
//            return favoriteProducts.stream()
//                    .map(product -> new ProductListDto(
//                            product.getProduct_id(),
//                            product.getImage(),
//                            product.getProductName(),
//                            product.getPrice(),
//                            product.getNumberOfLikes()
//                    ))
//                    .collect(Collectors.toList());
//        } else {
//            throw new EntityNotFoundException("User not found with id: " + userId);
//        }
//    }
//    @Override
//    public List<Product> getFavoriteProducts(int userId) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            List<Product> favoriteProducts = user.getFavoriteProducts();
//            return ResponseEntity.ok(favoriteProducts).getBody();
//        } else {
//            throw new UserNotFoundException("User not found with id: " + userId);
//        }
//
//    }
//    @Override
//    public UserFullDto getSingleUser(int id) {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isPresent()) {
//            User userEntity = user.get();
//            return new UserFullDto(
//                    userEntity.getUser_id(),
//                    userEntity.getFirstName(),
//                    userEntity.getLastName(),
//                    userEntity.getUsername(),
//                    userEntity.getEmail(),
//                    userEntity.getPhoneNumber(),
//                    userEntity.getBirthDate(),
//            );
//        }
//        throw new RuntimeException("User is not find " + id);
//    }

//    @Override
//    public UserFullDto getSingleUserByUsername(String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent()) {
//            User userEntity = user.get();
//            return new UserFullDto(
//                    userEntity.getUser_id(),
//                    userEntity.getFirstName(),
//                    userEntity.getLastName(),
//                    userEntity.getUsername(),
//                    userEntity.getEmail(),
//                    userEntity.getPhoneNumber(),
//                    userEntity.getBirthDate(),
//            );
//        }
//        throw new RuntimeException("User is not find " + username);
//    }

//    @Override
//    public void updateFullDateOfUser(int userId, UserFullDto fullInfoUserDto) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setFirstName(fullInfoUserDto.getFirstName());
//            user.setLastName(fullInfoUserDto.getLastName());
//            user.setPhoneNumber(fullInfoUserDto.getPhoneNumber());
//            user.setBirthDate(fullInfoUserDto.getBirthDate());
//
//            userRepository.save(user);
//        } else {
//            throw new EntityNotFoundException("User not found with ID: " + userId);
//        }
//    }


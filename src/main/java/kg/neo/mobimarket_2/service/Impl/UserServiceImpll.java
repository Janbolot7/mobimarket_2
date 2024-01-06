package kg.neo.mobimarket_2.service.Impl;

import kg.neo.mobimarket_2.dto.UserFullDto;
import kg.neo.mobimarket_2.exceptions.EmailNotFoundException;
import kg.neo.mobimarket_2.exceptions.UserNotFoundException;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import kg.neo.mobimarket_2.repository.ProductRepository;
import kg.neo.mobimarket_2.repository.UserRepository;
import kg.neo.mobimarket_2.repository.VerificationCodeRepository;
import kg.neo.mobimarket_2.service.CloudinaryService;
import kg.neo.mobimarket_2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

//@Service
//public class UserServiceImpll implements UserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private VerificationCodeRepository verificationCodeRepository;
//    @Autowired
//    private CloudinaryService cloudinaryService;
//    @Override
//    public ResponseEntity<User> updateFullDateOfUser(int id, UserFullDto user, MultipartFile file) {
//        User userInDB;
//        try {
//            userInDB = userRepository.findById(id).orElseThrow(()
//                    -> new IllegalStateException("User with id " + id + " not found!"));
//
//            userInDB.setUsername(user.getUsername());
//            userInDB.setFirstName(user.getFirstName());
//            userInDB.setLastName(user.getLastName());
//            userInDB.setPhoneNumber(user.getPhoneNumber());
//            userInDB.setBirthDate(user.getBirthDate());
//
//            if (file != null && !file.isEmpty()) {
//                String profileImageUrl = cloudinaryService.uploadImage(file);
//                userInDB.setAvatar(profileImageUrl);
//            }
//
//
//            userRepository.save(userInDB);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//        return ResponseEntity.ok(userInDB);
//    }
//    @Override
//    public Optional<User> findByUsername(String username) {
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (userOptional.isEmpty()) {
//            throw new UsernameNotFoundException("User with username " + username + " not found!");
//        }
//        return (userOptional);
//    }
//    @Override
//    public Optional<User> findByEmail(String email) {
//        Optional<User> userWithEmail = userRepository.findByEmail(email);
//        if (userWithEmail.isEmpty()) {
//            throw new EmailNotFoundException("User with email " + email + " not found!");
//        }
//        return (userWithEmail);
//    }
//    @Override
//    public Optional<User> getById(int id) {
//        return Optional.empty();
//    }
//    @Override
//    public ResponseEntity<List<User>> getAllUsers() {
//        return null;
//    }
//    @Override
//    public ResponseEntity<String> deleteUser(Long id) {
//        return null;
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
//    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUsername(username);
//        return user.map(value -> new org.springframework.security.core.userdetails.User(
//                        value.getUsername(), value.getPassword(), value.getAuthorities()))
//                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
//    }
//}

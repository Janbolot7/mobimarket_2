package kg.neo.mobimarket_2.service;

import kg.neo.mobimarket_2.dto.UserFullDto;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

//public interface UserServiceI {
//
//    ResponseEntity<User> updateFullDateOfUser(int id, UserFullDto user, MultipartFile file);
//
//    Optional<User> findByUsername(String username);
//
//    Optional<User> findByEmail(String email);
//
//    User save(User user);
//
//    Optional<User> getById(Long id);
//
//    ResponseEntity<User> update(Long id, UserFullDto user, MultipartFile file);
//
//    Optional<User> getById(int id);
//
//    ResponseEntity<List<User>> getAllUsers();
//
//    ResponseEntity<String> deleteUser(Long id);
//
//    List<Product> getFavoriteProducts(Long userId);
//
//    List<Product> getFavoriteProducts(int userId);
//
//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
//}
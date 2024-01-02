package kg.neo.mobimarket_2.service;



import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.UserFullDto;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> getUser();
    User saveUser (User user);
    public UserFullDto getSingleUser(int id);
    public UserFullDto getSingleUserByUsername(String username);
    void deleteUser (int id);
    public ResponseEntity<String> updatePhoneNumber(int userId, String newPhoneNumber);
    public User updateUser(String login, User updatedUser);
    public User getUserById(int userId);
    public boolean verifyPhoneNumber(String phoneNumber, String code);
    public User addOrRemoveFavoriteProduct(int userId, int productId);
    public boolean doesUserExistByEmail(String email);
    public boolean doesUserExistByUsername(String username);
    public List<ProductListDto> getUserProductList(int userId);
    public List<ProductListDto> getFavoriteProductList(int userId);
    public User findByUsername(String username);
    List<Product> findAllUserProducts(User user);
    public boolean findByPhoneNumberAndVerified(String newPhoneNumber);
    void updateFullDateOfUser(int userId, UserFullDto fullInfoUserDto);

}

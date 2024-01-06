package kg.neo.mobimarket_2.service;



import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.UserFullDto;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<User> getUser();
    User saveUser (User user);
    void deleteUser (int id);
    public ResponseEntity<String> updateEmail(int userId, String newEmail);
    public User getUserById(int userId);
    public boolean verifyEmail(String email, String code);
    public User addOrRemoveFavoriteProduct(int userId, int productId);
    public boolean doesUserExistByEmail(String email);
    public boolean doesUserExistByUsername(String username);
    public User findByUsername(String username);
    /////0948904380982908902390893
    List<Product> findAllUserProducts(User user);
    public boolean findByEmailAndVerified(String newEmail);
    public ResponseEntity<User> updateFullDateOfUser(int id, UserFullDto user, MultipartFile file);
}
//    public List<ProductListDto> getUserProductList(int userId);
//    public List<ProductListDto> getFavoriteProductList(int userId);
//    public UserFullDto getSingleUser(int id);
//    public UserFullDto getSingleUserByUsername(String username);
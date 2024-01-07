package kg.neo.mobimarket_2.controller;

import io.swagger.annotations.Api;
import kg.neo.mobimarket_2.dto.UserFullDto;
import kg.neo.mobimarket_2.model.User;
import kg.neo.mobimarket_2.repository.UserRepository;
import kg.neo.mobimarket_2.service.UserService;
import kg.neo.mobimarket_2.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kg.neo.mobimarket_2.configuration.SwaggerConfig.USER;

@Api(tags = USER)
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SmsService smsService;
    private final UserRepository userRepository;

    @PutMapping("/fullInfoOfUser/{id}")
    public ResponseEntity<User> addAllInfoUser(
            @PathVariable("id") int userId,
            @RequestBody UserFullDto userDto,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            ResponseEntity<User> response = userService.updateFullDateOfUser(userId, userDto, file);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/findUsers")
    public List<User> findAllUsers() {
        return userService.getUser();
    }

//    @GetMapping("/findUser/{username}")
//    public ResponseEntity<UserFullDto> findUser(@PathVariable String login) {
//        UserFullDto userDto = userService.getSingleUserByUsername(login);
//        return ResponseEntity.ok(userDto);
//    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
    }



//    @PutMapping("/updateUser/{username}")
//    public ResponseEntity<User> updateUser(
//            @PathVariable String username,
//            @RequestBody User updatedUser
//    ) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User authenticatedUser = userService.findByUsername(username);
//
//        if (authenticatedUser == null) {
//            System.out.println("user not found");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        User updatedUserData = userService.updateUser(username, updatedUser);
//
//        if (updatedUserData == null) {
//            System.out.println("There is no data");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        return ResponseEntity.ok(updatedUserData);
//    }

//    @GetMapping("/userProducts/{userId}")
//    public ResponseEntity<List<ProductListDto>> getUserProductListDtos(@PathVariable int userId) {
//        List<ProductListDto> userProductList = userService.getUserProductList(userId);
//        return ResponseEntity.ok(userProductList);
//    }


//    @GetMapping("/favorite-products/{userId}")
//    public ResponseEntity<List<ProductListDto>> getFavoriteProductList(@PathVariable int userId) {
//        List<ProductListDto> favoriteProductList = userService.getFavoriteProductList(userId);
//        return ResponseEntity.ok(favoriteProductList);
//    }

    @PutMapping("/{userId}/favorite-products/{productId}")
    public ResponseEntity<User> addOrRemoveFavoriteProduct(
            @PathVariable int userId,
            @PathVariable int productId
    ) {
        User updatedUser = userService.addOrRemoveFavoriteProduct(userId, productId);

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedUser);
    }
    /////////9348098098094389084840398
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Product>> getAllProductsByUserId(@PathVariable("userId") Integer userId) {
//        try {
//            List<Product> products = userService.getAllByUserId(userId);
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/update-email/{userId}")
    public ResponseEntity<String> updateEmail(
            @PathVariable int userId,
            @RequestParam String newEmail
    ) {
        return userService.updateEmail(userId, newEmail);
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam("email") String email) {
        String activationCode = "Copy your code and enter to confirm " + smsService.generateVerificationCode();
        try {
            smsService.sendEmail(email, activationCode);
            return ResponseEntity.ok("Activation email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send activation email");
        }
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String code) {
        boolean isVerified = userService.verifyEmail(email, code);

        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to verify email");
        }
    }

}

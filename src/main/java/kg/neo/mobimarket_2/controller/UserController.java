package kg.neo.mobimarket_2.controller;

import io.swagger.annotations.Api;
import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.UserFullDto;
import kg.neo.mobimarket_2.model.User;
import kg.neo.mobimarket_2.repository.UserRepository;
import kg.neo.mobimarket_2.service.UserService;
import kg.neo.mobimarket_2.sms.smsSender.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/findUsers")
    public List<User> findAllUsers() {
        return userService.getUser();
    }

    @GetMapping("/findUser/{username}")
    public ResponseEntity<UserFullDto> findUser(@PathVariable String login) {
        UserFullDto userDto = userService.getSingleUserByUsername(login);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
    }



    @PutMapping("/updateUser/{username}")
    public ResponseEntity<User> updateUser(
            @PathVariable String username,
            @RequestBody User updatedUser
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.findByUsername(username);

        if (authenticatedUser == null) {
            System.out.println("user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User updatedUserData = userService.updateUser(username, updatedUser);

        if (updatedUserData == null) {
            System.out.println("There is no data");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(updatedUserData);
    }

    @GetMapping("/userProducts/{userId}")
    public ResponseEntity<List<ProductListDto>> getUserProductListDtos(@PathVariable int userId) {
        List<ProductListDto> userProductList = userService.getUserProductList(userId);
        return ResponseEntity.ok(userProductList);
    }


    @GetMapping("/favorite-products/{userId}")
    public ResponseEntity<List<ProductListDto>> getFavoriteProductList(@PathVariable int userId) {
        List<ProductListDto> favoriteProductList = userService.getFavoriteProductList(userId);
        return ResponseEntity.ok(favoriteProductList);
    }

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

    @PutMapping("/update-phone-number/{userId}")
    public ResponseEntity<String> updatePhoneNumber(
            @PathVariable int userId,
            @RequestParam String newPhoneNumber
    ) {
        return userService.updatePhoneNumber(userId, newPhoneNumber);
    }




    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String phoneNumber) {
        smsService.sendVerificationCode(phoneNumber);
        return ResponseEntity.ok("Verification code sent successfully.");
    }


    @PostMapping("/verify-phone-number")
    public ResponseEntity<String> verifyPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ) {
        boolean isVerified = userService.verifyPhoneNumber(phoneNumber, code);

        if (isVerified) {
            return ResponseEntity.ok("Phone number verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Phone number verification failed.");
        }
    }

}

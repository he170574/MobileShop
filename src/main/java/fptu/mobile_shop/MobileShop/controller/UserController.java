package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.User; // Import User
import fptu.mobile_shop.MobileShop.entity.UserRole; // Import UserRole
import fptu.mobile_shop.MobileShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController { // Đổi tên lớp cho chính xác

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users with roles
    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getAllUsersWithRoles() {
        List<User> users = userService.getAllUsers(); // Giả định bạn có phương thức getAllUsers
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(users);
        return ResponseEntity.ok(responseDTO);
    }

    // Get user by ID with role
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Long id) { // Sửa kiểu ID cho đúng
        Optional<User> user = userService.getUserById(id); // Giả định bạn có phương thức getUserById
        ResponseDTO responseDTO = new ResponseDTO();
        if (user.isPresent()) {
            responseDTO.setMessage("Get success");
            responseDTO.setData(user.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            responseDTO.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    // Create or update user
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveUser(@RequestBody User user) {
        ResponseDTO responseDTO = new ResponseDTO();
        User savedUser = userService.saveUser(user); // Giả định bạn có phương thức saveUser
        responseDTO.setMessage("Save success");
        responseDTO.setData(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // Trả về 201 khi thành công
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id) { // Sửa kiểu ID cho đúng
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            userService.deleteUserById(id); // Giả định bạn có phương thức deleteUserById
            responseDTO.setMessage("Delete success");
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Delete failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }
}

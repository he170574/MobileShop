package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.UserWithRole;
import fptu.mobile_shop.MobileShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class CustomerController {

    private final UserService userService;

    @Autowired

    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    // Get all users with roles
    @GetMapping("/get-all")
    public ResponseEntity<ResponseDTO> getAllUsersWithRoles() {
        List<UserWithRole> usersWithRoles = userService.getAllUsersWithRoles();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Get success");
        responseDTO.setData(usersWithRoles);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // Get user by ID with role
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getUserWithRoleById(@PathVariable Integer id) {
        Optional<UserWithRole> userWithRole = userService.getUserWithRoleById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        if (userWithRole.isPresent()) {
            responseDTO.setMessage("Get success");
            responseDTO.setData(userWithRole.get());
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } else {
            responseDTO.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    }

    // Create or update user with role
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveUserWithRole(@RequestBody UserWithRole userWithRole) {
        ResponseDTO responseDTO = new ResponseDTO();
        UserWithRole savedUserWithRole = userService.saveUserWithRole(userWithRole);
        responseDTO.setMessage("Save success");
        responseDTO.setData(savedUserWithRole);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUserWithRole(@PathVariable Integer id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            userService.deleteUserWithRoleById(id);
            responseDTO.setMessage("Delete success");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Delete failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }
}

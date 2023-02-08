package account.Controllers;

import account.Models.User;
import account.Models.UserRequest;
import account.Models.UserResponse;
import account.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    UserService service;

    @PostMapping("/signup")
    public UserResponse signUp(@Valid @RequestBody UserRequest newUser){
        return service.signUp(newUser);
    }
}

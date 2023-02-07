package account.Controllers;

import account.Models.User;
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
    public ResponseEntity<?> signUp(@Valid @RequestBody User newUser){
        User tmp = service.signUp(newUser);

        if (tmp.equals(null)){
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(tmp);
    }
}

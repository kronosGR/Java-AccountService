package account.Controllers;

import account.Models.UserResponse;
import account.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "api/empl")
public class EmployeeController {

    @Autowired
    UserService userService;

    @GetMapping("/payment")
    public UserResponse getPayment(){
        return userService.findUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}

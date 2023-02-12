package account.Controllers;

import account.Models.PaymentRequest;
import account.Models.PaymentStatusResponse;
import account.Models.User;
import account.Models.UserResponse;
import account.Services.EmployeeService;
import account.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class EmployeeController {

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("empl/payment")
    public ResponseEntity<?> getPayment(@RequestParam Optional<String> period, @AuthenticationPrincipal User user) {
        if (period.isPresent()) {
            if (!period.get().matches("(0?[1-9]|1[0-2])-\\d+")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid period param");
            }
            return ResponseEntity.ok(employeeService.getUserPaymentByPeriod(period.get()));
        }
        return ResponseEntity.ok(employeeService.getPayments());
    }


    @PostMapping("acct/payments")
    public PaymentStatusResponse postPayment(@RequestBody @Valid List<PaymentRequest> payments) {
        try {
            return employeeService.postPayments(payments);
        } catch (Exception ex){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("acct/payments")
    public PaymentStatusResponse updatePayment(@RequestBody @Valid PaymentRequest payment) {
        return employeeService.updatePayment(payment);
    }
}

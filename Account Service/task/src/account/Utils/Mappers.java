package account.Utils;

import account.Models.*;
import account.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Mappers {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-yyyy");
    String DATE_PATTERN = "MMMM-yyyy";

    @Autowired
    UserService userService;

    public User requestToUser(UserRequest userRequest) {
        return new User(
                userRequest.getName(),
                userRequest.getLastname(),
                userRequest.getEmail().toLowerCase(),
                userRequest.getPassword()
        );
    }

    public UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getUsername()
        );
    }

    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return new Payment(LocalDate.parse(paymentRequest.getPeriod(), formatter),
                userService.loadUserByUsername(paymentRequest.getEmployee().toLowerCase()),
                paymentRequest.getSalary());
    }

    public PaymentResponse PaymentToResponse(Payment payment) {
        return new PaymentResponse(payment.getUser().getName(),
                payment.getUser().getLastname(),
                DateTimeFormatter.ofPattern(DATE_PATTERN).format(payment.getPeriod()),
                String.format("%d dollar(s) %d cent(s)", payment.getSalary() / 100, payment.getSalary() % 100));
    }
}

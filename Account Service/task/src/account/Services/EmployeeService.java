package account.Services;

import account.Exceptions.DuplicatePaymentException;
import account.Exceptions.NotExistPeriod;
import account.Models.*;
import account.Repositories.PaymentRepository;
import account.Utils.Mappers;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    Mappers mappers;
    @Autowired
    UserService userService;
    @Autowired
    PaymentRepository paymentRepository;

    public EmployeeService() {
        mappers = new Mappers();
    }

    public List<PaymentResponse> getPayments() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByUsername(username);
        mappers = new Mappers();
        return paymentRepository.findPaymentByUser(user).stream().map(item -> mappers.PaymentToResponse(item)).collect(Collectors.toList());
    }

    public PaymentResponse getUserPaymentByPeriod(String period) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByUsername(username);
        LocalDate date = LocalDate.parse(period, DateTimeFormatter.ofPattern("MMMM-yyyy"));
        return paymentRepository.findPaymentByUserAndPeriod(user, date).stream().map(item -> mappers.PaymentToResponse(item))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find"));
    }

    public PaymentStatusResponse postPayments(List<PaymentRequest> paymentRequestList){
        long count = paymentRequestList.stream().distinct().count();
        // TODO: the above maybe needs class comparison
        if (count != paymentRequestList.size()) throw  new DuplicatePaymentException();

        List<Payment> payments = paymentRequestList.stream().map(item -> mappers.paymentRequestToPayment(item))
                .sorted((a,b) -> b.getPeriod().compareTo(a.getPeriod())).collect(Collectors.toList());
        paymentRepository.saveAll(payments);
        return new PaymentStatusResponse("Added successfully!");
    }

    @Transactional
    public PaymentStatusResponse updatePayment(PaymentRequest paymentRequest){
        Payment payment = mappers.paymentRequestToPayment(paymentRequest);
        List<Payment> payments = paymentRepository.findPaymentByUser(payment.getUser());

        boolean periodExists = payments.stream().map(Payment::getPeriod).noneMatch(item -> item.equals(payment.getPeriod()));
        if (!periodExists) throw new NotExistPeriod();

        paymentRepository.updatePaymentByUserAndPeriod(payment.getUser(),payment.getPeriod(),payment.getSalary());
        return new PaymentStatusResponse("Updated successfully");
    }
}

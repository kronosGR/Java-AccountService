package account.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="payments")
@Data
public class Payment {

    public Payment(LocalDate period, User user, Long salary) {
        this.period = period;
        this.user = user;
        this.salary = salary;
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    LocalDate period;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @Column(nullable = false)
    Long salary;
}

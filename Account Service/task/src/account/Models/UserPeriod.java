package account.Models;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
public class UserPeriod {
    User user;
    LocalDate period;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPeriod that = (UserPeriod) o;
        return Objects.equals(user, that.user) && Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, period);
    }
}

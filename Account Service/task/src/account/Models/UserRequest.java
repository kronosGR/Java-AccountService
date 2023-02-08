package account.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull
    @NotEmpty
    String name;

    @NotNull
    @NotEmpty
    String lastname;

    @NotNull
    @NotEmpty
    @Email
    @Pattern(regexp = "^.*?\\b@acme.com.*?", flags = {Pattern.Flag.CASE_INSENSITIVE})
    String email;

    @NotNull
    @NotEmpty
    @Size(min = 12)
    String password;
}

package account.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {

    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @NotEmpty
    @NotNull
    String new_password;
}

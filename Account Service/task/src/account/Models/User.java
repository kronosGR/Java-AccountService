package account.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank
    @NotNull
    String name;

    @NotBlank
    @NotNull
    String lastname;

    @NotNull
    @NotNull
    @Email
    @Pattern(regexp = "^.*?\\b@acme.com.*?", flags = {Pattern.Flag.CASE_INSENSITIVE})
    String email;

    @NotBlank
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;




}

package account.Utils;

import account.Models.User;
import account.Models.UserRequest;
import account.Models.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

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
}

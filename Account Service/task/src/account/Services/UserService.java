package account.Services;

import account.Exceptions.UserExistsException;
import account.Models.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class UserService {

//    List<User> users = new ArrayList<>();
    User user;
    public User signUp(User user){
//        if (users.contains(user) )
//            try {
//            throw new UserExistsException();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        user= user;
        return user;
    }
}

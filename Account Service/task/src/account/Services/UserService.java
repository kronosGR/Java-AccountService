package account.Services;

import account.Configurations.BCryptEncoderConfig;
import account.Exceptions.UserExistsException;
import account.Models.User;
import account.Models.UserRequest;
import account.Models.UserResponse;
import account.Repositories.UserRepository;
import account.Utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements IUserService,UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptEncoderConfig bCryptEncoderConfig;

    @Autowired
    UserMapper userMapper;

    public UserResponse signUp(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getEmail().toLowerCase()).isPresent()){
            throw new UserExistsException();
        }

        userRequest.setPassword(bCryptEncoderConfig.passwordEncoder().encode(userRequest.getPassword()));
        User user = userRepository.save(userMapper.requestToUser(userRequest));
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username.toLowerCase())
                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username ½s not found")));
    }

    public UserResponse findUser(String username){
        User tmp = userRepository.findByUsername(username.toLowerCase()).orElseThrow(()->new UsernameNotFoundException(username));
        return userMapper.userToUserResponse(tmp);
    }

    @Override
    public Long saveUser(User user) {
        String passwd = user.getPassword();
        String encodedPasswd = bCryptEncoderConfig.passwordEncoder().encode(passwd);
        user.setPassword(encodedPasswd);
        user = userRepository.save(user);
        return user.getId();
    }
}

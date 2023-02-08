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
public class UserService implements UserDetailsService {

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

}

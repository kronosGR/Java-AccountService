package account.Services;

import account.Configurations.BCryptEncoderConfig;
import account.Exceptions.PasswordBreachedException;
import account.Exceptions.SamePasswordException;
import account.Exceptions.UserExistsException;
import account.Models.*;
import account.Repositories.UserRepository;
import account.Utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class UserService implements IUserService,UserDetailsService {

    String[] breachedPasswords = {
            "PasswordForJanuary",
            "PasswordForFebruary",
            "PasswordForMarch",
            "PasswordForApril",
            "PasswordForMay",
            "PasswordForJune",
            "PasswordForJuly",
            "PasswordForAugust",
            "PasswordForSeptember",
            "PasswordForOctober",
            "PasswordForNovember",
            "PasswordForDecember"
    };

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

        if (isPassBreached(userRequest.getPassword())){
            throw new PasswordBreachedException();
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

    @Transactional
    public PasswordResponse changePassword(PasswordRequest passwordRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String password = passwordRequest.getNew_password();

        if(isPassBreached(password)){
            throw new PasswordBreachedException();
        }

        User user = (User)loadUserByUsername(username);
        //String encPass = bCryptEncoderConfig.passwordEncoder().encode(password);
        if (bCryptEncoderConfig.passwordEncoder().matches(password, user.getPassword())){
            throw new SamePasswordException();
        }

        userRepository.updatePassword(bCryptEncoderConfig.passwordEncoder().encode(password),
                username);
        return new PasswordResponse(username, "The password has been updated successfully");
    }

    private boolean isPassBreached(String pass){
        return Arrays.asList(breachedPasswords).contains(pass);
    }
}

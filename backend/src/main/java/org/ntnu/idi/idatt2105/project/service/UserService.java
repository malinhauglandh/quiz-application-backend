qpackage org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.model.UserLogin;
import org.ntnu.idi.idatt2105.project.repository.UserRepository;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(UserLogin login){
        User user = new User();
        user.setUsername(login.getUsername());
        user.setPassword(PasswordUtils.encode(login.getPassword()));
        if(userRepository.findById(user.getUsername()).isPresent()){
            return false;
        }
        userRepository.save(user);
        return true;

    }

    public boolean login(UserLogin login){
        Optional<User> user = userRepository.findById(login.getUsername());
        return user.filter(value -> PasswordUtils.matches(login.getPassword(), value.getPassword())).isPresent();
    }
}
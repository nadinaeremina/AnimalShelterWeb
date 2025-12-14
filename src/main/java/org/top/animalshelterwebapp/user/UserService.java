package org.top.animalshelterwebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find User with ID" + id);
    }

    public User get(String login) throws UserNotFoundException {
        Optional<User> result = userRepository.findByLogin(login);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find User with Login" + login);
    }

    public boolean isExistByLogin(User user) {
        return userRepository.existsByLogin(user.getLogin());
    }

    public boolean isRightPassword(User user) {
        User finduser = this.get(user.getLogin());
        return finduser.getPasswordHash().equals(user.getPasswordHash());
    }
}

package ru.itmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.Role;
import ru.itmo.UserDTO;
import ru.itmo.entity.MyUser;
import ru.itmo.repository.UserRepository;
import ru.itmo.service.exception.UserException;
import ru.itmo.service.mapping.MappingUtilsUser;


@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(String username, String password, String roleName, Long ownerId) {
        if (userRepository.existsByUsername(username)) {
            throw new UserException("user name is taken :" + username);
        }
        Role role;
        if (roleName.equals(Role.ADMIN.name())) {
            role = Role.ADMIN;
        } else if (roleName.equals(Role.USER.name())) {
            role = Role.USER;
        } else {
            throw new UserException("No such role with roleName" + roleName);
        }
        MyUser myUser = new MyUser(username, encoder().encode(password), role, ownerId);
        userRepository.save(myUser);
        return MappingUtilsUser.asDto(myUser);
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    public MyUserDetails getCurrentUser() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

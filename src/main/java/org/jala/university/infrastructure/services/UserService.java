package org.jala.university.infrastructure.services;

import org.jala.university.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.jala.university.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserService extends GenericService<User, UUID> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }


    public List<String> getAllNameUser() {
        return userRepository.
                findAll()
                .stream().map(User::getUsername)
                .distinct()
                .collect(Collectors.toList());
    }


    public User getUserByUsername(String username) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getByIds(Set<UUID> userIds) {
        return repository.findAllById(userIds); // Usa una consulta que soporte varios IDs
    }

}

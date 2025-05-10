
package com.edutech.usuarios.service;

import com.edutech.usuarios.model.User;

public class UserService {
    private final UserRepository userRepository;

    // Inyección de dependencia a través del constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
/*
    // metodo para obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
*/
}

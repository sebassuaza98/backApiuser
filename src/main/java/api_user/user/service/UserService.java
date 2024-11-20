package api_user.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_user.user.model.UserModel;
import api_user.user.repository.UserRepositori;

@Service
public class UserService {

    private final UserRepositori userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepositori userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel registerUser(UserModel user) {
        if (user.getUserId() == null || user.getName() == null || user.getLastName() == null 
            || user.getPassword() == null || user.getPhone() == null || user.getAddress() == null
            || user.getEmail() == null || user.getRole() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new IllegalArgumentException("La identificación ya existe");
        } else if (!userRepository.findByEmail(user.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        user.setDate(LocalDateTime.now()); 
        user.setState(true); 
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); 

        return userRepository.save(user);
    }
    
    public List<UserModel> getUsers(Boolean state, String role) {
        if (state != null && role != null) {
            return userRepository.findByStateAndRole(state, role);
        } else if (state != null) {
            return userRepository.findByState(state);
        } else if (role != null) {
            return userRepository.findByRole(role);
        } else {
            return userRepository.findAll();
        }
    }

    public UserModel updateUser(UserModel user) {
        UserModel existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhone(user.getPhone());
        existingUser.setAddress(user.getAddress());
        existingUser.setRole(user.getRole());

        if (user.getState() != null) {
            existingUser.setState(user.getState());
        }
    
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            if (!userRepository.findByEmail(user.getEmail()).isEmpty()) {
                throw new IllegalArgumentException("El email ya está registrado");
            }
            existingUser.setEmail(user.getEmail());
        }
        
        existingUser.setDate(LocalDateTime.now());
        return userRepository.save(existingUser);
    }
    
}

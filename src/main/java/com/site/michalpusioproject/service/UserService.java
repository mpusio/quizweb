package com.site.michalpusioproject.service;

import com.site.michalpusioproject.domains.Role;
import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.repository.RoleRepository;
import com.site.michalpusioproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public void addUser(User user, String nameOfRole){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        checkRoleExistAndSave(user, nameOfRole);
    }

    public void registerUser(User user){
        String nameOfRole = "USER";
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        checkRoleExistAndSave(user, nameOfRole);
    }

    public void editUser(User user){
        editPassword(user);
        editProfile(user);
    }

    public void editPassword(User user){
        String nameOfRole = user.getRole().getRole();
        String newPassword = user.getPassword();
        Optional<User> byId = userRepository.findById(user.getId());
        String currentPassword = byId.get().getPassword();

        if (!currentPassword.equals(newPassword)){
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            checkRoleExistAndSave(user, nameOfRole);
        }
    }

    public void editProfile(User user){
        String nameOfRole = user.getRole().getRole();
        checkRoleExistAndSave(user, nameOfRole);
    }

    private void checkRoleExistAndSave(User user, String nameOfRole) {
        Optional<Role> optionalRole = roleRepository.findAll().stream()
                .filter(role -> role.getRole().equals(nameOfRole))
                .findFirst();

        if (optionalRole.isPresent()){
            optionalRole.get().addUser(user);
            roleRepository.save(optionalRole.get());
        }
    }

    public boolean isEmailExistInDatabase(String email){
        return userRepository.existsByEmail(email);
    }
}

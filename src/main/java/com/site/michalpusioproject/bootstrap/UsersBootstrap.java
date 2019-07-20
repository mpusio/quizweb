package com.site.michalpusioproject.bootstrap;

import com.site.michalpusioproject.domains.Role;
import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UsersBootstrap {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private List<Role> initRoles(){
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");

        return Arrays.asList(admin, user);
    }

    private List<User> initUsers(){
        List<Role> roles = initRoles();

        User adminAccount = User.builder()
                .firstName("Michal")
                .lastName("Pusio")
                .email("pusiomichal@gmail.com")
                .password(bCryptPasswordEncoder.encode("qwerty123"))
                .role(roles.get(0))
                .build();

        User userAccount = User.builder()
                .firstName("Marek")
                .lastName("Mostowiak")
                .email("mostowiakmarek@gmail.com")
                .password(bCryptPasswordEncoder.encode("karton123"))
                .role(roles.get(1))
                .build();

        return Arrays.asList(adminAccount, userAccount);
    }

    @Bean
    public void initData(){
        List<Role> roles = initRoles();

        User user1 = initUsers().get(0);
        User user2 = initUsers().get(1);

        Role role1 = roles.get(0);
        Role role2 = roles.get(1);

        List<User> usersList = new ArrayList<>();
        List<User> usersList1 = new ArrayList<>();

        role1.setUsers(usersList);
        role2.setUsers(usersList1);

        role1.addUser(user1);
        role2.addUser(user2);

        roleRepository.save(role1);
        roleRepository.save(role2);
    }
}

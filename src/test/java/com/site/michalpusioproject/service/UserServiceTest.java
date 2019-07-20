package com.site.michalpusioproject.service;

import com.site.michalpusioproject.domains.Role;
import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private List<User> users;

    @Before
    public void setUp() throws Exception {
        User user = new User(1L, "Abraham", "Lincoln", "alincoln@gmail.com", "password123", new Role("ADMIN"));
        User user2 = new User(2L, "Maria", "Sklodowska", "currie@gmail.com", "password321", new Role("USER"));

        users = new ArrayList<>(List.of(user, user2));
    }

    @Test
    public void getAllUsers() {
        MockitoAnnotations.initMocks(this);

        User user = new User(1L, "Abraham", "Lincoln", "alincoln@gmail.com", "password123", new Role("ADMIN"));
        User user1 = new User(2L, "Maria", "Sklodowska", "currie@gmail.com", "password321", new Role("USER"));
        List<User> localUsers = Lists.list(user, user1);

        when(userRepository.findAll()).thenReturn(users);
        List<User> foundedUsers = userService.getAllUsers();

        assertThat(foundedUsers.get(0)).isEqualToComparingFieldByField(localUsers.get(0));
        assertThat(foundedUsers.get(1)).isEqualToComparingFieldByField(localUsers.get(1));
        assertThat(foundedUsers.size()).isEqualTo(2);
    }

    @Test
    public void shouldGetUserByEmail() {
        String email = "alincoln@gmail.com";
        User user = new User(1L, "Abraham", "Lincoln", "alincoln@gmail.com", "password123", new Role("ADMIN"));

        Optional<User> findUserByEmail = users.stream().filter(user1 -> user1.getEmail().equals("alincoln@gmail.com")).findFirst();

        if (findUserByEmail.isPresent()){
            when(userRepository.findByEmail(anyString())).thenReturn(findUserByEmail.get());
            User foundedByEmail = userService.getUserByEmail(email);

            assertThat(foundedByEmail).isEqualToComparingFieldByField(user);
        }
    }

    @Test
    public void shouldGetUserById() {
        User user = new User(1L, "Abraham", "Lincoln", "alincoln@gmail.com", "password123", new Role("ADMIN"));

        Optional<User> findExistUserById = users.stream().filter(user1 -> user1.getId().equals(1L)).findFirst();

        when(userRepository.findById(anyLong())).thenReturn(findExistUserById);
        Optional<User> foundedById = userService.getUserById(user.getId());

        assertThat(foundedById.isPresent()).isTrue();
        assertThat(foundedById).get().isEqualToComparingFieldByField(user);
    }

    @Test
    public void shouldDeleteUserById() {
        doAnswer(invocationOnMock -> users.remove(0)).when(userRepository).deleteById(anyLong());
        userService.deleteUserById(1L);

        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void shouldEmailExistInDatabase() {
        String existEmail = "alincoln@gmail.com";

        Optional<User> shouldExist = users.stream().filter(user1 -> user1.getEmail().equals(existEmail)).findFirst();

        if (shouldExist.isPresent()){
            when(userRepository.findByEmail(anyString())).thenReturn(shouldExist.get());
            boolean exist = userService.isEmailExistInDatabase(existEmail);
            assertThat(exist).isFalse();
        }
    }

    @Test
    public void shouldNotEmailExistInDatabase(){
        String nonExistEmail = "example@gmail.com";

        Optional<User> shouldExist = users.stream().filter(user1 -> user1.getEmail().equals(nonExistEmail)).findFirst();

        if (shouldExist.isEmpty()){
            when(userRepository.findByEmail(anyString())).thenReturn(null);
            boolean exist = userService.isEmailExistInDatabase(nonExistEmail);
            assertThat(exist).isTrue();
        }
    }
}
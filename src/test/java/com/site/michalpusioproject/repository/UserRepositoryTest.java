package com.site.michalpusioproject.repository;

import com.site.michalpusioproject.domains.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindByEmail() {
        //given
        User user = new User();
        user.setEmail("newmail@gmail.com");
        entityManager.persist(user);
        entityManager.flush();

        //when
        User found = userRepository.findByEmail(user.getEmail());

        //then
        assertEquals(user.getEmail(), found.getEmail());
    }
}
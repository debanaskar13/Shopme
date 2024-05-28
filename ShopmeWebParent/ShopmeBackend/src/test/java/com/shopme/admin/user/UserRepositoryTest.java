package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userDebashis = User.builder().email("debanaskar13@gmail.com").password("Deba1234@").firstName("Debashis")
                .lastName("Naskar").build();
        userDebashis.addRole(roleAdmin);

        User savedUser = this.repository.save(userDebashis);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRole() {
        Role roleEditor = entityManager.find(Role.class, 3);
        Role roleAssistant = entityManager.find(Role.class, 5);
        User userRavi = User.builder().email("ravi@gmail.com").password("Ravi@2024").firstName("Ravi")
                .lastName("Kumar").build();
        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = this.repository.save(userRavi);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        List<User> listUsers = this.repository.findAll();
        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isGreaterThan(1);
    }

    @Test
    public void testGetUserById() {
        User userDebashis = this.repository.findById(1).get();
        assertThat(userDebashis).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userDebashis = this.repository.findById(1).get();
        userDebashis.setEnabled(true);
        userDebashis.setEmail("Deba@gmail.com");

        this.repository.save(userDebashis);
    }

    @Test
    public void testUpdateUserRoles() {
        User userRavi = this.repository.findById(2).get();
        Role roleSalesperson = Role.builder().id(2).build();
        Role roleEditor = Role.builder().id(3).build();

        userRavi.getRoles().remove(roleEditor);
        userRavi.getRoles().add(roleSalesperson);

        this.repository.save(userRavi);

    }

    @Test
    public void testDeleteUser() {
        this.repository.deleteById(2);
    }

    @Test
    public void testIsUserUnique() {
        Optional<User> user = this.repository.findByEmail("deba123@gmail.com");

        assertThat(user.isPresent()).isFalse();
    }
}

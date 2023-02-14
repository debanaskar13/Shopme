package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

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
    private UserRepository userRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userDebashis = new User("debanaskar13@gmail.com", "deba1234", "Debashis", "Naskar");
        userDebashis.addRole(roleAdmin);
        User savedUser = userRepo.save(userDebashis);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRole() {
        User userArijit = new User("arijitdas007@gmail.com", "arijit1234", "Arijit", "Das");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userArijit.addRole(roleEditor);
        userArijit.addRole(roleAssistant);
        User savedUser = userRepo.save(userArijit);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> allUsers = userRepo.findAll();
        allUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        Optional<User> userarijit = userRepo.findById(3);
        assertThat(userarijit.isEmpty()).isFalse();
    }

    @Test
    public void testUpdateUserDetails() {
        User userArijit = userRepo.findById(2).get();
        userArijit.setEnabled(true);
        userArijit.setEmail("arijit11012000@gmail.com");
        User savedUser = userRepo.save(userArijit);

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void testUpdateUserRoles() {
        User userArijit = userRepo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        userArijit.getRoles().remove(roleEditor);
        userArijit.addRole(roleSalesperson);

        userRepo.save(userArijit);
    }

    // @Test
    // public void testDeleteUser() {
    // Integer userId = 2;
    // userRepo.deleteById(userId);
    // }

    @Test
    public void testGetUserByEmail() {
        String email = "debanaskar13@gmail.com";
        User user = userRepo.getUserByEmail(email);
        assertThat(user).isNotNull();
    }
    
    @Test
    public void testCountById() {
    	Integer id = 1;
    	Long countById = userRepo.countById(id);
    	
    	assertThat(countById).isNotNull().isGreaterThan(0);
    }
    
    
    
    
    
    
    
    
    
    
    

}

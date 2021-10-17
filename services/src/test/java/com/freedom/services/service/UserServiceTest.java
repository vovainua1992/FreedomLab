package com.freedom.services.service;

import com.freedom.services.config.ServiceTestConfig;
import com.freedom.services.config.TestServiceApplication;
import com.freedom.services.dommain.User;
import com.freedom.services.repos.UserRepos;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static com.freedom.services.utils.UserUtils.equalsUser;
import static com.freedom.services.utils.UserUtils.newUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={TestServiceApplication.class, ServiceTestConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    private User admin;
    private User tester;
    private User remove;



    @Test
    @Order(1)
    public void addUser() {
        User admin = newUser("admin","12","test@test.com");
        userService.addUser( admin);
        userService.addUser( newUser("remove","12","test@test.com"));
        this.admin =userRepos.findByUsername("admin");
        assertNotNull(this.admin.getActivationCode());
        assertTrue(equalsUser(admin,this.admin));
    }

    @Test
    @Order(2)
    public void updateUserAuthority() {
        Map <String,String> map = new HashMap<>();
        map.put("USER","val");
        map.put("ADMIN","val");
        findTesterAndAdmin();
        userService.updateUserAuthority(admin,map);
        assertTrue(admin.isAdmin());
        assertFalse(tester.isAdmin());
    }

    @Test
    @Order(3)
    public void activateUser() {
        admin = userRepos.findByUsername("admin");
        userService.activateUser(admin.getActivationCode());
        assertNull(userRepos.findByUsername("admin").getActivationCode());
    }

    @Test
    @Order(4)
    public void loadUserByUsername() {
        assertThrows(BadCredentialsException.class,()->{
            UserDetails  fromDb = userService.loadUserByUsername("remove");
        });
        UserDetails fromDb = userService.loadUserByUsername("admin");
        assertNotNull(fromDb);

    }

    @Test
    @Order(5)
    @WithMockUser("user")
    public void updateProfile() {
        userService.updateProfile(userRepos.findByUsername("remove"),"REMOVE","231","231","new@test.com");
        admin = userRepos.findByUsername("REMOVE");
        assertNotNull(admin);
        assertEquals(admin.getEmail(),"new@test.com");
    }


    @Test
    @Order(6)
    public void removeUser() {
        remove = userRepos.findByUsername("REMOVE");
        assertNotNull(remove);
        userService.removeUser(remove.getId());
        assertNull(userRepos.findByUsername("REMOVE"));
    }

    @Test
    @Order(7)
    @Transactional
    public void subscribe() {
        findTesterAndAdmin();
        assertEquals(tester.getSubscriptions().size(),0);
        assertEquals(admin.getSubscribers().size(),0);
        userService.subscribe(tester,admin.getId());
        findTesterAndAdmin();
        assertEquals(tester.getSubscriptions().size(),1);
        assertEquals(admin.getSubscribers().size(),1);
    }

    @Test
    @Order(8)
    @Transactional
    public void unsubscribe() {
        findTesterAndAdmin();
        userService.unsubscribe(tester,admin.getId());
        assertEquals(tester.getSubscriptions().size(),0);
        assertEquals(admin.getSubscribers().size(),0);
    }

    private void findTesterAndAdmin(){
        tester = userRepos.findByUsername("tester");
        admin = userRepos.findByUsername("admin");
    }

}
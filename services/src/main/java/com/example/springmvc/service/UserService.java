package com.example.springmvc.service;

import com.example.springmvc.dommain.Gallery;
import com.example.springmvc.dommain.enums.Role;
import com.example.springmvc.dommain.User;
import com.example.springmvc.dommain.enums.Type;
import com.example.springmvc.repos.UserRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервіс користувачів
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepos userRepos;
    private final MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Загрузка користувача
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepos.findByUsername(username);
        ;
        if (user == null)
            throw new BadCredentialsException("Невірні дані користувача перевірте поля логіну та паролю");
        if (user.getActivationCode() != null)
            throw new BadCredentialsException("Потрібно пройти перевірку електронної почти");
        return user;
    }

    /**
     * Створення користувача
     *
     * @param user
     * @return
     */
    public boolean addUser(User user) {
        User userFromDb = userRepos.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepos.save(user);
        mailService.sendActivateCode(user);
        return true;
    }

    /**
     * Збереження користувачів
     *
     * @param user
     * @param form
     */
    public void updateUserAuthority(User user,
                                    Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepos.save(user);
    }

    /**
     * Оновлення профілю користувача
     *
     * @param user
     * @param username
     * @param password
     * @param password2
     * @param email
     */
    public void updateProfile(User user,
                              String username,
                              String password,
                              String password2,
                              String email) {
        String userEmail = user.getEmail();
        boolean isEmailChange = (!StringUtils.isEmpty(email) && !email.equals(userEmail));
        boolean isPasswordWantChange = (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(password2) && password.equals(password2));
        boolean isUsernameChange = (!StringUtils.isEmpty(username) && !user.getUsername().equals(username));
        if (isPasswordWantChange) {
            String pass = passwordEncoder.encode(password);
            if (!user.getPassword().equals(pass)) {
                user.setPassword(pass);
            }
        }
        if (isEmailChange) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
            mailService.sendActivateCode(user);
        }
        if (isUsernameChange) {
            user.setUsername(username);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        userRepos.save(user);
    }

    /**
     * Активація акаунту
     *
     * @param code
     * @return
     */
    public boolean activateUser(String code) {
        User user = userRepos.findByActivationCode(code);
        if (user == null)
            return false;
        user.setActivationCode(null);
        userRepos.save(user);
        return true;
    }

    /**
     * Видаленя користувача
     *
     * @param id
     * @return
     */
    public boolean removeUser(String id) {
        userRepos.deleteById(Long.parseLong(id));
        return true;
    }

    public void subscribe(User currentUser, long id) {
        User account = userRepos.findById(id);
        if (!account.equals(currentUser)){
            Set<User> subscribers = account.getSubscribers();
            if (!subscribers.contains(currentUser)){
                subscribers.add(currentUser);
            }
            userRepos.save(account);
        }
    }

    public void unsubscribe(User currentUser, long id) {
        User account = userRepos.findById(id);
        account.getSubscribers().remove(currentUser);
    }

    public void testCreateGallery(User user) {
        Set<Gallery> galleries =user.getGalleries();
        Gallery gallery= new Gallery();
        gallery.setName("test");
        gallery.setOwner(user);
        gallery.setImages(new ArrayList<>());
        gallery.setType(Type.CUSTOM);
        galleries.add(gallery);
        user.setGalleries(galleries);
        userRepos.save(user);
    }
}

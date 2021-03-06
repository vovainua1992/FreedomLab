package com.freedom.services.service;

import com.freedom.services.dommain.User;
import com.freedom.services.dommain.enums.Role;
import com.freedom.services.repos.UserRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepos userRepos;
    private final MailService mailService;
    private final PublicationService publicationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final AvatarService avatarService;

    /**
     * Load UserDetails
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepos.findByUsername(username);
        if (user == null)
            throw new BadCredentialsException("?????????????? ???????? ?????????????????????? ?????????????????? ???????? ???????????? ???? ????????????");
        if (user.getActivationCode() != null)
            throw new BadCredentialsException("???????????????? ???????????? ?????????????????? ?????????????????????? ??????????");
        return user;
    }

    /**
     * Adds the user to the database and send the activation code by mail
     *
     * @param user new user
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
     * Update user roles
     *
     * @param user user to update roles
     * @param form form of roles
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
     * Update user profile
     *
     * @param user      current user
     * @param username  new username
     * @param password  new password
     * @param password2 confirmation of a new password
     * @param email     new email
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
     * Activate account for this activation code
     *
     * @param code activation code
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
     * Remove a user by id
     *
     * @param id user id for remove
     * @return
     */
    public boolean removeUser(long id) {
        publicationService.removeAuthor(id);
        userRepos.deleteById(id);
        return true;
    }

    public void subscribe(User currentUser, long id) {
        User account = userRepos.findById(id);
        if (!account.equals(currentUser)) {
            Set<User> subscribers = account.getSubscribers();
            Set<User> subscriptions = currentUser.getSubscriptions();
            if (!subscribers.contains(currentUser)) {
                subscribers.add(currentUser);
                subscriptions.add(account);
                userRepos.save(account);
                userRepos.save(currentUser);
            }
        }
    }

    public void unsubscribe(User currentUser, long id) {
        User account = userRepos.findById(id);
        account.getSubscribers().remove(currentUser);
    }

    public void updateAvatar(User user, MultipartFile file,double scalar, int posX, int posY, int size) throws IOException {
        if (size==0){
            size = 100;
        }
        if (file != null)
            user.setAvatar(avatarService.createAvatar(file, scalar, posX, posY, size));
        else
            user.setAvatar(avatarService.updateAvatar(user.getAvatar(), scalar, posX, posY, size));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userRepos.save(user);
    }
    public Page<User> getMySubscribers(Pageable pageable, User currentUser) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
        List<User> subscribers = userRepos.findByUsername(currentUser.getUsername()).getSubscribers().stream().toList();
        if (subscribers.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, subscribers.size());
            list = subscribers.subList(startItem, toIndex);
        }

        Page<User> subscribersPage= new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), subscribers.size());
        return subscribersPage;
    }

    public Page<User> getMySubscriptions(Pageable pageable, User currentUser) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
        List<User> subscriptions = userRepos.findByUsername(currentUser.getUsername()).getSubscriptions().stream().toList();
        if (subscriptions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, subscriptions.size());
            list = subscriptions.subList(startItem, toIndex);
        }

        Page<User> subscribersPage= new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), subscriptions.size());
        return subscribersPage;
    }
}

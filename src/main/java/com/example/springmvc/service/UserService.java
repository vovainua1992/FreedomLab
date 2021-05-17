package com.example.springmvc.service;

import com.example.springmvc.dommain.Role;
import com.example.springmvc.dommain.User;
import com.example.springmvc.repos.UserRepos;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepos userRepos;
    private final MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${my.server.url}")
    private String url;

    public UserService(UserRepos userRepos, MailService mailService) {
        this.userRepos = userRepos;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepos.findByUsername(username);;

        if (user==null){
            throw new BadCredentialsException("Невірні дані користувача перевірте поля логіну та паролю");
        }
        if(user.getActivationCode()!=null)
            throw new BadCredentialsException("Потрібно пройти перевірку електронної почти");

        return user;
    }

    public boolean addUser(User user){
        User userFromDb =  userRepos.findByUsername(user.getUsername());
        if(userFromDb!=null){
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepos.save(user);

        sendActivateCode(user);
        return true;
    }

    public List<User> findAll() {
        return userRepos.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key:form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }


        userRepos.save(user);
    }

    public void updateProfile(User user, String username,
                              String password,String password2, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChange= (!StringUtils.isEmpty(email)&&!email.equals(userEmail));
        boolean isPasswordWantChange = (!StringUtils.isEmpty(password)&&!StringUtils.isEmpty(password2)&&password.equals(password2));
        boolean isUsernameChange = (!StringUtils.isEmpty(username)&&!user.getUsername().equals(username));
        if (isPasswordWantChange){
            String pass = passwordEncoder.encode(password);
            if (!user.getPassword().equals(pass)){
                user.setPassword(pass);
            }
        }

        if (isEmailChange){
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
            sendActivateCode(user);
        }

        if (isUsernameChange){
            user.setUsername(username);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        userRepos.save(user);
    }

    public boolean activateUser(String code) {
        User user =userRepos.findByActivationCode(code);
        if (user ==null)
            return false;
        user.setActivationCode(null);
        userRepos.save(user);
        return true;
    }

    public boolean removeUser(String id){
        userRepos.deleteById(Long.parseLong(id));
        return true;
    }

    private String buildActivateLink(User user) {
        String link = String.format("%s/activate/%s",url,user.getActivationCode());
        return link;
    }

    private void sendActivateCode(User user) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",user.getUsername());
        map.put("link",buildActivateLink(user));
        if(!StringUtils.isEmpty(user.getEmail())){
            try {
                mailService.sendMessageUsingFreemarkerTemplate(user.getEmail()
                        ,"Активація акаунту FreedomLab"
                        ,map);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }


}

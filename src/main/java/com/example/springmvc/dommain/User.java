package com.example.springmvc.dommain;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

/**
 * Entity for users
 */
@Entity
@Table(name ="usr")
@Data
@ToString(includeFieldNames = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @NotBlank(message = "Ваш логін не може бути пустим")
    private String username;
    @NotBlank(message = "Пароль не може бути порожнім")
    private String password;
    private  boolean active;
    @Column(columnDefinition = "varchar(255) default 'static/icons/image.svg'")
    private String avatare;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns =@JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Email(message = "Не коректний емейл")
    @NotBlank(message = "Email не може бути порожнім")
    private String email;
    private String activationCode;

    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

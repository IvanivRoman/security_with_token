package com.ivaniv.barber.project.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "barber")
public class Barber implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Введіть ім'я")
    private String name;
    @Email
    @NotBlank(message = "Введіть коректну електронну скриньку")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

    private String aboutMe;
    private String photos;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="barber")
    private List<Records> records;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "barber")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
        return "/barber-photos/" + id + "/" + photos;
    }
}

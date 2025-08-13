package com.dohimenezg.auth.model;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "Email not be blank")
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password not be blank")
    @Column(name = "user_password", nullable = false)
    private String password;

    @NotBlank(message = "First name not be blank")
    @Column(name = "user_first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name not be blank")
    @Column(name = "user_last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Address not be blank")
    @Column(name = "user_address", nullable = false)
    private String address;

    @Column(name = "user_bio", nullable = true)
    private String bio;

    @Column(name = "user_picture_id", nullable = true)
    private Long profilePictureId; 

    // @Column(name = "user_enabled", nullable = false)
    // private boolean enabled = true; // El valor por defecto en Java debe coincidir con el de la BD
    @Column(name = "user_enabled", nullable = false)
    private boolean enabled;

    @ManyToMany(
        fetch = FetchType.EAGER, 
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
        name = "user_has_role",
        joinColumns = @JoinColumn(name = "user_role_user_id", referencedColumnName = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "user_role_role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.enabled;
    }
}
package tn.esprit.pidevarctic.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String email;
    int number;
    String password;
    boolean firstAuth = true;
    int code;
    String enableToken ;
    @OneToMany(mappedBy = "user")
    Set<Absence> absences;
    @ManyToMany(fetch = FetchType.EAGER)
    Set <Role> roles;
    State state;
    boolean locked = false;
    LocalDateTime lockoutExpiration;
    String lockedReason;
    boolean enabled = false;
    @OneToMany(mappedBy = "pId.student")
    Set<Profile> profiles;
    @Enumerated(EnumType.STRING)
    Speciality speciality;
    @Enumerated(EnumType.STRING)
    Badge badges;
    @OneToMany(mappedBy = "teacher")
    Set<Classroom> classrooms;
    @ManyToMany
    Set<Event> events;
    @OneToMany(mappedBy = "student")
    Set<Post> posts;
    @OneToMany
    Set<Comment> comments;
    @OneToMany(mappedBy = "student")
    Set<Complaint> complaints;
    @OneToMany(mappedBy = "student")
    Set<Demand> demands;
    @ManyToMany(mappedBy = "students")
    Set<RessourceSpace> ressourceSpaces;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

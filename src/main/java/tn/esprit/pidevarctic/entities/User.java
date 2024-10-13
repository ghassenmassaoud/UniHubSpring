package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import java.util.concurrent.Flow;

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
   // @OneToMany
    //@JsonManagedReference
    String enableToken ;
    @OneToMany

    Set<Absence> absences;
    @ManyToMany(fetch = FetchType.EAGER)
    Set <Role> roles;
    State state;
    boolean locked = false;
    LocalDateTime lockoutExpiration;
    String lockedReason;
    boolean enabled = true;
    @OneToMany(mappedBy = "pId.student")
    @JsonIgnore
    Set<Profile> profiles;
    @Enumerated(EnumType.STRING)
    Speciality speciality;

    @Enumerated(EnumType.STRING)
    Badge badges;
    @OneToMany(mappedBy = "teacher")
   // @JsonManagedReference

    Set<Classroom> classrooms;
    @ManyToMany
    //@JsonIgnore
    Set<Event> events;
    @OneToMany(mappedBy = "student")
    //@JsonManagedReference
    //@JsonIgnore
    Set<Post> posts;
    @OneToMany
    //@JsonIgnore
   // @JsonManagedReference
    Set<Comment> comments;
    @OneToMany(mappedBy = "student")
    Set<Complaint> complaints;
    @OneToMany(mappedBy = "student")
    Set<Demand> demands;
    @OneToMany(mappedBy = "students")
    @JsonIgnore
    Set<Ressource> ressources;
    @OneToMany(mappedBy = "user")
    //@JsonManagedReference
    //@JsonIgnore
    Set<PostLike> postLikes = new HashSet<>();
//    @OneToOne
//    private Post favoritePost;
    //@JsonBackReference
    @ManyToMany(mappedBy="students")
    //@JsonBackReference

    Set<Classroom> classroomStudent;
    @OneToMany(mappedBy = "student")
   // @JsonIgnore
    Set<ReplyTask> replyTasks;

    @OneToMany
    Set<Post> favoritePosts = new HashSet<>();

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

package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String email;
    int number;
    String password;
    boolean firstAuth;
    int code;
    @OneToMany
    //@JsonManagedReference
    Set<Absence> absences;
    @ManyToMany
    Set <Role> roles;
    State state;
    @OneToMany(mappedBy = "pId.student")
    Set<Profile> profiles;
    @Enumerated(EnumType.STRING)
    Speciality speciality;

    @OneToMany(mappedBy = "teacher")
   // @JsonManagedReference
    Set<Classroom> classrooms;
    @ManyToMany
    Set<Event> events;
    @OneToMany(mappedBy = "student")
    //@JsonManagedReference
    Set<Post> posts;
    @OneToMany
   // @JsonManagedReference
    Set<Comment> comments;
    @OneToMany(mappedBy = "student")
    Set<Complaint> complaints;
    @OneToMany(mappedBy = "student")
    Set<Demand> demands;
    @ManyToMany(mappedBy = "students")
    Set<RessourceSpace> ressourceSpaces;
    @OneToMany(mappedBy = "user")
    //@JsonManagedReference

    Set<PostLike> postLikes = new HashSet<>();
    @OneToOne
    private Post favoritePost;
    @JsonBackReference
    @ManyToMany(mappedBy="students")
    //@JsonBackReference
    Set<Classroom> classroomStudent;
    @OneToMany(mappedBy = "student")
    Set<ReplyTask> replyTasks;
}

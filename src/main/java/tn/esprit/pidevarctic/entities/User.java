package tn.esprit.pidevarctic.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.Flow;

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
    @OneToMany(mappedBy = "user")
    Set<Absence> absences;
    @ManyToMany
    Set <Role> roles;
    State state;
   @OneToMany(mappedBy = "pId.student")
   Set<Profile> profiles;
    @Enumerated(EnumType.STRING)
    Speciality speciality;
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



}

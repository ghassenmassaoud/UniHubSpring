package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Classroom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClassroom;
    String classroomName;
    @ManyToOne
    @JsonIgnore
    User teacher;
    @OneToMany(mappedBy = "classroom")
    Set<Absence> absences;
    @OneToMany(mappedBy = "classroom")
    Set<Task> tasks;


}

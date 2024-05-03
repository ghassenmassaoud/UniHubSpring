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
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idLesson;
    String LessonName;
    @Enumerated(EnumType.STRING)
    Visibility visibility;
    @ManyToOne
    @JsonIgnore
    Classroom classroom;
//    @OneToMany(mappedBy = "lesson")
//    Set<Task> tasks;
    @OneToMany(mappedBy = "lesson")
    Set<Document>documents ;

}

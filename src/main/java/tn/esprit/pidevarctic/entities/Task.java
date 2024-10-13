package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTask;
    String TaskDescription;
    LocalDateTime deadline;
    float mark;
    @Enumerated(EnumType.STRING)
    TaskState taskState;
    @ManyToOne
    @JsonIgnore
    Classroom classroom;
    @OneToMany(mappedBy = "task")
    @JsonIgnore
    Set<Document> documents;
    @OneToMany(mappedBy = "task")
    @JsonIgnore
    Set<ReplyTask> replyTasks;

}
